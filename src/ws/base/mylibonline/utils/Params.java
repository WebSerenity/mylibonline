package ws.base.mylibonline.utils;

public interface Params {
	public static final String TAG_GEN = "WS";
	public static final boolean TAG_FG_DEBUG = true;
	public static final String VERS = "1.0.0";
	
	public static final String CATEGORIES[] = { "Titres", "Auteurs", "Lus", "A lire", "A acheter","Acheter", "Prêtés"  };
	
	public static final String EXPORT_REP = "/download/";
	//public static final String EXPORT_REP = "";
	
	public static final int DB_DATABASE_VERSION = 1;
	public static final String DB_DATABASE_NAME = "dbBook";
	public static final String DB_EXPORT = "dbBook.db";
	
	//Book
	public static final String DB_TABLE_BOOKS = "tab_book";
	
	public static final String DB_KEY_ID = "id";
	public static final String DB_KEY_TITRE = "titre";
	public static final String DB_KEY_TITRE_SOUS = "titre_sous";
	public static final String DB_KEY_AUTEUR = "auteur";
	public static final String DB_KEY_ISBN = "isbn";
	public static final String DB_KEY_LU = "lu";
	public static final String DB_KEY_ACHAT = "achat";
	public static final String DB_KEY_PRET = "pret";
	public static final String DB_KEY_RATIO = "ratio";
	public static final String DB_KEY_FAMILLE = "famille";
	public static final String DB_KEY_RESUME = "resume";
	public static final String DB_KEY_COMMENT = "comment";
	public static final String DB_KEY_EDITEUR = "editeur";
	public static final String DB_KEY_DATE = "annee";
	public static final String DB_KEY_SERIE = "serie";
	
	public static final int DB_NUM_KEY_ID = 0;
	public static final int DB_NUM_KEY_TITRE = 1;
	public static final int DB_NUM_KEY_TITRE_SOUS = 2;
	public static final int DB_NUM_KEY_AUTEUR = 3;
	public static final int DB_NUM_KEY_ISBN = 4;
	public static final int DB_NUM_KEY_LU = 5;
	public static final int DB_NUM_KEY_ACHAT = 6;
	public static final int DB_NUM_KEY_PRET = 7;
	public static final int DB_NUM_KEY_RATIO = 8;
	public static final int DB_NUM_KEY_FAMILLE = 9;
	public static final int DB_NUM_KEY_RESUME = 10;
	public static final int DB_NUM_KEY_COMMENT = 11;
	public static final int DB_NUM_KEY_EDITEUR = 12;
	public static final int DB_NUM_KEY_DATE = 13;
	public static final int DB_NUM_KEY_SERIE = 14;
	
	
	public static final String CREATE_BDD_BOOK = "CREATE TABLE " + Params.DB_TABLE_BOOKS + " ("
			+ Params.DB_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ Params.DB_KEY_TITRE + " TEXT," 
			+ Params.DB_KEY_TITRE_SOUS + " TEXT," 
			+ Params.DB_KEY_AUTEUR + " TEXT,"
            + Params.DB_KEY_ISBN + " TEXT,"
            + Params.DB_KEY_LU + " TEXT,"
            + Params.DB_KEY_ACHAT + " TEXT,"
            + Params.DB_KEY_PRET + " TEXT,"
            + Params.DB_KEY_RATIO + " TEXT,"
            + Params.DB_KEY_FAMILLE + " TEXT,"
            + Params.DB_KEY_RESUME + " TEXT,"
            + Params.DB_KEY_COMMENT + " TEXT,"
            + Params.DB_KEY_EDITEUR + " TEXT,"
            + Params.DB_KEY_DATE + " TEXT,"
            + Params.DB_KEY_SERIE + " TEXT"
			+ ");";
	
	//Categorie
	public static final String DB_TABLE_CATS = "tab_cat";
	
	public static final String DB_KEY_CAT_ID = "id";
	public static final String DB_KEY_CAT_CAT = "cat";
	
	public static final int DB_NUM_KEY_CAT_ID = 0;
	public static final int DB_NUM_KEY_CAT_CAT = 1;
	
	public static final String CREATE_BDD_CAT = "CREATE TABLE IF NOT EXISTS " + Params.DB_TABLE_CATS + "("
            + Params.DB_KEY_CAT_ID + " INTEGER PRIMARY KEY," 
			+ Params.DB_KEY_CAT_CAT + " TEXT"
			+ ")";
		
	

}
