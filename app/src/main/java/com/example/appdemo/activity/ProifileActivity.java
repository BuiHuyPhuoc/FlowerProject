package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdemo.R;
import com.example.appdemo.model.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProifileActivity extends AppCompatActivity {

    DatabaseHelper db;

    Toolbar toolbar;
    TextView userName,fullName,phoneNumber,email,btnEdit,btnChangeps;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proifile);
        anhxa();
        actionBar();

//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent editProfile = new Intent(getApplicationContext(), UploadActivity.class);
//                startActivity(editProfile);
//            }
//        });
//
//        btnChangeps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
        DatabaseHelper dbHelper = new DatabaseHelper(this, "DBFlowerShop.sqlite", null, 1);
//        //Cursor cursor = dbHelper.getUser();
//
//        if (cursor.getCount() == 0)
//        {
//            Toast.makeText(this,"No Profile Details",Toast.LENGTH_SHORT).show();
//        }else {
//            while (cursor.moveToNext()){
//                fullName.setText(""+cursor.getString(1));
//                email.setText(""+cursor.getString(2));
//                phoneNumber.setText(""+cursor.getString(3));
//
//
//            }
//        }

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ProifileActivity.this,UploadActivity.class);
//                startActivity(intent);
//            }
//        });
    }


    private void actionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);
//        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        this.getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void anhxa(){
        toolbar = findViewById(R.id.toolbarProfile);
        userName = findViewById(R.id.tvUsername);
        fullName = findViewById(R.id.tvFullname);
        phoneNumber = findViewById(R.id.tvPhone);
        email = findViewById(R.id.tvEmail);
        btnEdit = findViewById(R.id.btnEdit);
        btnChangeps = findViewById(R.id.btnChangePass);
//        fab = findViewById(R.id.fa)
    }


}