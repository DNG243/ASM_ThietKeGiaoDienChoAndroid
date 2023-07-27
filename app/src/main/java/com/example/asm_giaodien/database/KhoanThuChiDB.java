package com.example.asm_giaodien.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KhoanThuChiDB extends SQLiteOpenHelper {

    public  KhoanThuChiDB(Context context){
        super(context,"KHOANTHUCHIDB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qLoai = "CREATE TABLE LOAI(maloai integer primary key autoincrement,tenloai text,trangthai text)";
        sqLiteDatabase.execSQL(qLoai);

        String qKhoan = "CREATE TABLE KHOANTHUCHI(makhoan integer primary key autoincrement,tien integer,maloai integer)";
        sqLiteDatabase.execSQL(qKhoan);

        String insLoai = "INSERT INTO LOAI VALUES(1,'tiền lương','thu'),(2,'tiền trúng vé số','thu'),(3,'tiền xăng','chi')";
        sqLiteDatabase.execSQL(insLoai);

        String insKhoan = "INSERT INTO KHOANTHUCHI VALUES(1,1000,2),(2,5000,1),(3,15000,2)";
        sqLiteDatabase.execSQL(insKhoan);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i!=i1){
            String dLoai = "DROP TABLE IF EXISTS LOAI";
            sqLiteDatabase.execSQL(dLoai);
            String dKhoan = "DROP TABLE IF EXISTS KHOANTHUCHI";
            sqLiteDatabase.execSQL(dKhoan);
            onCreate(sqLiteDatabase);
        }
    }
}
