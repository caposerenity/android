package com.example.my_back_end.controller.task;


import com.example.my_back_end.bl.task.TaskService;
import com.example.my_back_end.vo.ResponseVO;
import com.example.my_back_end.vo.TaskVO;
import com.example.my_back_end.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/addtask")
    public ResponseVO addTask(@RequestBody TaskVO taskVO) {
        return taskService.addTask(taskVO);
    }
}
