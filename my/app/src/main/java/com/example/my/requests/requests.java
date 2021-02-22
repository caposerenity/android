package com.example.my.requests;

import android.util.Log;
import com.example.my.activity.testconnect;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import rxhttp.RxHttp;
import com.example.chapter3.demo.R;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class requests {
    static MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void showSimpleWarningDialog(String message) {
//        new MaterialDialog.Builder(this)
//                .iconRes(R.drawable.icon_warning)
//                .title("提示")
//                .content(message)
//                .positiveText("确定")
//                .show();
    }
    public static void showSimpleTipDialog(String message) {
//        new MaterialDialog.Builder(this)
//                .iconRes(R.drawable.icon_tip)
//                .title("提示")
//                .content(message)
//                .positiveText("确定")
//                .show();
    }
    //@interface 注册
    public static void register(String name,String phone,String password){

        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("phone", phone);
            json.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RxHttp.postJson("http://10.0.2.2:8000/api/user/register")
                .addAll(String.valueOf(json))
                .asString()
                .observeOn(AndroidSchedulers.mainThread()) //指定在主线程回调
                .subscribe(res -> {
                    JSONObject j= new JSONObject(res);
                    String message =j.getString("message");
                    if(message!=null){
                        Log.d("TAG", message);
                        showSimpleWarningDialog(message);
                    }
                }, throwable -> {
                    //失败回调
                    showSimpleWarningDialog("注册失败，请重试");
                });
    }

    //@interface 登录
    public static void login(String phone,String password){
        JSONObject json = new JSONObject();
        try {
            json.put("phone", phone);
            json.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RxHttp.postJson("http://10.0.2.2:8000/api/user/login")
                .addAll(String.valueOf(json))
                .asString()
                .observeOn(AndroidSchedulers.mainThread()) //指定在主线程回调
                .subscribe(res -> {
                    JSONObject j= new JSONObject(res);
                    String message =j.getString("message");
                    if(message!=null){
                        Log.d("TAG", message);
                        showSimpleWarningDialog(message);
                    }
                }, throwable -> {
                    //失败回调
                    showSimpleWarningDialog("登录失败，请重试");
                });
    }

    //@interface 改变人员职位(用户类别)
    public static void changePos(String phone,String newPos){
        JSONObject json = new JSONObject();
        try {
            json.put("phone", phone);
            json.put("newPos", newPos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //RequestBody requestBody =RequestBody.create(JSON, String.valueOf(json));
        //return testconnect.postRequest("http://10.0.2.2:8000/api/user/changePos",requestBody);
        RxHttp.postJson("http://10.0.2.2:8000/api/user/changePos")
                .addAll(String.valueOf(json))
                .asString()
                .observeOn(AndroidSchedulers.mainThread()) //指定在主线程回调
                .subscribe(res -> {
                    JSONObject j= new JSONObject(res);
                    String message =j.getString("message");
                    if(message!=null){
                        Log.d("TAG", message);
                        showSimpleWarningDialog(message);
                    }
                }, throwable -> {
                    showSimpleWarningDialog("请重试");
                });
    }

    //@interface 根据类别筛选用户
    public static void filterPosition(String position){
        testconnect.getRequest("http://10.0.2.2:8000/api/user"+position+"/filterPosition");
        RxHttp.get("http://10.0.2.2:8000/api/user"+position+"/filterPosition")
                .asList(String.class)
                .subscribe(res->{
                    if(res.size()!=0){
                        Log.d("TAG", res.toString());
                        //后续处理
                    }
                },throwable -> {

                });
    }

    //@interface 修改密码
    public static void changePassword(String user_id,String phone,String password,String newPassword){
        JSONObject json = new JSONObject();
        try {
            json.put("user_id",user_id);
            json.put("phone", phone);
            json.put("password",password);
            json.put("newPassword", newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //RequestBody requestBody =RequestBody.create(JSON, String.valueOf(json));
        //testconnect.postRequest("http://10.0.2.2:8000/api/user/changePassword",requestBody);
        RxHttp.postJson("http://10.0.2.2:8000/api/user/changePassword")
                .addAll(String.valueOf(json))
                .asString()
                .observeOn(AndroidSchedulers.mainThread()) //指定在主线程回调
                .subscribe(res -> {
                    JSONObject j= new JSONObject(res);
                    String message =j.getString("message");
                    if(message!=null&&!message.equals("修改成功")){
                        Log.d("TAG", message);
                        showSimpleWarningDialog(message);
                    }else{
                        showSimpleTipDialog("修改成功");
                    }
                }, throwable -> {
                    showSimpleWarningDialog("请重试");
                });
    }

    //@interface 修改个人信息
    public static void changeInfo(String user_id,String phone,String name){
        JSONObject json = new JSONObject();
        try {
            json.put("user_id",user_id);
            json.put("phone", phone);
            json.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //RequestBody requestBody =RequestBody.create(JSON, String.valueOf(json));
        //testconnect.postRequest("http://10.0.2.2:8000/api/user/changeInfo",requestBody);
        RxHttp.postJson("http://10.0.2.2:8000/api/user/changeInfo")
                .addAll(String.valueOf(json))
                .asString()
                .observeOn(AndroidSchedulers.mainThread()) //指定在主线程回调
                .subscribe(res -> {
                    JSONObject j= new JSONObject(res);
                    String message =j.getString("message");
                    if(message!=null&&!message.equals("修改成功")){
                        Log.d("TAG", message);
                        showSimpleWarningDialog(message);
                    }else{
                        showSimpleTipDialog("修改成功");
                    }
                }, throwable -> {
                    showSimpleWarningDialog("网络不良,请重试");
                });
    }

    //@interface 添加新任务
    public static void addtask(String task_name,String expected_time,String expected_exam_time,
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
        //RequestBody requestBody =RequestBody.create(JSON, String.valueOf(json));
        //testconnect.postRequest("http://10.0.2.2:8000/api/task/addtask",requestBody);
        RxHttp.postJson("http://10.0.2.2:8000/api/task/addtask")
                .addAll(String.valueOf(json))
                .asString()
                .observeOn(AndroidSchedulers.mainThread()) //指定在主线程回调
                .subscribe(res -> {
                    JSONObject j= new JSONObject(res);
                    String message =j.getString("message");
                    if(message!=null){
                        Log.d("TAG", message);
                        showSimpleWarningDialog(message);
                    }
                }, throwable -> {
                    showSimpleWarningDialog("网络不良,请重试");
                });

    }

    //@interface 质检员或小组长获取对应的任务
    public static void getTasks(String user_id){
        testconnect.getRequest("http://10.0.2.2:8000/api/task"+user_id+"/getTasks");
    }

    //@interface 获取全部接口
    public static void getAllTasks() {
        RxHttp.get("http://10.0.2.2:8000/api/task/getAllTasks")
                .asString()
                .subscribe(s -> {
                    JSONObject j= new JSONObject(s);
                    String content =j.getString("content");
                    Log.d("TAG", content);
                }, throwable -> {
            //失败回调
                });
        //return testconnect.getRequest("http://10.0.2.2:8000/api/user/getAllTasks");
    }

    //@interface 筛选出对应状态的任务
    public static String getTasksByStatus(String status){
        return testconnect.getRequest("http://10.0.2.2:8000/api/task"+status+"/getTasksByStatus");
    }
}
