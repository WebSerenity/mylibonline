package ws.base.mylibonline.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import ws.base.modeles.Book;
import ws.base.mylibonline.BaseActivity;
import android.util.Log;

public class DecodeXmpLivre {
	private static String TAG_LOCAL = "DecodeXmpLivre - ";
	private static boolean fgDebugLocal = true;
	
	private String GG_KIND = "kind";
	private String strKing;
	private String GG_TOTAL_ITEMS = "totalItems";
	private int totalItems;
	private String GG_ITEMS = "items";
	private JSONArray jsonArrayItems;
	private JSONObject  jsonObjectItem;
	private String GG_ITEM_KIND = "kind";
	private String strItemKind;
	private String GG_ITEM_ID = "id";
	private String strItemId;
	private String GG_ITEM_ETAG = "etag";
	private String strItemEtag;
	private String GG_ITEM_SELF_LINK = "selfLink";
	private String strItemSelfLink;
	private String GG_ITEM_VOLUME_INFO = "volumeInfo";
	private JSONObject  jsonObjectVolumeInfo;
	private String GG_ITEM_VOLUME_INFO_TITRE = "title";
	private String strTitre = "";
	private String GG_ITEM_VOLUME_INFO_TITRE_SUB = "subtitle";
	private String strTitreSub = "";
	private JSONArray jsonArrayAuteurs;
	private String GG_ITEM_VOLUME_INFO_AUTHORS = "authors";
	private String strAuteur = "";
	//private ArrayList<String> listAuteurs = new ArrayList<String>();
	private String GG_ITEM_VOLUME_INFO_EDITEUR = "publisher";
	private String strEditeur = "";
	private String GG_ITEM_VOLUME_INFO_EDITEUR_DATE = "publishedDate";
	private String strEditeurDate = "";
	private String GG_ITEM_VOLUME_INFO_RESUME = "description";
	private String strResume = "";
	
	private Book book = null; 
	private String strResult;
	
	public DecodeXmpLivre(String strResult, String isbn){
		this.strResult = strResult;
		book = new Book(isbn);
	}
	
	public Book decode(){
		try {
			JSONObject jsonObject = new JSONObject(strResult);
			strKing = jsonObject.getString(GG_KIND);
			totalItems = jsonObject.getInt(GG_TOTAL_ITEMS);
			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "totalItems = " + totalItems);};
			if (totalItems == 0){
				return book = null;
			}
			
			jsonArrayItems = jsonObject.getJSONArray(GG_ITEMS);
			jsonObjectItem = jsonArrayItems.getJSONObject(0);
			strItemKind = jsonObjectItem.getString(GG_ITEM_KIND);
			strItemId = jsonObjectItem.getString(GG_ITEM_ID);
			strItemEtag = jsonObjectItem.getString(GG_ITEM_ETAG);
			if (jsonObjectItem.has(GG_ITEM_SELF_LINK)){
				strItemSelfLink = jsonObjectItem.getString(GG_ITEM_SELF_LINK);
			}
			
			jsonObjectVolumeInfo = jsonObjectItem.getJSONObject(GG_ITEM_VOLUME_INFO);
			strTitre = jsonObjectVolumeInfo.getString(GG_ITEM_VOLUME_INFO_TITRE);
			if (jsonObjectVolumeInfo.has(GG_ITEM_VOLUME_INFO_TITRE_SUB)){
				strTitreSub = jsonObjectVolumeInfo.getString(GG_ITEM_VOLUME_INFO_TITRE_SUB);
			}
			
			jsonArrayAuteurs = jsonObjectVolumeInfo.getJSONArray(GG_ITEM_VOLUME_INFO_AUTHORS);
			for (int Cpt = 0; Cpt < jsonArrayAuteurs.length(); Cpt++){
				strAuteur =  strAuteur + jsonArrayAuteurs.getString(Cpt) + ";";
			}
			strAuteur = strAuteur.substring(0, strAuteur.length() - 1);
			if (jsonObjectVolumeInfo.has(GG_ITEM_VOLUME_INFO_EDITEUR)){
				strEditeur = jsonObjectVolumeInfo.getString(GG_ITEM_VOLUME_INFO_EDITEUR);
			}
			if (jsonObjectVolumeInfo.has(GG_ITEM_VOLUME_INFO_EDITEUR_DATE)){
				strEditeurDate = jsonObjectVolumeInfo.getString(GG_ITEM_VOLUME_INFO_EDITEUR_DATE);
				strEditeurDate = Tools.convertDateEnToFr(strEditeurDate);
			}
			if (jsonObjectVolumeInfo.has(GG_ITEM_VOLUME_INFO_RESUME)){
				strResume = jsonObjectVolumeInfo.getString(GG_ITEM_VOLUME_INFO_RESUME);
			}
			
			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strKing = " + strKing);};
			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "totalItems = " + totalItems);};
			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strItemKind = " + strItemKind);};
			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strItemId = " + strItemId);};
			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strItemEtag = " + strItemEtag);};
			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strItemSelfLink = " + strItemSelfLink);};
			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strTitre = " + strTitre);};
			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strTitreSub = " + strTitreSub);};
			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strAuteur = " + strAuteur);};
			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strEditeur = " + strEditeur);};
			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strEditeurDate = " + strEditeurDate);};
			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strResume = " + strResume);};
			
			book.setTitre(strTitre);
			book.setSousTitre(strTitreSub);
			book.setAuteur(strAuteur);
			book.setEditeur(strEditeur);
			book.setDate(strEditeurDate);
			book.setResume(strResume);
			
			
		} catch (JSONException e) {
			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "JSONException = " + e.getMessage());};
		}
		
		return book;
	}

}
