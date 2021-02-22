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
        if(role.equals("Admin"))res=0;
        else if(role.equals("Produce_manager"))res=1;
        else if(role.equals("GroupLeader"))res=2;
        else if(role.equals("Quality_manager"))res=3;
        else if(role.equals("Quality_inspector"))res=4;
        else if(role.equals("Comprehensive_depart"))res=5;
        return res;
    }

}
