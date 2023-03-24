package com.example.appdemo.model;

public class SanPhamMoi {
    String MASP, TENSP, PHANLOAI, NOINHAP, NOIDUNG;
    Integer SOLUONG, HINHANH;
    Double DONGIA;
    public SanPhamMoi(){ }
    public SanPhamMoi(String MASP, String TENSP, String PHANLOAI, String NOINHAP, String NOIDUNG, Integer SOLUONG, Integer HINHANH, Double DONGIA) {
        this.MASP = MASP;
        this.TENSP = TENSP;
        this.PHANLOAI = PHANLOAI;
        this.NOINHAP = NOINHAP;
        this.NOIDUNG = NOIDUNG;
        this.SOLUONG = SOLUONG;
        this.HINHANH = HINHANH;
        this.DONGIA = DONGIA;
    }

    public String getMASP() {
        return MASP;
    }

    public void setMASP(String MASP) {
        this.MASP = MASP;
    }

    public String getTENSP() {
        return TENSP;
    }

    public void setTENSP(String TENSP) {
        this.TENSP = TENSP;
    }

    public String getPHANLOAI() {
        return PHANLOAI;
    }

    public void setPHANLOAI(String PHANLOAI) {
        this.PHANLOAI = PHANLOAI;
    }

    public String getNOINHAP() {
        return NOINHAP;
    }

    public void setNOINHAP(String NOINHAP) {
        this.NOINHAP = NOINHAP;
    }

    public String getNOIDUNG() {
        return NOIDUNG;
    }

    public void setNOIDUNG(String NOIDUNG) {
        this.NOIDUNG = NOIDUNG;
    }

    public Integer getSOLUONG() {
        return SOLUONG;
    }

    public void setSOLUONG(Integer SOLUONG) {
        this.SOLUONG = SOLUONG;
    }

    public Integer getHINHANH() {
        return HINHANH;
    }

    public void setHINHANH(Integer HINHANH) {
        this.HINHANH = HINHANH;
    }

    public Double getDONGIA() {
        return DONGIA;
    }

    public void setDONGIA(Double DONGIA) {
        this.DONGIA = DONGIA;
    }
}
