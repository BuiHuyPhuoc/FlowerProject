package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appdemo.Class.HoaDon;
import com.example.appdemo.Class.QLHoaDon;
import com.example.appdemo.Class.QLSanPham;
import com.example.appdemo.Class.QLVoucher;
import com.example.appdemo.Class.Voucher;
import com.example.appdemo.R;

import java.util.ArrayList;
import java.util.List;

public class QLHoaDonActivity extends AppCompatActivity {
android.widget.ListView ListView;
    Toolbar mToolBar;
    EditText edtID, edtDate, edtTKC, edtAddress;
    Button btnXoa,btnSua,btnThem,btnHienthi;
    QLHoaDon qlHoaDon;
    List<String> list = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlhoa_don);
        edtID = (EditText) findViewById(R.id.edtIDHD);
        edtDate= (EditText) findViewById(R.id.edtDate);
        edtTKC= (EditText) findViewById(R.id.edtTKC);
        edtAddress=(EditText) findViewById(R.id.edtAddRHD);
        btnXoa=(Button)findViewById(R.id.btnXoaHD);
        btnSua=(Button)findViewById(R.id.btnSuaHD);
        btnThem=(Button)findViewById(R.id.btnThemHD);
        btnHienthi=(Button)findViewById(R.id.btnShowHD);
        ListView = (ListView) findViewById(R.id.listHD);
        mToolBar =(Toolbar) findViewById(R.id.toolbarHD);
        setSupportActionBar(mToolBar);

        //hiển thị dữ liệu khi chạy chương trình
        qlHoaDon = new QLHoaDon(QLHoaDonActivity.this);
        list=qlHoaDon.getAllHoaDonToString();
        ArrayAdapter adapter =new ArrayAdapter(QLHoaDonActivity.this, android.R.layout.simple_list_item_1,list);
        ListView.setAdapter(adapter);

        //button hiển thị
        btnHienthi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qlHoaDon = new QLHoaDon(QLHoaDonActivity.this);
                list=qlHoaDon.getAllHoaDonToString();
                ArrayAdapter adapter =new ArrayAdapter(QLHoaDonActivity.this, android.R.layout.simple_list_item_1,list);
                ListView.setAdapter(adapter);
            }
        });
        //button thêm
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HoaDon s = new HoaDon();// tạo đối tượng chứa dữ liệu người dùng nhập
                //đưa dữ liệu vào đối tượng
                s.setID(Integer.parseInt(edtID.getText().toString()));
                s.setDATEORDER(edtDate.getText().toString());
                s.setTAIKHOANCUS(edtTKC.getText().toString());
                s.setADDRESSDELIVERRY(edtAddress.getText().toString());
                //gọi hàm thêm
                int kq = qlHoaDon.ThemHoaDon(s);
                if(kq==-1){
                    Toast.makeText(QLHoaDonActivity.this,"Thêm thất bại",Toast.LENGTH_LONG).show();
                }
                if (kq==1){
                    Toast.makeText(QLHoaDonActivity.this,"Thêm thành công",Toast.LENGTH_LONG).show();
                }
            }
        });
        //Button Xóa
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer IDHD = Integer.parseInt(edtID.getText().toString());
                //gọi hàm Xóa
                int kq = qlHoaDon.XoaHoaDon(IDHD);
                if(kq==-1){
                    Toast.makeText(QLHoaDonActivity.this,"Xóa thất bại",Toast.LENGTH_LONG).show();
                }
                if (kq==1){
                    Toast.makeText(QLHoaDonActivity.this,"Xóa thành công",Toast.LENGTH_LONG).show();
                }
            }
        });
        //Button Sửa
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HoaDon s = new HoaDon();// tạo đối tượng chứa dữ liệu người dùng nhập
                //đưa dữ liệu vào đối tượng
                s.setID(Integer.parseInt(edtID.getText().toString()));
                s.setDATEORDER(edtDate.getText().toString());
                s.setTAIKHOANCUS(edtTKC.getText().toString());
                s.setADDRESSDELIVERRY(edtAddress.getText().toString());
                //gọi hàm Sửa
                int kq = qlHoaDon.SuaHoaDon(s);
                if(kq==-1){
                    Toast.makeText(QLHoaDonActivity.this,"Sửa thất bại",Toast.LENGTH_LONG).show();
                }
                if (kq==1){
                    Toast.makeText(QLHoaDonActivity.this,"Sửa thành công",Toast.LENGTH_LONG).show();
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
                Intent KH = new Intent(QLHoaDonActivity.this, QLAccountActivity.class);
                startActivity(KH);
                return true;
            case R.id.QL:
                Intent QL = new Intent(QLHoaDonActivity.this, AdminActivity.class);
                startActivity(QL);
                return true;
            case R.id.SanPham:
                Intent HD = new Intent(QLHoaDonActivity.this, QLSanPham.class);
                startActivity(HD);
                return true;
            case R.id.Logout:
                Intent Logout = new Intent(QLHoaDonActivity.this, LoginActivity.class);
                startActivity(Logout);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
