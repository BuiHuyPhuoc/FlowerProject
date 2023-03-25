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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.appdemo.R;
import com.example.appdemo.adapter.CategoryAdapter;
import com.example.appdemo.adapter.MenuAdapter;
import com.example.appdemo.adapter.SanPhamAdapter;
import com.example.appdemo.model.Category;
import com.example.appdemo.model.DatabaseHelper;
import com.example.appdemo.model.ItemMenu;
import com.example.appdemo.model.SanPhamMoi;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SanPhamActivity extends AppCompatActivity {
    //Activity hiển thị Danh mục sản phẩm (tất cả sản phẩm)
    Toolbar toolbar;
    RecyclerView recyclerViewManHinhChinh;
    ListView lvManHinhChinh;
    DrawerLayout drawerLayout;
    MenuAdapter adapter;
    ArrayList<ItemMenu> arrayList;
    List<SanPhamMoi> mangSpMoi = new ArrayList<SanPhamMoi>();
    SanPhamAdapter spAdapter;
    GridView gvCateList;
    CategoryAdapter categoryAdapter;
    DatabaseHelper db;
    ArrayList<Category> cateListContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        db = new DatabaseHelper(this, "DBFlowerShop.sqlite", null, 1);
        anhxa();
        actionBar();
        actionMenu();
        intData();
        getEventClick();

        //Phân loại sản phẩm
        gvCateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                              @Override
                                              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                  //lấy ra Cate được click
                                                  Category cate = (Category) parent.getItemAtPosition(position);
                                                  if (cate.getName() == "ALL") {
                                                      Cursor listSanPham = db.GetData(
                                                              "Select* from SANPHAM"
                                                      );
                                                      //Xóa List<SanPhamMoi> cũ đi
                                                      mangSpMoi.removeAll(mangSpMoi);
                                                      //Tạo List<SanPhamMoi> mới
                                                      while (listSanPham.moveToNext()) {
                                                          mangSpMoi.add(new SanPhamMoi(
                                                                  listSanPham.getString(0),
                                                                  listSanPham.getString(1),
                                                                  listSanPham.getString(2),
                                                                  listSanPham.getInt(3),
                                                                  listSanPham.getString(4),
                                                                  listSanPham.getString(5),
                                                                  listSanPham.getLong(6),
                                                                  listSanPham.getInt(7)
                                                          ));
                                                      }
                                                      //Cập nhật vào Adapter
                                                      spAdapter.notifyDataSetChanged();
                                                  } else {
                                                      //Lọc danh sách sản phẩm có chứa PHANLOAI theo NAME của cate vừa lấy
                                                      //Lệnh SQL
                                                      //Select*
                                                      //From SANPHAM
                                                      //Where PHANLOAI = 'cate.getName()';
                                                      Cursor listSanPham = db.GetData(
                                                              "Select* " +
                                                                      "from SANPHAM " +
                                                                      "where PHANLOAI = '" + cate.getName() + "';"
                                                      );
                                                      //Xóa List<SanPhamMoi> cũ đi
                                                      mangSpMoi.removeAll(mangSpMoi);
                                                      //Tạo List<SanPhamMoi> mới
                                                      while (listSanPham.moveToNext()) {
                                                          mangSpMoi.add(new SanPhamMoi(
                                                                  listSanPham.getString(0),
                                                                  listSanPham.getString(1),
                                                                  listSanPham.getString(2),
                                                                  listSanPham.getInt(3),
                                                                  listSanPham.getString(4),
                                                                  listSanPham.getString(5),
                                                                  listSanPham.getLong(6),
                                                                  listSanPham.getInt(7)
                                                          ));
                                                      }
                                                      //Cập nhật vào Adapter
                                                      spAdapter.notifyDataSetChanged();
                                                  }
                                              }
                                          }
        );
    }

    public void intData () {
        Cursor listSanPham = db.GetData(
                "Select* from SANPHAM"
        );
        //Lấy danh sách sản phẩm hiện có trong database
        while (listSanPham.moveToNext()) {
            mangSpMoi.add(new SanPhamMoi(
                    listSanPham.getString(0),
                    listSanPham.getString(1),
                    listSanPham.getString(2),
                    listSanPham.getInt(3),
                    listSanPham.getString(4),
                    listSanPham.getString(5),
                    listSanPham.getLong(6),
                    listSanPham.getInt(7)
            ));
        }
        recyclerViewManHinhChinh = (RecyclerView) findViewById(R.id.recyclerView);
        spAdapter = new SanPhamAdapter(this, mangSpMoi);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        recyclerViewManHinhChinh.setAdapter(spAdapter);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);

    }

    private void getEventClick () {
        lvManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(trangchu);
                        break;

                    case 1:
                        Intent sanpham = new Intent(getApplicationContext(), SanPhamActivity.class);
                        startActivity(sanpham);
                        break;


                    case 2:
                        Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                        startActivity(giohang);
                        break;

                    case 3:
                        Intent gioithieu = new Intent(getApplicationContext(), GioiThieuActivity.class);
                        startActivity(gioithieu);
                        break;

                    case 4:
                        Intent dangxuat = new Intent(getApplicationContext(), DangXuatActivity.class);
                        startActivity(dangxuat);
                        break;
                }
            }
        });
    }

    private void actionMenu () {
        //khoi tao list
        arrayList = new ArrayList<>();
        arrayList.add(new ItemMenu(R.drawable.baseline_home_24, "Home"));
        arrayList.add(new ItemMenu(R.drawable.product, "Product"));
        arrayList.add(new ItemMenu(R.drawable.baseline_shopping_cart_24, "Cart"));
        arrayList.add(new ItemMenu(R.drawable.introduce, "About us"));
        arrayList.add(new ItemMenu(R.drawable.baseline_logout_24, "Log Out"));


        //Khoi tao adapter
        adapter = new MenuAdapter(arrayList, R.layout.item_sanpham, this);
        lvManHinhChinh.setAdapter(adapter);
    }

    private void actionBar () {
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


    private void anhxa () {
        toolbar = (Toolbar) findViewById(R.id.toolbarManhinhChinh);
        recyclerViewManHinhChinh = (RecyclerView) findViewById(R.id.recyclerView);
        lvManHinhChinh = (ListView) findViewById(R.id.listManHinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        gvCateList = (GridView) findViewById(R.id.gvCateList);
        //Lấy danh sách các Category hiện có
        Cursor listCate = db.GetData(
                "Select* from [CATEGORY]"
        );
        cateListContent = new ArrayList<Category>();
        cateListContent.add(new Category("ALL", "Tất cả"));
        while (listCate.moveToNext()) {
            cateListContent.add(new Category(listCate.getString(0), listCate.getString(1)));
        }
        //Tạo listview danh sách Category
        categoryAdapter = new CategoryAdapter(this, R.layout.activity_category_view, cateListContent);
        gvCateList.setAdapter(categoryAdapter);
    }
}
