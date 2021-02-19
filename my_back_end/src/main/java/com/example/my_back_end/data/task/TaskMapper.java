package com.example.my_back_end.data.task;

import com.example.my_back_end.po.Task;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TaskMapper {
    void createNewTask(Task task);

}
