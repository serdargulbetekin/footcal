package com.sgfootcal.android.footcal.SqliteFavoriteMatches;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VeritabaniYardimcisi extends SQLiteOpenHelper {
	 
	private static final int Surum=1;

	private static String veritabaniAdi = "FavoriteMatches";

	public VeritabaniYardimcisi(Context context) {
		
		super(context, veritabaniAdi, null, Surum);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
        db.execSQL("CREATE TABLE FavoriteMatches " +
				"(matchNo INTEGER PRIMARY KEY AUTOINCREMENT," +
				"firstTeam_Id TEXT);");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

		db.execSQL("DROP TABLE IF EXIST FavoriteMatches");

		onCreate(db);
		
	}
	
}