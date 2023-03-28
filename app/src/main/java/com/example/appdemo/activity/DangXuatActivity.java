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