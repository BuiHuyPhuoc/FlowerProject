package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdemo.R;
import com.example.appdemo.model.DatabaseHelper;
import com.example.appdemo.model.EvenBus.Profile;

public class UploadActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText uploaduserName,uploadEmail,uploadSDT,uploadFullName;
    Button buttonSave;
    Uri uri;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
//        anhxa();
//        actionBar();

//        buttonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                storedata();
//            }
//        });
    }

//    private void storedata(){
//        if (!uploadFullName.getText().toString().isEmpty()
//                && !uploadSDT.getText().toString().isEmpty() && !uploadEmail.getText().toString().isEmpty()){
//            db.storeData(new Profile(uploadFullName.getText().toString(),
//                    uploadSDT.getText().toString(),uploadEmail.getText().toString()));
//            Intent intent = new Intent(UploadActivity.this,ProifileActivity.class);
//            startActivity(intent);
//        }
//        else {
//            Toast.makeText(this,"Chỉnh sửa thất bại",Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void anhxa(){
//
//        toolbar = findViewById(R.id.toolbarProfile);
//        uploadFullName = findViewById(R.id.uploadFullName);
//        uploadEmail = findViewById(R.id.uploadEmail);
//        uploadSDT = findViewById(R.id.uploadUserSDT);
//        buttonSave = findViewById(R.id.btnSave);
//        db = new DatabaseHelper(this, "DBFlowerShop.sqlite", null, 1);
//    }
//
//    private void actionBar(){
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);
//        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        this.getSupportActionBar().setHomeButtonEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//    }
}