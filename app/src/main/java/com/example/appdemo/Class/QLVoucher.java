package com.example.appdemo.Class;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appdemo.model.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class QLVoucher {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;

    public QLVoucher(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context,"DBFlowerShop.sqlite",null,1);
        db = dbHelper.getWritableDatabase();//cho phép ghi dữ liệu vào database
    }
    //Thêm dữ liệu
    public int ThemVoucher(Voucher s){
        ContentValues values = new ContentValues();//Tạo đối tượng chứa dữ liệu
        //Đưa dữ liệu vào đối tượng chứa
        values.put("MAVOUCHER",s.getMAVOUCHER());
        values.put("GIAM",s.getGIAM());
        values.put("HANSD",s.getHANSD());
        //thực thi Thêm
        long kq = db.insert("VOUCHER",null, values);
        //Kiểm tra kết quả Insert
        if(kq <= 0){
            return -1;//Thêm thất bại
        }
        return 1;//Thêm thành công
    }
    //Hiển thị dữ liệu dạng string
    public List<String> getAllVoucherToString(){
        List<String> ls = new ArrayList<>();//tạo danh sách rỗng
        //tạo con trỏ đọc bảng dữ liệu sản phẩm
        Cursor c = db.query("VOUCHER",null,null,null,null,null,null);
        c.moveToFirst();//di chuyển con trỏ về bảng ghi đầu tiên
        //đọc
        while(c.isAfterLast()==false){
            Voucher s = new Voucher();//tạo đối tượng chứa dữ liệu
            s.setMAVOUCHER(c.getString(0));
            s.setNOIDUNG(c.getString(1));
            s.setHANSD(c.getString(2));
            s.setGIAM(c.getDouble(3));
            //chuyển đối tượng thành chuỗi
            String chuoi = "Mã Voucher: "+s.getMAVOUCHER()+"\nGiảm giá: "+s.getGIAM()*100 + "%" + "\nHạn sử dụng: "+s.getHANSD();
            //đưa chuỗi vào list
            ls.add(chuoi);
            c.moveToNext();//di chuyển đến bảng ghi tiếp theo
        }
        c.close();
        return ls;
    }
    public int XoaVoucher(String MAVOUCHER){
        int kq = db.delete("VOUCHER","MAVOUCHER=?",new String[]{MAVOUCHER});
        if(kq <= 0){
            return -1;//Thêm thất bại
        }
        return 1;//Thêm thành công
    }
    public int SuaVoucher(Voucher s) {
        ContentValues values = new ContentValues();//Tạo đối tượng chứa dữ liệu
        //Đưa dữ liệu vào đối tượng chứa
        values.put("MAVOUCHER", s.getMAVOUCHER());
        values.put("GIAM", s.getGIAM());
        values.put("HANSD", s.getHANSD());
        //thực thi Thêm
        long kq = db.update("VOUCHER", values, "MAVOUCHER=?",new String[]{s.getMAVOUCHER()});
        //Kiểm tra kết quả Insert
        if (kq <= 0) {
            return -1;//Thêm thất bại
        }
        return 1;//Thêm thành công
    }
}
