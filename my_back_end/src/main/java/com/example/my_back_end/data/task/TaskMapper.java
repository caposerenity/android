package com.example.my_back_end.data.task;

import com.example.my_back_end.enums.statuses;
import com.example.my_back_end.po.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TaskMapper {
    void createNewTask(Task task);

    Task getTaskById(@Param("task_id") int task_id);

    void modifyTask(Task task);

    List<Task> getTasks(@Param("user_id") int user_id);

    List<Task> getAllTasks();

    List<Task> filterTasksByStatus(@Param("status")String status);
}
