package com.example.my.listview;

import java.io.Serializable;
import java.util.ArrayList;

//此类用于存放所有任务的具体数据
public class Task implements Serializable {
    //TODO:修改部分参数的属性
    private static final long serialVersionUID = -6099312954099962806L;
    //id
    private String task_id;
    //任务名称
    private String task_name;
    //组长
    private String group_leader;
    //质检员
    private String quality_inspector;
    //完成截止日期
    private String expected_time;
    //质检截止日期
    private String expected_exam_time;
    //创建日期
    private String create_time;
    //当前状态
    private String status;
    //备注信息
    private String comments;
    //作业完成时间
    private String finish_time;
    //质检完成时间
    private String finish_exam_time;


    public Task(String task_id, String task_name, String group_leader, String quality_inspector, String expected_time, String expected_exam_time, String create_time, String status, String comments, String finish_time, String finish_exam_time) {
        this.task_id = task_id;
        this.task_name = task_name;
        this.group_leader = group_leader;
        this.quality_inspector = quality_inspector;
        this.expected_time = expected_time;
        this.expected_exam_time = expected_exam_time;
        this.create_time = create_time;
        this.status = status;
        this.comments = comments;
        this.finish_time = finish_time;
        this.finish_exam_time = finish_exam_time;
    }

    public String getTask_id() {
        return task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public String getGroup_leader() {
        return group_leader;
    }

    public String getQuality_inspector() {
        return quality_inspector;
    }

    public String getExpected_time() {
        return expected_time;
    }

    public String getExpected_exam_time() {
        return expected_exam_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getStatus() {
        return status;
    }

    public String getComments() {
        return comments;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public String getFinish_exam_time() {
        return finish_exam_time;
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
        return getTask_name()+"      "+"截止日期"+getExpected_time();
    }
}
