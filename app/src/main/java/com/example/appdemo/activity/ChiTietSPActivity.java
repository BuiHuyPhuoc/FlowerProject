package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appdemo.R;
import com.example.appdemo.model.SanPhamMoi;

import java.text.DecimalFormat;

public class ChiTietSPActivity extends AppCompatActivity {
    TextView tensp,giasp,motasp;
    Button btnthem;
    ImageView imgHinhAnh;
    Spinner spinner;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_spactivity);
        intView();
        //intData();

    }
    private void intData(){
        SanPhamMoi sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPhamMoi.getTENSP());
        motasp.setText(sanPhamMoi.getNOIDUNG());
        Glide.with(getApplicationContext()).load(sanPhamMoi.getHINHANH()).into(imgHinhAnh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá: "+decimalFormat.format(Double.parseDouble(String.valueOf(sanPhamMoi.getDONGIA()))) + " VNĐ");
    }
    private void intView(){
        tensp = (TextView) findViewById(R.id.tvTenSp);
        giasp = (TextView) findViewById(R.id.tvGia);
        motasp = (TextView) findViewById(R.id.tvMotasp);
        btnthem = (Button) findViewById(R.id.btnThemvaoGioHang);
        spinner = (Spinner) findViewById(R.id.spinner);
        imgHinhAnh = (ImageView) findViewById(R.id.imgchitiet);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void actionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
    }
}