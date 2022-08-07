package com.tyha.langl;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private static String DB_PATH = "/data/data/" + BuildConfig.APPLICATION_ID + "/databases/";
    private static String DB_NAME = "english_test.db";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "english_test.db", null, 1);
        this.context = context;

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
            System.out.println("DB exists");
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
            System.out.println("DB does not exist");
        }

        return dbExist;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStmt = "CREATE TABLE IF NOT EXISTS ENGLISH_TEST_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ENGLISH_WORD TEXT);";

        db.execSQL(createTableStmt);

        db.close();
    }

    public String getCorrectWord() {
        Random rand = new Random();

        int randNum = rand.nextInt(12975);

        String query = "SELECT ENGLISH_WORD FROM ENGLISH_TEST_TABLE WHERE ID = " + randNum + ";";

        db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null, null);
        String word;
        if(cursor.moveToFirst()) {
            word = cursor.getString(0);
            cursor.close();
            db.close();

            return word;
        } else {
            cursor.close();
            db.close();

            return "";
        }
    }

    public ArrayList<String> getWords() {
        ArrayList<String> words = new ArrayList<>();

        String query = "SELECT * FROM ENGLISH_TEST_TABLE";
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

    }
}
