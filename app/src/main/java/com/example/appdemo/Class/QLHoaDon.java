package com.example.appdemo.Class;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appdemo.model.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class QLHoaDon {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;

    public QLHoaDon(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context,"DBFlowerShop.sqlite",null,1);
        db = dbHelper.getWritableDatabase();//cho phép ghi dữ liệu vào database
    }
    //Thêm dữ liệu
    public int ThemHoaDon(HoaDon s){
        ContentValues values = new ContentValues();//Tạo đối tượng chứa dữ liệu
        //Đưa dữ liệu vào đối tượng chứa
        values.put("ID",s.getID());
        values.put("DATEORDER",s.getDATEORDER());
        values.put("TAIKHOANCUS",s.getTAIKHOANCUS());
        values.put("ADDRESSDELIVERRY",s.getADDRESSDELIVERRY());
        //thực thi Thêm
        long kq = db.insert("BILL",null, values);
        dbHelper.close();
        //Kiểm tra kết quả Insert
        if(kq <= 0){
            return -1;//Thêm thất bại
        }
        return 1;//Thêm thành công
    }
    //Hiển thị dữ liệu dạng string
    public List<String> getAllHoaDonToString(){
        List<String> ls = new ArrayList<>();//tạo danh sách rỗng
        //tạo con trỏ đọc bảng dữ liệu sản phẩm
        Cursor c = db.query("BILL",null,null,null,null,null,null);
        c.moveToFirst();//di chuyển con trỏ về bảng ghi đầu tiên
        //đọc
        while(c.isAfterLast()==false){
            HoaDon s = new HoaDon();//tạo đối tượng chứa dữ liệu
            s.setID(c.getInt(0));
            s.setDATEORDER(c.getString(1));
            s.setTAIKHOANCUS(c.getString(2));
            s.setADDRESSDELIVERRY(c.getString(3));
            //chuyển đối tượng thành chuỗi
            String chuoi = "Mã ID: "+s.getID()+" - "+"Ngày tạo đơn: "+s.getDATEORDER()+" - "+"Tài khoản mua: "+s.getTAIKHOANCUS();
            //đưa chuỗi vào list
            ls.add(chuoi);
            c.moveToNext();//di chuyển đến bảng ghi tiếp theo
        }
        c.close();
        return ls;
    }
    public int XoaHoaDon(Integer ID){
        int kq = db.delete("BILL","ID=?",new String[]{String.valueOf(ID)});
        if(kq <= 0){
            return -1;//Thêm thất bại
        }
        return 1;//Thêm thành công
    }
    public int SuaHoaDon(HoaDon s) {
        ContentValues values = new ContentValues();//Tạo đối tượng chứa dữ liệu
        //Đưa dữ liệu vào đối tượng chứa
        values.put("ID",s.getID());
        values.put("DATEORDER",s.getDATEORDER());
        values.put("TAIKHOANCUS",s.getTAIKHOANCUS());
        values.put("ADDRESSDELIVERRY",s.getADDRESSDELIVERRY());
        //thực thi Thêm
        long kq = db.update("BILL",values,"ID=?",new String[]{String.valueOf(s.getID())});
        //Kiểm tra kết quả Insert
        if(kq <= 0){
            return -1;//Thêm thất bại
        }
        return 1;//Thêm thành công
    }
}
