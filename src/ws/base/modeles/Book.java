package ws.base.modeles;

import ws.base.mylibonline.utils.Params;
import android.util.Log;

public class Book {
	private String TAG_LOCAL = "Book - ";
	private boolean fgDebugLocal = true;
	
	private int id;
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
	public Book(){
		
	}
	
	public Book(String isbn){
		this.ISBN = isbn;
		
	}
	
	public Book(String titre, String auteur){
		this.titre = titre;
		this.auteur = auteur;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public boolean isFgLu() {
		return fgLu;
	}

	public void setFgLu(boolean fgLu) {
		this.fgLu = fgLu;
	}
	
	public boolean isFgAchat() {
		return fgAchat;
	}

	public void setFgAchat(boolean fgAchat) {
		this.fgAchat = fgAchat;
	}

	public boolean isFgPret() {
		return fgPret;
	}

	public void setFgPret(boolean fgPret) {
		this.fgPret = fgPret;
	}

	public float getRatio() {
		return ratio;
		
	}

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public String getFamille() {
		return famille;
	}

	public void setFamille(String famille) {
		this.famille = famille;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getEditeur() {
		return editeur;
	}

	public void setEditeur(String editeur) {
		this.editeur = editeur;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSousTitre() {
		return sousTitre;
	}

	public void setSousTitre(String sousTitre) {
		this.sousTitre = sousTitre;
	}
	
	public void afficheData(){
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "titre = " + titre);};
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "Soustitre = " + sousTitre);};
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "auteur = " + auteur);};
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "isbn = " + ISBN);};
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "fgLu = " + fgLu);};
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "fgAchat = " + fgAchat);};
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "fgPret = " + fgPret);};
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "ratio = " + ratio);};
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "famille = " + famille);};
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "resume = " + resume);};
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "comment = " + comment);};
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "editeur = " + editeur);};
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "date = " + date);};
	
	
	}

}
