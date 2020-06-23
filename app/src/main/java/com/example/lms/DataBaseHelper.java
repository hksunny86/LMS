package com.example.lms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String name = "DBSTUDENT";
    public static final int version = 1;
    public static final String TABLE_NAME = "Student";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT, BOOKNAME TEXT, ISSUEDATE DATE)";






    public DataBaseHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

    }

    Boolean ADDDATA(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("NAME", student.name);
        contentValues.put("BOOKNAME", student.book_issued);
        contentValues.put("ISSUEDATE", student.date);


        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();

        if (result == -1)
            return false;
        else
            return true;
    }

    public void update(String name , String bookname ,String issuedate,int id){

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "UPDATE Student SET NAME= '"+name+"', BOOKNAME='"+bookname+"', ISSUEDATE='"+issuedate+"' WHERE ID="+id;

       db.execSQL(sql);
       db.close();

    }

    public Cursor search (String text){

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID='" + text + "'";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    public void delete(int id){


        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "DELETE FROM Student WHERE ID="+id;

        db.execSQL(sql);
        db.close();
    }

    public Cursor get_Data() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        return cursor;
    }

    public Cursor get_data(String sql){
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(sql,null);
    }


}
