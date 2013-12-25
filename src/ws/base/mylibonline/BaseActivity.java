package ws.base.mylibonline;

import java.util.ArrayList;
import java.util.Locale;

import ws.base.modeles.Book;
import ws.base.modeles.Cat;
import ws.base.mylibonline.utils.ConvertISBN;
import ws.base.mylibonline.utils.DatabaseBook;
import ws.base.mylibonline.utils.DatabaseCat;
import ws.base.mylibonline.utils.SqLiteBook;
import ws.base.mylibonline.utils.Params;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

public class BaseActivity extends FragmentActivity{
	private String TAG_LOCAL = "BaseActivity - ";
	private boolean fgDebugLocal = true;
	
	public static DatabaseBook databaseBook;
	public static ArrayList<Book> listBook = new ArrayList<Book>();
	public static int nbrBook = 0;
	
	public static DatabaseCat databaseCat;
	public static ArrayList<Cat> listCat = new ArrayList<Cat>();
	public static int nbrCat = 0;
	
	protected static int ScreenWidth;
	protected static int ScreenHeight;
	public static int ScreenWidthPopup;
	public static int ScreenHeightPopup;
	
	protected Context context;
	
	//gestion des fragments
	public static boolean mIsDualPane = false;
    public static BookLinesFragment mHeadlinesFragment;
    public static ArticleFragment mArticleFragment;
    
    public static String strPackage = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		context = getApplicationContext();
		
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "Start BaseActivity");};
		
		strPackage = getApplicationContext().getPackageName();
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		ScreenWidth = size.x;
		ScreenHeight = size.y;
		ScreenWidthPopup = (int) (ScreenWidth*0.8);
		ScreenHeightPopup = (int) (ScreenHeight*0.8);
		
		//gestion de la base de données
		databaseBook = new DatabaseBook(getApplicationContext());
		databaseBook.openWrite();
		//databaseBook.resetDB();
		//int nbrColonne = databaseBook.getNbrColonne();
		//if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "nbrColonne = " + nbrColonne);};
        
		Book livre = new Book("123456789", "Programmez pour Android");
		livre.setTitre("titre1");
		livre.setSousTitre("SousTitre1");
		livre.setAuteur("auteur1");
		livre.setComment("comment1");
		livre.setISBN("123456789");
        //databaseBook.insertLivre(livre);
		
        
		listBook = databaseBook.getListBookTitre();
		nbrBook = listBook.size();
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "nbrBook = " + nbrBook);};
		
        //gestion des familles
		databaseCat = new DatabaseCat(getApplicationContext());
		databaseCat.openWrite();
		databaseCat.getNbrColonne();
		listCat = databaseCat.getListCat();
		nbrCat = listCat.size();
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "nbrBook = " + nbrCat);};
		
		String strISBN = ConvertISBN.ISBN13to10("9782811202415");
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.e(Params.TAG_GEN, TAG_LOCAL + "strISBN = " + strISBN);};
		
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//databaseBook.close();
	}
	


}
