package com.mendoanjoe.module5;

public class Data {
    private int id;
    private String nama;
    private String alamat;
    private String phone;

    public Data(int id, String nama, String alamat, String phone) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.phone = phone;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
