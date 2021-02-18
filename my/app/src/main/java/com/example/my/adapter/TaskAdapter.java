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
            // 将ViewHolder存储在View中（即将控件的实例存储在其中）
            view.setTag(viewHolder);
        } else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        // 获取控件实例，并调用set...方法使其显示出来
        viewHolder.tag.setText(task.getTag());
        viewHolder.title.setText(task.getName());
        viewHolder.subtitle.setText("完成截止时间:"+task.getDdl1()+"  "+"质检截止时间"+task.getDdl2());
        return view;
    }
    // 定义一个内部类，用于对控件的实例进行缓存
    class ViewHolder{
        TextView tag;
        TextView title;
        TextView subtitle;
    }
}
