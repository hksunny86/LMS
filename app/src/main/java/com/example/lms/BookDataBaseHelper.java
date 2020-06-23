package com.example.lms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookDataBaseHelper extends SQLiteOpenHelper {

    public static final String name = "DBBOOK";
    public static final int version = 1;
    public static final String BOOK_TABLE_NAME = "Book";




    public  static final String CREATE_TABLE_BOOK =
            "CREATE TABLE " + BOOK_TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,AUTHOR TEXT)";


    public BookDataBaseHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BOOK);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BOOK_TABLE_NAME);
    }


    Boolean ADDBOOK(BookGetSet bookGetSet){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE", bookGetSet.name);
        contentValues.put("AUTHOR", bookGetSet.author);


        long result = db.insert(BOOK_TABLE_NAME, null, contentValues);
        db.close();

        if (result == -1)
            return false;
        else
            return true;


    }

    public void update(String title , String author ,int id){

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "UPDATE Book SET TITLE='"+title+"', AUTHOR='"+author+"' WHERE ID="+id;
//        Log.e("Value123","Data Ni Ja Rha");

        db.execSQL(sql);
        db.close();
    }

    public void delete(int id){


        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "DELETE FROM Book WHERE ID="+id;

        db.execSQL(sql);
        db.close();

    }
    public Cursor search (String text){

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + BOOK_TABLE_NAME + " WHERE ID='" + text + "'";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }


    public Cursor get_Book_Data() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + BOOK_TABLE_NAME, null);
        return cursor;
    }
    public Cursor get_data(String sql){
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(sql,null);
    }

}
