package com.example.appdemo.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
    public boolean AddAccount(String taikhoan, String matkhau, String quyenhan, String ten, String sdt, String gmail,String diachi){
        SQLiteDatabase db = getReadableDatabase();
        Cursor i = this.GetData("Select* from ACCOUNT");
        boolean check = true;
        while (i.moveToNext()){
            if (taikhoan.equals(i.getString(0))){
                check = false;
                break;
            }
        }
        if (check){
            this.WriteQuery("Insert into ACCOUNT Values" +
                    "('" + taikhoan + "', '" + matkhau + "', '" + quyenhan + "', '" + ten + "', '" + sdt + "', '" + gmail + "','" + diachi + "');");
        }
        return check;
    }
    public boolean AddRole(String role, String content){
        SQLiteDatabase db = getReadableDatabase();
        Cursor i = this.GetData("Select* from [ROLE]");
        boolean check = true;
        while (i.moveToNext()){
            if (role.equals(i.getString(0))){
                check = false;
                break;
            }
        }
        if (check){
            this.WriteQuery("Insert into [ROLE] Values" +
                    "('" + role + "', '" + content + "');");
        }
        return check;
    }
    public boolean AddProduct(String MASP, String TENSP,String PHANLOAI, Integer SOLUONG,String NOINHAP,String NOIDUNG, double DONGIA, int HINHANH){
        Cursor listSanPham = this.GetData(
                "Select* from SANPHAM"
        );
        boolean check = true;
        while (listSanPham.moveToNext()){
            if (MASP.equals(listSanPham.getString(0))){
                check = false;
                break;
            }
        }
        if (check){
            this.WriteQuery("Insert into SANPHAM Values" +
                    "('" + MASP + "', '" + TENSP + "', '" + PHANLOAI + "', '" + SOLUONG + "', '" + NOINHAP + "', '" + NOIDUNG + "', '" + DONGIA + "', '" + HINHANH + "');");
        }
        return check;
    }
    public boolean AddCategory(String NAME, String CONTENT){
        Cursor listCategory = this.GetData(
                "Select* from [CATEGORY]"
        );
        boolean check = true;
        while (listCategory.moveToNext()){
            if (NAME.equals(listCategory.getString(0))){
                check = false;
                break;
            }
        }
        if (check){
            this.WriteQuery("Insert into [CATEGORY] Values" +
                    "('" + NAME + "', '" + CONTENT + "');");
        }
        return check;
    }
}
