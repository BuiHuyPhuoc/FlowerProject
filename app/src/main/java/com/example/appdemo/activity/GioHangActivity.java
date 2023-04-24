package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import com.example.appdemo.R;
import com.example.appdemo.Utils;
import com.example.appdemo.adapter.GioHangAdapter;
import com.example.appdemo.adapter.EvenBus.TinhTongEvent;
import com.example.appdemo.model.GioHang;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {
    //123
    TextView giohangtrong,tongtien;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnmuahang;
    GioHangAdapter adapter;
    List<GioHang> gioHangList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        intView();
        intControl();
        totalMoney();
    }

    private void totalMoney() {
        long tongtiensp = 0;
        for (int i = 0 ; i < Utils.manggiohang.size() ; i++){
            tongtiensp = tongtiensp + (Utils.manggiohang.get(i).getDONGIA() * Utils.manggiohang.get(i).getSoluong());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
      tongtien.setText(decimalFormat.format(tongtiensp) + " VNĐ ");
    }


    private void intControl() {
        setSupportActionBar(toolbar);//lay doi tuong actonbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//bat cai icon len

        //Click vào nút icon quay lại
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);//LayoutManager: xác định ra vị trí của các item trong RecyclerView.
        recyclerView.setLayoutManager(layoutManager);
        //trường hợp giỏ hàng trống
        if (Utils.manggiohang.size() == 0){
            giohangtrong.setVisibility(View.VISIBLE);
        }
        else {
            adapter = new GioHangAdapter(getApplicationContext(),Utils.manggiohang);
            recyclerView.setAdapter(adapter);
        }
    }

    private void intView(){
        giohangtrong = findViewById(R.id.tvGioHangTrong);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleViewgiohang);
        tongtien = findViewById(R.id.tongtien);
        btnmuahang = findViewById(R.id.btnMuahang);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);//đăng ký lắng nghe sk
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    //ThreadMode:sẽ chạy trên luồng nào và nó sẽ cập nhật gì thì chạy luồng này
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventTinhTien(TinhTongEvent event){
        if (event != null){
            totalMoney();
        }
    }
}