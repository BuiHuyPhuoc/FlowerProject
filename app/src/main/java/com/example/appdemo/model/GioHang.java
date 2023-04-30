package com.example.appdemo.model;

import java.io.Serializable;

public class GioHang{

    Integer soLuong, idCartList, hinhSanPham;
    String idVoucher, idSanPham, idCus;
    long totalMoney, donGia;
    String tenSP;
    public GioHang(Integer soLuong, Integer idCartList, String idVoucher, String idSanPham, String idCus, long donGia, String tenSP, Integer hinhSanPham) {
        this.soLuong = soLuong;
        this.idCartList = idCartList;
        this.idVoucher = idVoucher;
        this.idSanPham = idSanPham;
        this.idCus = idCus;
        this.donGia = donGia;
        this.totalMoney = soLuong*donGia;
        this.tenSP = tenSP;
        this.hinhSanPham = hinhSanPham;
    }
    public GioHang(){}

    public Integer getSoLuong() {
        return soLuong;
    }

    public Integer getHinhSanPham() {
        return hinhSanPham;
    }

    public void setHinhSanPham(Integer hinhSanPham) {
        this.hinhSanPham = hinhSanPham;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Integer getIdCartList() {
        return idCartList;
    }

    public void setIdCartList(Integer idCartList) {
        this.idCartList = idCartList;
    }

    public String getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(String idVoucher) {
        this.idVoucher = idVoucher;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getIdCus() {
        return idCus;
    }

    public void setIdCus(String idCus) {
        this.idCus = idCus;
    }

    public long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public long getDonGia() {
        return donGia;
    }

    public void setDonGia(long donGia) {
        this.donGia = donGia;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }


}
