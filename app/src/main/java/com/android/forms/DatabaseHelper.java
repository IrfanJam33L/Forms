package com.android.forms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "Info.db";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE INFORMATION_TABLE(FNAME TEXT, LNAME TEXT, EMAIL TEXT, P_NUM INTEGER PRIMARY KEY, ADDRESS TEXT)");
        sqLiteDatabase.execSQL("CREATE UNIQUE INDEX DIFFRENT ON INFORMATION_TABLE(P_NUM,EMAIL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS INFORMATION_TABLE ");
    }



    public boolean insertData(String fname, String lname, String email, String p_num, String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FNAME", fname);
        contentValues.put("LNAME",lname);
        contentValues.put("EMAIL",email);
        contentValues.put("P_NUM",p_num);
        contentValues.put("ADDRESS",address);
        long result = db.insert("INFORMATION_TABLE",null ,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from INFORMATION_TABLE", null);
        return res;
    }

}
