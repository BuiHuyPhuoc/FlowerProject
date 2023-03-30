package com.example.appdemo.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Truy vấn không trả kết quả
    //Truy vấn không trả kết quả là truy vấn thêm, xóa, sửa trên database
    public void WriteQuery(String content){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(content);
    }
    //Truy vấn trả kết quả (Select)
    public Cursor GetData(String content){
        SQLiteDatabase db =  getReadableDatabase();
        return db.rawQuery(content, null);
    }
    //Hàm AddRole, Thêm dữ liệu vào bảng "ROLE"



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    //Thêm nội dung vào db
    private boolean CheckExists(String PrimaryColumn, String TableName){
        Cursor i = this.GetData("Select* from " + TableName);
        while (i.moveToNext()){
            if (PrimaryColumn.equals(i.getString(0))){
                return false;
            }
        }
        return true;
    }
    public boolean AddAccount(String taikhoan, String matkhau, String quyenhan, String ten, String sdt, String gmail,String diachi){
        if (CheckExists(taikhoan, "Account")){
            this.WriteQuery("Insert into ACCOUNT Values" +
                    "('" + taikhoan + "', '" + matkhau + "', '" + quyenhan + "', '" + ten + "', '" + sdt + "', '" + gmail + "','" + diachi + "');");
        }
        return CheckExists(taikhoan, "Account");
    }
    public boolean AddRole(String role, String content){
        if (CheckExists(role, "[ROLE]")){
            this.WriteQuery("Insert into ACCOUNT Values" +
                    "('" + role + "', '" + content + "');");
        }
        return CheckExists(role, "[ROLE]");
    }
    public boolean AddProduct(String MASP, String TENSP,String PHANLOAI, Integer SOLUONG,String NOINHAP,String NOIDUNG, double DONGIA, int HINHANH){
        if (CheckExists(MASP, "SANPHAM")){
            this.WriteQuery("Insert into SANPHAM Values" +
                    "('" + MASP + "', '" + TENSP + "', '" + PHANLOAI + "', '" + SOLUONG + "', '" + NOINHAP + "', '" + NOIDUNG + "', '" + DONGIA + "', '" + HINHANH + "');");
        }
        return CheckExists(MASP, "SANPHAM");
    }
    public boolean AddCategory(String NAME, String CONTENT){
        if (CheckExists(NAME, "[CATEGORY]")){
            this.WriteQuery("Insert into [CATEGORY] Values" +
                    "('" + NAME + "', '" + CONTENT + "');");
        }
        return CheckExists(NAME, "[CATEGORY]");
    }
    public boolean AddBill(String TAIKHOANCUS, String ADDRESSDELIVERRY){
        try{
            Calendar c = Calendar.getInstance();
            String DATEORDER = Integer.toString(c.get(Calendar.DAY_OF_MONTH)) + "/" + Integer.toString(c.get(Calendar.MONTH) + 1) + "/" + Integer.toString(c.get(Calendar.YEAR)) ;
            SQLiteDatabase db = getReadableDatabase();
            this.WriteQuery("Insert into [CATEGORY] (DATEORDER, TAIKHOANCUS, ADDRESSDELIVERRY) Values" +
                    "('" + DATEORDER + "', '" + TAIKHOANCUS + "', '" + ADDRESSDELIVERRY + "');");
            return true;
        }
       catch (Exception e){
            return false;
       }
    }
}
