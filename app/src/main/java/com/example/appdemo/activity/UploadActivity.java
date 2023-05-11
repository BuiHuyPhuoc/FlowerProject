package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdemo.Class.Account;
import com.example.appdemo.Class.StatusLogin;
import com.example.appdemo.R;
import com.example.appdemo.model.DatabaseHelper;
import com.example.appdemo.model.EvenBus.Profile;
import com.example.appdemo.model.GioHang;

public class UploadActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText uploadEmail,uploadSDT,uploadFullName,uploadAddress;

    Button buttonSave;
    Uri uri;
    DatabaseHelper db;

    StatusLogin statusLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        anhxa();
        actionBar();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuaDuLieu();
            }
        });
    }

    private void SuaDuLieu(){
        if (!uploadFullName.getText().toString().isEmpty()
                && !uploadSDT.getText().toString().isEmpty() && !uploadEmail.getText().toString().isEmpty()
                && !uploadAddress.getText().toString().isEmpty()){
            Account account = new Account();

            account.setTAIKHOAN(statusLogin.getUser().toString());
            account.setTEN(uploadFullName.getText().toString());
            account.setSDT(uploadSDT.getText().toString());
            account.setGMAIL(uploadEmail.getText().toString());
            account.setDIACHI(uploadAddress.getText().toString());
            db.updateUser(account);
            Intent i = new Intent(UploadActivity.this,ProifileActivity.class);
            startActivity(i);

        }
        else {
            Toast.makeText(this,"Empty",Toast.LENGTH_SHORT).show();
        }
    }

    private void anhxa(){

        toolbar = findViewById(R.id.toolbarProfile);
        uploadFullName = findViewById(R.id.uploadFullName);
        uploadEmail = findViewById(R.id.uploadEmail);
        uploadSDT = findViewById(R.id.uploadUserSDT);
        uploadAddress = findViewById(R.id.uploadAddress);
        buttonSave = findViewById(R.id.btnSave);
        statusLogin = (StatusLogin) getApplication();
        db = new DatabaseHelper(this, "DBFlowerShop.sqlite", null, 1);



    }
//
    private void actionBar(){
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}