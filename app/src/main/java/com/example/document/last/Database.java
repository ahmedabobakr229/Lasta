package com.example.document.last;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by document on 24/09/2017.
 */

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="DATA";
    public static final String TABLE_NAME = "MOVIES";
    public static final String COULOMN_ID = "MOVIE_id";
    public static final String COULOMN_IMAGE = "MOVIE_POSTER";
    public static final String COULMN_TITLE = "ORIGINAL_TITLE";
    public static final String COULMN_OVER= "OVERVIEW";
    public static final String COULMN_DATE= "RELEASE_DATE";
    public static final String VOTE_AVERAGE= "VOTE_AVERAGE";

    static final int version1 = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, version1);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {


        final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
                COULOMN_ID + " INTEGER PRIMARY KEY, " +
                COULMN_TITLE + " TEXT NOT NULL, " +
                COULOMN_IMAGE + " TEXT NOT NULL, " +
                COULMN_OVER + " TEXT NOT NULL, " +
                VOTE_AVERAGE + " TEXT NOT NULL, " +
                COULMN_DATE + " TEXT NOT NULL " +");";
        db.execSQL(SQL_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
