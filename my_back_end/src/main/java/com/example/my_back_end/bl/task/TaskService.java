package com.example.my_back_end.bl.task;

import com.example.my_back_end.enums.statuses;
import com.example.my_back_end.vo.ResponseVO;
import com.example.my_back_end.vo.TaskVO;

public interface TaskService {
    ResponseVO addTask(TaskVO taskVO);

    ResponseVO modifyTask(TaskVO taskVO);

    ResponseVO getTasks(int user_id);

    ResponseVO getAllTasks();

    ResponseVO filterTasksByStatus(statuses status);
}
