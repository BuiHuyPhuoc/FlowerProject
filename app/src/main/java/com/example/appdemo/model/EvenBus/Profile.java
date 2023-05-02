package com.example.appdemo.model.EvenBus;

public class Profile {
    String fullname,sodienthoai,email,address;

    public Profile() {
    }

    public Profile(String fullname, String sodienthoai, String email, String address) {
        this.fullname = fullname;
        this.sodienthoai = sodienthoai;
        this.email = email;
        this.address = address;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
