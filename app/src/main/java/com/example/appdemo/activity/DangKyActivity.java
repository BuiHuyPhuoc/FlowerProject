package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdemo.R;
import com.example.appdemo.model.DatabaseHelper;

public class DangKyActivity extends AppCompatActivity {
    //Activity đăng kí tài khoản mới
    DatabaseHelper db;
    Button mRegisterBack, mRegisterSubmit;
    CheckBox mRegisterAcceptTerm;
    EditText mregisterAddress, mregisterGmail, mregisterPhonenumber, mregisterName, mregisterPassword, mregisterUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        db = new DatabaseHelper(this, "DBFlowerShop.sqlite", null, 1);
        mRegisterBack = (Button) findViewById(R.id.RegisterBack);
        mRegisterSubmit = (Button) findViewById(R.id.RegisterSubmit);
        mRegisterAcceptTerm = (CheckBox) findViewById(R.id.RegisterAcceptTerm)  ;
        mregisterAddress = (EditText) findViewById(R.id.registerAddress);
        mregisterGmail = (EditText) findViewById(R.id.registerGmail);
        mregisterPhonenumber = (EditText) findViewById(R.id.registerPhonenumber);
        mregisterName = (EditText) findViewById(R.id.registerName);
        mregisterPassword = (EditText) findViewById(R.id.registerPassword);
        mregisterUser = (EditText) findViewById(R.id.registerUser);
        mRegisterBack.setOnClickListener(onClick_RegisterBack);
        mRegisterSubmit.setOnClickListener(onClick_mRegisterSubmit);
    }
    public View.OnClickListener onClick_mRegisterSubmit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String taikhoan = mregisterUser.getText().toString();
            String matkhau = mregisterPassword.getText().toString();
            String hoten = mregisterName.getText().toString();
            String sdt = mregisterPhonenumber.getText().toString();
            String gmail = mregisterGmail.getText().toString();
            String diachi = mregisterAddress.getText().toString();
            //Kiểm tra chuỗi sdt có phải số không
            boolean is_Number = true;
            String[] token_sdt = sdt.split("\\.");
            for(int i = 0; i < token_sdt.length; i++){
                is_Number = isNumber(token_sdt[i]);
                if (is_Number == false) break;
            }
            if (sdt.length() == 0 || is_Number == false){
                Toast.makeText(getApplicationContext(), "Phone number is invalid. Please check again!", Toast.LENGTH_LONG).show();
                return;
            }
            if (taikhoan.length() == 0 || matkhau.length() == 0 || hoten.length() == 0){
                Toast.makeText(DangKyActivity.this, "Username, Password and Name are required", Toast.LENGTH_LONG).show();
                return;
            }
            if (mRegisterAcceptTerm.isChecked() == false){
                Toast.makeText(DangKyActivity.this  , "You must accepted the Terms and Conditions once.", Toast.LENGTH_LONG).show();
                return;
            }
            if (db.AddAccount(taikhoan, matkhau, "customer", hoten, sdt, gmail, diachi) == true){
                AlertDialog.Builder builder = new AlertDialog.Builder(DangKyActivity.this);
                builder.setMessage("Create Account Success.");
                builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(DangKyActivity.this, DangXuatActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("user", mregisterUser.getText().toString());
                        bundle.putString("pass", mregisterPassword.getText().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                // Create the AlertDialog object
                builder.create().show();
                return;
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(DangKyActivity.this);
                builder.setMessage("Username already exists.");
                builder.setPositiveButton("Forgot Password?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(DangKyActivity.this, ForgotPassword.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("New Username", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                // Create the AlertDialog object
                builder.create().show();
                return;
            }
        }
    };
    public View.OnClickListener onClick_RegisterBack = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(DangKyActivity.this, DangXuatActivity.class);
            startActivity(i);
        }
    };
    //Hàm kiểm tra một chuỗi có phải là số không ?
    public boolean isNumber(String x){
        for (int i = 0; i < x.length(); i++) {
            if (Character.isLetter(x.charAt(i))) {
                return false;
            }
            if (i + 1 == x.length()) {
                return true;
            }
        }
        return false;
    }
}