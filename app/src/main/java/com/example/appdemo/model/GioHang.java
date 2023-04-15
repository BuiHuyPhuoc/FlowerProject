package com.example.appdemo.model;

import java.io.Serializable;

public class GioHang{
    String idsp;
    String tenSp;
    Long DONGIA;
    Integer hinhSp,soluong;

    public GioHang() {
    }

    public GioHang(String idsp, String tenSp, Long DONGIA, Integer hinhSp, Integer soluong) {
        this.idsp = idsp;
        this.tenSp = tenSp;
        this.DONGIA = DONGIA;
        this.hinhSp = hinhSp;
        this.soluong = soluong;
    }

    public String getIdsp() {
        return idsp;
    }

    public void setIdsp(String idsp) {
        this.idsp = idsp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }
    public Long getDONGIA() {
        return DONGIA;
    }

    public void setDONGIA(Long DONGIA) {
        this.DONGIA = DONGIA;
    }

    public Integer getHinhSp() {
        return hinhSp;
    }

    public void setHinhSp(Integer hinhSp) {
        this.hinhSp = hinhSp;
    }

    public Integer getSoluong() {
        return soluong;
    }

    public void setSoluong(Integer soluong) {
        this.soluong = soluong;
    }
}
