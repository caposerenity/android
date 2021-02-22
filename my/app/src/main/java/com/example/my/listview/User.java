package com.example.my.listview;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String name;
    private String role;
    private String phone;

    public User(String name,String role,String phone){
        this.name=name;
        this.role=role;
        this.phone=phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }
    public static ArrayList<User> getItems() {
        ArrayList<User> items = new ArrayList<User>();
        items.add(new User("张东北大茬子","组长","13000000000"));
        items.add(new User("张西南大茬子","质检员","13000000000"));
        items.add(new User("张西北大茬子","质检部部长","13000000000"));
        return items;
    }
}
