package com.example.appdemo.Class;

import java.io.Serializable;

public class Voucher implements Serializable {
    private String MAVOUCHER;
    private double GIAM;
    private String HANSD;
    private String NOIDUNG;

    public String getNOIDUNG() {
        return NOIDUNG;
    }

    public void setNOIDUNG(String NOIDUNG) {
        this.NOIDUNG = NOIDUNG;
    }

    public Voucher(String MAVOUCHER, String NOIDUNG, String HANSD, double GIAM) {
        this.MAVOUCHER = MAVOUCHER;
        this.GIAM = GIAM;
        this.HANSD = HANSD;
        this.NOIDUNG  = NOIDUNG;
    }
    public Voucher(){}

    public String getMAVOUCHER() {
        return MAVOUCHER;
    }

    public void setMAVOUCHER(String MAVOUCHER) {
        this.MAVOUCHER = MAVOUCHER;
    }

    public double getGIAM() {
        return GIAM;
    }

    public void setGIAM(double GIAM) {
        this.GIAM = GIAM;
    }

    public String getHANSD() {
        return HANSD;
    }

    public void setHANSD(String HANSD) {
        this.HANSD = HANSD;
    }
    public String toString(){
        return "Mã Voucher: "+ this.getMAVOUCHER()+"\nGiảm giá: "+ this.getGIAM()*100 + "%" + "\nHạn sử dụng: "+ this.getHANSD();
    }
}
