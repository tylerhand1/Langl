package com.tyha.langl;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    Context context;
    private static final String DB_PATH = "/data/data/" + BuildConfig.APPLICATION_ID + "/databases/";
    private static final String DB_NAME = "langl.db";
    private static final String TAG = "DataBaseHelper";
    private static final int version = 12;
    private String TABLE;
    private final int LENGTH;

    public DataBaseHelper(@Nullable Context context, int lang, int length) {
        super(context, "langl.db", null, version);
        this.context = context;
        this.LENGTH = length;

        switch(lang) {
            case 0:
                TABLE = "DE_WORD_TABLE";
                break;
            case 1:
                TABLE = "FR_WORD_TABLE";
                break;
            case 2:
                TABLE = "RU_WORD_TABLE";
                break;
            case 3:
                TABLE = "CS_WORD_TABLE";
                break;
            case 4:
                TABLE = "IT_WORD_TABLE";
                break;
        }

        boolean dbExist = checkDatabase();
        if (dbExist) {
            openDatabase();
        } else {
            createDatabase();
        }
    }

    private void createDatabase() {
        boolean dbExist = checkDatabase();
        if (dbExist) {
            Log.d(TAG, "DB exists");
        } else {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    private void copyDatabase() throws IOException {
        Log.d(TAG, "Copying db from assets to database folder");
        InputStream input = context.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream output = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        output.flush();
        output.close();
        input.close();
    }

    private void openDatabase() {
        String path = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private boolean checkDatabase() {
        boolean dbExist = false;
        try {
            String path = DB_PATH + DB_NAME;
            File dbFile = new File(path);
            dbExist = dbFile.exists();
        } catch (SQLException e) {
            Log.e(TAG, "DB does not exist");
        }
        return dbExist;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStmt = "CREATE TABLE IF NOT EXISTS DE_WORD_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "WORD TEXT);";

        db.execSQL(createTableStmt);

        createTableStmt = "CREATE TABLE IF NOT EXISTS FR_WORD_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "WORD TEXT);";

        db.execSQL(createTableStmt);

        createTableStmt = "CREATE TABLE IF NOT EXISTS RU_WORD_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "WORD TEXT);";

        db.execSQL(createTableStmt);

        createTableStmt = "CREATE TABLE IF NOT EXISTS CS_WORD_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "WORD TEXT);";

        db.execSQL(createTableStmt);

        createTableStmt = "CREATE TABLE IF NOT EXISTS IT_WORD_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "WORD TEXT);";

        db.execSQL(createTableStmt);

        try {
            copyDatabase();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    public String getCorrectWord() {
        db = this.getReadableDatabase();
        // https://stackoverflow.com/questions/2279706/select-random-row-from-a-sqlite-table
        String query = "SELECT WORD FROM " + TABLE + " WHERE LENGTH(WORD) = " + LENGTH + " ORDER BY RANDOM() LIMIT 1;";

        Cursor cursor = db.rawQuery(query, null, null);
        String word;
        if(cursor.moveToFirst()) {
            word = cursor.getString(0);
            cursor.close();
            db.close();
            Log.d(TAG, "Word: " + word);
            return word;
        } else {
            Log.d(TAG, "Word: None");
            cursor.close();
            db.close();

            return "";
        }
    }

    public ArrayList<String> getWords() {
        ArrayList<String> words = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE + " WHERE LENGTH(WORD) = " + LENGTH + ";";
        db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null, null);
        if(cursor.moveToFirst()) {
            do {
                String word = cursor.getString(1);

                words.add(word);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return words;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DE_WORD_TABLE");
        db.execSQL("DROP TABLE IF EXISTS FR_WORD_TABLE");
        db.execSQL("DROP TABLE IF EXISTS RU_WORD_TABLE");
        db.execSQL("DROP TABLE IF EXISTS IT_WORD_TABLE");
        db.execSQL("DROP TABLE IF EXISTS CS_WORD_TABLE");
        onCreate(db);
    }
}
