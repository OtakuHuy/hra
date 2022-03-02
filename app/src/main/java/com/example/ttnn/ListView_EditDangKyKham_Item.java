package com.example.ttnn;

public class ListView_EditDangKyKham_Item {
    String MaDangKy;
    String NgayDangKy;

    public ListView_EditDangKyKham_Item(String maDangKy, String ngayDangKy) {
        MaDangKy = maDangKy;
        NgayDangKy = ngayDangKy;
    }

    public String getMaDangKy() {
        return MaDangKy;
    }

    public void setMaDangKy(String maDangKy) {
        MaDangKy = maDangKy;
    }

    public String getNgayDangKy() {
        return NgayDangKy;
    }

    public void setNgayDangKy(String ngayDangKy) {
        NgayDangKy = ngayDangKy;
    }
}
