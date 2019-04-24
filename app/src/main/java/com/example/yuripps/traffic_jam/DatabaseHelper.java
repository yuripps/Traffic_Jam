package com.example.yuripps.traffic_jam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mushroomclassify1.db";
    private static final int DATABASE_VERSION = 1;


    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private static final String TABLE_RECENT = "recent";
    private static final String COLUMN_ID = "user_id";
    private static final String COLUMN_NAME = "user_name";

    private static final String TABLE_NGROK = "ng";
    private static final String COLUMN_NG_ID = "ng_id";
    private static final String COLUMN_NG_PATH = "ng_path";




    private String CREATE_NGROK_TABLE = "CREATE TABLE " + TABLE_NGROK + "("
            + COLUMN_NG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NG_PATH + " TEXT" + ")";

    private String DROP_NGROK_TABLE = "DROP TABLE IF EXISTS " + TABLE_NGROK;

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    private String CREATE_RECENT_TABLE = "CREATE TABLE " + TABLE_RECENT + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT" + ")";

    private String DROP_RECENT_TABLE = "DROP TABLE IF EXISTS " + TABLE_RECENT;


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_RECENT_TABLE);
        db.execSQL(CREATE_NGROK_TABLE);
    }

    public  void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_RECENT_TABLE);
        db.execSQL(DROP_NGROK_TABLE);
        onCreate(db);
    }


        public void addNg(String ng){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NG_PATH, ng);
            db.insert(TABLE_NGROK, null, values);
            db.close();
        }

        public String getName(){
            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.rawQuery("SELECT " + COLUMN_NAME
                    + " FROM " + TABLE_RECENT  , null);
//        cursor.moveToPosition(1);
            cursor.moveToLast();
            String cr = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            return cr;
        }

        public String getNg(){
            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.rawQuery("SELECT " + COLUMN_NG_PATH
                    + " FROM " + TABLE_NGROK  , null);
            cursor.moveToLast();
            String cr = cursor.getString(cursor.getColumnIndex(COLUMN_NG_PATH));
            return cr;
        }

        public boolean checkUser(String name){
            String[] columns = {
                    COLUMN_USER_ID
            };
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = COLUMN_USER_NAME + " = ?";
            String[] selectionArgs = { name };

            Cursor cursor = db.query(TABLE_USER,
                    columns,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
            int cursorCount = cursor.getCount();
            cursor.close();
            db.close();

            if (cursorCount > 0){
                return true;
            }
            return false;
        }

        public boolean checkUser(String name, String password){
            String[] columns = {
                    COLUMN_USER_ID
            };
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = COLUMN_USER_NAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " =?";
            String[] selectionArgs = { name, password };

            Cursor cursor = db.query(TABLE_USER,
                    columns,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
            int cursorCount = cursor.getCount();
            cursor.close();
            db.close();

            if (cursorCount > 0){
                return true;
            }
            return false;
        }


}
