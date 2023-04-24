package com.example.appdemo;
import android.app.Application;

import com.example.appdemo.model.GioHang;

import java.util.ArrayList;
import java.util.List;

public class ListGioHang extends Application {
    public static List<GioHang> manggiohang = new ArrayList<>();
    public Integer getLength() {
        try{
            return manggiohang.size();
        }
        catch (Exception e){
            return -1;
        }
    }

}
