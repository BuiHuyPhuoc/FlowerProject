package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.appdemo.R;
import com.example.appdemo.utils.Utils;
import com.example.appdemo.model.GioHang;
import com.example.appdemo.model.SanPhamMoi;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class ChiTietSPActivity extends AppCompatActivity {
    TextView tensp,giasp,motasp,noinhapsp;
    Button btnthem;
    ImageView imgHinhAnh;
    Spinner spinner;
    Toolbar toolbar;
    SanPhamMoi sanPhamMoi;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_spactivity);
        intView();
        actionBar();
        intData();
        Clickbtn();
    }
    private void Clickbtn(){
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themGioHang();
            }
        });
    }

    private void themGioHang() {
        if (Utils.manggiohang.size() > 0){
            boolean flag = false;
            int sl = Integer.parseInt(spinner.getSelectedItem().toString());
            for (Integer i = 0 ; i < Utils.manggiohang.size() ; i++)
            {
                //Truong72 hop trung sp
               if (Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getMASP())
               {
                   Utils.manggiohang.get(i).setSoluong(sl + Utils.manggiohang.get(i).getSoluong());
                   long gia = sanPhamMoi.getDONGIA() * Utils.manggiohang.get(i).getSoluong();
                   Utils.manggiohang.get(i).setDONGIA(gia);
                   flag = true;
               }
            }
            if(flag == false)
            {
                long gia = sanPhamMoi.getDONGIA() * sl;
                GioHang gioHang = new GioHang();
                gioHang.setDONGIA(gia);
                gioHang.setSoluong(sl);
                gioHang.setIdsp(sanPhamMoi.getMASP());
                gioHang.setTenSp(sanPhamMoi.getTENSP());
                gioHang.setHinhSp(sanPhamMoi.getHINHANH());
                Utils.manggiohang.add(gioHang);
            }
        }
        else {
            int sl = Integer.parseInt(spinner.getSelectedItem().toString());
            long gia = sanPhamMoi.getDONGIA() * sl;
            GioHang gioHang = new GioHang();
            gioHang.setDONGIA(gia);
            gioHang.setSoluong(sl);
            gioHang.setIdsp(sanPhamMoi.getMASP());
            gioHang.setTenSp(sanPhamMoi.getTENSP());
            gioHang.setHinhSp(sanPhamMoi.getHINHANH());
            Utils.manggiohang.add(gioHang);

        }
        badge.setText(String.valueOf(Utils.manggiohang.size()));
    }

    private void intData(){
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPhamMoi.getTENSP());
        motasp.setText(sanPhamMoi.getNOIDUNG());
        Glide.with(getApplicationContext()).load(sanPhamMoi.getHINHANH()).into(imgHinhAnh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá: "+decimalFormat.format(Double.parseDouble(String.valueOf(sanPhamMoi.getDONGIA()))) + " VNĐ");
        Integer[] sl = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,sl);
        spinner.setAdapter(adapterspin);
    }
    private void intView(){
        tensp = (TextView) findViewById(R.id.tvTenSp);
        giasp = (TextView) findViewById(R.id.tvGia);
        motasp = (TextView) findViewById(R.id.tvMotasp);
        noinhapsp = (TextView) findViewById(R.id.tvNoiNhap);
        btnthem = (Button) findViewById(R.id.btnthemvaoGioHang);
        spinner = (Spinner) findViewById(R.id.spinner);
        imgHinhAnh = (ImageView) findViewById(R.id.imgchitiet);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        badge = (NotificationBadge) findViewById(R.id.menu_sl);
        if (Utils.manggiohang != null){
            badge.setText(String.valueOf(Utils.manggiohang.size()));
        }
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