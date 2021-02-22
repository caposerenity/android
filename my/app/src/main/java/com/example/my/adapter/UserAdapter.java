package com.example.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.chapter3.demo.R;
import com.example.my.listview.User;

import java.io.Serializable;
import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private int resourceId;

    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        User user=getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null){

            // 避免ListView每次滚动时都要重新加载布局，以提高运行效率
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            // 避免每次调用getView()时都要重新获取控件实例
            viewHolder=new ViewHolder();
            viewHolder.name=view.findViewById(R.id.tv_title);
            viewHolder.role=view.findViewById(R.id.tv_tag);
            // 将ViewHolder存储在View中（即将控件的实例存储在其中）
            view.setTag(viewHolder);
        } else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }
        viewHolder.role.setText(user.getRole());
        viewHolder.name.setText(user.getName());
        return view;
    }
    class ViewHolder{
        TextView role;
        TextView name;
    }
}
