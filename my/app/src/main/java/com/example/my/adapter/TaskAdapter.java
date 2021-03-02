package com.example.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.chapter3.demo.R;
import com.example.my.listview.Task;
import com.xuexiang.xutil.data.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    private int resourceId;

    public TaskAdapter(@NonNull Context context, int resource, List<Task> items) {
        super(context, resource,items);
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Task task=getItem(position); //获取当前项的Fruit实例

        // 加个判断，以免ListView每次滚动时都要重新加载布局，以提高运行效率
        View view;
        ViewHolder viewHolder;
        if (convertView==null){

            // 避免ListView每次滚动时都要重新加载布局，以提高运行效率
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            // 避免每次调用getView()时都要重新获取控件实例
            viewHolder=new ViewHolder();
            viewHolder.tag=view.findViewById(R.id.tv_tag);
            viewHolder.title=view.findViewById(R.id.tv_title);
            viewHolder.subtitle=view.findViewById(R.id.tv_sub_title);
            viewHolder.overdue=view.findViewById(R.id.tv_overdue);
            // 将ViewHolder存储在View中（即将控件的实例存储在其中）
            view.setTag(viewHolder);
        } else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        // 获取控件实例，并调用set...方法使其显示出来
        viewHolder.tag.setText(task.getStatus());
        viewHolder.title.setText(task.getTask_name());
        viewHolder.subtitle.setText("完成截止时间:"+task.getExpected_time()+"  "+"质检截止时间"+task.getExpected_exam_time());
        viewHolder.overdue.setVisibility(View.INVISIBLE);
        if(task.getStatus().equals("待完成")){
            Date beginTime= DateUtils.string2Date(task.getExpected_time(),DateUtils.yyyyMMddHHmmss.get());
            Date endTime= DateUtils.getNowDate();
            if (DateUtils.date2Millis(beginTime) <DateUtils.date2Millis( endTime)) {
                viewHolder.overdue.setVisibility(View.VISIBLE);
                viewHolder.overdue.setText("生产已逾期");
            }
            else{
                Calendar cal=Calendar.getInstance();
                beginTime= DateUtils.string2Date(task.getExpected_time(),DateUtils.yyyyMMddHHmmss.get());
                cal.setTime(beginTime);
                cal.add(Calendar.HOUR,6);
                endTime= DateUtils.getNowDate();
                if (DateUtils.date2Millis(cal.getTime()) <DateUtils.date2Millis( endTime)) {
                    viewHolder.overdue.setVisibility(View.VISIBLE);
                    viewHolder.overdue.setText("生产即将逾期");
                }
            }
        }
        else if(task.getStatus().equals("待质检")||task.getStatus().equals("质检中")){
            Date beginTime= DateUtils.string2Date(task.getExpected_exam_time(),DateUtils.yyyyMMddHHmmss.get());
            Date endTime= DateUtils.getNowDate();
            if (DateUtils.date2Millis(beginTime) <DateUtils.date2Millis( endTime)) {
                viewHolder.overdue.setVisibility(View.VISIBLE);
                viewHolder.overdue.setText("质检已逾期");
            }
            else{
                beginTime= DateUtils.string2Date(task.getExpected_exam_time(),DateUtils.yyyyMMddHHmmss.get());
                Calendar cal=Calendar.getInstance();
                cal.setTime(beginTime);
                cal.add(Calendar.HOUR,6);
                endTime= DateUtils.getNowDate();
                if (DateUtils.date2Millis(cal.getTime()) <DateUtils.date2Millis( endTime)) {
                    viewHolder.overdue.setVisibility(View.VISIBLE);
                    viewHolder.overdue.setText("质检即将逾期");
                }
            }
        }
        return view;
    }
    // 定义一个内部类，用于对控件的实例进行缓存
    class ViewHolder{
        TextView tag;
        TextView title;
        TextView subtitle;
        TextView overdue;
    }
}
