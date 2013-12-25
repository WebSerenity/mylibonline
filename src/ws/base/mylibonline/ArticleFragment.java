package ws.base.mylibonline;


import java.util.ArrayList;

import ws.base.modeles.Book;
import ws.base.modeles.Cat;
import ws.base.mylibonline.utils.DatabaseBook;
import ws.base.mylibonline.utils.DatabaseCat;
import ws.base.mylibonline.utils.GestionCat;
import ws.base.mylibonline.utils.Params;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;

/**
 * Fragment that displays a news article.
 */
public class ArticleFragment extends Fragment {
	private String TAG_LOCAL = "ArticleFragment - ";
	private boolean fgDebugLocal = true;
	
	private View rootView;
	private TextView tvTitre;
	private TextView tvSousTitre;
	private TextView tvAuteur;
	private TextView tvFamille;
	private TextView tvISBN;
	private TextView tvResume;
	private TextView tvComment;
	
	private ImageView ivLu;
	private ImageView ivAchat;
	private ImageView ivPret;
	private RatingBar rbNote;
	
	private int id = -1;
	private String titre = "";
	private String sousTitre = "";
	private String auteur = "";
	private String ISBN = "";
	private boolean fgLu = false;
	private boolean fgAchat = false;
	private boolean fgPret = false;
	private float ratio = 0;
	private String famille = "";
	private String resume = "";
	private String comment = "";
	private String editeur = "";
	private String date = "";
	

    public ArticleFragment() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	rootView = inflater.inflate(R.layout.fiche,container, false);
    	if (rootView != null){
    		afficheData();
    	}
    	return rootView;
    }

    
    public void displayArticle(Book book) {
    	//NewsArticle art = article;
    	//titre = art.getHeadline();
    	book = BaseActivity.databaseBook.getLivreById(book.getId());
    	
    	id = book.getId();
    	titre = book.getTitre();
    	sousTitre = book.getSousTitre();
    	auteur = book.getAuteur();
    	ISBN = book.getISBN();
    	fgLu = book.isFgLu();
    	fgAchat = book.isFgAchat();
    	fgPret = book.isFgPret();
    	ratio = book.getRatio();
    	famille = book.getFamille();
    	resume = book.getResume();
    	comment = book.getComment();
    	editeur = book.getEditeur();
    	date = book.getDate();
    	//book.afficheData();
    	if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "true");};
    	
    	if (rootView != null){
    		afficheData();
    	}
    	
    }
    
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "Resume");};
    }
	
    private void afficheData(){
    	if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "afficheData");};
    	tvTitre = (TextView)rootView.findViewById(R.id.tvTitre);
    	tvSousTitre = (TextView)rootView.findViewById(R.id.tvSousTitre);
    	tvAuteur = (TextView)rootView.findViewById(R.id.tvAuteur);
    	tvFamille = (TextView)rootView.findViewById(R.id.tvFamille);
    	tvISBN = (TextView)rootView.findViewById(R.id.tvISBN);
    	tvResume = (TextView)rootView.findViewById(R.id.tvResume);
    	tvComment = (TextView)rootView.findViewById(R.id.tvComment);
    	
    	tvTitre.setText(titre);
    	tvTitre.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	tvSousTitre.setText(sousTitre);
    	tvAuteur.setText(auteur);
    	
    	if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "famille = " + famille);};
    	
    	if (famille == null || famille.isEmpty()){
    		famille = getResources().getString(R.string.fiche_cat);
    	}
    	
    	tvFamille.setText(famille);
    	tvFamille.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getActivity().getBaseContext(),"famille", Toast.LENGTH_LONG).show();
				GestionCat.showPopup(getActivity(), true, tvFamille, id);
			    
			    
			}
		});
    	
    	if (ISBN == null || ISBN.isEmpty()){
    		ISBN = getResources().getString(R.string.fiche_isbn);
    	}
    	tvISBN.setText(ISBN);
    	
    	tvResume.setText(resume);
    	tvComment.setText(comment);
    	
    	ivLu = (ImageView)rootView.findViewById(R.id.ivLu);
    	if (!fgLu){
    		ivLu.setImageDrawable(getResources().getDrawable(R.drawable.ic_lu_on));
    		fgLu = true;
    	}else{
    		fgLu = false;
    		ivLu.setImageDrawable(getResources().getDrawable(R.drawable.ic_lu));
    	}
    	ivLu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (!fgLu){
		    		ivLu.setImageDrawable(getResources().getDrawable(R.drawable.ic_lu_on));
		    		fgLu = true;
		    	}else{
		    		fgLu = false;
		    		ivLu.setImageDrawable(getResources().getDrawable(R.drawable.ic_lu));
		    	}
				BaseActivity.databaseBook.updateLu(id, fgLu);
			}
		});
    	
    	ivAchat = (ImageView)rootView.findViewById(R.id.ivAchat);
    	if (!fgAchat){
    		ivAchat.setImageDrawable(getResources().getDrawable(R.drawable.ic_acheter_on));
    		fgAchat = true;
    	}else{
    		fgAchat = false;
    		ivAchat.setImageDrawable(getResources().getDrawable(R.drawable.ic_acheter));
    	}
    	ivAchat.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (!fgAchat){
		    		ivAchat.setImageDrawable(getResources().getDrawable(R.drawable.ic_acheter_on));
		    		fgAchat = true;
		    	}else{
		    		fgAchat = false;
		    		ivAchat.setImageDrawable(getResources().getDrawable(R.drawable.ic_acheter));
		    	}
				BaseActivity.databaseBook.updateAchat(id,fgAchat);
				
			}
		});
    	
    	ivPret = (ImageView)rootView.findViewById(R.id.ivPret);
    	if (!fgPret){
    		ivPret.setImageDrawable(getResources().getDrawable(R.drawable.ic_pret_on));
    		fgPret = true;
    	}else{
    		fgPret = false;
    		ivPret.setImageDrawable(getResources().getDrawable(R.drawable.ic_pret));
    	}
    	ivPret.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (!fgPret){
		    		ivPret.setImageDrawable(getResources().getDrawable(R.drawable.ic_pret_on));
		    		fgPret = true;
		    	}else{
		    		fgPret = false;
		    		ivPret.setImageDrawable(getResources().getDrawable(R.drawable.ic_pret));
		    	}
				BaseActivity.databaseBook.updatePret(id, fgPret);
			}
		});
    	
    	rbNote = (RatingBar)rootView.findViewById(R.id.rbNote);
    	rbNote.setRating(ratio);
    	rbNote.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
    		public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
    				BaseActivity.databaseBook.updateRatio(id, rating);
    				//Toast.makeText(getActivity().getBaseContext(), String.valueOf(rating), Toast.LENGTH_LONG).show();
    			}
    		}
    	);
    	
    }
    


}
