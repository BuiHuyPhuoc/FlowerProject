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

import com.example.appdemo.Class.Account;
import com.example.appdemo.Class.QLSanPham;
import com.example.appdemo.Class.QLVoucher;
import com.example.appdemo.Class.SanPham;
import com.example.appdemo.Class.Voucher;
import com.example.appdemo.R;

import java.util.ArrayList;
import java.util.List;

public class QLVoucherActivity extends AppCompatActivity {
    ListView ListView;
    Toolbar mToolBar;
    EditText edtMaVCh, edtGiam, edtHSD;
    Button btnXoa,btnSua,btnThem,btnHienthi;
    QLVoucher qlVoucher;
    List<String> list = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlvoucher);
        edtMaVCh = (EditText) findViewById(R.id.edtMVCH);
        edtGiam= (EditText) findViewById(R.id.edtGiam);
        edtHSD= (EditText) findViewById(R.id.edtHSD);
        btnXoa=(Button)findViewById(R.id.btnXoaVC);
        btnSua=(Button)findViewById(R.id.btnSuaVC);
        btnThem=(Button)findViewById(R.id.btnThemVC);
        btnHienthi=(Button)findViewById(R.id.btnShowVC);
        ListView = (ListView) findViewById(R.id.listVch);
        mToolBar =(Toolbar) findViewById(R.id.toolbarVch);
        setSupportActionBar(mToolBar);

        //hiển thị dữ liệu khi chạy chương trình
        qlVoucher = new QLVoucher(QLVoucherActivity.this);
        list=qlVoucher.getAllVoucherToString();
        ArrayAdapter adapter =new ArrayAdapter(QLVoucherActivity.this, android.R.layout.simple_list_item_1,list);
        ListView.setAdapter(adapter);

        //button hiển thị
        btnHienthi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qlVoucher = new QLVoucher(QLVoucherActivity.this);
                list=qlVoucher.getAllVoucherToString();
                ArrayAdapter adapter =new ArrayAdapter(QLVoucherActivity.this, android.R.layout.simple_list_item_1,list);
                ListView.setAdapter(adapter);
            }
        });
        //button thêm
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Voucher s = new Voucher();// tạo đối tượng chứa dữ liệu người dùng nhập
                //đưa dữ liệu vào đối tượng
                if(edtGiam.getText().toString().isEmpty()){
                    Toast.makeText(QLVoucherActivity.this,"Bạn chưa nhập giá giảm",Toast.LENGTH_LONG).show();
                }else s.setGIAM(Integer.parseInt(edtGiam.getText().toString()));
                s.setMAVOUCHER(edtMaVCh.getText().toString());
                s.setHANSD(edtHSD.getText().toString());
                //gọi hàm thêm
                int kq = qlVoucher.ThemVoucher(s);
                if(kq==-1){
                    Toast.makeText(QLVoucherActivity.this,"Thêm thất bại",Toast.LENGTH_LONG).show();
                }
                if (kq==1){
                    Toast.makeText(QLVoucherActivity.this,"Thêm thành công",Toast.LENGTH_LONG).show();
                }
            }
        });
        //Button Xóa
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MAVOUVHER = edtMaVCh.getText().toString();
                //gọi hàm Xóa
                int kq = qlVoucher.XoaVoucher(MAVOUVHER);
                if(kq==-1){
                    Toast.makeText(QLVoucherActivity.this,"Xóa thất bại",Toast.LENGTH_LONG).show();
                }
                if (kq==1){
                    Toast.makeText(QLVoucherActivity.this,"Xóa thành công",Toast.LENGTH_LONG).show();
                }
            }
        });
        //Button Sửa
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Voucher s = new Voucher();// tạo đối tượng chứa dữ liệu người dùng nhập
                //đưa dữ liệu vào đối tượng
                s.setMAVOUCHER(edtMaVCh.getText().toString());
                s.setGIAM(Integer.parseInt(edtGiam.getText().toString()));
                s.setHANSD(edtHSD.getText().toString());
                //gọi hàm Sửa
                int kq = qlVoucher.SuaVoucher(s);
                if(kq==-1){
                    Toast.makeText(QLVoucherActivity.this,"Sửa thất bại",Toast.LENGTH_LONG).show();
                }
                if (kq==1){
                    Toast.makeText(QLVoucherActivity.this,"Sửa thành công",Toast.LENGTH_LONG).show();
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
                Intent KH = new Intent(QLVoucherActivity.this, QLAccountActivity.class);
                startActivity(KH);
                return true;
            case R.id.QL:
                Intent QL = new Intent(QLVoucherActivity.this, AdminActivity.class);
                startActivity(QL);
                return true;
            case R.id.SanPham:
                Intent HD = new Intent(QLVoucherActivity.this, QLSanPham.class);
                startActivity(HD);
                return true;
            case R.id.Logout:
                Intent Logout = new Intent(QLVoucherActivity.this, LoginActivity.class);
                startActivity(Logout);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}