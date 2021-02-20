package com.example.my_back_end.vo;

import com.example.my_back_end.enums.statuses;

import java.util.Date;

public class TaskInfoVO {
    private Integer task_id;
    private String task_name;
    private Date create_time;
    private Date expected_time;
    private Date expected_exam_time;
    private Integer quality_inspector;
    private Integer group_leader;
    private String comments;
    private statuses status;
    private String finish_time;
    private String finish_exam_time;

    private String new_task_name;
    private Date new_expected_time;
    private Date new_expected_exam_time;
    private Integer new_quality_inspector;
    private Integer new_group_leader;
    private String new_comments;
    private statuses new_status;

}
