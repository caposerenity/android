package com.example.my.listview;

import java.io.Serializable;
import java.util.ArrayList;

//此类用于存放所有任务的具体数据
public class Task implements Serializable {
    private static final long serialVersionUID = -6099312954099962806L;
    //id
    private String id;
    //任务名称
    private String name;
    //组长
    private String teamleader;
    //质检员
    private String checkman;
    //完成截止日期
    private String ddl1;
    //质检截止日期
    private String ddl2;
    //创建日期
    private String maketime;
    //当前状态
    private String state;
    //备注信息
    private String note;
    //作业完成时间
    private String finish1;
    //质检完成时间
    private String finish2;


    public Task(String id,String name,String teamleader, String checkman, String ddl1,String ddl2,String maketime,String state,String note,String finish1,String finish2) {
        this.id=id;
        this.checkman=checkman;
        this.ddl1=ddl1;
        this.ddl2=ddl2;
        this.maketime=maketime;
        this.name=name;
        this.teamleader=teamleader;
        this.state=state;
        this.note=note;
        this.finish1=finish1;
        this.finish2=finish2;
    }

    public String getName() {
        return name;
    }

    public String getDdl1() {
        return ddl1;
    }

    public String getTeamleader(){
        return teamleader;
    }
    public String getCheckman(){
        return checkman;
    }
    public String getDdl2(){
        return ddl2;
    }
    public String getMaketime(){
        return maketime;
    }
    public String getTag(){
        return state;
    }
    public String getNote(){
        return note;
    }
    public String getId(){
        return id;
    }
    public String getFinish1(){
        return finish1;
    }
    public String getFinish2(){
        return finish2;
    }

    public static ArrayList<Task> getItems() {
        ArrayList<Task> items = new ArrayList<Task>();
        items.add(new Task("0","任务1","张西秀","张东南","2020-02-27 20:00","2020-02-29 12:00","2020-02-25 11:00","已提交客户","好好干嗷","2020-02-28 12:00","2020-03-01 12:00"));
        items.add(new Task("1","任务2","张西秀","张东南","2020-02-27 20:00","2020-02-29 12:00","2020-02-25 11:00","待提交客户","好好干嗷好好干嗷","2020-02-28 12:00","2020-03-01 12:00"));
        items.add(new Task("2","任务3","张西秀","张东南","2020-02-27 20:00","2020-02-29 12:00","2020-02-25 11:00","待质检","好好干嗷好好干嗷好好干嗷","2020-02-28 12:00","2020-03-01 12:00"));
        items.add(new Task("3","任务4","张西秀","张东南","2020-02-27 20:00","2020-02-29 12:00","2020-02-25 11:00","待完成","好好干嗷好好干嗷好好干嗷","2020-02-28 12:00","2020-03-01 12:00"));
        items.add(new Task("4","任务5","张西秀","张东南","2020-02-27 20:00","2020-02-29 12:00","2020-02-25 11:00","不合格","好好干嗷好好干嗷好好干嗷","2020-02-28 12:00","2020-03-01 12:00"));
        return items;
    }

    @Override
    public String toString() {
        return getName()+"      "+"截止日期"+getDdl1();
    }
}
