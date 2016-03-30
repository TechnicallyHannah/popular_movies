package com.example.a712948.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Hannah Paulson
 * @since 3/30/16.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String FAVORITES_TABLE_NAME = "favorites";
    public static final String FAVORITES_COLUMN_ID = "id";
    public static final String FAVORITES_COLUMN_MOVIEID = "movie_id";
    public static final String FAVORITES_COLUMN_POSTER_PATH = "poster_path";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table favorites " +
                        "(id integer primary key, movie_id text,poster_path text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS favorites");
        onCreate(db);
    }

    public boolean insertFavorites(String movieID, String posterPath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("movie_id", movieID);
        contentValues.put("poster_path", posterPath);
        db.insert("favorites", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from favorites where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, FAVORITES_TABLE_NAME);
        return numRows;
    }

    public boolean updateFavorites(Integer id, String movieID, String posterPath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("movie_id", movieID);
        contentValues.put("poster_path", posterPath);
        db.update("favorites", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteFavorites(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("favorites",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<String> getAllFavorites() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from favorites", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(FAVORITES_COLUMN_MOVIEID)));
            res.moveToNext();
        }
        return array_list;
    }
}

