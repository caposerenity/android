package com.example.my_back_end.vo;

import com.example.my_back_end.enums.positions;

public class UserVO {
    private int user_id;
    private String name;
    private String phone;
    private String password;
    private positions position;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public positions getPosition() {
        return position;
    }

    public void setPosition(positions position) {
        this.position = position;
    }
}
