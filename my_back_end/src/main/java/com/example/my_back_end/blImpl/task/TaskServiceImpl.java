package com.example.my_back_end.blImpl.task;

import com.example.my_back_end.bl.task.TaskService;
import com.example.my_back_end.data.task.TaskMapper;
import com.example.my_back_end.enums.statuses;
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
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private static final String DDL_NOT_EXIST = "请先设置截止日期";
    private static final String LEADER_NOT_EXIST = "请设置生产组长";
    private static final String NAMENOT_EXIST = "请设置任务名";
    private static final String TASK_NOT_EXIST = "任务不存在";

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

    @Override
    public ResponseVO modifyTask(TaskVO taskVO) {
        Task task=taskMapper.getTaskById(taskVO.getTask_id());
        if(task==null){
            return ResponseVO.buildFailure(TASK_NOT_EXIST);
        }
        Task new_task=new Task();
        if(taskVO.getTask_name()==null)taskVO.setTask_name(task.getTask_name());
        if(taskVO.getCreate_time()==null)taskVO.setCreate_time(task.getCreate_time());
        if(taskVO.getExpected_time()==null)taskVO.setExpected_time(task.getExpected_time());
        if(taskVO.getExpected_exam_time()==null)taskVO.setExpected_exam_time(task.getExpected_exam_time());
        if(taskVO.getQuality_inspector()==null)taskVO.setQuality_inspector(task.getQuality_inspector());
        if(taskVO.getGroup_leader()==null)taskVO.setGroup_leader(task.getGroup_leader());
        if(taskVO.getComments()==null)taskVO.setComments(task.getComments());
        if(taskVO.getFinish_time()==null)taskVO.setFinish_time(task.getFinish_time());
        if(taskVO.getFinish_exam_time()==null)taskVO.setFinish_exam_time(task.getFinish_exam_time());

        BeanUtils.copyProperties(taskVO,new_task);
        if(taskVO.getStatus()==null) new_task.setStatus(task.getStatus());
        else new_task.setStatus(taskVO.getStatus().toString());

        taskMapper.modifyTask(new_task);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO getTasks(int user_id) {
        List<Task> tasks = taskMapper.getTasks(user_id);
        return ResponseVO.buildSuccess(tasks);
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = taskMapper.getAllTasks();
        System.out.print(tasks);
        return tasks;
    }

    @Override
    public ResponseVO filterTasksByStatus(statuses status) {
        List<Task> tasks = taskMapper.filterTasksByStatus(status.toString());
        return ResponseVO.buildSuccess(tasks);
    }


}
