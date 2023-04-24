package com.example.appdemo.Class;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.appdemo.model.DatabaseHelper;

public class QLGioHang {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;
    public QLGioHang(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context,"DBFlowerShop.sqlite",null,1);
        db = dbHelper.getWritableDatabase();//cho phép ghi dữ liệu vào database
    }

}
