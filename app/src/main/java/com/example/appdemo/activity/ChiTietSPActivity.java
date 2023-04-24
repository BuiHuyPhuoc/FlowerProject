package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appdemo.Class.QLGioHang;
import com.example.appdemo.Class.StatusLogin;
import com.example.appdemo.R;
import com.example.appdemo.Utils;
import com.example.appdemo.model.DatabaseHelper;
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
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    StatusLogin statusLogin;
    QLGioHang qlGioHang;

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
        String masp = sanPhamMoi.getMASP();
        String statusLogin_User = statusLogin.getUser();
        int sl = Integer.parseInt(spinner.getSelectedItem().toString());
        Cursor cursor = databaseHelper.GetData("Select* from CARTLIST where IDSANPHAM = '" + masp + "' and IDCUS = '" + statusLogin_User + "'");
        boolean checkExisted = false;
        while (cursor.moveToNext()){
            //Tìm thấy dữ liệu trong database đã tồn tại
            if (cursor.getString(2).equals(masp) && cursor.getString(1).equals(statusLogin_User)){
                GioHang gioHang = new GioHang();
                gioHang.setIdCartList(cursor.getInt(0));
                gioHang.setIdCus(cursor.getString(1));
                gioHang.setIdSanPham(cursor.getString(2));
                gioHang.setIdVoucher(null);
                gioHang.setDonGia(sanPhamMoi.getDONGIA());
                gioHang.setTenSP(sanPhamMoi.getTENSP());
                gioHang.setSoLuong(sl + cursor.getInt(4));
                gioHang.setHinhSanPham(sanPhamMoi.getHINHANH());
                long kq = databaseHelper.updateCartList(gioHang);
                if (kq == 1){
                    Toast.makeText(getApplicationContext(), "Sản phẩm đã được thêm vào giỏ hàng, chỉnh số lượng", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Sản phẩm đã được thêm vào giỏ hàng, lỗi update dữ liệu", Toast.LENGTH_SHORT).show();
                }
                checkExisted = true;
                return;
            }
        }
        if (!checkExisted){
            GioHang gioHang = new GioHang();
            gioHang.setIdCus(statusLogin_User);
            gioHang.setIdSanPham(sanPhamMoi.getMASP());
            gioHang.setIdVoucher(null);
            gioHang.setDonGia(sanPhamMoi.getDONGIA());
            gioHang.setTenSP(sanPhamMoi.getTENSP());
            gioHang.setSoLuong(sl);
            gioHang.setHinhSanPham(sanPhamMoi.getHINHANH());
            long kq = databaseHelper.addCartList(gioHang);
            if (kq == 1){
                Toast.makeText(getApplicationContext(), "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        }
        updateBadge();

//        if (cursor == null){
//            GioHang gioHang = new GioHang(sanPhamMoi.getMASP(), statusLogin_Name, "", sanPhamMoi.getTENSP(), sanPhamMoi.getDONGIA(), sanPhamMoi.getHINHANH(), sl);
//            long i = qlGioHang.themGioHang(gioHang);
//            if (i == -1){
//                Toast.makeText(this, "That bai", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Thanh cong", Toast.LENGTH_SHORT).show();
//            }
//        }
//        if (Utils.manggiohang.size() > 0){
//            boolean flag = false;
//            int sl = Integer.parseInt(spinner.getSelectedItem().toString());
//            long gia = sanPhamMoi.getDONGIA() * sl;
//            for (Integer i = 0 ; i < Utils.manggiohang.size() ; i++)
//            {
//                //Truong72 hop trung sp
//               if (Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getMASP())
//               {
//                   Utils.manggiohang.get(i).setSoluong(sl + Utils.manggiohang.get(i).getSoluong());
//                   gia = sanPhamMoi.getDONGIA() * Utils.manggiohang.get(i).getSoluong();
//                   flag = true;
//               }
//            }
//            if(flag == false)
//            {
//
//                GioHang gioHang = new GioHang();
//                gioHang.setDONGIA(sanPhamMoi.getDONGIA());
//                gioHang.setSoluong(sl);
//                gioHang.setIdsp(sanPhamMoi.getMASP());
//                gioHang.setTenSp(sanPhamMoi.getTENSP());
//                gioHang.setHinhSp(sanPhamMoi.getHINHANH());
//                Utils.manggiohang.add(gioHang);
//            }
//        }
//        else {
//            int sl = Integer.parseInt(spinner.getSelectedItem().toString());
//            long gia = sanPhamMoi.getDONGIA() * sl;
//            GioHang gioHang = new GioHang();
//            gioHang.setDONGIA(sanPhamMoi.getDONGIA());
//            gioHang.setSoluong(sl);
//            gioHang.setIdsp(sanPhamMoi.getMASP());
//            gioHang.setTenSp(sanPhamMoi.getTENSP());
//            gioHang.setHinhSp(sanPhamMoi.getHINHANH());
//            Utils.manggiohang.add(gioHang);
//
//        }
//        int toTalItem = 0;
//        for (int i = 0 ; i < Utils.manggiohang.size() ; i++){
//            toTalItem = toTalItem + Utils.manggiohang.get(i).getSoluong();
//        }
//        badge.setText(String.valueOf((toTalItem)));
    }

    private void intData(){
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPhamMoi.getTENSP());
        motasp.setText(sanPhamMoi.getNOIDUNG());
        Glide.with(getApplicationContext()).load(sanPhamMoi.getHINHANH()).into(imgHinhAnh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá: "+decimalFormat.format(Double.parseDouble(String.valueOf(sanPhamMoi.getDONGIA()))) + " Đ");
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
        qlGioHang = new QLGioHang(this);
        FrameLayout frameLayoutgiohang = findViewById(R.id.framegiohang);
        databaseHelper = new DatabaseHelper(this, "DBFlowerShop.sqlite", null, 1);
        statusLogin = (StatusLogin) getApplication();
        updateBadge();

        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(giohang);
            }
        });
        if (Utils.manggiohang != null){
            int toTalItem = 0;
            for (int i = 0 ; i < Utils.manggiohang.size() ; i++){
                toTalItem = toTalItem + Utils.manggiohang.get(i).getSoLuong();
            }
            badge.setText(String.valueOf(toTalItem));
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

    //cập nhật lại giỏ hàng sau khi xóa sp khỏi giỏ hàng

    @Override
    protected void onResume() {
        super.onResume();
        updateBadge();
    }
    private void updateBadge(){
        Cursor cursor = databaseHelper.GetData("Select* from CARTLIST");
        Integer count = 0;
        while (cursor.moveToNext()){
            count++;
        }
        badge.setText(count.toString());
    }
}