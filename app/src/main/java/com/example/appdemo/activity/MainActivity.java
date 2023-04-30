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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdemo.Class.StatusLogin;
import com.example.appdemo.R;
import com.example.appdemo.Utils;
import com.example.appdemo.adapter.MenuAdapter;
import com.example.appdemo.adapter.SanPhamAdapter;
import com.example.appdemo.model.DatabaseHelper;
import com.example.appdemo.model.ItemMenu;
import com.example.appdemo.model.SanPhamMoi;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.nio.BufferUnderflowException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //Test Github
    DatabaseHelper db; //Khởi tạo database
    StatusLogin status;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    RecyclerView listsaleProduct;
    NavigationView navigationView;
    ListView lvManHinhChinh;
    DrawerLayout drawerLayout;
    MenuAdapter adapter = new MenuAdapter(this);
    List<SanPhamMoi> mangSpMoi = new ArrayList<SanPhamMoi>();
    List<SanPhamMoi> saleProducts = new ArrayList<SanPhamMoi>();
    SanPhamAdapter spAdapter;
    NotificationBadge badge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Tạo database
        db = new DatabaseHelper(this, "DBFlowerShop.sqlite", null, 1);

//        //Reset Nội dung trong database, chỉ kích hoạt khi muốn reset các bảng
        db.WriteQuery("Drop table if exists CARTLIST");
        db.WriteQuery("Drop table if exists VOUCHER_DETAIL");
        db.WriteQuery("Drop table if exists VOUCHER");
        db.WriteQuery("Drop table if exists BILLDETAIL");
        db.WriteQuery("Drop table if exists BILL");
        db.WriteQuery("Drop table if exists SANPHAM");
        db.WriteQuery("Drop table if exists [CATEGORY]");
        db.WriteQuery("Drop table if exists ACCOUNT");
        db.WriteQuery("Drop table if exists [ROLE]");
////
        //region Tạo bảng ROLE: Quyền hạn
        db.WriteQuery("CREATE TABLE IF NOT EXISTS [ROLE] (" +
                "QUYENHAN VARCHAR PRIMARY KEY NOT NULL," +
                "NOIDUNG Text NOT NULL)");
        //Thêm dữ liệu vào bảng [ROLE]
        db.AddRole("admin", "Quản trị viên");
        db.AddRole("customer", "Khách hàng");
        //endregion

        //region Tạo bảng ACCOUNT: chứa các tài khoản
        db.WriteQuery("CREATE TABLE IF NOT EXISTS ACCOUNT (\n" +
                "\tTAIKHOAN VARCHAR PRIMARY KEY NOT NULL,\n" +
                "\tMATKHAU VARCHAR NOT NULL,\n" +
                "\tQUYENHAN VARCHAR NOT NULL, \n" +
                "\tTEN VARCHAR,\n" +
                "\tSDT VARCHAR,\n" +
                "\tGMAIL VARCHAR,\n" +
                "\tDIACHI VARCHAR,\n" +
                "\tFOREIGN KEY (QUYENHAN) REFERENCES [ROLE](QUYENHAN)\n" +
                ");");
        //Thêm tài khoản admin và khách hàng mẫu để test
        db.AddAccount("123", "123", "admin", "Nguyen Van A", "", "", "");
        db.AddAccount("1234", "1234", "customer", "Nguyen Thi B", "0334379439", "", "119");
        //endregion

        //region Tạo bảng CATEGORY: Phân loại sản phẩm
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS [CATEGORY] (" +
                        "NAME VARCHAR PRIMARY KEY NOT NULL, " +
                        "NOIDUNG VARCHAR);"
        );
        //Thêm một số CATEGORY
        db.AddCategory("COMBO", "Bó hoa");
        db.AddCategory("TULIP", "Hoa Tulip");
        db.AddCategory("VASE", "Bình hoa");
        //endregion

        //region Tạo bảng SẢN PHẨM: Lưu trữ sản phẩm (hoa)
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
                        "\tNGAYNHAP date,\n" +
                        "FOREIGN KEY (PHANLOAI) REFERENCES [CATEGORY](NAME)" +
                        ");"
        );
        //cách lấy dữ liệu theo mong muón datetime SELECT strftime('%d/%m/%Y', date_column) AS formatted_date FROM my_table;

        //Thêm 1 vài sản phẩm mẫu vào database
        db.AddProduct("CB001", "You Look Gorgeous", "COMBO", 10, "Đà Lạt", "ASD", 9500000, R.drawable.you_look_gorgeous);
        db.AddProduct("CB002", "Hello Sweetheart", "COMBO", 10, "Đà Lạt", "ASD", 9500000, R.drawable.hello_sweetheart);
        db.AddProduct("CB003", "Strawberry Sundea", "COMBO", 10, "Đà Lạt", "ASD", 9500000, R.drawable.strawberry_sundea);
        db.AddProduct("CB004", "Wintry Wonder", "COMBO", 10, "Đà Lạt", "ASD", 9500000, R.drawable.wintry_wonder);
        db.AddProduct("CB005", "Hopeful Romantic", "COMBO", 10, "Đà Lạt", "ASD", 9500000, R.drawable.hopeful_romantic);
        db.AddProduct("TL001", "All In Bloom", "TULIP", 10, "TPHCM", "ASD", 9500000, R.drawable.all_in_bloom);
        db.AddProduct("TL002", "Blue Day", "TULIP", 10, "TPHCM", "ASD", 9500000, R.drawable.blue_day);
        db.AddProduct("TL003", "Red Love", "TULIP", 10, "TPHCM", "ASD", 9500000, R.drawable.red_love);
        db.AddProduct("TL004", "Pure White", "TULIP", 10, "TPHCM", "ASD", 9500000, R.drawable.pure_white);
        db.AddProduct("TL005", "Pastel Tulip", "TULIP", 10, "TPHCM", "ASD", 9500000, R.drawable.pastel_tulip);
        db.AddProduct("BH001", "Hope For Love", "VASE", 10, "TPHCM", "ASD", 9500000, R.drawable.hope_for_love);
        db.AddProduct("BH002", "Big Rose", "VASE", 10, "TPHCM", "ASD", 9500000, R.drawable.big_rose);
        //endregion

        //region Tạo bảng BILL: Lưu trữ các hóa đơn của người mua
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS BILL (\n" +
                        "    ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "    DATEORDER        VARCHAR           NOT NULL,\n" +
                        "    TAIKHOANCUS            VARCHAR            NOT NULL,\n" +
                        "    ADDRESSDELIVERRY VARCHAR NOT NULL,\n" +
                        "    FOREIGN KEY (TAIKHOANCUS) REFERENCES ACCOUNT(TAIKHOAN)\n" +
                        ");"
        );
        //endregion

        //region Tạo bảng VOUCHER: Lưu trữ các voucher hiện có
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS VOUCHER(\n" +
                        "\tMAVOUCHER VARCHAR PRIMARY KEY not null,\n" +
                        "\tNOIDUNG TEXT," +
                        "\tHSD date," +
                        "\tGIAM INTEGER DEFAULT(1) Check(GIAM >= 0)\n" +
                        ");"
        );
        int year = LocalDate.now().getYear();
        db.AddVoucher("SALET5", "Sale tháng 5", year + "/05/31" , 10.0/100);
        //endregion

        //region Tạo bảng VOUCHER DETAIL: Chi tiết voucher sử dụng cho một hoặc nhiều sản phẩm cụ thể
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS VOUCHER_DETAIL(\n" +
                        "\tMAVOUCHER VARCHAR,\n" +
                        "\tMASP VARCHAR NOT NULL,\n" +
                        "\tFOREIGN KEY (MAVOUCHER) REFERENCES VOUCHER(MAVOUCHER),\n" +
                        "  FOREIGN KEY (MASP) REFERENCES SANPHAM(MASP)\n" +
                        ");"
        );
        db.AddVoucherProduct("SALET5", "CB001");
        db.AddVoucherProduct("SALET5", "CB002");
        db.AddVoucherProduct("SALET5", "CB003");
        //endregion

        //region Tạo bảng Bill_Detail: Chi tiết hóa đơn
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS BILLDETAIL (\n" +
                        "    ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                        "    MASP VARCHAR NOT NULL,\n" +
                        "    IDORDER   INTEGER not NULL,\n" +
                        "    IDVoucher VARCHAR not null, \n" +
                        "    QUANTITY  INTEGER check(QUANTITY > 0) not NULL,\n" +
                        "    UNITPRICE Real check(UNITPRICE > 0) not NULL,\n" +
                        "    TOTALPRICE Real check (TOTALPRICE > 0) not Null,\n" +
                        "    FOREIGN KEY (MASP) REFERENCES SANPHAM(MASP),\n" +
                        "    FOREIGN KEY (IDORDER) REFERENCES BILL(ID)\n" +
                        "    FOREIGN KEY (IDVoucher) REFERENCES VOUCHER(MAVOUCHER)" +
                        ");"
        );

        //endregion



        //region Tạo bảng CARTLIST: Lưu trữ giỏ hàng của người dùng, tự động cập nhật khi người dùng đăng nhập lại
        db.WriteQuery(
                "CREATE TABLE IF NOT EXISTS CARTLIST (\n" +
                        "\tIDCARTLIST   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                        "\tIDCUS        VARCHAR NULL,\n" +
                        "\tIDSANPHAM    VARCHAR NOT NULL,\n" +
                        "\tIDVoucher    VARCHAR null,\n" +
                        "\tSOLUONG      INTEGER CHECK(SOLUONG > 0) NOT NULL," +
                        "\tDONGIA       REAL NULL,\n" +
                        "\tFOREIGN KEY (IDCUS) REFERENCES ACCOUNT(TAIKHOAN),\n" +
                        "\tFOREIGN KEY (IDSANPHAM) REFERENCES SANPHAM(MASP)\n" +
                        "\tFOREIGN KEY (IDVoucher) REFERENCES VOUCHER(MAVOUCHER)\n" +
                        ")"
        );
        //endregion

        //region Linh tinh trong activity
        anhxa();
        actionBar();
        actionMenu();
        actionViewFilpper();
        AllProduct();
        SaleProduct();
        //endregion
    }
    private void AllProduct(){
        Cursor listSanPham = db.GetData(
                "Select* from SANPHAM ORDER BY NGAYNHAP asc"
        );
        while (listSanPham.moveToNext()){
            mangSpMoi.add(new SanPhamMoi(   listSanPham.getString(0),
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
        spAdapter = new SanPhamAdapter( this, mangSpMoi);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewManHinhChinh.setAdapter(spAdapter);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
    }
    private void SaleProduct(){
        Cursor listSale = db.GetData(
                "Select SANPHAM.* from SANPHAM, VOUCHER_DETAIL where SANPHAM.MASP = VOUCHER_DETAIL.MASP"
        );
        while (listSale.moveToNext()){
            saleProducts.add(new SanPhamMoi(   listSale.getString(0),
                    listSale.getString(1),
                    listSale.getString(2),
                    listSale.getInt(3),
                    listSale.getString(4),
                    listSale.getString(5),
                    listSale.getLong(6),
                    listSale.getInt(7),
                    listSale.getString(8)
            ));
        }
        spAdapter = new SanPhamAdapter( this, saleProducts);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1,LinearLayoutManager.HORIZONTAL, false);
        listsaleProduct.setAdapter(spAdapter);
        listsaleProduct.setLayoutManager(layoutManager);
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
        recyclerViewManHinhChinh = (RecyclerView) findViewById(R.id.listnewProduct);
        listsaleProduct = (RecyclerView) findViewById(R.id.listsaleProduct);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        lvManHinhChinh = (ListView) findViewById(R.id.listManHinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        status = (StatusLogin) getApplication();
        mangSpMoi = new ArrayList<>();
        if (Utils.manggiohang == null){
          Utils.manggiohang = new ArrayList<>();
        }
    }
}
