package com.example.appdemo.Class;
import android.app.Application;
import com.example.appdemo.model.GioHang;

import java.util.ArrayList;
import java.util.List;

public class Utils extends Application {
    public static ArrayList<GioHang> manggiohang = new ArrayList<>();

    public static ArrayList<GioHang> getManggiohang() {
        return manggiohang;
    }

    public static void setManggiohang(ArrayList<GioHang> manggiohang) {
        Utils.manggiohang = manggiohang;
    }
}
