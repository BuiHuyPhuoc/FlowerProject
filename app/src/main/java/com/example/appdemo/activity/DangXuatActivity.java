package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdemo.R;
import com.example.appdemo.model.DatabaseHelper;

public class DangXuatActivity extends AppCompatActivity {
    DatabaseHelper db; //Khởi tạo database
    EditText medtUser, medtPassword;
    Button mbtnLogin;
    TextView mtvRegister, mtvForgotpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_xuat);
        db = new DatabaseHelper(this, "DBFlowerShop.sqlite", null, 1);

        medtUser = (EditText) findViewById(R.id.edtUser);
        medtPassword = (EditText) findViewById(R.id.edtPassword);
        mtvForgotpass = (TextView) findViewById(R.id.tvForgotpass);
        mtvRegister = (TextView) findViewById(R.id.tvRegister);
        mbtnLogin = (Button) findViewById(R.id.btnLogin);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            medtUser.setText(bundle.getString("user"));
            medtPassword.setText(bundle.getString("pass"));
        }
        String x =  "CREATE TABLE IF NOT EXISTS [ROLE] (" +
                "QUYENHAN VARCHAR PRIMARY KEY NOT NULL," +
                "NOIDUNG Text NOT NULL);";
        db.WriteQuery(x);
//        //Thêm dữ liệu vào bảng [ROLE]
        db.AddRole("admin", "quan tri vien");
        db.AddRole("customer", "khach hang");
//        //Tạo bảng ACCOUNT: chứa các tài khoản
        String y = "CREATE TABLE IF NOT EXISTS ACCOUNT (\n" +
                "\tTAIKHOAN VARCHAR PRIMARY KEY NOT NULL,\n" +
                "\tMATKHAU VARCHAR NOT NULL,\n" +
                "\tQUYENHAN VARCHAR NOT NULL, \n" +
                "\tTEN VARCHAR NOT NULL,\n" +
                "\tSDT VARCHAR NOT NULL,\n" +
                "\tGMAIL VARCHAR,\n" +
                "\tDIACHI VARCHAR,\n" +
                "\tFOREIGN KEY (QUYENHAN) REFERENCES [ROLE](QUYENHAN)\n" +
                ");";
        db.WriteQuery(y);
        //Thêm tài khoản admin và khách hàng mẫu để test
        db.AddAccount("123", "123", "admin", "Nguyen Van A", "", "", "");
        db.AddAccount("1234", "1234", "customer", "Nguyen Thi B", "0334379439", "", "119");
        //Tạo bảng CATEGORY: Phân loại sản phẩm
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS [CATEGORY] (" +
                        "NAME VARCHAR PRIMARY KEY NOT NULL, " +
                        "NOIDUNG VARCHAR);"
        );
        //Thêm một số CATEGORY
        db.AddCategory("COMBO", "Bó hoa");
        db.AddCategory("TULIP", "Hoa Tulip");
        db.AddCategory("VASE", "Bình hoa");
        //Tạo bảng SẢN PHẨM: Lưu trữ sản phẩm (hoa)
//        db.WriteQuery(
//                "Drop table if exists SANPHAM;"
//        );//thao tác xóa bảng và tạo lại bảng
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS SANPHAM (\n" +
                        "\tMASP VARCHAR PRIMARY KEY NOT NULL,\n" +
                        "\tTENSP VARCHAR NOT NULL,\n" +
                        "\tPHANLOAI VARCHAR NOT NULL, \n" +
                        "\tSOLUONG INTEGER NOT NULL,\n" +
                        "\tNOINHAP VARCHAR NOT NULL,\n" +
                        "\tNOIDUNG VARCHAR NULL,\n" +
                        "\tDONGIA REAL CHECK(DONGIA > 0) NOT NULL,\n" +
                        "\tHINHANH INTEGER NOT NULL,\n" +
                        "FOREIGN KEY (PHANLOAI) REFERENCES [CATEGORY](NAME)" +
                        ");"
        );
//        //Thêm 1 vài sản phẩm mẫu vào database
        db.AddProduct("CB001", "You Look Gorgeous", "COMBO", 10, "Đà Lạt", "das", 9500000, R.drawable.you_look_gorgeous);
        db.AddProduct("CB002", "Hello Sweetheart", "COMBO", 10, "Đà Lạt", "asd", 9500000, R.drawable.hello_sweetheart);
        db.AddProduct("CB003", "Strawberry Sundea", "COMBO", 10, "Đà Lạt", "ad", 9500000, R.drawable.strawberry_sundea);
        db.AddProduct("CB004", "Wintry Wonder", "COMBO", 10, "Đà Lạt", "asd", 9500000, R.drawable.wintry_wonder);
        db.AddProduct("CB005", "Hopeful Romantic", "COMBO", 10, "Đà Lạt", "asd", 9500000, R.drawable.hopeful_romantic);
        db.AddProduct("TL001", "All In Bloom", "TULIP", 10, "TPHCM", "asd", 9500000, R.drawable.all_in_bloom);
        db.AddProduct("TL002", "Blue Day", "TULIP", 10, "TPHCM", "asd", 9500000, R.drawable.blue_day);
        db.AddProduct("TL003", "Red Love", "TULIP", 10, "TPHCM", "asd", 9500000, R.drawable.red_love);
        db.AddProduct("TL004", "Pure White", "TULIP", 10, "TPHCM", "asd", 9500000, R.drawable.pure_white);
        db.AddProduct("TL005", "Pastel Tulip", "TULIP", 10, "TPHCM", "asd", 9500000, R.drawable.pastel_tulip);
        db.AddProduct("BH001", "Hope For Love", "VASE", 10, "TPHCM", "asd", 9500000, R.drawable.hope_for_love);
        db.AddProduct("BH002", "Big Rose", "VASE", 10, "TPHCM", "ads", 9500000, R.drawable.big_rose);
//        //Tạo bảng BILL: Lưu trữ các hóa đơn của người mua
        00db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS BILL (\n" +
                        "    ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                        "    DATEORDER        VARCHAR           NOT NULL,\n" +
                        "    TAIKHOANCUS            VARCHAR            NOT NULL,\n" +
                        "    ADDRESSDELIVERRY VARCHAR NOT NULL,\n" +
                        "    FOREIGN KEY (TAIKHOANCUS) REFERENCES ACCOUNT(TAIKHOAN)\n" +
                        ");"
        );
//        //Tạo bảng Bill_Detail: Chi tiết hóa đơn
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS BILLDETAIL (\n" +
                        "    ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                        "    MASP VARCHAR NOT NULL,\n" +
                        "    IDORDER   INTEGER not NULL,\n" +
                        "    QUANTITY  INTEGER check(QUANTITY > 0) not NULL,\n" +
                        "    UNITPRICE Real check(UNITPRICE > 0) not NULL,\n" +
                        "    FOREIGN KEY (MASP) REFERENCES SANPHAM(MASP),\n" +
                        "    FOREIGN KEY (IDORDER) REFERENCES BILL(ID)\n" +
                        ");"
        );
//        //Tạo bảng VOUCHER: Lưu trữ các voucher hiện có
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS VOUCHER(\n" +
                        "\tMAVOUCHER VARCHAR PRIMARY KEY not null,\n" +
                        "\tGIAM INTERGER DEFAULT(1) Check(GIAM >= 0),\n" +
                        "\tHANSD VARCHAR \n" +
                        ")"
        );

//        //Tạo bảng VOUCHER DETAIL: Chi tiết voucher sử dụng cho một hoặc nhiều sản phẩm cụ thể
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS VOUCHER_DETAIL(\n" +
                        "\tMAVOUCHER VARCHAR,\n" +
                        "\tMASP VARCHAR NOT NULL,\n" +
                        "\tFOREIGN KEY (MAVOUCHER) REFERENCES VOUCHER(MAVOUCHER),\n" +
                        "  FOREIGN KEY (MASP) REFERENCES SANPHAM(MASP)\n" +
                        ");"
        );
//        //Tạo bảng CARTLIST: Lưu trữ giỏ hàng của người dùng, tự động cập nhật khi người dùng đăng nhập lại
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS CARTLIST (\n" +
                        "\tIDCARTLIST   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                        "\tIDCUS        VARCHAR NOT NULL,\n" +
                        "\tIDSANPHAM    VARCHAR NOT NULL,\n" +
                        "\tSOLUONG      INTEGER CHECK(SOLUONG > 0) NOT NULL,\n" +
                        "\tFOREIGN KEY (IDCUS) REFERENCES ACCOUNT(TAIKHOAN),\n" +
                        "\tFOREIGN KEY (IDSANPHAM) REFERENCES SANPHAM(MASP)\n" +
                        ")"
        );
        //Xử lí onClick
        mtvRegister.setOnClickListener(onClick_tvRegister); //Hàm onClick đổi sang Activity Đăng kí
        mbtnLogin.setOnClickListener(onClick_btnLogin); //Hàm onClick xử lí đăng nhập

    }
    public View.OnClickListener onClick_tvRegister = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), DangKyActivity.class);
            startActivity(i);
        }
    };
    public View.OnClickListener onClick_btnLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String taikhoan = medtUser.getText().toString();
            String matkhau = medtPassword.getText().toString();
            //Kiểm tra nhập tài khoản chưa
            if (taikhoan.length() == 0){
                Toast.makeText(getApplicationContext(), "Nhập tài khoản", Toast.LENGTH_LONG).show();
                medtUser.requestFocus();
                return;
            }
            //Kiểm tra nhập mật khẩu chưa
            if (matkhau.length() == 0){
                Toast.makeText(getApplicationContext(), "Nhập mật khẩu", Toast.LENGTH_LONG).show();
                medtPassword.requestFocus();
                return;
            }
            //Lấy dữ liệu account
            Cursor listAccount = db.GetData(
                    "Select*" +
                            "From ACCOUNT"
            );

            String role = ""; //Lưu lại role của tài khoản khi tìm thấy
            boolean existAccount = false;
            while (listAccount.moveToNext()){
                if (taikhoan.equals(listAccount.getString(0)) && matkhau.equals(listAccount.getString(1))){
                    role = listAccount.getString(2);
                    existAccount = true;
                    break;
                }
            }
            if(existAccount){
                //Nếu tài khoản được tìm thấy có role là admin thì vô trang admin
                //ngược lại thì vào trang chủ của app
                if (role.equals("admin")){
                    Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                    startActivity(i);
                    return;
                } else {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    return;
                }
            } else {
                Toast.makeText(getApplicationContext(), "Sai mật khẩu hoặc tài khoản", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    };
}