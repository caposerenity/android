package com.example.my_back_end.bl.task;

import com.example.my_back_end.enums.statuses;
import com.example.my_back_end.po.Task;
import com.example.my_back_end.vo.ResponseVO;
import com.example.my_back_end.vo.TaskVO;

import java.util.List;

public interface TaskService {
    ResponseVO addTask(TaskVO taskVO);

    ResponseVO modifyTask(TaskVO taskVO);

    List<Task> getTasks(int user_id);

    List<Task> getAllTasks();

    ResponseVO filterTasksByStatus(statuses status);
}
