package com.example.appdemo.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdemo.Class.QLSanPham;
import com.example.appdemo.Class.QLVoucher;
import com.example.appdemo.Class.Voucher;
import com.example.appdemo.R;
import com.example.appdemo.activity.DangXuatActivity;
import com.example.appdemo.model.DatabaseHelper;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class QLVoucherActivity extends AppCompatActivity {
    ListView ListView;
    Toolbar mToolBar;
    EditText edtMaVCh, edtGiam, edtHSD, edtContent;
    Button btnXoa,btnSua,btnThem,btnHienthi, btnPickDate;
    QLVoucher qlVoucher;
    TextView tvDatePicker;
    List<String> list = new ArrayList<>();
    LocalDate HSD;
    DatabaseHelper db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlvoucher);
        AnhXa();

        //hiển thị dữ liệu khi chạy chương trình
        qlVoucher = new QLVoucher(QLVoucherActivity.this);
        list = qlVoucher.getAllVoucherToString();
        ArrayAdapter adapter =new ArrayAdapter(QLVoucherActivity.this, android.R.layout.simple_list_item_1,list);
        ListView.setAdapter(adapter);
        //Button chọn hạn sử dụng
        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ngày hiện tại để thiết lập giá trị mặc định cho DatePicker
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Hiển thị DatePickerDialog để cho phép người dùng chọn ngày
                DatePickerDialog datePickerDialog = new DatePickerDialog(QLVoucherActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Xử lý ngày được chọn
                        String selectedDay = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        HSD = LocalDate.parse(year + "-" + ((monthOfYear+"").length() == 1 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "-" + ((dayOfMonth+"").length() == 1 ? "0" + (dayOfMonth + 1) : (dayOfMonth + 1)));
                        tvDatePicker.setText("Hạn sử dụng: " + selectedDay);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        //button thêm
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String maVoucher = edtMaVCh.getText().toString();
                    double giam = Double.parseDouble(edtGiam.getText().toString())/100;
                    String NOIDUNG = edtContent.getText().toString();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    if (HSD.equals("") || maVoucher.equals("") || giam == 0){
                        Toast.makeText(getApplicationContext(), "Mã voucher, mức giảm và hạn dùng là bắt buộc!!", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        boolean check = db.AddVoucher(maVoucher, NOIDUNG, HSD.toString(), giam);
                        if (check)
                            Toast.makeText(getApplicationContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Mã voucher đã tồn tại!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //Hiển thị lại danh sách listview
                        qlVoucher = new QLVoucher(QLVoucherActivity.this);
                        list = qlVoucher.getAllVoucherToString();
                        ArrayAdapter adapter =new ArrayAdapter(QLVoucherActivity.this, android.R.layout.simple_list_item_1,list);
                        ListView.setAdapter(adapter);
                        return;
                    }
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra trong quá trình thêm voucher, vui lòng kiểm tra và thử lại!", Toast.LENGTH_LONG).show();
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
                    //Hiển thị lại danh sách listview
                    qlVoucher = new QLVoucher(QLVoucherActivity.this);
                    list = qlVoucher.getAllVoucherToString();
                    ArrayAdapter adapter =new ArrayAdapter(QLVoucherActivity.this, android.R.layout.simple_list_item_1,list);
                    ListView.setAdapter(adapter);
                    return;
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
                    //Hiển thị lại danh sách listview
                    qlVoucher = new QLVoucher(QLVoucherActivity.this);
                    list = qlVoucher.getAllVoucherToString();
                    ArrayAdapter adapter =new ArrayAdapter(QLVoucherActivity.this, android.R.layout.simple_list_item_1,list);
                    ListView.setAdapter(adapter);
                    return;
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
                Intent Logout = new Intent(QLVoucherActivity.this, DangXuatActivity.class);
                startActivity(Logout);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void AnhXa(){
        edtMaVCh = (EditText) findViewById(R.id.edtMVCH);
        edtGiam= (EditText) findViewById(R.id.edtGiam);
        btnPickDate = (Button) findViewById(R.id.btnPickDate);
        edtContent = findViewById(R.id.edtContent);
        btnXoa=(Button)findViewById(R.id.btnXoaVC);
        btnSua=(Button)findViewById(R.id.btnSuaVC);
        btnThem=(Button)findViewById(R.id.btnThemVC);
        ListView = (ListView) findViewById(R.id.listVch);
        mToolBar =(Toolbar) findViewById(R.id.toolbarVch);
        tvDatePicker = findViewById(R.id.tvDatePicker);
        db = new DatabaseHelper(this, "DBFlowerShop.sqlite", null, 1);
        setSupportActionBar(mToolBar);
    }
}