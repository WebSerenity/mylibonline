package ws.base.mylibonline.utils;

import java.util.ArrayList;

import ws.base.modeles.Cat;
import ws.base.mylibonline.BaseActivity;
import ws.base.mylibonline.utils.DatabaseCat;
import ws.base.mylibonline.utils.Params;
import ws.base.mylibonline.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class GestionCat {
	private static String TAG_LOCAL = "GestionCat - ";
	private static boolean fgDebugLocal = true;
	
	
	//private Context context;
	private static PopupWindow popupWindow;
	private static View popupViewCat;
	
    private static ListView lvCat;
    private static ArrayAdapter<String> adapterLvCat;
    private static TextView tvCatTitre;
    private static Activity activityCat;
    private static boolean fgWhere = false;
    private static Cat catSelected;
    
    private Button btCatCancel;
    private Button btCatAdd;
    private Button btCatUpadte;
    
    private LinearLayout layout;
    private TextView tv;
    private LayoutParams params;
    private LinearLayout mainLayout;
    private Button but;
    private boolean click = true;
    private static int idBook;
    
    private static ArrayList<String> listCatTexte = new ArrayList<String>();
    
    
    
    public static void showPopup(final Activity activity, final boolean bWhere, final TextView tv, final int id) {
    	idBook = id;
    	fgWhere = bWhere;
    	activityCat = activity;
    	LinearLayout viewGroup = (LinearLayout) activity.findViewById(R.id.popupCat);
    	LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	popupViewCat = layoutInflater.inflate(R.layout.cat, viewGroup);
    	 
    	//Creating the PopupWindow
    	popupWindow = new PopupWindow(activity);
    	popupWindow.setContentView(popupViewCat);
    	popupWindow.setWidth(BaseActivity.ScreenWidthPopup);
    	popupWindow.setHeight(BaseActivity.ScreenHeightPopup);
    	popupWindow.setFocusable(true);
    	 
    	lvCat = (ListView)popupViewCat.findViewById(R.id.lvCat);
    	tvCatTitre = (TextView)popupViewCat.findViewById(R.id.tvCatTitre);
    	if (!fgWhere){
    		adapterLvCat = new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1, android.R.id.text1, listCatTexte);
    	}else{
    		adapterLvCat = new ArrayAdapter<String>(activity,android.R.layout.simple_selectable_list_item, android.R.id.text1, listCatTexte);
    	}
    	setListCat();
    	
    	popupWindow.showAtLocation(popupViewCat, Gravity.CENTER,0,0);
    	
    	lvCat.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,final int position, long id) {
				catSelected = BaseActivity.listCat.get(position);
				if (!fgWhere){
					afficheCatAddUpdate(true, catSelected);
				}else{
					if (position > -1){
						tv.setText(catSelected.getTexte());
						BaseActivity.databaseBook.updateCat(idBook, catSelected.getTexte());
						popupWindow.dismiss();
					}
				}
			}
		});
    	
    	lvCat.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,final int position, long id) {
				 final Cat cat = BaseActivity.listCat.get(position);
				 
				 AlertDialog.Builder builder = new AlertDialog.Builder(activityCat);
				 builder.setTitle(activityCat.getResources().getString(R.string.cat_sup_titre));
				 String msg = activityCat.getResources().getString(R.string.cat_sup_msg) + " " + cat.getTexte();
				 builder.setMessage(msg);
				 builder.setNegativeButton(activityCat.getResources().getString(R.string.gen_cancel), new DialogInterface.OnClickListener() {
					 public void onClick(DialogInterface dialog, int id) {
					 }
				 });
				 builder.setPositiveButton(activityCat.getResources().getString(R.string.gen_ok), new DialogInterface.OnClickListener() {
					 public void onClick(DialogInterface dialog, int id) {
						 BaseActivity.databaseCat.deleteCat(cat);
						 setListCat();
						 //Toast.makeText(activityCat.getApplicationContext(), strFin, Toast.LENGTH_SHORT).show();
					 }
				 });
				 AlertDialog alertDialog = builder.create();
				 alertDialog.show();
				 
				 return false;
				 
				
			}
		});
	    
	    
	    Button btCatCancel = (Button)popupViewCat.findViewById(R.id.btCatCancel);
	    btCatCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
	    
	    Button btCatAdd = (Button)popupViewCat.findViewById(R.id.btCatAdd);
	    btCatAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(activityCat.getApplicationContext(), "add", Toast.LENGTH_SHORT).show();
				afficheCatAddUpdate(false, null);
			}
		});
    	

    }
    
    private static void setListCat(){
    	for (int Cpt = 0; Cpt < BaseActivity.nbrCat; Cpt++){
 			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "Cpt = " + Cpt + " - Texte = " + BaseActivity.listCat.get(Cpt).getTexte());};
 		}
    	
    	
    	BaseActivity.listCat.clear();
		BaseActivity.listCat = BaseActivity.databaseCat.getListCat();
		ArrayList<String> listCatTexte = new ArrayList<String>();
	    for (int Cpt = 0; Cpt < BaseActivity.listCat.size(); Cpt++){
	    	listCatTexte.add(BaseActivity.listCat.get(Cpt).getTexte());
	    	
	    }
    	BaseActivity.nbrCat = BaseActivity.listCat.size();
 		
		listCatTexte = new ArrayList<String>();
	    for (int Cpt = 0; Cpt < BaseActivity.listCat.size(); Cpt++){
	    	listCatTexte.add(BaseActivity.listCat.get(Cpt).getTexte());
	    	
	    }
		adapterLvCat.clear();
		adapterLvCat.addAll(listCatTexte);
		
	    lvCat.setAdapter(adapterLvCat);
		lvCat.invalidate();
		
		String strTitre = activityCat.getResources().getString(R.string.cat_titre) + " (" + BaseActivity.nbrCat + ")";
	    tvCatTitre.setText(strTitre);
		
		
	}
	
	private static void afficheCatAddUpdate(final boolean fgType, final Cat cat){
		LayoutInflater layoutInflater = (LayoutInflater) activityCat.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout viewGroup = (LinearLayout) activityCat.findViewById(R.id.popupCatUpdate);
		View popupViewCatUpdate = layoutInflater.inflate(R.layout.cat_add_update, viewGroup);
    	 
    	//Creating the PopupWindow
    	final PopupWindow popupWindowCatUpdate = new PopupWindow(popupViewCatUpdate, LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT, true);
    	popupWindowCatUpdate.setContentView(popupViewCatUpdate);
    	popupWindowCatUpdate.setFocusable(true);
    	popupWindowCatUpdate.showAtLocation(popupViewCatUpdate,Gravity.CENTER,0,0);
		
    	
    	TextView tvCatUpdate = (TextView)popupViewCatUpdate.findViewById(R.id.tvCatAddUpdate);
		tvCatUpdate.setText(activityCat.getResources().getString(R.string.cat_update_titre));
		    
		Button btCancel = (Button)popupViewCatUpdate.findViewById(R.id.btCatAddUpdateCancel);
		btCancel.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
				popupWindowCatUpdate.dismiss();
			}
		});
			
		final EditText etCatAddUpdate = (EditText)popupViewCatUpdate.findViewById(R.id.etCatAddUpdate);
		if (fgType){
			etCatAddUpdate.setText(cat.getTexte());
		}
		
		Button btValid = (Button)popupViewCatUpdate.findViewById(R.id.btCatAddUpdateValid);
		btValid.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String strCat = etCatAddUpdate.getText().toString();
				if (fgType){
					cat.setTexte(strCat);
					BaseActivity.databaseCat.updateCat(cat);
					setListCat();
				}else{
					if (!strCat.isEmpty()){
						Cat cat = new Cat("fantasy");
						cat.setTexte(strCat);
						BaseActivity.databaseCat.insertCat(cat);
						setListCat();
					}
				}
				popupWindowCatUpdate.dismiss();
			}
		});
    	
    	
		
	}
   
	public static Cat getCat(){
		if (fgWhere){
			return catSelected;
		}
		return null;
		
	}
    
}
