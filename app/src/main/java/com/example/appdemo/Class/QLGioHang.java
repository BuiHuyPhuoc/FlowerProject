package com.example.appdemo.Class;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.appdemo.model.DatabaseHelper;
import com.example.appdemo.model.GioHang;

public class QLGioHang {
    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;
    private Context context;
    public QLGioHang(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context,"DBFlowerShop.sqlite",null,1);
        db = databaseHelper.getWritableDatabase();//cho phép ghi dữ liệu vào database
    }
    public long themGioHang(GioHang gioHang){
        SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IDCUS", gioHang.getIdCus());
        values.put("IDSANPHAM", gioHang.getIdSanPham());
        values.put("IDVoucher", gioHang.getIdVoucher());
        values.put("SOLUONG", gioHang.getSoLuong());
        return db.insert("CARTLIST", null, values);
    }

}
