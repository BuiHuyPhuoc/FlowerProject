package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;



import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.appdemo.Class.SanPham;
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



public class SanPhamActivity extends AppCompatActivity {
    //Activity hiển thị Danh mục sản phẩm (tất cả sản phẩm)
    Toolbar toolbar;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView lvManHinhChinh;
    SearchView searchView;
    DrawerLayout drawerLayout;

    MenuAdapter adapter = new MenuAdapter(this);

    MenuAdapter adapter;
    EditText EdtSearch;

    ArrayList<ItemMenu> arrayList;
    List<SanPhamMoi> mangSpMoi = new ArrayList<SanPhamMoi>();
    SanPhamAdapter spAdapter;
    GridView gvCateList;
    CategoryAdapter categoryAdapter;
    DatabaseHelper db;
    SQLiteDatabase sqLiteDatabase;
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
        SearchItem();
        sqLiteDatabase = db.getWritableDatabase();

        //Phân loại sản phẩm
        gvCateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                              @Override
                                              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                  //lấy ra Cate được click
                                                  Category cate = (Category) parent.getItemAtPosition(position);
                                                  if (cate.getName() == "ALL") {
                                                      Cursor listSanPham = db.GetData(
                                                              "Select* from SANPHAM order by TENSP ASC"
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
                                                                  listSanPham.getInt(7),
                                                                  listSanPham.getString(8)
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
                                                                      "where PHANLOAI = '" + cate.getName() + "' " +
                                                                      "order by TENSP ASC;"
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
                                                                  listSanPham.getInt(7),
                                                                  listSanPham.getString(8)
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
                "Select* from SANPHAM order by TENSP ASC"
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
                    listSanPham.getInt(7),
                    listSanPham.getString(8)
            ));
        }
        recyclerViewManHinhChinh = (RecyclerView) findViewById(R.id.listnewProduct);
        spAdapter = new SanPhamAdapter(this, mangSpMoi);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        recyclerViewManHinhChinh.setAdapter(spAdapter);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);

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

    private void actionMenu(){

        lvManHinhChinh.setAdapter(adapter);
        //chức năng của từng item trong actionmenu
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
                        Intent gioithieu = new Intent(getApplicationContext(),GioiThieuActivity.class);
                        startActivity(gioithieu);
                        break;

                    case 3:
                        Intent dangxuat = new Intent(getApplicationContext(), DangXuatActivity.class);
                        startActivity(dangxuat);
                        break;
                }
            }
        });
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
    // Lấy danh sách sản phẩm từ database
    private List<SanPhamMoi> getAllProducts() {
        List<SanPhamMoi> products = new ArrayList<>();
        // Thực hiện câu lệnh SQL để lấy dữ liệu từ database
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM products", null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("MASP"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("TENSP"));
            @SuppressLint("Range") int price = cursor.getInt(cursor.getColumnIndex("DONGIA"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("NOIDUNG"));
            SanPhamMoi product = new SanPhamMoi(id, name, price, description);
            products.add(product);
        }
        cursor.close();
        return products;
    }
     private void SearchItem(){
        // Khởi tạo SearchManager
         SearchManager searchManager = (SearchManager) getSystemService(this.SEARCH_SERVICE);
        // Khởi tạo SearchableInfo từ ComponentName của Activity và searchable configuration trong manifest
         SearchableInfo searchableInfo = searchManager.getSearchableInfo(new ComponentName(this, SanPhamActivity.class));
        // Thiết lập SearchableInfo cho SearchView
         searchView.setSearchableInfo(searchableInfo);
         searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Xử lý yêu cầu tìm kiếm khi người dùng nhấn nút tìm kiếm
                List<SanPhamMoi> filteredProducts = getFilteredProducts(query);
                showFilteredProducts(filteredProducts);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Xử lý yêu cầu tìm kiếm khi người dùng nhập văn bản vào thanh tìm kiếm
                if(newText.isEmpty()){
                    // Nếu newText rỗng, hiển thị tất cả sản phẩm
                    HienLaiSanPham();
                }
                return true;
            }
        });
         // Thiết lập cursor adapter để hiển thị danh sách gợi ý

     }
    public void HienLaiSanPham() {
            // Nếu đang hiển thị danh sách sản phẩm tìm kiếm, xóa danh sách đó và hiển thị lại tất cả sản phẩm
            mangSpMoi.clear();
            spAdapter.notifyDataSetChanged();
            intData();
    }
    private void showFilteredProducts(List<SanPhamMoi> filteredProducts) {
        // Xóa dữ liệu cũ trong danh sách sản phẩm
        mangSpMoi.clear();

        // Thêm sản phẩm mới vào danh sách
        for (SanPhamMoi product : filteredProducts) {
            mangSpMoi.add(new SanPhamMoi(
                    product.getMASP(),
                    product.getTENSP(),
                    product.getPHANLOAI(),
                    product.getSOLUONG(),
                    product.getNOINHAP(),
                    product.getNOIDUNG(),
                    product.getDONGIA(),
                    product.getHINHANH()
            ));
        }
        // Cập nhật adapter
        spAdapter.notifyDataSetChanged();
    }
    public List<SanPhamMoi> getFilteredProducts(String searchText) {

        List<SanPhamMoi> filteredProducts = new ArrayList<>();
        for (SanPhamMoi product : mangSpMoi) {
            if (product.getTENSP().toLowerCase().contains(searchText.toLowerCase())) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }
    private void anhxa () {
        toolbar = (Toolbar) findViewById(R.id.toolbarManhinhChinh);
        recyclerViewManHinhChinh = (RecyclerView) findViewById(R.id.listnewProduct);
        lvManHinhChinh = (ListView) findViewById(R.id.listManHinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        gvCateList = (GridView) findViewById(R.id.gvCateList);
        EdtSearch = (EditText) findViewById(R.id.edtSearch);
        searchView = findViewById(R.id.SVItem);
        //tắt tự động focus vào SearchView
        searchView.clearFocus();
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
