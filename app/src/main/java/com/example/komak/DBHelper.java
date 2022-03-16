package com.example.komak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.widget.TextView;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "appdata.db";

    public DBHelper( Context context) {

        super(context, "appdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {

        MyDB.execSQL("create Table users" +
                "(email TEXT primary Key, " +
                "password TEXT," +
                " firstname TEXT," +
                " lastname TEXT)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {

        MyDB.execSQL("drop Table if exists users");

    }
    public boolean register (String firstname, String lastname, String email, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);
        if (result==-1){
            return false;
        }
        else
            return true;
    }
    public boolean checkemail (String email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ?", new String[] {email});
        if (cursor.getCount() > 0){
            return true;
        }
        else
            return false;
    }
    public Cursor checkpass (String email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select password from users where email = ?", new String[] {email});
        return cursor;
        }
    public boolean updatepass (String pass, String email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Update users set password = ? where email = ?", new String[] {pass, email});
        if (cursor.getCount() > 0){
            return true;
        }
        else
            return false;
    }
    public boolean checkemailpassword(String email, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ? and password = ?", new String[] {email, password});
        if (cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }
    }
    public Cursor getuserdata(String email){

        String[] columns = {"email", "firstname", "lastname"};

        SQLiteDatabase MyDB = this.getReadableDatabase();
        String selection = "email"+" = ?";
        String [] selectionArgs = {email};
        Cursor cursor = MyDB.query("users", columns, selection, selectionArgs,
                null, null, null);

        return cursor;
    }
    public Cursor UserName(String email){

        String[] columns = {"firstname"};
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String selection = "email"+" = ?";
        String[] selectionArgs = {email};
        Cursor cursor = MyDB.query("users", columns, selection, selectionArgs,
                null, null, null);
        return cursor;
    }


}
