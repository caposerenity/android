package com.example.my.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import com.example.chapter3.demo.R;
import static com.xuexiang.xutil.XUtil.getSystemService;


public class notification {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void showMessage(Context context, Class cl, String tittle, String content, int i) {
        Intent intent = new Intent(context, cl);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        String id = context.getPackageName();//频道的ID。每个包必须是唯一的
        //渠道名字
        String name = context.getString(R.string.app_name);//频道的用户可见名称
        //创建一个通知管理器
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id,name,NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(context)
                    .setChannelId(id)
                    .setContentTitle(tittle)//设置通知标题
                    .setContentText(content)//设置通知内容
                    //.setSmallIcon(R.mipmap.icon4)//设置小图标
                    //.setLargeIcon(BitmapFactory.decodeResource
                      //      (context.getResources(), R.mipmap.icon4))//设置大图标
                    .setContentIntent(pendingIntent)//打开消息跳转到这儿
                    .setAutoCancel(false)// 将AutoCancel设为true后，当你点击通知栏的notification后，它会自动被取消消失
                    .setOngoing(true)//将Ongoing设为true 那么notification将不能滑动删除
                    // 从Android4.1开始，可以通过以下方法，设置notification的优先级，优先级越高的，通知排的越靠前，优先级低的，不会在手机最顶部的状态栏显示图标
                    //.setPriority(NotificationCompat.PRIORITY_MAX)

                    .setDefaults(Notification.DEFAULT_ALL)
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .build();
        } else {
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(context)
                            .setContentTitle(tittle)
                            .setContentText(content)
                            //.setSmallIcon(R.mipmap.icon4)
                            //.setLargeIcon(BitmapFactory.decodeResource
                             //       (context.getResources(), R.mipmap.ic_launcher))//设置大图标
                            //.setVibrate(vibrate)//震动
                            .setContentIntent(pendingIntent)//打开消息跳转到这儿
                            .setAutoCancel(false)
                            .setOngoing(true)
                            //.setPriority(NotificationCompat.PRIORITY_MAX)
                            .setVisibility(Notification.VISIBILITY_PUBLIC)//在锁屏上的显示

                            .setOngoing(true);
                            //.setChannel(id);//无效
            notification = notificationBuilder.build();
        }
        notificationManager.notify(i, notification);
    }


    public static void destroy(Context context, int i){
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.cancel(i);
    }

}
