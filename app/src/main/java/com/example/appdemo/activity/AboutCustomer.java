package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.appdemo.R;

public class AboutCustomer extends AppCompatActivity {
    TextView tvHS, tvPI, tvLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_customer);
        AnhXa();
        OnClick();
    }
    private  void AnhXa(){
        tvHS = findViewById(R.id.tvHS);
        tvPI = findViewById(R.id.tvPI);
        tvLogout = findViewById(R.id.tvLogout);
    }
    private void OnClick(){
        tvHS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HistoryShopping.class);
                startActivity(i);
            }
        });
        tvPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ProifileActivity.class);
                startActivity(i);
            }
        });
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DangXuatActivity.class);
                startActivity(i);
            }
        });
    }
}