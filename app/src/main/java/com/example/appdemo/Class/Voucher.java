package com.example.appdemo.Class;

public class Voucher {
    private String MAVOUCHER;
    private Integer GIAM;
    private String HANSD;

    public Voucher(String MAVOUCHER, Integer GIAM, String HANSD) {
        this.MAVOUCHER = MAVOUCHER;
        this.GIAM = GIAM;
        this.HANSD = HANSD;
    }
    public Voucher(){}

    public String getMAVOUCHER() {
        return MAVOUCHER;
    }

    public void setMAVOUCHER(String MAVOUCHER) {
        this.MAVOUCHER = MAVOUCHER;
    }

    public Integer getGIAM() {
        return GIAM;
    }

    public void setGIAM(Integer GIAM) {
        this.GIAM = GIAM;
    }

    public String getHANSD() {
        return HANSD;
    }

    public void setHANSD(String HANSD) {
        this.HANSD = HANSD;
    }
}
