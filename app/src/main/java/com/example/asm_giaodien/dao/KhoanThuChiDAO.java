package com.example.asm_giaodien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.asm_giaodien.database.KhoanThuChiDB;
import com.example.asm_giaodien.model.KhoanThuChi;
import com.example.asm_giaodien.model.Loai;

import java.util.ArrayList;

public class KhoanThuChiDAO {
    KhoanThuChiDB khoanThuChiDB;
    public KhoanThuChiDAO(Context context){
        khoanThuChiDB = new KhoanThuChiDB(context);
    }

    public ArrayList<Loai> getDSLoai(String loai){
        ArrayList<Loai> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAI",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                String trangthai = cursor.getString(2);
                        if(trangthai.equals(loai)){
                            list.add(new Loai(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
                        }
            }while (cursor.moveToNext());
        }

        return list;
    }

    public boolean themMoiLoaiThuChi(Loai loai){
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("tenloai",loai.getTenloai());
        contentValues.put("trangthai",loai.getTrangthai());
        long check = sqLiteDatabase.insert("LOAI",null,contentValues);
        if(check ==-1)
            return false;
        return true;
    }
    public  boolean capnhatLoaiThuChi(Loai loai){
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("tenloai",loai.getTenloai());
        contentValues.put("trangthai",loai.getTrangthai());
        long check = sqLiteDatabase.update("LOAI",contentValues,"maloai =?",new String[]{String.valueOf(loai.getMaloai())});
        if(check ==-1)
            return false;
        return true;
    }
    public boolean xoaLoaiThuChi(int maLoai){
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getWritableDatabase();
        long check = sqLiteDatabase.delete("LOAI","maloai=?",new String[]{String.valueOf(maLoai)});
        if(check ==-1)
            return false;
        return true;
    }

    public ArrayList<KhoanThuChi> getDSKhoanThuChi(String loai){
        ArrayList<KhoanThuChi> list = new ArrayList<>();
        SQLiteDatabase  sqLiteDatabase = khoanThuChiDB.getReadableDatabase();

        String query = "select k.makhoan,k.tien,k.maloai,l.tenloai from khoanthuchi k,loai l where k.maloai = l.maloai and l.trangthai = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{loai});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new KhoanThuChi(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getString(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themMoiKhoanThuChi(KhoanThuChi khoanThuChi){
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tien", khoanThuChi.getTien());
        contentValues.put("maloai",khoanThuChi.getMaloai());
        long check = sqLiteDatabase.insert("khoanthuchi",null,contentValues);
        if(check==-1){
            return false;
        }
        return true;
    }

    public boolean capNhatKhoanThuChi(KhoanThuChi khoanThuChi){
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tien", khoanThuChi.getTien());
        contentValues.put("maloai",khoanThuChi.getMaloai());
        long check = sqLiteDatabase.update("KHOANTHUCHI",contentValues, "makhoan = ?", new String[]{String.valueOf(khoanThuChi.getMakhoan())});
        if(check==-1){
            return false;
        }
        return true;
    }
    public boolean xoaKhoanThuChi(int makhoan){
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getWritableDatabase();
        long check = sqLiteDatabase.delete("KHOANTHUCHI","makhoan = ?",new String[]{String.valueOf(makhoan)});
        if(check==-1){
            return false;
        }
        return true;
    }

    public float[] getThongTinThuChi() {
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getReadableDatabase();
        int thu = 0, chi = 0;

        Cursor cursorThu = sqLiteDatabase.rawQuery("select sum(tien) from KHOANTHUCHI where maloai in (select maloai from loai where trangthai = 'thu') ", null);
        if (cursorThu.getCount() != 0) {
            cursorThu.moveToFirst();
            thu = cursorThu.getInt(0);
        }

        Cursor cursorChi = sqLiteDatabase.rawQuery("select sum(tien) from KHOANTHUCHI where maloai in (select maloai from loai where trangthai = 'chi') ", null);
        if (cursorChi.getCount() != 0) {
            cursorChi.moveToFirst();
            chi = cursorChi.getInt(0);
        }
        float[] ketQua = new float[]{thu, chi};
        return ketQua;
    }

    public ArrayList<Loai> getDSLoaiChi(String loai){
        ArrayList<Loai> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAI",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                String trangthai = cursor.getString(2);
                if(trangthai.equals(loai)){
                    list.add(new Loai(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
                }
            }while (cursor.moveToNext());
        }

        return list;
    }

    public boolean themMoiLoaiChi(Loai loai){
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("tenloai",loai.getTenloai());
        contentValues.put("trangthai",loai.getTrangthai());
        long check = sqLiteDatabase.insert("LOAI",null,contentValues);
        if(check ==-1)
            return false;
        return true;
    }
    public  boolean capnhatLoaiChi(Loai loai){
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("tenloai",loai.getTenloai());
        contentValues.put("trangthai",loai.getTrangthai());
        long check = sqLiteDatabase.update("LOAI",contentValues,"maloai =?",new String[]{String.valueOf(loai.getMaloai())});
        if(check ==-1)
            return false;
        return true;
    }
    public boolean xoaLoaiChi(int maLoai){
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getWritableDatabase();
        long check = sqLiteDatabase.delete("LOAI","maloai=?",new String[]{String.valueOf(maLoai)});
        if(check ==-1)
            return false;
        return true;
    }

    public ArrayList<KhoanThuChi> getDSKhoanChi(String loai){
        ArrayList<KhoanThuChi> list = new ArrayList<>();
        SQLiteDatabase  sqLiteDatabase = khoanThuChiDB.getReadableDatabase();

        String query = "select k.makhoan,k.tien,k.maloai,l.tenloai from khoanthuchi k,loai l where k.maloai = l.maloai and l.trangthai = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{loai});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new KhoanThuChi(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getString(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themMoiKhoanChi(KhoanThuChi khoanThuChi){
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tien", khoanThuChi.getTien());
        contentValues.put("maloai",khoanThuChi.getMaloai());
        long check = sqLiteDatabase.insert("khoanthuchi",null,contentValues);
        if(check==-1){
            return false;
        }
        return true;
    }

    public boolean capNhatKhoanChi(KhoanThuChi khoanThuChi){
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tien", khoanThuChi.getTien());
        contentValues.put("maloai",khoanThuChi.getMaloai());
        long check = sqLiteDatabase.update("KHOANTHUCHI",contentValues, "makhoan = ?", new String[]{String.valueOf(khoanThuChi.getMakhoan())});
        if(check==-1){
            return false;
        }
        return true;
    }
    public boolean xoaKhoanChi(int makhoan){
        SQLiteDatabase sqLiteDatabase = khoanThuChiDB.getWritableDatabase();
        long check = sqLiteDatabase.delete("KHOANTHUCHI","makhoan = ?",new String[]{String.valueOf(makhoan)});
        if(check==-1){
            return false;
        }
        return true;
    }

}
