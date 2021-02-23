package com.example.my.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chapter3.demo.R;
import com.example.my.adapter.TaskAdapter;
import com.example.my.listview.Task;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import rxhttp.RxHttp;

public class CheckManagerFragment_1 extends Fragment {
    private ArrayList<Task> tasks;
    private ArrayList<Task> showTasks;
    private ArrayAdapter<Task> adapterItems;
    private OnItemSelectedListener listener;
    private String s;

    public interface OnItemSelectedListener {
        public void onItemSelected(Task i);
    }
    public static CheckManagerFragment_1 newInstance(ArrayList<Task> all,String s) {
        CheckManagerFragment_1 mf = new CheckManagerFragment_1();
        mf.tasks=new ArrayList<Task>(all);
        mf.s=s;
        Bundle args = new Bundle();
        mf.setArguments(args);
        return mf;
    }
    @Override
    public void onAttach(@NotNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof CheckManagerFragment_1.OnItemSelectedListener) {
            listener = (CheckManagerFragment_1.OnItemSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement ItemsListFragment.OnItemSelectedListener");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create arraylist from item fixtures
        showTasks=new ArrayList<Task>(tasks);
        adapterItems = new TaskAdapter(getActivity(),
                R.layout.list_item, showTasks);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_checkmanager1, container, false);
        TextView text=view.findViewById(R.id.mnum);

        //TODO:编辑超期任务数.此处是质检超期
        text.setText("1");
        ListView lvItems = (ListView) view.findViewById(R.id.mlist);
        lvItems.setAdapter(adapterItems);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position,
                                    long rowId) {
                // Retrieve item based on position
                Task i = adapterItems.getItem(position);
                // Fire selected event for item
                listener.onItemSelected(i);

            }
        });
        RefreshLayout refreshLayout=view.findViewById(R.id.refreshlayout);
        refreshLayout.setEnableAutoLoadMore(true);
        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh();
                refreshLayout.getLayout().postDelayed(() -> {
                    getall();
                    adapterItems.notifyDataSetChanged();
                    refreshLayout.finishRefresh();
                    refreshLayout.resetNoMoreData();//setNoMoreData(false);
                }, 2000);
            }
        });
        return view;
    }
    private void update(String s){
        Log.d("abc", s);
        for(int i=0;i<tasks.size();i++){
            if(tasks.get(i).getStatus().equals(s)){
                Log.d(tasks.get(i).getStatus(), s);
                if(!isIn(tasks.get(i).getTask_id())){
                    showTasks.add(tasks.get(i));
                }
            }
            if(!tasks.get(i).getStatus().equals(s)){
                showTasks.remove(tasks.get(i));
            }
        }
    }
    private void getall(){
        int size=showTasks.size();
        int t=0;
        while(t<size){
            if(!isIn2(showTasks.get(t).getTask_id())){
                showTasks.remove(t);
                size--;
            }
            else{
                t++;
            }
        }
        for(int i=0;i<tasks.size();i++){
            if(!isIn(tasks.get(i).getTask_id())){
                showTasks.add(tasks.get(i));
            }
        }
        update(s);
    }
    private void refresh(){
        ArrayList<Task> temp=new ArrayList<Task>();
        RxHttp.get("http://192.168.1.106:8000/api/task/getAllTasks")
                .asList(String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.d("TAG", s.get(0).toString());
                    for (String value : s) {
                        JSONObject js = new JSONObject(value);
                        Task t = new Task(js.getString("task_id"), js.getString("task_name"), js.getString("group_leader"),
                                js.getString("quality_inspector"), js.getString("expected_time"),
                                js.getString("expected_exam_time"), js.getString("create_time"), js.getString("status"),
                                js.getString("comments"), js.getString("finish_time"), js.getString("finish_exam_time"));
                        temp.add(t);
                    }
                }, throwable -> {
                    showSimpleWarningDialog("获取任务列表失败");
                });
        tasks=temp;
    }
    private void showSimpleWarningDialog(String message) {
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.drawable.icon_warning)
                .title("提示")
                .content(message)
                .positiveText("确定")
                .show();
    }
    private boolean isIn(String Id){
        for(int i=0;i<showTasks.size();i++){
            if (showTasks.get(i).getTask_id().equals(Id)){
                return true;
            }
        }
        return false;
    }
    private boolean isIn2(String Id){
        for(int i=0;i<tasks.size();i++){
            if (tasks.get(i).getTask_id().equals(Id)){
                return true;
            }
        }
        return false;
    }
}
