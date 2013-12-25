package ws.base.mylibonline.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqLiteBook extends SQLiteOpenHelper{	
	
	public SqLiteBook(Context context) {
		super(context, Params.DB_DATABASE_NAME, null, Params.DB_DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + Params.DB_TABLE_BOOKS);
		db.execSQL(Params.CREATE_BDD_BOOK);
		db.execSQL("DROP TABLE IF EXISTS " + Params.DB_TABLE_CATS);
		db.execSQL(Params.CREATE_BDD_CAT);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS " + Params.DB_TABLE_BOOKS);
		db.execSQL("DROP TABLE IF EXISTS " + Params.DB_TABLE_CATS);
	    onCreate(db);
	}
	
	
	

	
	
	
	
	
	

}
