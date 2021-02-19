package com.example.my_back_end.blImpl.task;

import com.example.my_back_end.bl.task.TaskService;
import com.example.my_back_end.data.task.TaskMapper;
import com.example.my_back_end.po.Task;
import com.example.my_back_end.po.User;
import com.example.my_back_end.vo.ResponseVO;
import com.example.my_back_end.vo.TaskVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TaskServiceImpl implements TaskService {
    private static final String DDL_NOT_EXIST = "请先设置截止日期";
    private static final String LEADER_NOT_EXIST = "请设置生产组长";
    private static final String NAMENOT_EXIST = "请设置任务名";

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public ResponseVO addTask(TaskVO taskVO) {
        Task task=new Task();
        BeanUtils.copyProperties(taskVO,task);
        if(task.getCreate_time()==null){
            Date time=new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            task.setCreate_time(time);
        }
        if(task.getExpected_time()==null||task.getExpected_exam_time()==null) {
            return ResponseVO.buildFailure(DDL_NOT_EXIST);
        }
        else if(task.getGroup_leader()==null){
            return ResponseVO.buildFailure(LEADER_NOT_EXIST);
        }
        else if(task.getTask_name()==null){
            return ResponseVO.buildFailure(NAMENOT_EXIST);
        }
        else {
            taskMapper.createNewTask(task);
            return ResponseVO.buildSuccess(true);
        }
    }
}
