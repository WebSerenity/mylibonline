package ws.base.mylibonline;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.w3c.dom.ls.LSInput;

import ws.base.modeles.Book;
import ws.base.modeles.Cat;
import ws.base.mylibonline.BookLinesFragment.OnHeadlineSelectedListenerLong;
import ws.base.mylibonline.utils.ConvertISBN;
import ws.base.mylibonline.utils.DetectInternet;
import ws.base.mylibonline.utils.GestionCat;
import ws.base.mylibonline.utils.LanceAsync;
import ws.base.mylibonline.utils.Params;
import ws.base.mylibonline.utils.Tools;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends BaseActivity implements BookLinesFragment.OnHeadlineSelectedListener,CompatActionBarNavListener,OnClickListener, AsyncResponse, OnHeadlineSelectedListenerLong  {
	private String TAG_LOCAL = "Home - ";
	private boolean fgDebugLocal = true;
    
    // The news category and book index currently being displayed
    private int catIndex = 0;
    private int bookIndex = 0;
    
    
    private int CAMERA_REQUEST_CODE = 1000;
    private MenuItem menuItem;
    private String strISBN = "";
    private String isbn13 = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        // find our fragments
        mHeadlinesFragment = (BookLinesFragment) getSupportFragmentManager().findFragmentById(R.id.headlines);
        mArticleFragment = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.article);

        // Determine whether we are in single-pane or dual-pane mode by testing the visibility
        // of the article view.
        View articleView = findViewById(R.id.article);
       
        
        mIsDualPane = articleView != null && articleView.getVisibility() == View.VISIBLE;

        
        // Register ourselves as the listener for the headlines fragment events.
        mHeadlinesFragment.setOnHeadlineSelectedListener(this);
        mHeadlinesFragment.setOnHeadlineSelectedListenerLong(this);
        //mHeadlinesFragment.setOnHeadlineSelectedListenerLong(this);

        // Set up the Action Bar (or not, if one is not available)
        int catIndex = savedInstanceState == null ? 0 : savedInstanceState.getInt("catIndex", 0);
        setUpActionBar(mIsDualPane, catIndex);

        // Set up headlines fragment
        mHeadlinesFragment.setSelectable(mIsDualPane);
        if (nbrBook == 0 && mIsDualPane){
        	articleView.setVisibility(View.INVISIBLE);
        }
        restoreSelection(savedInstanceState);
        
    }

    /** Restore category/article selection from saved state. */
    void restoreSelection(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            setNewsCategory(savedInstanceState.getInt("catIndex", 0));
            if (mIsDualPane) {
                int bookIndex = savedInstanceState.getInt("bookIndex", 0);
                if (nbrBook > 0){
                	mHeadlinesFragment.setSelection(bookIndex);
                	onHeadlineSelected(bookIndex);
                }
            }
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        restoreSelection(savedInstanceState);
    }

    /** Sets up Action Bar (if present).
     *
     * @param showTabs whether to show tabs (if false, will show list).
     * @param selTab the selected tab or list item.
     */
    public void setUpActionBar(boolean showTabs, int selTab) {
        if (Build.VERSION.SDK_INT < 11) {
            // No action bar for you!
            // But do not despair. In this case the layout includes a bar across the
            // top that looks and feels like an action bar, but is made up of regular views.
            return;
        }

        android.app.ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        // Set up a CompatActionBarNavHandler to deliver us the Action Bar nagivation events
        CompatActionBarNavHandler handler = new CompatActionBarNavHandler(this);
        if (showTabs) {
            actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);
            for (int i = 0; i < Params.CATEGORIES.length; i++) {
                actionBar.addTab(actionBar.newTab().setText(Params.CATEGORIES[i]).setTabListener(handler));
            }
            actionBar.setSelectedNavigationItem(selTab);
        }
        else {
            actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_LIST);
            SpinnerAdapter adap = new ArrayAdapter<String>(this, R.layout.actionbar_list_item,Params.CATEGORIES);
            actionBar.setListNavigationCallbacks(adap, handler);
        }

        actionBar.setDisplayUseLogoEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        setNewsCategory(0);
    }

    /** Sets the displayed news category.
     *
     * This causes the headlines fragment to be repopulated with the appropriate headlines.
     */
    void setNewsCategory(int categoryIndex) {
        catIndex = categoryIndex;
        mHeadlinesFragment.loadCategory(categoryIndex);
        if (mIsDualPane) {
        	if (nbrBook > 0){
        		mArticleFragment.displayArticle(listBook.get(0));
        	}
        }

    }

    /** Called when a headline is selected.
     *
     * This is called by the HeadlinesFragment (via its listener interface) to notify us that a
     * headline was selected in the Action Bar. The way we react depends on whether we are in
     * single or dual-pane mode. In single-pane mode, we launch a new activity to display the
     * selected article; in dual-pane mode we simply display it on the article fragment.
     *
     * @param index the index of the selected headline.
     */
    @Override
    public void onHeadlineSelected(int index) {
    	bookIndex = index;
        if (mIsDualPane) {
            // display it on the article fragment
        	if (nbrBook > 0){
        		mArticleFragment.displayArticle(listBook.get(index));
        	}
        }
        else {
            // use separate activity
            Intent i = new Intent(this, ArticleActivity.class);
            i.putExtra("bookIndex", index);
            startActivity(i);
        }
    }
    
    @Override
	public void onHeadlineSelectedLong(int index) {
    	final Book book = listBook.get(index);
		 
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle(getResources().getString(R.string.book_sup));
    	String msg = getResources().getString(R.string.book_sup_msg) + " " + book.getTitre();
    	builder.setMessage(msg);
    	builder.setNegativeButton(getResources().getString(R.string.gen_cancel), new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    		}
    	});
    	builder.setPositiveButton(getResources().getString(R.string.gen_ok), new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			databaseBook.deleteBook(book);
    			mHeadlinesFragment.getActivity().recreate();
    			Toast.makeText(getApplicationContext(), getResources().getString(R.string.book_sup_fait), Toast.LENGTH_SHORT).show();
    		}
    	});
    	AlertDialog alertDialog = builder.create();
    	alertDialog.show();
	}
      
   

    /** Called when a news category is selected.
     *
     * This is called by our CompatActionBarNavHandler in response to the user selecting a
     * news category in the Action Bar. We react by loading and displaying the headlines for
     * that category.
     *
     * @param catIndex the index of the selected news category.
     */
    @Override
    public void onCategorySelected(int catIndex) {
    	//Toast.makeText(getApplicationContext(), Params.CATEGORIES[catIndex], Toast.LENGTH_SHORT).show();
    	if (Params.CATEGORIES[catIndex] == Params.CATEGORIES[0]){
    		listBook.clear();
    	    listBook = databaseBook.getListBookTitre();
    	}
    	if (Params.CATEGORIES[catIndex] == Params.CATEGORIES[1]){
    		listBook.clear();
    	    listBook = databaseBook.getListBookAuteur();
    	}
    	
    	if (Params.CATEGORIES[catIndex] == Params.CATEGORIES[2]){
    		listBook.clear();
    	    listBook = databaseBook.getListBookLu();
    	}
    	if (Params.CATEGORIES[catIndex] == Params.CATEGORIES[3]){
    		listBook.clear();
    	    listBook = databaseBook.getListBookLuNon();
    	}
    	
    	if (Params.CATEGORIES[catIndex] == Params.CATEGORIES[4]){
    		listBook.clear();
    	    listBook = databaseBook.getListBookAchat();
    	}
    	if (Params.CATEGORIES[catIndex] == Params.CATEGORIES[5]){
    		listBook.clear();
    	    listBook = databaseBook.getListBookAchatNon();
    	}
    	if (Params.CATEGORIES[catIndex] == Params.CATEGORIES[6]){
    		listBook.clear();
    	    listBook = databaseBook.getListBookPret();
    	}
	    
        setNewsCategory(catIndex);
    }

    /** Save instance state. Saves current category/article index. */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("catIndex", catIndex);
        outState.putInt("bookIndex", bookIndex);
        super.onSaveInstanceState(outState);
    }

    /** Called when news category button is clicked.
     *
     * This is the button that we display on UIs that don't have an action bar. This button
     * calls up a list of news categories and switches to the given category.
     */
    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a Category");
        builder.setItems(Params.CATEGORIES, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setNewsCategory(which);
            }
        });
        AlertDialog d = builder.create();
        d.show();
    }
    
    
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuScan:
			Intent intent = new Intent(Home.this, ScanCode.class);
			startActivityForResult(intent, CAMERA_REQUEST_CODE);
			menuItem = item;	//utilise par LanceAsynch
			return true;
		case R.id.menuSearch:
			Toast.makeText(this, getString(R.string.menu_search),Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menuCat:
			GestionCat.showPopup(this, false, null, 0);
			return true;
		case R.id.menuExportDB:
			Tools.exportDB();
			Toast.makeText(this, getString(R.string.export_fin),Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menuImportDB:
			final Context context = getApplicationContext();
			//Toast.makeText(this, getString(R.string.import_deb),Toast.LENGTH_SHORT).show();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getResources().getString(R.string.import_titre));
			String msg = getResources().getString(R.string.import_deb);
			builder.setMessage(msg);
			builder.setNegativeButton(getResources().getString(R.string.gen_cancel), new DialogInterface.OnClickListener() {
				 public void onClick(DialogInterface dialog, int id) {
				 }
			});
			builder.setPositiveButton(getResources().getString(R.string.gen_ok), new DialogInterface.OnClickListener() {
				 public void onClick(DialogInterface dialog, int id) {
					 try {
							String strPackage = getPackageName();
							String pathAppli = getPackageManager().getPackageInfo(getPackageName(), 0).applicationInfo.dataDir;
							if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "pathAppli = " + pathAppli);};
							boolean fgResult = Tools.importDB(strPackage, pathAppli);
							if (!fgResult){
								Toast.makeText(context, getString(R.string.import_error),Toast.LENGTH_SHORT).show();
							}else{
								mHeadlinesFragment.getActivity().recreate();
								Toast.makeText(context, getString(R.string.import_fin),Toast.LENGTH_SHORT).show();
							}
						} catch (NameNotFoundException e) {
							if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "importDB Error : " + e.getMessage());};
							Toast.makeText(context, getString(R.string.import_error),Toast.LENGTH_SHORT).show();
						}
				 }
			});
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
			
			return true;
		case R.id.menuHelp:
			Toast.makeText(this, getString(R.string.menu_help),Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menuAbout:
			Toast.makeText(this, getString(R.string.menu_about),Toast.LENGTH_SHORT).show();
			return true;
		}
		return false;
	}
	
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
    		if (data.hasExtra("ean")) {
    			strISBN = data.getExtras().getString("ean");
    			if (strISBN == "" || strISBN == null || strISBN.length() < 10){
    				return;
    			}
    			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strISBN = " + strISBN);};
    			isbn13 = strISBN;
    			//In DB
				Book book = databaseBook.getLivreByISBN(strISBN);
				if (book != null){
					if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "Trouve = " + book.getTitre());};
					afficheArticle(book);
					return;
				}else{
					if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "Pas trouve en base");};
					
					//String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:9782811204051&key=AIzaSyCj0iZndMfGU5939TpSndhEKS2XQVk24lw";
	    			String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + strISBN + "&key=AIzaSyCj0iZndMfGU5939TpSndhEKS2XQVk24lw";
	    			//String url = "https://www.googleapis.com/books/v1/volumes?q=intitle:Cendrecoeur&key=AIzaSyCj0iZndMfGU5939TpSndhEKS2XQVk24lw";
	    			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "url = " + url);};
	    			DetectInternet detectInternet = new DetectInternet(getApplicationContext());
	    			boolean isInternet = detectInternet.isConnectingToInternet();
	    			if (isInternet){
	    				if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "Connected");};
	    				LanceAsync lanceAsync = new LanceAsync(menuItem, url, strISBN );
	    				lanceAsync.delegate = this;
	    				lanceAsync.execute();
	    				
	    			}else{
	    				AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    				builder.setTitle(getResources().getString(R.string.error_titre));
	    				String msg = getResources().getString(R.string.error_3G);
	    				builder.setMessage(msg);
	    				builder.setPositiveButton(getResources().getString(R.string.gen_ok), new DialogInterface.OnClickListener() {
	    					public void onClick(DialogInterface dialog, int id) {
	    					}
	    				});
	    				AlertDialog alertDialog = builder.create();
	    				alertDialog.show();
	    			}
				}
    			
    		}
    	}
    }
    
    @Override
    public void processFinish(Book book) {
    	if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.e(Params.TAG_GEN, TAG_LOCAL + "processFinish");};
    	if (book != null){
    		databaseBook.insertLivre(book);
		    listBook.clear();
		    listBook = databaseBook.getListBookTitre();
    		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "FIN = " + book.getTitre());};
    		afficheArticle(book);
    	}else{
    		menuItem.collapseActionView();
    		menuItem.setActionView(null);
    		//String isbn13 = strISBN;
    		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "PAS TROUVE");};
    		if (strISBN.length() == 13){
    			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strISBN 13 = " + strISBN);};
    			strISBN = ConvertISBN.ISBN13to10(strISBN);
    			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strISBN 10 = " + strISBN);};
    			Toast.makeText(context, strISBN, Toast.LENGTH_SHORT).show();
    			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "Connected");};
    			String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + strISBN + "&key=AIzaSyCj0iZndMfGU5939TpSndhEKS2XQVk24lw";
    			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "url = " + url);};
    			LanceAsync lanceAsync = new LanceAsync(menuItem, url, strISBN );
				lanceAsync.delegate = this;
				lanceAsync.execute();
				return;
    		}
    		
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getResources().getString(R.string.error_titre));
			String msg = getResources().getString(R.string.error_pas_trouve) + " : " + isbn13;
			builder.setMessage(msg);
			builder.setPositiveButton(getResources().getString(R.string.gen_ok), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
				}
			});
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
    	}
    }
    
    private void afficheArticle(Book book){
    	if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "bookIndex = " + book.getId());};
    	bookIndex = 0;
    	for (int Cpt = 0; Cpt < listBook.size(); Cpt++){
    		String isbn = listBook.get(Cpt).getISBN();
    		if (isbn.equalsIgnoreCase(book.getISBN())){
    			bookIndex = Cpt;
    			break;
    		}
    	}
    	
    	if (mIsDualPane) {
    		if (nbrBook > 0){
    			mArticleFragment.displayArticle(book);
    		}
        }
        else {
            Intent intent = new Intent(this, ArticleActivity.class);
            intent.putExtra("bookIndex", bookIndex);
            startActivity(intent);
        }
    	menuItem.collapseActionView();
		menuItem.setActionView(null);
    }

	
    
}
