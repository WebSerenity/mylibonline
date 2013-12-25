package ws.base.mylibonline.utils;

import java.util.ArrayList;

import ws.base.modeles.Book;
import ws.base.modeles.Cat;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseCat {
	private String TAG_LOCAL = "DatabaseCat - ";
	private boolean fgDebugLocal = true;
	
	private SqLiteBook sqLiteBook;
	private SQLiteDatabase db;
	private ArrayList<Cat> listCat = new ArrayList<Cat>();
	
	public DatabaseCat(Context context){
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
	
	public long insertCat(Cat cat){
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(Params.DB_KEY_CAT_CAT, cat.getTexte());
		
		//on insère l'objet dans la BDD via le ContentValues
		return db.insert(Params.DB_TABLE_CATS, null, values);
	}
	
	public void deleteCat(Cat cat){
		int del = db.delete(Params.DB_TABLE_CATS, Params.DB_KEY_CAT_ID + "=" + cat.getId(), null);
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "del = " + String.valueOf(del));};
	}
	
	
	public void updateCat(Cat cat){
		ContentValues values = new ContentValues();
	    values.put(Params.DB_KEY_CAT_ID, cat.getId());
	    values.put(Params.DB_KEY_CAT_CAT, cat.getTexte());
	    db.update(Params.DB_TABLE_CATS, values, Params.DB_KEY_CAT_ID + " = " + cat.getId(),null);
	}
	
	
	public ArrayList<Cat> getListCat(){
        String strQuery = "SELECT  * FROM " + Params.DB_TABLE_CATS + " ORDER BY " + Params.DB_KEY_CAT_CAT + " ASC";
        Cursor cursor = db.rawQuery(strQuery, null);
        if (cursor.getCount() == 0){
        	return listCat;
        }
        if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "nbrCursorCat = " + cursor.getCount());};
        if (cursor.moveToFirst()) {
            do {
                Cat cat = cursorToCat(cursor);                
                listCat.add(cat);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listCat;
	}
	
	private Cat cursorToCat(Cursor cursor){
		if (cursor.getCount() == 0)
			return null;
 
		Cat cat = new Cat();
		cat.setId(cursor.getInt(Params.DB_NUM_KEY_CAT_ID));
		cat.setTexte(cursor.getString(Params.DB_NUM_KEY_CAT_CAT));
		//ne pas fermer le curseur ici
		return cat;
	}
	
	public void resetDB(){
		db.execSQL("DROP TABLE " + Params.DB_TABLE_CATS);
        db.execSQL(Params.CREATE_BDD_CAT);	
	}
	
	
	public int getNbrCat(){
		String countQuery = "SELECT  * FROM " + Params.DB_TABLE_CATS;
        Cursor cursor = db.rawQuery(countQuery, null);
        int nbrCat = cursor.getCount();
        cursor.close();

        return nbrCat;
	}
	
	public int getNbrColonne(){
		Cursor cursor = db.rawQuery("select name as _id from sqlite_master where type='table'", null);
		int nbrCol = cursor.getCount();
		if (Params.TAG_FG_DEBUG && fgDebugLocal){Log.i(Params.TAG_GEN, TAG_LOCAL + "nbrCol = " + nbrCol);};
		return nbrCol;
	}

}
