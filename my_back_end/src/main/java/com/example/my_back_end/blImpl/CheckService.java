package com.example.my_back_end.blImpl;

import com.example.my_back_end.bl.task.TaskService;
import com.example.my_back_end.bl.user.AccountService;
import com.example.my_back_end.po.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

@Slf4j
@Component
public class CheckService {
    @Autowired private AccountService accountService;
    @Autowired private TaskService taskService;

    @Scheduled(cron = "00 00 11,15 * * *")
    public void scheduled() throws JSONException {
        List<Task> tasks=taskService.getAllTasks();
        for (Task task : tasks) {
            if(task.getStatus().equals("待完成")||task.getStatus().equals("不合格")) {
                Date now = new Date();
                if(task.getExpected_time()!=null&&task.getExpected_time().getTime()-now.getTime()<=24*60*60*1000
                        &&task.getExpected_time().getTime()-now.getTime()>0
                ){
                    //给组长发短信,ddl快到了
                    if(task.getGroup_leader()!=null&&task.getGroup_leader()!=-1){
                        String phone=accountService.getPhoneById(task.getGroup_leader());
                        Date time=task.getExpected_time();
                        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        SmsService.sendSms(phone,task.getTask_name(),format.format(time),"SMS_212480519");
                    }
                }
            }
            else if(task.getStatus().equals("质检中")){
                Date now = new Date();
                if(task.getExpected_exam_time()!=null&&task.getExpected_exam_time().getTime()-now.getTime()<=12*60*60*1000
                        &&task.getExpected_exam_time().getTime()-now.getTime()>0
                ){
                    //给质检员发短信,ddl快到了'
                    if(task.getQuality_inspector()!=null&&task.getQuality_inspector()!=-1){
                        String phone=accountService.getPhoneById(task.getQuality_inspector());
                        Date time=task.getExpected_exam_time();
                        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        SmsService.sendSms(phone,task.getTask_name(),format.format(time),"SMS_212480529");
                    }
                }
            }

        }

    }
//    @Scheduled(fixedRate = 5000)
//    public void scheduled1() {
//        System.out.println("=====>>>>>使用fixedRate{}"+System.currentTimeMillis());
//    }
//    @Scheduled(fixedDelay = 5000)
//    public void scheduled2() {
//        System.out.println("=====>>>>>fixedDelay{}"+System.currentTimeMillis());
//    }


}
