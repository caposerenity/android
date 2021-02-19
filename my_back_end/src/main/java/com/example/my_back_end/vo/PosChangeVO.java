package com.example.my_back_end.vo;

import com.example.my_back_end.enums.positions;

public class PosChangeVO {
    private String phone;
    private positions newPos;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public positions getNewPos() {
        return newPos;
    }

    public void setNewPos(positions newPos) {
        this.newPos = newPos;
    }
}
