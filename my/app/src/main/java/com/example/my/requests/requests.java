package com.example.my.requests;

import com.example.my.activity.testconnect;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;

public class requests {
    static MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    //@interface 注册
    public static String register(String name,String phone,String password,String position){

        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("phone", phone);
            json.put("password", password);
            json.put("position",position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody =RequestBody.create(JSON, String.valueOf(json));
        return testconnect.postRequest("http://10.0.2.2:8000/api/user/register",requestBody);
    }

    //@interface 登录
    public static String login(String phone,String password){
        JSONObject json = new JSONObject();
        try {
            json.put("phone", phone);
            json.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody =RequestBody.create(JSON, String.valueOf(json));
        return testconnect.postRequest("http://10.0.2.2:8000/api/user/login",requestBody);
    }

    //@interface 改变人员职位(用户类别)
    public static String changePos(String phone,String newPos){
        JSONObject json = new JSONObject();
        try {
            json.put("phone", phone);
            json.put("newPos", newPos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody =RequestBody.create(JSON, String.valueOf(json));
        return testconnect.postRequest("http://10.0.2.2:8000/api/user/changePos",requestBody);
    }

    //@interface 根据类别筛选用户
    public static String filterPosition(String position){
        return testconnect.getRequest("http://10.0.2.2:8000/api/user"+position+"/filterPosition");
    }

    //@interface 修改密码
    public static String changePassword(String user_id,String phone,String newPassword){
        JSONObject json = new JSONObject();
        try {
            json.put("user_id",user_id);
            json.put("phone", phone);
            json.put("newPassword", newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody =RequestBody.create(JSON, String.valueOf(json));
        return testconnect.postRequest("http://10.0.2.2:8000/api/user/changePassword",requestBody);
    }

    //@interface 修改个人信息
    public static String changeInfo(String user_id,String phone,String name){
        JSONObject json = new JSONObject();
        try {
            json.put("user_id",user_id);
            json.put("phone", phone);
            json.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody =RequestBody.create(JSON, String.valueOf(json));
        return testconnect.postRequest("http://10.0.2.2:8000/api/user/changeInfo",requestBody);
    }

    //@interface 添加新任务
    public static String addtask(String task_name,String expected_time,String expected_exam_time,
                                 String group_leader,String comments){
        JSONObject json = new JSONObject();
        try {
            json.put("task_name",task_name);
            json.put("expected_time", expected_time);
            json.put("expected_exam_time", expected_exam_time);
            json.put("group_leader", group_leader);
            json.put("comments", comments);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody =RequestBody.create(JSON, String.valueOf(json));
        return testconnect.postRequest("http://10.0.2.2:8000/api/user/addtask",requestBody);
    }

    //@interface 质检员或小组长获取对应的任务
    public static String getTasks(String user_id){
        return testconnect.getRequest("http://10.0.2.2:8000/api/user"+user_id+"/getTasks");
    }

    //@interface 获取全部接口
    public static String getAllTasks(){
        return testconnect.getRequest("http://10.0.2.2:8000/api/user/getAllTasks");
    }

    //@interface 筛选出对应状态的任务
    public static String getTasksByStatus(String status){
        return testconnect.getRequest("http://10.0.2.2:8000/api/user"+status+"/getTasksByStatus");
    }
}
