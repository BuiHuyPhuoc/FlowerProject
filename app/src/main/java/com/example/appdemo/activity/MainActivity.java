package com.example.appdemo.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdemo.R;
import com.example.appdemo.adapter.MenuAdapter;
import com.example.appdemo.adapter.SanPhamAdapter;
import com.example.appdemo.model.DatabaseHelper;
import com.example.appdemo.model.ItemMenu;
import com.example.appdemo.model.SanPhamMoi;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db; //Khởi tạo database
    //Check GitHub 2
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView lvManHinhChinh;
    DrawerLayout drawerLayout;
    MenuAdapter adapter;
    ArrayList<ItemMenu> arrayList;
    List<SanPhamMoi> mangSpMoi = new ArrayList<SanPhamMoi>();
    SanPhamAdapter spAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Tạo database
        db = new DatabaseHelper(this, "DBFlowerShop.sqlite", null, 1);
        //Tạo bảng ROLE: Quyền hạn
        String x =  "CREATE TABLE IF NOT EXISTS [ROLE] (" +
                "QUYENHAN VARCHAR PRIMARY KEY NOT NULL," +
                "NOIDUNG Text NOT NULL);";
        db.WriteQuery(x);
//        //Thêm dữ liệu vào bảng [ROLE]
        db.AddRole("admin", "quan tri vien");
        db.AddRole("customer", "khach hang");
//        //Tạo bảng ACCOUNT: chứa các tài khoản
        String y = "CREATE TABLE IF NOT EXISTS ACCOUNT (\n" +
                "\tTAIKHOAN VARCHAR PRIMARY KEY NOT NULL,\n" +
                "\tMATKHAU VARCHAR NOT NULL,\n" +
                "\tQUYENHAN VARCHAR NOT NULL, \n" +
                "\tTEN VARCHAR NOT NULL,\n" +
                "\tSDT VARCHAR,\n" +
                "\tGMAIL VARCHAR,\n" +
                "\tDIACHI VARCHAR,\n" +
                "\tFOREIGN KEY (QUYENHAN) REFERENCES [ROLE](QUYENHAN)\n" +
                ");";
        db.WriteQuery(y);
        //Thêm tài khoản admin và khách hàng mẫu để test
        db.AddAccount("123", "123", "admin", "Nguyen Van A", "", "", "");
        db.AddAccount("1234", "1234", "customer", "Nguyen Thi B", "0334379439", "", "119");
        //Tạo bảng CATEGORY: Phân loại sản phẩm
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS [CATEGORY] (" +
                        "NAME VARCHAR PRIMARY KEY NOT NULL, " +
                        "NOIDUNG VARCHAR);"
        );
        //Thêm một số CATEGORY
        db.AddCategory("COMBO", "Bó hoa");
        db.AddCategory("TULIP", "Hoa Tulip");
        db.AddCategory("VASE", "Bình hoa");
//        //Tạo bảng SẢN PHẨM: Lưu trữ sản phẩm (hoa)
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS SANPHAM (\n" +
                        "\tMASP VARCHAR PRIMARY KEY NOT NULL,\n" +
                        "\tTENSP VARCHAR NOT NULL,\n" +
                        "\tPHANLOAI VARCHAR NOT NULL, \n" +
                        "\tSOLUONG INTEGER NOT NULL,\n" +
                        "\tNOINHAP VARCHAR NOT NULL,\n" +
                        "\tNOIDUNG VARCHAR NULL,\n" +
                        "\tDONGIA REAL CHECK(DONGIA > 0) NOT NULL,\n" +
                        "\tHINHANH INTEGER NOT NULL,\n" +
                        "FOREIGN KEY (PHANLOAI) REFERENCES [CATEGORY](NAME)" +
                        ");"
        );
//        //Thêm 1 vài sản phẩm mẫu vào database
        db.AddProduct("CB001", "You Look Gorgeous", "COMBO", 10, "Đà Lạt", "", 9500000, R.drawable.you_look_gorgeous);
        db.AddProduct("CB002", "Hello Sweetheart", "COMBO", 10, "Đà Lạt", "", 9500000, R.drawable.hello_sweetheart);
        db.AddProduct("CB003", "Strawberry Sundea", "COMBO", 10, "Đà Lạt", "", 9500000, R.drawable.strawberry_sundea);
        db.AddProduct("CB004", "Wintry Wonder", "COMBO", 10, "Đà Lạt", "", 9500000, R.drawable.wintry_wonder);
        db.AddProduct("CB005", "Hopeful Romantic", "COMBO", 10, "Đà Lạt", "", 9500000, R.drawable.hopeful_romantic);
        db.AddProduct("TL001", "All In Bloom", "TULIP", 10, "TPHCM", "", 9500000, R.drawable.all_in_bloom);
        db.AddProduct("TL002", "Blue Day", "TULIP", 10, "TPHCM", "", 9500000, R.drawable.blue_day);
        db.AddProduct("TL003", "Red Love", "TULIP", 10, "TPHCM", "", 9500000, R.drawable.red_love);
        db.AddProduct("TL004", "Pure White", "TULIP", 10, "TPHCM", "", 9500000, R.drawable.pure_white);
        db.AddProduct("TL005", "Pastel Tulip", "TULIP", 10, "TPHCM", "", 9500000, R.drawable.pastel_tulip);
        db.AddProduct("BH001", "Hope For Love", "VASE", 10, "TPHCM", "", 9500000, R.drawable.hope_for_love);
        db.AddProduct("BH002", "Big Rose", "VASE", 10, "TPHCM", "", 9500000, R.drawable.big_rose);
//        //Tạo bảng BILL: Lưu trữ các hóa đơn của người mua
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS BILL (\n" +
                        "    ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                        "    DATEORDER        VARCHAR           NOT NULL,\n" +
                        "    TAIKHOANCUS            VARCHAR            NOT NULL,\n" +
                        "    ADDRESSDELIVERRY VARCHAR NOT NULL,\n" +
                        "    FOREIGN KEY (TAIKHOANCUS) REFERENCES ACCOUNT(TAIKHOAN)\n" +
                        ");"
        );
//        //Tạo bảng Bill_Detail: Chi tiết hóa đơn
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS BILLDETAIL (\n" +
                        "    ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                        "    MASP VARCHAR NOT NULL,\n" +
                        "    IDORDER   INTEGER not NULL,\n" +
                        "    QUANTITY  INTEGER check(QUANTITY > 0) not NULL,\n" +
                        "    UNITPRICE Real check(UNITPRICE > 0) not NULL,\n" +
                        "    FOREIGN KEY (MASP) REFERENCES SANPHAM(MASP),\n" +
                        "    FOREIGN KEY (IDORDER) REFERENCES BILL(ID)\n" +
                        ");"
        );
//        //Tạo bảng VOUCHER: Lưu trữ các voucher hiện có
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS VOUCHER(\n" +
                        "\tMAVOUCHER VARCHAR PRIMARY KEY not null,\n" +
                        "\tGIAM INTERGER DEFAULT(1) Check(GIAM >= 0),\n" +
                        "\tHANSD VARCHAR \n" +
                        ")"
        );

//        //Tạo bảng VOUCHER DETAIL: Chi tiết voucher sử dụng cho một hoặc nhiều sản phẩm cụ thể
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS VOUCHER_DETAIL(\n" +
                        "\tMAVOUCHER VARCHAR,\n" +
                        "\tMASP VARCHAR NOT NULL,\n" +
                        "\tFOREIGN KEY (MAVOUCHER) REFERENCES VOUCHER(MAVOUCHER),\n" +
                        "  FOREIGN KEY (MASP) REFERENCES SANPHAM(MASP)\n" +
                        ");"
        );
//        //Tạo bảng CARTLIST: Lưu trữ giỏ hàng của người dùng, tự động cập nhật khi người dùng đăng nhập lại
//
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS CARTLIST (\n" +
                        "\tIDCARTLIST   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                        "\tIDCUS        VARCHAR NOT NULL,\n" +
                        "\tIDSANPHAM    VARCHAR NOT NULL,\n" +
                        "\tSOLUONG      INTEGER CHECK(SOLUONG > 0) NOT NULL,\n" +
                        "\tFOREIGN KEY (IDCUS) REFERENCES ACCOUNT(TAIKHOAN),\n" +
                        "\tFOREIGN KEY (IDSANPHAM) REFERENCES SANPHAM(MASP)\n" +
                        ")"
        );
        anhxa();
        actionBar();
        actionMenu();
        actionViewFilpper();
        intData();
        getEventClick();
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
    private void actionViewFilpper()
    {
        List<String> mangqc = new ArrayList<>();
        mangqc.add("https://lavieestbelle.vn/image/cache/catalog/slider/DSCF6208-web-min-1400x700.jpg");
        mangqc.add("https://lavieestbelle.vn/image/cache/catalog/slider/DSCF0031-cover-web-min-1400x700.jpg");
        mangqc.add("https://lavieestbelle.vn/image/cache/catalog/slider/DSCF1267web-1400x700.jpg");
        for (int i = 0 ; i < mangqc.size() ; i++){
            ImageView imgView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangqc.get(i)).into(imgView);
            imgView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imgView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in_right = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out);
        viewFlipper.setInAnimation(slide_in_right);
        viewFlipper.setOutAnimation(slide_out);
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
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        recyclerViewManHinhChinh = (RecyclerView) findViewById(R.id.recyclerView);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        lvManHinhChinh = (ListView) findViewById(R.id.listManHinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        mangSpMoi = new ArrayList<>();
    }
}