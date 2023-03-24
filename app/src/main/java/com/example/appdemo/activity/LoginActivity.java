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

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper db; //Khởi tạo database
    EditText medtUser, medtPassword;
    Button mbtnLogin;
    TextView mtvRegister, mtvForgotpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DatabaseHelper(this, "DBFlowerShop.sqlite", null, 1);
        medtUser = (EditText) findViewById(R.id.edtUser);
        medtPassword = (EditText) findViewById(R.id.edtPassword);
        mtvForgotpass = (TextView) findViewById(R.id.tvForgotpass);
        mtvRegister = (TextView) findViewById(R.id.tvRegister);
        mbtnLogin = (Button) findViewById(R.id.btnLogin);
        Bundle  bundle = getIntent().getExtras();
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
            Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(i);
        }
    };
    public View.OnClickListener onClick_btnLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String taikhoan = medtUser.getText().toString();
            String matkhau = medtPassword.getText().toString();
            if (taikhoan.length() == 0){
                Toast.makeText(getApplicationContext(), "Nhập tài khoản", Toast.LENGTH_LONG).show();
                medtUser.requestFocus();
                return;
            }
            if (matkhau.length() == 0){
                Toast.makeText(getApplicationContext(), "Nhập mật khẩu", Toast.LENGTH_LONG).show();
                medtPassword.requestFocus();
                return;
            }
            Cursor listAccount = db.GetData(
                    "Select*" +
                            "From ACCOUNT"
            );
            boolean existAccount = false;
            while (listAccount.moveToNext()){
                if (taikhoan.equals(listAccount.getString(0)) && matkhau.equals(listAccount.getString(1))){
                    existAccount = true;
                    break;
                }
            }
            if(existAccount){
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "Sai mật khẩu hoặc tài khoản", Toast.LENGTH_SHORT).show();
            }
        }
    };
}