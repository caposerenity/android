package com.example.my_back_end.enums;

public enum statuses {
    wait_done("待完成"),

    wait_exam("待质检"),

    examing("质检中"),

    fail("不合格"),

    wait_submit("待提交客户"),

    submitted("已提交客户");

    private String value;

    statuses(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
