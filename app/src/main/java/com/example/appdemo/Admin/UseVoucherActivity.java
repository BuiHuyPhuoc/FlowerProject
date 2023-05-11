package com.example.appdemo.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdemo.Class.QLVoucher;
import com.example.appdemo.Class.SanPham;
import com.example.appdemo.Class.Voucher;
import com.example.appdemo.R;
import com.example.appdemo.adapter.UseVoucherAdapter;
import com.example.appdemo.model.DatabaseHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UseVoucherActivity extends AppCompatActivity {
    RecyclerView lvSP;
    TextView tvMaVch, tvMucGiam, tvHanVch;
    Button btnUseVoucher;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    String maVoucher;
    QLVoucher qlVoucher;
    UseVoucherAdapter adapter;
    ArrayList<SanPham> sanPhams;
    ArrayList<SanPham> Voucher_SanPham = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_voucher);
        tvHanVch = findViewById(R.id.tvHanVch);
        tvMucGiam = findViewById(R.id.tvMucGiam);
        tvMaVch = (TextView) findViewById(R.id.tvMaVch);
        lvSP = findViewById(R.id.lvSP);
        btnUseVoucher = findViewById(R.id.btnUseVoucher);
        sanPhams = new ArrayList<>();
        qlVoucher = new QLVoucher(this);
        databaseHelper = new DatabaseHelper(this, "DBFlowerShop.sqlite", null, 1);
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Voucher voucher = (Voucher) bundle.get("VOUCHER");
            maVoucher = voucher.getMAVOUCHER();
            tvMaVch.setText("Mã voucher: " + voucher.getMAVOUCHER().toString());
            DateTimeFormatter inFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate inputDate = LocalDate.parse(voucher.getHANSD(), inFormat);
            String outputDate = inputDate.format(outFormat);
            tvHanVch.setText("Hạn sử dụng: " + outputDate);
            tvMucGiam.setText("Mức giảm: " + voucher.getGIAM()*100 + "%");
        }
        Cursor listSanPham = databaseHelper.GetData(
                "Select* from SANPHAM ORDER BY MASP ASC"
        );
        while (listSanPham.moveToNext()){
            sanPhams.add(new SanPham(listSanPham.getString(0),
                    listSanPham.getString(1),
                    listSanPham.getString(2),
                    listSanPham.getInt(3),
                    listSanPham.getString(4),
                    listSanPham.getString(5),
                    listSanPham.getDouble(6),
                    listSanPham.getInt(7)
            ));
        }
        adapter = new UseVoucherAdapter(this, sanPhams, maVoucher);
        lvSP.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lvSP.setAdapter(adapter);
        Toast.makeText(UseVoucherActivity.this, listSanPham.getCount()+"", Toast.LENGTH_SHORT).show();
        btnUseVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UseVoucherActivity.this);
                if (Voucher_SanPham.size() == 0){
                    builder.setMessage("Chưa có chọn sản phẩm nào để áp dụng voucher");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.create().show();
                } else {
                    builder.setTitle("Đồng ý thêm voucher vào " + Voucher_SanPham.size() + " sản phẩm?");
                    String content = "";
                    for(int i = 0; i < Voucher_SanPham.size(); i++){
                        content += Voucher_SanPham.get(i).getTENSP() + "\n";
                    }
                    builder.setMessage(content);
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for(int i = 0; i < Voucher_SanPham.size(); i++){
                                ContentValues values = new ContentValues();
                                values.put("MAVOUCHER", maVoucher);
                                values.put("MASP", Voucher_SanPham.get(i).getMASP());
                                sqLiteDatabase.insert("VOUCHER_DETAIL", null, values);
                            }
                            Voucher_SanPham.removeAll(Voucher_SanPham);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(UseVoucherActivity.this, "Áp dụng thành công voucher vào sản phẩm.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.create().show();
                }
            }
        });
        adapter.setOnItemClickListener(new UseVoucherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SanPham item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UseVoucherActivity.this);
                builder.setTitle("Thông báo.");
                builder.setMessage("Bạn muốn sử dụng voucher cho sản phẩm này?");
                Cursor cursor1 = databaseHelper.GetData("Select* from VOUCHER_DETAIL Where MASP = '"+item.getMASP()+"' and MAVOUCHER='"+maVoucher+"'");
                if (!Voucher_SanPham.contains(item) && !cursor1.moveToNext()){
                    builder.setNegativeButton("Sử dụng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Voucher_SanPham.add(item);
                        }
                    });
                }
                builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (Voucher_SanPham != null){
            Voucher_SanPham.removeAll(Voucher_SanPham);
        }
        if (sanPhams != null){
            sanPhams.removeAll(sanPhams);
        }
        super.onBackPressed();
    }
}