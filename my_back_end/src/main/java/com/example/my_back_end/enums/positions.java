package com.example.my_back_end.enums;

public enum positions {
    //TODO:获取用户角色。0：系统管理员 1：生产部经理 2：组长 3：质检部经理 4：质检员 5：行政综合部
    Admin("系统管理员"),

    Produce_manager("生产部经理"),

    GroupLeader("组长"),

    Quality_manager("质检部经理"),

    Quality_inspector("质检员"),

    Comprehensive_depart("行政综合部"),

    Visitor("游客");


    private String value;

    positions(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
