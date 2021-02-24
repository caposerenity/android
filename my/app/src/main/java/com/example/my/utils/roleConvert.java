package com.example.my.utils;

public class roleConvert {
    public static String roleNumToStr(int num){
        String res=null;
        switch (num){
            case 0:
                res="Admin";
                break;
            case 1:
                res="Produce_manager";
                break;
            case 2:
                res="GroupLeader";
                break;
            case 3:
                res="Quality_manager";
                break;
            case 4:
                res="Quality_inspector";
                break;
            case 5:
                res="Comprehensive_depart";
                break;
            case 6:
                res="Visitor";
                break;
        }
        return res;
    }
    public static int roleStrToNum(String role){
        int res=6;
        if(role.equals("Admin")||role.equals("系统管理员"))res=0;
        else if(role.equals("Produce_manager")||role.equals("生产部经理"))res=1;
        else if(role.equals("GroupLeader")||role.equals("组长"))res=2;
        else if(role.equals("Quality_manager")||role.equals("质检部经理"))res=3;
        else if(role.equals("Quality_inspector")||role.equals("质检员"))res=4;
        else if(role.equals("Comprehensive_depart")||role.equals("行政综合部"))res=5;
        return res;
    }
    public static String roleEngToCN(String role){
        String res="游客";
        if(role.equals("Admin"))res="系统管理员";
        else if(role.equals("Produce_manager"))res="生产部经理";
        else if(role.equals("GroupLeader"))res="组长";
        else if(role.equals("Quality_manager"))res="质检部经理";
        else if(role.equals("Quality_inspector"))res="质检员";
        else if(role.equals("Comprehensive_depart"))res="行政综合部";
        return res;
    }
    public static String roleCNToEng(String role){
        String res="Visitor";
        if(role.equals("系统管理员"))res="Admin";
        else if(role.equals("生产部经理"))res="Produce_manager";
        else if(role.equals("组长"))res="GroupLeader";
        else if(role.equals("质检部经理"))res="Quality_manager";
        else if(role.equals("质检员"))res="Quality_inspector";
        else if(role.equals("行政综合部"))res="Comprehensive_depart";
        return res;
    }

}
