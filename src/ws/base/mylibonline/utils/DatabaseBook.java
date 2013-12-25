package ws.base.mylibonline.utils;

import java.util.ArrayList;

import ws.base.modeles.Book;
import ws.base.modeles.Cat;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseBook {
	private String TAG_LOCAL = "DatabaseBook - ";
	private boolean fgDebugLocal = true;
	
	
	private SqLiteBook sqLiteBook;
	private SQLiteDatabase db;
	private ArrayList<Book> listBook = new ArrayList<Book>();
	
	
	public DatabaseBook(Context context){
		sqLiteBook = new SqLiteBook(context);
	}
	
	public void openWrite(){
		db = sqLiteBook.getWritableDatabase();
	}
	
	public void openRead(){
		db = sqLiteBook.getReadableDatabase();
	}
	
	public SQLiteDatabase getdb(){
		return db;
	}
	
	public void close(){
		sqLiteBook.close();
	}
	
	public long insertLivre(Book livre){
		livre.afficheData();
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(Params.DB_KEY_TITRE, livre.getTitre());
		values.put(Params.DB_KEY_TITRE_SOUS, livre.getSousTitre());
		values.put(Params.DB_KEY_AUTEUR, livre.getAuteur());
		values.put(Params.DB_KEY_ISBN, livre.getISBN());
		values.put(Params.DB_KEY_LU, livre.isFgLu());
		values.put(Params.DB_KEY_ACHAT, livre.isFgAchat());
		values.put(Params.DB_KEY_PRET, livre.isFgPret());
		values.put(Params.DB_KEY_RATIO, livre.getRatio());
		values.put(Params.DB_KEY_FAMILLE, livre.getFamille());
		values.put(Params.DB_KEY_RESUME, livre.getResume());
		values.put(Params.DB_KEY_EDITEUR, livre.getEditeur());
		values.put(Params.DB_KEY_DATE, livre.getDate());
		//on insère l'objet dans la BDD via le ContentValues
		return db.insert(Params.DB_TABLE_BOOKS, null, values);
	}
	
	public Book getLivreWithTitre(String titre){
		//Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
		Cursor c = db.query(Params.DB_TABLE_BOOKS, new String[] {Params.DB_KEY_ID, Params.DB_KEY_TITRE, Params.DB_KEY_AUTEUR}, Params.DB_KEY_TITRE + " LIKE \"" + titre +"\"", null, null, null, null);
		c.moveToFirst();
		Book book = cursorToLivre(c);
		c.close();
		return book;
	}
	
	public Book getLivreByISBN(String isbn){
		String query = "SELECT  * FROM " + Params.DB_TABLE_BOOKS + " WHERE " + Params.DB_KEY_ISBN + " = \"" + isbn + "\"";
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "query = " + query);};
		Cursor c = db.rawQuery(query, null);

		c.moveToFirst();
		Book book = cursorToLivre(c);
		c.close();
		return book;
	}
	
	public Book getLivreById(int id){
		String query = "SELECT  * FROM " + Params.DB_TABLE_BOOKS + " WHERE " + Params.DB_KEY_ID + " = " + id;
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "query = " + query);};
		Cursor c = db.rawQuery(query, null);

		c.moveToFirst();
		Book book = cursorToLivre(c);
		c.close();
		return book;
	}
	
	public ArrayList<Book> getListBookTitre(){
        String strQuery = "SELECT  * FROM " + Params.DB_TABLE_BOOKS + " ORDER BY titre ASC";
        Cursor cursor = db.rawQuery(strQuery, null);
        if (cursor.getCount() == 0){
        	return listBook;
        }
        if (cursor.moveToFirst()) {
            do {
                Book book = cursorToLivre(cursor);                
                listBook.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        
        return listBook;
	}
	
	public ArrayList<Book> getListBookAuteur(){
        String strQuery = "SELECT  * FROM " + Params.DB_TABLE_BOOKS + " GROUP BY auteur ORDER BY auteur ASC";
        Cursor cursor = db.rawQuery(strQuery, null);
        if (cursor.getCount() == 0){
        	return listBook;
        }
        if (cursor.moveToFirst()) {
            do {
                Book book = cursorToLivre(cursor);                
                listBook.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listBook;
	}
	
	public ArrayList<Book> getListBookLu(){
        String strQuery = "SELECT  * FROM " + Params.DB_TABLE_BOOKS + " WHERE lu = 1 ORDER BY titre ASC";
        Cursor cursor = db.rawQuery(strQuery, null);
        if (cursor.getCount() == 0){
        	return listBook;
        }
        if (cursor.moveToFirst()) {
            do {
                Book book = cursorToLivre(cursor);                
                listBook.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listBook;
	}
	
	public ArrayList<Book> getListBookLuNon(){
        String strQuery = "SELECT  * FROM " + Params.DB_TABLE_BOOKS + " WHERE lu = 0 ORDER BY titre ASC";
        Cursor cursor = db.rawQuery(strQuery, null);
        if (cursor.getCount() == 0){
        	return listBook;
        }
        if (cursor.moveToFirst()) {
            do {
                Book book = cursorToLivre(cursor);                
                listBook.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listBook;
	}
	
	public ArrayList<Book> getListBookAchat(){
        String strQuery = "SELECT  * FROM " + Params.DB_TABLE_BOOKS + " WHERE achat = 1 ORDER BY titre ASC";
        Cursor cursor = db.rawQuery(strQuery, null);
        if (cursor.getCount() == 0){
        	return listBook;
        }
        if (cursor.moveToFirst()) {
            do {
                Book book = cursorToLivre(cursor);                
                listBook.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listBook;
	}
	
	public ArrayList<Book> getListBookAchatNon(){
        String strQuery = "SELECT  * FROM " + Params.DB_TABLE_BOOKS + " WHERE achat = 0 ORDER BY titre ASC";
        Cursor cursor = db.rawQuery(strQuery, null);
        if (cursor.getCount() == 0){
        	return listBook;
        }
        if (cursor.moveToFirst()) {
            do {
                Book book = cursorToLivre(cursor);                
                listBook.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listBook;
	}
	
	public ArrayList<Book> getListBookPret(){
        String strQuery = "SELECT  * FROM " + Params.DB_TABLE_BOOKS + " WHERE pret = 1 ORDER BY titre ASC";
        Cursor cursor = db.rawQuery(strQuery, null);
        if (cursor.getCount() == 0){
        	return listBook;
        }
        if (cursor.moveToFirst()) {
            do {
                Book book = cursorToLivre(cursor);                
                listBook.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listBook;
	}
	
	private Book cursorToLivre(Cursor c){
		//si aucun élément n'a été retourné dans la requête, on renvoie null
		if (c.getCount() == 0)
			return null;
		
		//On créé un livre
		Book book = new Book();
		//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
		book.setId(c.getInt(Params.DB_NUM_KEY_ID));
		book.setAuteur(c.getString(Params.DB_NUM_KEY_AUTEUR));
		book.setTitre(c.getString(Params.DB_NUM_KEY_TITRE));
		book.setSousTitre(c.getString(Params.DB_NUM_KEY_TITRE_SOUS));
		book.setISBN(c.getString(Params.DB_NUM_KEY_ISBN));
		
		boolean fg = false;
		if (c.getString(Params.DB_NUM_KEY_LU).equalsIgnoreCase("0")){
			fg = true;
		}else{
			fg = false;
		}
		book.setFgLu(fg);
		
		if (c.getString(Params.DB_NUM_KEY_ACHAT).equalsIgnoreCase("0")){
			fg = true;
		}else{
			fg = false;
		}
		book.setFgAchat(fg);
		
		if (c.getString(Params.DB_NUM_KEY_PRET).equalsIgnoreCase("0")){
			fg = true;
		}else{
			fg = false;
		}
		book.setFgPret(fg);
		
		
		if (c.getString(Params.DB_NUM_KEY_RATIO) != null){
			float ratio = Float.parseFloat(c.getString(Params.DB_NUM_KEY_RATIO));
			book.setRatio(ratio);
		}
		book.setFamille(c.getString(Params.DB_NUM_KEY_FAMILLE));
		book.setResume(c.getString(Params.DB_NUM_KEY_RESUME));
		book.setComment(c.getString(Params.DB_NUM_KEY_COMMENT));
		book.setEditeur(c.getString(Params.DB_NUM_KEY_EDITEUR));
		book.setDate(c.getString(Params.DB_NUM_KEY_DATE));
		
		//book.afficheData();
		return book;
	}
	
	public void resetDB(){
		db.execSQL("DROP TABLE " + Params.DB_TABLE_BOOKS + ";");
		db.execSQL("DROP TABLE " + Params.DB_TABLE_CATS + ";");	
	}
	
	public void updateLu(int id, boolean fg){
		String strFg = "0";
		if (fg){
			strFg = "1";
		}
		String strSQL = "UPDATE " + Params.DB_TABLE_BOOKS + " SET " + Params.DB_KEY_LU + " = " + strFg + " WHERE " + Params.DB_KEY_ID + " = "+ id;
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strSQL = " + strSQL);};
		db.execSQL(strSQL);
	}
	
	public void updateAchat(int id, boolean fg){
		String strFg = "0";
		if (fg){
			strFg = "1";
		}
		String strSQL = "UPDATE " + Params.DB_TABLE_BOOKS + " SET " + Params.DB_KEY_ACHAT + " = " + strFg + " WHERE " + Params.DB_KEY_ID + " = "+ id;
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strSQL = " + strSQL);};
		db.execSQL(strSQL);
	}
	
	public void updatePret(int id, boolean fg){
		String strFg = "0";
		if (fg){
			strFg = "1";
		}
		String strSQL = "UPDATE " + Params.DB_TABLE_BOOKS + " SET " + Params.DB_KEY_PRET + " = " + strFg + " WHERE " + Params.DB_KEY_ID + " = "+ id;
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strSQL = " + strSQL);};
		db.execSQL(strSQL);
	}
	
	public void updateRatio(int id, float ratio){
		String strSQL = "UPDATE " + Params.DB_TABLE_BOOKS + " SET " + Params.DB_KEY_RATIO + " = " + String.valueOf(ratio) + " WHERE " + Params.DB_KEY_ID + " = "+ id;
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strSQL = " + strSQL);};
		db.execSQL(strSQL);
	}
	
	public void updateCat(int id, String cat){
		String strSQL = "UPDATE " + Params.DB_TABLE_BOOKS + " SET " + Params.DB_KEY_FAMILLE + " = \"" + cat + "\" WHERE " + Params.DB_KEY_ID + " = "+ id;
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "strSQL = " + strSQL);};
		db.execSQL(strSQL);
	}
	
	
	public int getNbrBooks(){
		String countQuery = "SELECT  * FROM " + Params.DB_TABLE_BOOKS;
        Cursor cursor = db.rawQuery(countQuery, null);
        int nbrBook = cursor.getCount();
        cursor.close();

        return nbrBook;
	}
	
	public int getNbrColonne(){
		Cursor cursor = db.rawQuery("SELECT * FROM " + Params.DB_TABLE_BOOKS + ";", null);
		int nbrRow = 0;
		String[] rows = cursor.getColumnNames();
		nbrRow = rows.length;
		for (int Cpt = 0; Cpt < rows.length; Cpt++){
			if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "nom = " + rows[Cpt]);};
		}
        cursor.close();
        
		return nbrRow;
	}
	
	public void deleteBook(Book book){
		int del = db.delete(Params.DB_TABLE_BOOKS, Params.DB_KEY_ID + "=" + book.getId(), null);
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "del = " + String.valueOf(del));};
	}

}
