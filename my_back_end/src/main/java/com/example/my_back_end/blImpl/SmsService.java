package com.example.my_back_end.blImpl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class SmsService {

        public static void sendSms(String phone,String task_name,String time,String code) throws JSONException {
            DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                    "","");//这两个地方写你的对应 Id 和Secret
            IAcsClient client = new DefaultAcsClient(profile);

            JSONObject templateParam=new JSONObject();
            templateParam.put("task_name",task_name);
            if(time!=null)
            templateParam.put("time",time);

            CommonRequest request = new CommonRequest();
            request.setSysMethod(MethodType.POST);
            request.setSysDomain("dysmsapi.aliyuncs.com");
            request.setSysVersion("2017-05-25");
            request.setSysAction("SendSms");
            request.putQueryParameter("RegionId", "cn-hangzhou");
            //确定发送的电话号码和 验证码
            request.putQueryParameter("PhoneNumbers", phone);
            request.putQueryParameter("TemplateParam", templateParam.toString());
            //确定是的模版和签名
            request.putQueryParameter("SignName", "连云港四方测绘");
            request.putQueryParameter("TemplateCode", code);
            // RAM账号AccessKey Secret




            try {
                CommonResponse response = client.getCommonResponse(request);
                System.out.println(response.getData());
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }


}
