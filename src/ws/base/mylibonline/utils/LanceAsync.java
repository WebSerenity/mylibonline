package ws.base.mylibonline.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

import ws.base.modeles.Book;
import ws.base.mylibonline.ArticleActivity;
import ws.base.mylibonline.AsyncResponse;
import ws.base.mylibonline.BaseActivity;
import ws.base.mylibonline.R;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LanceAsync extends AsyncTask<Void, Integer, Void>{
	private String TAG_LOCAL = "LanceAsync - ";
	private boolean fgDebugLocal = true;
	
	private String strResult = "";
	private String strUrl;
	private MenuItem menuItem;
	private String ISBN = "";
	private Book book = null;
	public AsyncResponse delegate=null;
	
	public LanceAsync(MenuItem menuItem, String strUrl){
		this.menuItem = menuItem;
		this.strUrl = strUrl;
		
	}
	
	public LanceAsync(MenuItem menuItem, String strUrl, String isbn){
		this.menuItem = menuItem;
		this.strUrl = strUrl;
		this.ISBN = isbn;
		
	}
	
	public LanceAsync(String strUrl){
		this.strUrl = strUrl;
	}
	
	public void setISBN(String isbn){
		this.ISBN = isbn;
	}

	@Override
	protected Void doInBackground(Void... params) {
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "doInBackground");};
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strUrl = " + strUrl);};
		try {
			URL url = new URL(strUrl);
			HttpURLConnection urlConnection;
			urlConnection = (HttpURLConnection) url.openConnection();
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		    String line = "";
		    while ((line = reader.readLine()) != null) {
		    	//if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + line);};
		    	strResult = strResult + line;
		    }
		    
		    DecodeXmpLivre decodeXmpLivre = new DecodeXmpLivre(strResult, ISBN);
		    book = decodeXmpLivre.decode();
		    //BaseActivity.databaseBook.insertLivre(book);
		    //BaseActivity.listBook.clear();
		    //BaseActivity.listBook = BaseActivity.databaseBook.getListBook();
		    
		} catch (IOException e) {
			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "doInBackground erreur = " + e.getMessage());};
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "onPreExecute");};
		menuItem.setActionView(R.layout.lanceur_progressbar);
	    menuItem.expandActionView();
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "onPostExecute");};
		//menuItem.collapseActionView();
		//menuItem.setActionView(null);	
		delegate.processFinish(book);
		//book.afficheData();
		//BaseActivity.mArticleFragment.displayArticle(book);

	}
}
