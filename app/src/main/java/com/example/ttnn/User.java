package com.example.ttnn;

public class User {
    int status;
    String mabhyt;

    public User(int status, String mabhyt) {
        this.status = status;
        this.mabhyt = mabhyt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMabhyt() {
        return mabhyt;
    }

    public void setMabhyt(String mabhyt) {
        this.mabhyt = mabhyt;
    }
}
