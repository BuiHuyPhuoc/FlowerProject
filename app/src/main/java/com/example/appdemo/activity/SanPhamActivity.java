package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.appdemo.R;
import com.example.appdemo.adapter.MenuAdapter;
import com.example.appdemo.adapter.SanPhamAdapter;
import com.example.appdemo.model.DatabaseHelper;
import com.example.appdemo.model.ItemMenu;
import com.example.appdemo.model.SanPhamMoi;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SanPhamActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView lvManHinhChinh;
    DrawerLayout drawerLayout;
    MenuAdapter adapter;
    ArrayList<ItemMenu> arrayList;

    List<SanPhamMoi> mangSpMoi = new ArrayList<>();

    SanPhamAdapter spAdapter;

    TextView categoryFBox;

    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);

        db = new DatabaseHelper(this, "DBFlowerShop.sqlite", null, 1);
        String x =  "CREATE TABLE IF NOT EXISTS [ROLE] (" +
                "QUYENHAN VARCHAR PRIMARY KEY NOT NULL," +
                "NOIDUNG Text NOT NULL);";
        db.WriteQuery(x);

        db.AddProduct("CB001", "You Look Gorgeous", "COMBO", 10, "Đà Lạt", "", 9500000, R.drawable.you_look_gorgeous);
        db.AddProduct("CB002", "Hello Sweetheart", "COMBO", 10, "Đà Lạt", "", 9500000, R.drawable.hello_sweetheart);
        db.AddProduct("CB003", "Strawberry Sundea", "COMBO", 10, "Đà Lạt", "", 9500000, R.drawable.strawberry_sundea);
        anhxa();
        getEventClick();
        actionBar();
        actionMenu();
        intData();
    }

    private void intData(){
        Cursor listSanPham = db.GetData(
                "Select* from SANPHAM"
        );
        while (listSanPham.moveToNext()){
            mangSpMoi.add(new SanPhamMoi(   listSanPham.getString(0),
                    listSanPham.getString(1),
                    listSanPham.getString(2),
                    listSanPham.getString(3),
                    listSanPham.getString(4),
                    listSanPham.getInt(5),
                    listSanPham.getLong(6),
                    listSanPham.getInt(7)
            ));
        }

        spAdapter = new SanPhamAdapter( this,mangSpMoi);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        recyclerViewManHinhChinh.setAdapter(spAdapter);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);

    }

    private void getEventClick(){
        lvManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(trangchu);
                        break;

                    case 1:
                        Intent sanpham = new Intent(getApplicationContext(),SanPhamActivity.class);
                        startActivity(sanpham);
                        break;


                    case 2:
                        Intent giohang = new Intent(getApplicationContext(),GioHangActivity.class);
                        startActivity(giohang);
                        break;

                    case 3:
                        Intent gioithieu = new Intent(getApplicationContext(),GioiThieuActivity.class);
                        startActivity(gioithieu);
                        break;

                    case 4:
                        Intent dangxuat = new Intent(getApplicationContext(),DangXuatActivity.class);
                        startActivity(dangxuat);
                        break;
                }
            }
        });
    }

    private void actionMenu(){
        //khoi tao list
        arrayList = new ArrayList<>();
        arrayList.add(new ItemMenu(R.drawable.baseline_home_24,"Home"));
        arrayList.add(new ItemMenu(R.drawable.product,"Product"));
        arrayList.add(new ItemMenu(R.drawable.baseline_shopping_cart_24,"Cart"));
        arrayList.add(new ItemMenu(R.drawable.introduce,"About us"));
        arrayList.add(new ItemMenu(R.drawable.baseline_logout_24,"Log Out"));


        //Khoi tao adapter
        adapter = new MenuAdapter(arrayList,R.layout.item_sanpham, this);
        lvManHinhChinh.setAdapter(adapter);
    }

    private void actionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.baseline_menu_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    private void anhxa(){
        //ánh xạ
        toolbar = (Toolbar) findViewById(R.id.toolbarManhinhChinh);
        recyclerViewManHinhChinh = (RecyclerView) findViewById(R.id.recyclerView);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        lvManHinhChinh = (ListView) findViewById(R.id.listManHinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        categoryFBox = (TextView) findViewById(R.id.categoryFBox);

    }
}