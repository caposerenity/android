package com.example.my.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
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
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;
import com.xuexiang.xutil.data.DateUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import rxhttp.RxHttp;

public class TeamLeaderFragment extends Fragment {
    private ArrayList<Task> tasks;
    private ArrayList<Task> showTasks;
    private ArrayAdapter<Task> adapterItems;
    private TeamLeaderFragment.OnItemSelectedListener listener;
    private TextView text1;
    private TextView text2;
    private MaterialSpinner mMaterialSpinner;
    //这里是组长Id
    private String leaderName;
    private RefreshLayout refreshLayout;

    public interface OnItemSelectedListener {
        public void onItemSelected(Task i);
    }

    //MainActivity需要传入组长名称
    public static TeamLeaderFragment newInstance(ArrayList<Task> all,String leaderName) {
        TeamLeaderFragment mf = new TeamLeaderFragment();
        mf.tasks=new ArrayList<Task>(all);
        mf.leaderName=leaderName;
        Bundle args = new Bundle();
        mf.setArguments(args);
        return mf;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof TeamLeaderFragment.OnItemSelectedListener) {
            listener = (TeamLeaderFragment.OnItemSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement ItemsListFragment.OnItemSelectedListener");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create arraylist from item fixtures
        showTasks=new ArrayList<Task>();
        adapterItems = new TaskAdapter(getActivity(),
                R.layout.list_item, showTasks);
    }

    @Override
    public void onStart() {
        super.onStart();
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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_teamleader, container, false);
        mMaterialSpinner=view.findViewById(R.id.spinner);
        text1=view.findViewById(R.id.tnum1);
        text2=view.findViewById(R.id.tnum2);


        mMaterialSpinner.setOnItemSelectedListener((spinner, position, id, item) ->
        {
            switch(position){
                case 0:
                    update("待完成");
                    break;
                case 1:
                    update("不合格");
                    break;
            }
            adapterItems.notifyDataSetChanged();
        });
        ListView lvItems = (ListView) view.findViewById(R.id.tlist);
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
        refreshLayout=view.findViewById(R.id.refreshlayout);
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
        text1.setText(""+needToDo());
        text2.setText(""+overdue());
        int size=showTasks.size();
        int t=0;
        while(t<size){
            showTasks.remove(t);
            size--;
        }
        for(int i=0;i<tasks.size();i++){
            if(!isIn(tasks.get(i).getTask_id())){
                showTasks.add(tasks.get(i));
            }
        }
        update("待完成");
    }
    private void refresh(){
        ArrayList<Task> temp=new ArrayList<Task>();
        SharedPreferences sharedPre=getActivity().getSharedPreferences("config",getActivity().MODE_PRIVATE);
        RxHttp.get("http://3s784625n5.qicp.vip:80/api/task/"+String.valueOf(sharedPre.getInt("user_id",-1))+"/getTasks")
                .asList(String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    //Log.d("TAG", s.get(0).toString());
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
        mMaterialSpinner.setSelectedIndex(0);
        tasks=temp;
    }
    private void showSimpleWarningDialog(String message) {
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.drawable.icon_warning)
                .title("提示")
                .content(message)
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
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
    private int overdue(){
        int res=0;
        for(int i=0;i<tasks.size();i++){
            if(tasks.get(i).getStatus().equals("待完成")||tasks.get(i).getStatus().equals("不合格")){
                Date beginTime= DateUtils.string2Date(tasks.get(i).getExpected_time(),DateUtils.yyyyMMddHHmmss.get());
                Date endTime= DateUtils.getNowDate();
                if (DateUtils.date2Millis(beginTime) <DateUtils.date2Millis( endTime)) {
                    res++;
                }
            }
        }
        return res;
    }
    private int needToDo(){
        int res=0;
        for(int i=0;i<tasks.size();i++){
            if(tasks.get(i).getStatus().equals("待完成")||tasks.get(i).getStatus().equals("不合格")){
                res++;
            }
        }
        return res;
    }
}
