package com.example.appdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appdemo.R;
import com.example.appdemo.adapter.MenuAdapter;
import com.example.appdemo.model.ItemMenu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class GioiThieuActivity extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView navigationView;
    ListView lvManHinhChinh;
    DrawerLayout drawerLayout;
    MenuAdapter adapter;
    ArrayList<ItemMenu> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioi_thieu);
        anhxa();
        getEventClick();
        actionBar();
        actionMenu();
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

    private void anhxa(){
        //ánh xạ
        toolbar = (Toolbar) findViewById(R.id.toolbarManhinhChinh);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        lvManHinhChinh = (ListView) findViewById(R.id.listManHinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

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
}