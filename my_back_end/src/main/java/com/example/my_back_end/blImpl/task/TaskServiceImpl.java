package com.example.my_back_end.blImpl.task;

import com.example.my_back_end.bl.task.TaskService;
import com.example.my_back_end.bl.user.AccountService;
import com.example.my_back_end.blImpl.SmsService;
import com.example.my_back_end.data.task.TaskMapper;
import com.example.my_back_end.enums.positions;
import com.example.my_back_end.enums.statuses;
import com.example.my_back_end.po.Task;
import com.example.my_back_end.po.User;
import com.example.my_back_end.vo.ResponseVO;
import com.example.my_back_end.vo.TaskVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Collections;
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
    @Autowired
    private AccountService accountService;
    @Override
    public ResponseVO addTask(TaskVO taskVO) throws JSONException {
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
            //发短信通知组长有新任务
            String phone=accountService.getPhoneById(task.getGroup_leader());
            SmsService.sendSms(phone,task.getTask_name(),null,"SMS_212480527");
            return ResponseVO.buildSuccess(true);
        }
    }

    @Override
    public ResponseVO modifyTask(TaskVO taskVO) throws JSONException {
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
        if(new_task.getStatus().equals("质检中")&&new_task.getQuality_inspector()!=null
        &&new_task.getQuality_inspector()!=-1){
            //给质检员发短信
            String phone=accountService.getPhoneById(new_task.getQuality_inspector());
            SmsService.sendSms(phone,new_task.getTask_name(),null,"SMS_212480527");
        }
        if(new_task.getStatus().equals("待质检")){
            //给质检部经理发短信,提醒选质检员
            List<User> qua_manager_l=accountService.filterPosition(positions.Quality_manager);
            if(qua_manager_l.size()!=0){
                String phone=qua_manager_l.get(0).getPhone();
                Date time=new_task.getExpected_exam_time();
                SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SmsService.sendSms(phone,new_task.getTask_name(),format.format(time),"SMS_212485535");
            }
        }
        if(new_task.getStatus().equals("待提交客户")){
            //给行政综合部发短信，提醒提交
            List<User> comprehensives=accountService.filterPosition(positions.Comprehensive_depart);
            for (User people:comprehensives) {
                String phone=people.getPhone();
                SmsService.sendSms(phone,new_task.getTask_name(),null,"SMS_212485537");
            }
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Task> getTasks(int user_id) {
        List<Task> tasks = taskMapper.getTasks(user_id);
        Collections.reverse(tasks);
        return tasks;
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = taskMapper.getAllTasks();
        Collections.reverse(tasks);
        return tasks;
    }

    @Override
    public ResponseVO filterTasksByStatus(statuses status) {
        List<Task> tasks = taskMapper.filterTasksByStatus(status.toString());
        Collections.reverse(tasks);
        return ResponseVO.buildSuccess(tasks);
    }


}
