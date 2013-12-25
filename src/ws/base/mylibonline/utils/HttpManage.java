package ws.base.mylibonline.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.util.Log;

public class HttpManage {
	private String strUrl;
	private ArrayList<String> listData = new ArrayList<String>();
	
	public HttpManage(String url){
		this.strUrl = url;
	}
	
	private String HttpVerifUrl(String url){
		if (!url.startsWith("http://"))
			url = "http://" + url;
		return url;
	}
	
	public ArrayList<String> HttpLireUrl() throws Exception{
		URL url = new URL(strUrl);
		URLConnection connection = url.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String ligne;
        int Cpt=0;
        while ((ligne = reader.readLine()) != null)
        {
        	if (ligne.length() > 0){
        		listData.add(ligne);
        		//Log.i("Sup","Data lu = " + ligne);
        	}
        }
        //Log.i("Sup", "NbrLigne = " + listData.size());
        reader.close();
        return listData;
	}
	

}
