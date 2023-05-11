package com.example.appdemo.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.appdemo.Class.Account;
import com.example.appdemo.R;

import java.time.LocalDate;
import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String tablename = "TAIKHOAN";
    Context context;
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DatabaseHelper(Context context) {
        super(context, "DBFlowerShop.sqlite", null, 1);
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
    public void onCreate(SQLiteDatabase db) {
        //region Tạo bảng ROLE: Quyền hạn
        this.WriteQuery("CREATE TABLE IF NOT EXISTS [ROLE] (" +
                "QUYENHAN VARCHAR PRIMARY KEY NOT NULL," +
                "NOIDUNG Text NOT NULL)");
        //Thêm dữ liệu vào bảng [ROLE]
        this.AddRole("admin", "Quản trị viên");
        this.AddRole("customer", "Khách hàng");
        //endregion

        //region Tạo bảng ACCOUNT: chứa các tài khoản
        this.WriteQuery("CREATE TABLE IF NOT EXISTS ACCOUNT (\n" +
                "\tTAIKHOAN VARCHAR PRIMARY KEY NOT NULL,\n" +
                "\tMATKHAU VARCHAR NOT NULL,\n" +
                "\tQUYENHAN VARCHAR NOT NULL, \n" +
                "\tTEN VARCHAR,\n" +
                "\tSDT VARCHAR,\n" +
                "\tGMAIL VARCHAR,\n" +
                "\tDIACHI VARCHAR,\n" +
                "\tFOREIGN KEY (QUYENHAN) REFERENCES [ROLE](QUYENHAN)\n" +
                ");");
        //Thêm tài khoản admin và khách hàng mẫu để test
        this.AddAccount("123", "123", "admin", "Nguyen Van A", "0924939352", "voquinamit1@gmail.com", "thailan");
        this.AddAccount("1234", "1234", "customer", "Nguyen Thi B", "0334379439", "", "119");
        //endregion

        //region Tạo bảng CATEGORY: Phân loại sản phẩm
        this.WriteQuery(
                "CREATE TABLE IF NOT EXISTS [CATEGORY] (" +
                        "NAME VARCHAR PRIMARY KEY NOT NULL, " +
                        "NOIDUNG VARCHAR);"
        );
        //Thêm một số CATEGORY
        this.AddCategory("COMBO", "Bó hoa");
        this.AddCategory("TULIP", "Hoa Tulip");
        this.AddCategory("VASE", "Bình hoa");
        //endregion

        //region Tạo bảng SẢN PHẨM: Lưu trữ sản phẩm (hoa)
        this.WriteQuery(
                "CREATE TABLE IF NOT EXISTS SANPHAM (\n" +
                        "\tMASP VARCHAR PRIMARY KEY NOT NULL,\n" +
                        "\tTENSP VARCHAR NOT NULL,\n" +
                        "\tPHANLOAI VARCHAR NOT NULL, \n" +
                        "\tSOLUONG INTEGER NOT NULL,\n" +
                        "\tNOINHAP VARCHAR NOT NULL,\n" +
                        "\tNOIDUNG VARCHAR NULL,\n" +
                        "\tDONGIA REAL CHECK(DONGIA > 0) NOT NULL,\n" +
                        "\tHINHANH INTEGER NOT NULL,\n" +
                        "\tNGAYNHAP date,\n" +
                        "FOREIGN KEY (PHANLOAI) REFERENCES [CATEGORY](NAME)" +
                        ");"
        );
        //cách lấy dữ liệu theo mong muón datetime SELECT strftime('%d/%m/%Y', date_column) AS formatted_date FROM my_table;

        //Thêm 1 vài sản phẩm mẫu vào database
        this.AddProduct("CB001", "You Look Gorgeous", "COMBO", 10, "Đà Lạt", "", 1150000, R.drawable.you_look_gorgeous);
        this.AddProduct("CB002", "Hello Sweetheart", "COMBO", 10, "Đà Lạt", "", 4500000, R.drawable.hello_sweetheart);
        this.AddProduct("CB003", "Strawberry Sundea", "COMBO", 10, "Đà Lạt", "", 2500000, R.drawable.strawberry_sundea);
        this.AddProduct("CB004", "Wintry Wonder", "COMBO", 10, "Đà Lạt", "", 1500000, R.drawable.wintry_wonder);
        this.AddProduct("CB005", "Hopeful Romantic", "COMBO", 10, "Đà Lạt", "", 2000000, R.drawable.hopeful_romantic);
        this.AddProduct("TL001", "All In Bloom", "TULIP", 10, "TPHCM", "", 9500000, R.drawable.all_in_bloom);
        this.AddProduct("TL002", "Blue Day", "TULIP", 10, "TPHCM", "", 1000000, R.drawable.blue_day);
        this.AddProduct("TL003", "Red Love", "TULIP", 10, "TPHCM", "", 1000000, R.drawable.red_love);
        this.AddProduct("TL004", "Pure White", "TULIP", 10, "TPHCM", "", 1000000, R.drawable.pure_white);
        this.AddProduct("TL005", "Pastel Tulip", "TULIP", 10, "TPHCM", "", 1000000, R.drawable.pastel_tulip);
        this.AddProduct("BH001", "Hope For Love", "VASE", 0, "TPHCM", "", 5000000, R.drawable.hope_for_love);
        this.AddProduct("BH002", "Big Rose", "VASE", 10, "TPHCM", "", 5000000, R.drawable.big_rose);
        //endregion

        //region Tạo bảng BILL: Lưu trữ các hóa đơn của người mua
        this.WriteQuery(
                "CREATE TABLE IF NOT EXISTS BILL (\n" +
                        "   ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "   DATEORDER date NOT NULL,\n" +
                        "   TAIKHOANCUS VARCHAR NOT NULL,\n" +
                        "   NAMECUS VARCHAR NOT NULL,\n" +
                        "   ADDRESSDELIVERRY VARCHAR NOT NULL,\n" +
                        "   SDT VARCHAR not null);"
        );
        //endregion

        //region Tạo bảng Bill_Detail: Chi tiết hóa đơn
        this.WriteQuery(
                "CREATE TABLE IF NOT EXISTS BILLDETAIL (\n" +
                        "    ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                        "    MASP VARCHAR NOT NULL,\n" +
                        "    IDORDER   INTEGER not NULL,\n" +
                        "    IDVoucher VARCHAR not null, \n" +
                        "    QUANTITY  INTEGER check(QUANTITY > 0) not NULL,\n" +
                        "    UNITPRICE Real check(UNITPRICE > 0) not NULL,\n" +
                        "    TOTALPRICE Real check (TOTALPRICE > 0) not Null,\n" +
                        "    FOREIGN KEY (MASP) REFERENCES SANPHAM(MASP),\n" +
                        "    FOREIGN KEY (IDORDER) REFERENCES BILL(ID)\n" +
                        "    FOREIGN KEY (IDVoucher) REFERENCES VOUCHER(MAVOUCHER)" +
                        ");"
        );

        //endregion

        //region Tạo bảng VOUCHER: Lưu trữ các voucher hiện có
        this.WriteQuery(
                "CREATE TABLE IF NOT EXISTS VOUCHER(\n" +
                        "\tMAVOUCHER VARCHAR PRIMARY KEY not null,\n" +
                        "\tNOIDUNG TEXT," +
                        "\tHSD date," +
                        "\tGIAM INTEGER DEFAULT(1) Check(GIAM >= 0)\n" +
                        ");"
        );
        int year = LocalDate.now().getYear();
        this.AddVoucher("SALET5", "Sale tháng 5", "2023-05-31" , 10.0/100);
        this.AddVoucher("SALET2", "Sale tháng 2", "2023-02-28", 30.0/100);
        //endregion

        //region Tạo bảng VOUCHER DETAIL: Chi tiết voucher sử dụng cho một hoặc nhiều sản phẩm cụ thể
        this.WriteQuery(
                "CREATE TABLE IF NOT EXISTS VOUCHER_DETAIL(\n" +
                        "\tMAVOUCHER VARCHAR,\n" +
                        "\tMASP VARCHAR NOT NULL,\n" +
                        "\tFOREIGN KEY (MAVOUCHER) REFERENCES VOUCHER(MAVOUCHER),\n" +
                        "  FOREIGN KEY (MASP) REFERENCES SANPHAM(MASP)\n" +
                        ");"
        );
        this.AddVoucherProduct("SALET5", "CB001");
        this.AddVoucherProduct("SALET5", "CB002");
        this.AddVoucherProduct("SALET5", "CB003");
        this.AddVoucherProduct("SALET2", "CB001");
        //endregion





        //region Tạo bảng CARTLIST: Lưu trữ giỏ hàng của người dùng, tự động cập nhật khi người dùng đăng nhập lại
        this.WriteQuery(
                "CREATE TABLE IF NOT EXISTS CARTLIST (\n" +
                        "\tIDCARTLIST   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                        "\tIDCUS        VARCHAR NULL,\n" +
                        "\tIDSANPHAM    VARCHAR NOT NULL,\n" +
                        "\tIDVoucher    VARCHAR null,\n" +
                        "\tSOLUONG      INTEGER CHECK(SOLUONG > 0) NOT NULL," +
                        "\tDONGIA       REAL NULL,\n" +
                        "\tFOREIGN KEY (IDCUS) REFERENCES ACCOUNT(TAIKHOAN),\n" +
                        "\tFOREIGN KEY (IDSANPHAM) REFERENCES SANPHAM(MASP)\n" +
                        "\tFOREIGN KEY (IDVoucher) REFERENCES VOUCHER(MAVOUCHER)\n" +
                        ")"
        );
        //endregion
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
        boolean check = CheckExists(taikhoan, "Account");
        if (check){
            this.WriteQuery("Insert into ACCOUNT Values" +
                    "('" + taikhoan + "', '" + matkhau + "', '" + quyenhan + "', '" + ten + "', '" + sdt + "', '" + gmail + "','" + diachi + "');");
        }
        return check;
    }
    public boolean AddRole(String role, String content){
        boolean check = CheckExists(role, "[ROLE]");
        if (check){
            this.WriteQuery("Insert into [ROLE] Values" +
                    "('" + role + "', '" + content + "');");
        }
        return check;
    }
    public boolean AddProduct(String MASP, String TENSP, String PHANLOAI, Integer SOLUONG, String NOINHAP, String NOIDUNG, double DONGIA, int HINHANH){
        boolean check = CheckExists(MASP, "SANPHAM");
        if (check){
            LocalDate currentDate = LocalDate.now(); //định dạng ngày sẽ là "YYYY/MM/dd"
            this.WriteQuery("Insert into SANPHAM Values" +
                    "('" + MASP + "', '" + TENSP + "', '" + PHANLOAI + "', '" + SOLUONG + "', '" + NOINHAP + "', '" + NOIDUNG + "', '" + DONGIA + "', '" + HINHANH + "', '" + currentDate + "');");
        }
        return check;
    }
    public boolean AddCategory(String NAME, String CONTENT){
        boolean check = CheckExists(NAME, "[CATEGORY]");
        if (check){
            this.WriteQuery("Insert into [CATEGORY] Values" +
                    "('" + NAME + "', '" + CONTENT + "');");
        }
        return check;
    }
    public boolean AddBill(String TAIKHOANCUS, String ADDRESSDELIVERRY) {
        try {
            Calendar c = Calendar.getInstance();
            String DATEORDER = Integer.toString(c.get(Calendar.DAY_OF_MONTH)) + "/" + Integer.toString(c.get(Calendar.MONTH) + 1) + "/" + Integer.toString(c.get(Calendar.YEAR));
            SQLiteDatabase db = getReadableDatabase();
            this.WriteQuery("Insert into [CATEGORY] (DATEORDER, TAIKHOANCUS, ADDRESSDELIVERRY) Values" +
                    "('" + DATEORDER + "', '" + TAIKHOANCUS + "', '" + ADDRESSDELIVERRY + "');");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean AddVoucher(String MAVOUCHER, String NOIDUNG, String HSD, double GIAM){
        boolean check = CheckExists(MAVOUCHER, "VOUCHER");
        if (check){
            this.WriteQuery("Insert into VOUCHER values" +
                    "('"+ MAVOUCHER +"', '"+ NOIDUNG +"', '"+ HSD +"', "+ GIAM +");");
        }
        return check;
    }
    public boolean AddVoucherProduct(String MAVOUCHER, String MASP){
        try{
            Cursor cursor = this.GetData(
                    "Select*" +
                            " From VOUCHER_DETAIL" +
                            " Where MAVOUCHER = '" + MAVOUCHER + "'" +
                            " and MASP = '" + MASP + "'"
            );
            if (!cursor.moveToFirst()){
                this.WriteQuery("Insert into VOUCHER_DETAIL values" +
                        "('" + MAVOUCHER + "', '" + MASP + "')");
                return true;
            }
            return false;
        }
        catch (Exception e){
            return false;
        }
    }
    public long addCartList(GioHang gioHang) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IDCUS", gioHang.getIdCus());
        values.put("IDSANPHAM", gioHang.getIdSanPham());
        values.put("IDVoucher", gioHang.getIdVoucher());
        values.put("SOLUONG", gioHang.getSoLuong());
        values.put("DONGIA", gioHang.getDonGia());
        return db.insert("CARTLIST", "IDCUS", values);
    }
    public long updateCartList(GioHang gioHang) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IDCARTLIST", gioHang.getIdCartList());
        values.put("IDCUS", gioHang.getIdCus());
        values.put("IDSANPHAM", gioHang.getIdSanPham());
        values.put("IDVoucher", gioHang.getIdVoucher());
        values.put("SOLUONG", gioHang.getSoLuong());
        values.put("DONGIA", gioHang.getDonGia());

        long kq = db.update("CARTLIST", values, "IDCARTLIST=?",  new String[]{gioHang.getIdCartList().toString()});
        db.close();
        if(kq <= 0){
            return -1;//Thêm thất bại
        }
        return 1;//Thêm thành công
    }

    public void updateUser(Account account) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
//            values.put("TAIKHOAN",account.getTAIKHOAN());
//            values.put("MATKHAU",account.getMATKHAU());
//            values.put("QUYENHAN",account.getQUYENHAN());
            values.put("TEN", account.getTEN());
            values.put("SDT", account.getSDT());
            values.put("GMAIL", account.getGMAIL());
            values.put("DIACHI",account.getDIACHI());

           long check = db.update("ACCOUNT",values,"TAIKHOAN=?",new String[]{account.getTAIKHOAN().toString()});
        //long check = db.insert("ACCOUNT",null,values);
//            if (check != -1){
//                Toast.makeText(context,"Saved", Toast.LENGTH_SHORT).show();
//                db.close();
//
//            }else {
//                Toast.makeText(context, "Chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
//            }
    }

    public Cursor getUser(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from ACCOUNT",null);
        return cursor;
    }

    public long changePassword(String user, String oldPass, String newPass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MATKHAU", newPass);
        return db.update("ACCOUNT", values, "TAIKHOAN=?", new String[]{user});
    }

}
