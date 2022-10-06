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
import java.util.Random;

public class DataBaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    Context context;
    private static final String DB_PATH = "/data/data/" + BuildConfig.APPLICATION_ID + "/databases/";
    private static final String DB_NAME = "langl.db";
    private static final String TAG = "DataBaseHelper";
    private static final int version = 6;
    private String TABLE, WORD;

    public DataBaseHelper(@Nullable Context context, int lang) {
        super(context, "langl.db", null, version);
        this.context = context;

        switch(lang) {
            case 0:
                TABLE = "DE_WORD_TABLE";
                WORD = "DE_WORD";
                break;
            case 1:
                TABLE = "FR_WORD_TABLE";
                WORD = "FR_WORD";
                break;
            case 2:
                TABLE = "RU_WORD_TABLE";
                WORD = "RU_WORD";
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
                throw new Error("Error copying database");
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
                "DE_WORD TEXT);";

        db.execSQL(createTableStmt);

        createTableStmt = "CREATE TABLE IF NOT EXISTS FR_WORD_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FR_WORD TEXT);";

        db.execSQL(createTableStmt);

        createTableStmt = "CREATE TABLE IF NOT EXISTS RU_WORD_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "RU_WORD TEXT);";

        db.execSQL(createTableStmt);

        try {
            copyDatabase();
        } catch (IOException e) {
            throw new Error("Error copying database");
        }

        db.close();
    }

    public String getCorrectWord() {
        Random rand = new Random();

        int bound;

        db = this.getReadableDatabase();

        String length_query = "SELECT COUNT(*) FROM " + TABLE + ";";

        Cursor cursor = db.rawQuery(length_query, null, null);
        if(cursor.moveToFirst()) {
            bound = cursor.getInt(0);
            cursor.close();
            Log.d(TAG, "Length: " + bound);
        } else {
            db.close();
            cursor.close();
            return "";
        }

        int randNum = rand.nextInt(bound);

        String query = "SELECT " + WORD + " FROM " + TABLE + " WHERE ID = " + randNum + ";";

        cursor = db.rawQuery(query, null, null);
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

        String query = "SELECT * FROM " + TABLE + ";";
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
        onCreate(db);
    }
}
