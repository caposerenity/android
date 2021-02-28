package com.example.my_back_end.controller.task;


import com.example.my_back_end.bl.task.TaskService;
import com.example.my_back_end.enums.statuses;
import com.example.my_back_end.po.Task;
import com.example.my_back_end.vo.ResponseVO;
import com.example.my_back_end.vo.TaskVO;
import com.example.my_back_end.vo.UserVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/addtask")
    public ResponseVO addTask(@RequestBody TaskVO taskVO) throws JSONException {
        return taskService.addTask(taskVO);
    }

    //一个统一接口，所有task修改均使用它
    @PostMapping("/modifytask")
    public ResponseVO modifyTask(@RequestBody TaskVO taskVO) throws JSONException {
        return taskService.modifyTask(taskVO);
    }
    //质检员或小组长获取对应的task
    @GetMapping("/{user_id}/getTasks")
    public List<Task> getTasks(@PathVariable int user_id) {
        return taskService.getTasks(user_id);
    }

    @GetMapping("/getAllTasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
    //筛选对应状态的task
    @GetMapping("/{status}/getTasksByStatus")
    public ResponseVO filterTasksByStatus(@PathVariable statuses status) {
        return taskService.filterTasksByStatus(status);
    }
}
