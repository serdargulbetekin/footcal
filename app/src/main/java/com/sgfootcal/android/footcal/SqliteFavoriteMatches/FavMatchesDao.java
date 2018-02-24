package com.sgfootcal.android.footcal.SqliteFavoriteMatches;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


public class FavMatchesDao {

    public ArrayList<FavMatches> allFavMatches(VeritabaniYardimcisi vt){

        ArrayList<FavMatches> favMatchesArrayList =new ArrayList<>();
        SQLiteDatabase dbx=vt.getWritableDatabase();
        Cursor cursor=dbx.rawQuery("SELECT * FROM FavoriteMatches ", null);

        while(cursor.moveToNext()){

            FavMatches k = new FavMatches(
                    cursor.getInt(cursor.getColumnIndex("matchNo")),
                    cursor.getString(cursor.getColumnIndex("firstTeam_Id")));
            favMatchesArrayList.add(k);
        }
        return favMatchesArrayList;

    }


    public FavMatches specificMatches(VeritabaniYardimcisi vt, int matchNo){

        FavMatches kisi=new FavMatches();
        SQLiteDatabase dbx=vt.getWritableDatabase();
        Cursor cursor=dbx.rawQuery("SELECT * FROM FavoriteMatches WHERE matchNo="+matchNo, null);

        while(cursor.moveToNext()){
            FavMatches k = new FavMatches(
                    cursor.getInt(cursor.getColumnIndex("matchNo")),
                    cursor.getString(cursor.getColumnIndex("firstTeam_Id")));
            kisi = k ;
        }
        return kisi;

    }

    public void removeFavMatches(VeritabaniYardimcisi vt,String firstTeam_Id){
        SQLiteDatabase dbx=vt.getWritableDatabase();
        dbx.delete("FavoriteMatches", "firstTeam_Id=?",new String[] {firstTeam_Id} );
        dbx.close();

    }


    public void addFavMatches(VeritabaniYardimcisi vt, String firstTeam_Id){

        SQLiteDatabase dbx=vt.getWritableDatabase();

        ContentValues values=new ContentValues();

        values.put("firstTeam_Id",firstTeam_Id );


        dbx.insertOrThrow("FavoriteMatches", null, values);
        dbx.close();

    }



}
