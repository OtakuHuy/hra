package com.example.ttnn;

public class Listview_DanhSachBenhAn_Item {
    String MaBenhAn, Ngaykham, Thangkham, NamKham;

    public Listview_DanhSachBenhAn_Item(String maBenhAn, String ngaykham, String thangkham, String namKham) {
        MaBenhAn = maBenhAn;
        Ngaykham = ngaykham;
        Thangkham = thangkham;
        NamKham = namKham;
    }

    public String getMaBenhAn() {
        return MaBenhAn;
    }

    public void setMaBenhAn(String maBenhAn) {
        MaBenhAn = maBenhAn;
    }

    public String getNgaykham() {
        return Ngaykham;
    }

    public void setNgaykham(String ngaykham) {
        Ngaykham = ngaykham;
    }

    public String getThangkham() {
        return Thangkham;
    }

    public void setThangkham(String thangkham) {
        Thangkham = thangkham;
    }

    public String getNamKham() {
        return NamKham;
    }

    public void setNamKham(String namKham) {
        NamKham = namKham;
    }
}
