package com.example.appdemo.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appdemo.Class.QLSanPham;
import com.example.appdemo.Class.SanPham;
import com.example.appdemo.R;
import com.example.appdemo.activity.DangXuatActivity;
import com.example.appdemo.model.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class QLSanPhamActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    ListView spListView;
    Toolbar mToolBar;
    EditText edtMa, edtTen, edtGia, edtSL, edtND, edtNN, edtHA, edtPL;
    String cate;
    Button btnXoa,btnSua,btnThem,btnHienthi;
    QLSanPham qlSanPham;
    Spinner spnCate;
    List<String> list = new ArrayList<>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlsan_pham);
        dbHelper = new DatabaseHelper(this, "DBFlowerShop.sqlite", null, 1);
        edtMa = (EditText) findViewById(R.id.edtMSP);
        edtTen= (EditText) findViewById(R.id.edtTSP);
        edtGia= (EditText) findViewById(R.id.edtGia);
        edtSL= (EditText) findViewById(R.id.edtSL);
        edtND= (EditText) findViewById(R.id.edtND);
        edtNN= (EditText) findViewById(R.id.edtNN);
        edtHA= (EditText) findViewById(R.id.edtHA);
        spnCate = findViewById(R.id.spnCate);
        btnXoa=(Button)findViewById(R.id.btnXoa);
        btnSua=(Button)findViewById(R.id.btnSua);
        btnThem=(Button)findViewById(R.id.btnThem);
        btnHienthi=(Button)findViewById(R.id.btnShow);
// Tạo dữ liệu cho Spinner
        ArrayList<String> data = new ArrayList<>();
        Cursor cursor = dbHelper.GetData("Select NOIDUNG from [CATEGORY]");
        while (cursor.moveToNext()){
            data.add(cursor.getString(0));
        }
        adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, data);
        // Đặt giao diện của Spinner

        spnCate.setAdapter(adapter);
        // Xử lý sự kiện khi một mục được chọn
        spnCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý khi một mục được chọn
                Cursor cursor = dbHelper.GetData("Select NAME from [CATEGORY] where NOIDUNG = '"+data.get(position)+"'");
                cursor.moveToFirst();
                cate = cursor.getString(0);
                Toast.makeText(getApplicationContext(), cate, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có mục nào được chọn
            }
        });

        spListView = (ListView) findViewById(R.id.listSP);
        mToolBar =(Toolbar) findViewById(R.id.toolbarSP);
        setSupportActionBar(mToolBar);

        //hiển thị dữ liệu khi chạy chương trình
        qlSanPham = new QLSanPham(QLSanPhamActivity.this);
        list=qlSanPham.getAllSanPhamToString();
        ArrayAdapter adapter =new ArrayAdapter(QLSanPhamActivity.this, android.R.layout.simple_list_item_1,list);
        spListView.setAdapter(adapter);

        //button hiển thị
        btnHienthi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qlSanPham = new QLSanPham(QLSanPhamActivity.this);
                list=qlSanPham.getAllSanPhamToString();
                ArrayAdapter adapter =new ArrayAdapter(QLSanPhamActivity.this, android.R.layout.simple_list_item_1,list);
                spListView.setAdapter(adapter);
            }
        });


        //button thêm
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SanPham s = new SanPham();// tạo đối tượng chứa dữ liệu người dùng nhập
                //đưa dữ liệu vào đối tượng
                if(edtSL.getText().toString().isEmpty()){
                    Toast.makeText(QLSanPhamActivity.this,"Bạn chưa nhập số lượng",Toast.LENGTH_LONG).show();
                }else s.setSOLUONG(Integer.parseInt(edtSL.getText().toString()));
                if(edtGia.getText().toString().isEmpty()){
                    Toast.makeText(QLSanPhamActivity.this,"Bạn chưa nhập giá",Toast.LENGTH_LONG).show();
                }else s.setDONGIA(Double.parseDouble(edtGia.getText().toString()));
                if(edtHA.getText().toString().isEmpty()){
                    Toast.makeText(QLSanPhamActivity.this,"Bạn chưa chọn hình ảnh",Toast.LENGTH_LONG).show();
                }else s.setHINHANH(Integer.parseInt(edtHA.getText().toString()));
                s.setMASP(edtMa.getText().toString());
                s.setTENSP(edtTen.getText().toString());
                s.setNOIDUNG(edtND.getText().toString());
                s.setNOINHAP(edtNN.getText().toString());
                s.setPHANLOAI(edtPL.getText().toString());
                //gọi hàm thêm
                int kq = qlSanPham.ThemSanPham(s);
                if(kq==-1){
                    Toast.makeText(QLSanPhamActivity.this,"Thêm thất bại",Toast.LENGTH_LONG).show();
                }
                if (kq==1){
                    Toast.makeText(QLSanPhamActivity.this,"Thêm thành công",Toast.LENGTH_LONG).show();
                }
            }
        });
        //Button Xóa
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MASP = edtMa.getText().toString();
                //gọi hàm Xóa
                int kq = qlSanPham.XoaSanPham(MASP);
                if(kq==-1){
                    Toast.makeText(QLSanPhamActivity.this,"Xóa thất bại",Toast.LENGTH_LONG).show();
                }
                if (kq==1){
                    Toast.makeText(QLSanPhamActivity.this,"Xóa thành công",Toast.LENGTH_LONG).show();
                }
            }
        });
        //Button Sửa
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SanPham s = new SanPham();// tạo đối tượng chứa dữ liệu người dùng nhập
                //đưa dữ liệu vào đối tượng
                s.setMASP(edtMa.getText().toString());
                s.setTENSP(edtTen.getText().toString());
                s.setDONGIA(Double.parseDouble(edtGia.getText().toString()));
                s.setSOLUONG(Integer.parseInt(edtSL.getText().toString()));
                s.setNOIDUNG(edtND.getText().toString());
                s.setNOINHAP(edtNN.getText().toString());
                s.setPHANLOAI(edtPL.getText().toString());
                s.setHINHANH(Integer.parseInt(edtHA.getText().toString()));
                //gọi hàm Sửa
                int kq = qlSanPham.SuaSanPham(s);
                if(kq==-1){
                    Toast.makeText(QLSanPhamActivity.this,"Sửa thất bại",Toast.LENGTH_LONG).show();
                }
                if (kq==1){
                    Toast.makeText(QLSanPhamActivity.this,"Sửa thành công",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuadmin, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.KH:
                Intent KH = new Intent(QLSanPhamActivity.this, QLAccountActivity.class);
                startActivity(KH);
                return true;
            case R.id.QL:
                Intent QL = new Intent(QLSanPhamActivity.this, AdminActivity.class);
                startActivity(QL);
                return true;
            case R.id.SanPham:
                Intent HD = new Intent(QLSanPhamActivity.this, QLSanPhamActivity.class);
                startActivity(HD);
                return true;
            case R.id.Logout:
                Intent Logout = new Intent(QLSanPhamActivity.this, DangXuatActivity.class);
                startActivity(Logout);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}