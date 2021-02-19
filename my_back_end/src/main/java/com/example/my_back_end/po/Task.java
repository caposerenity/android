package com.example.my_back_end.po;

import com.example.my_back_end.enums.statuses;

import java.util.Date;

public class Task {
    private Integer task_id;
    private String task_name;
    private Date create_time;
    private Date expected_time;
    private Date expected_exam_time;
    private Integer quality_inspector;
    private Integer group_leader;
    private String comments;
    private String status;
    private String finish_time;
    private String finish_exam_time;


    public Integer getTask_id() {
        return task_id;
    }

    public void setTask_id(Integer task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getExpected_time() {
        return expected_time;
    }

    public void setExpected_time(Date expected_time) {
        this.expected_time = expected_time;
    }

    public Date getExpected_exam_time() {
        return expected_exam_time;
    }

    public void setExpected_exam_time(Date expected_exam_time) {
        this.expected_exam_time = expected_exam_time;
    }

    public Integer getQuality_inspector() {
        return quality_inspector;
    }

    public void setQuality_inspector(Integer quality_inspector) {
        this.quality_inspector = quality_inspector;
    }

    public Integer getGroup_leader() {
        return group_leader;
    }

    public void setGroup_leader(Integer group_leader) {
        this.group_leader = group_leader;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getFinish_exam_time() {
        return finish_exam_time;
    }

    public void setFinish_exam_time(String finish_exam_time) {
        this.finish_exam_time = finish_exam_time;
    }
}
