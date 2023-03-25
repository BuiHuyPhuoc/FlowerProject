package com.example.appdemo.model;

public class GioHang {
    String idsp,tenSp;
    Long DONGIA;
    Integer hinhSp,soluong;

    public GioHang() {
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

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
