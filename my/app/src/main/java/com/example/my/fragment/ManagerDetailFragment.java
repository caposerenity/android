package com.example.my.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.chapter3.demo.R;
import com.example.my.activity.NoteActivity;
import com.example.my.listview.Task;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.configure.TimePickerType;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener;
import com.xuexiang.xutil.data.DateUtils;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import rxhttp.RxHttp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ManagerDetailFragment extends Fragment {
	private Task item;
	private static final int REQUEST_CODE_ADD = 1002;
	private TimePickerView mTimePickerDialog;
	public static TextView note;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		item = (Task) getArguments().getSerializable("task");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_manager_detail, container, false);
		TextView tag = view.findViewById(R.id.state);
		TextView name = view.findViewById(R.id.TaskName);
		TextView ddl1=view.findViewById(R.id.ddl1);
		TextView ddl2=view.findViewById(R.id.ddl2);
		TextView tl=view.findViewById(R.id.Teamleader);
		TextView checkman=view.findViewById(R.id.Checkman);
		note=view.findViewById(R.id.note);
		TextView maketime=view.findViewById(R.id.makeTime);
		TextView finish1=view.findViewById(R.id.finish1);
		TextView finish2=view.findViewById(R.id.finish2);
		if(item.getQuality_inspector()!=null&& !item.getQuality_inspector().equals("null")) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					OkHttpClient client = new OkHttpClient();
					Request request = new Request.Builder().url("http://10.0.2.2:8000/api/user/" + item.getQuality_inspector() + "/getNameById").build();
					try {
						Response response = client.newCall(request).execute();//发送请求
						String result = response.body().string();
						checkman.append(result);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		if(item.getGroup_leader()!=null&& !item.getGroup_leader().equals("null")) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					OkHttpClient client = new OkHttpClient();
					Request request = new Request.Builder().url("http://10.0.2.2:8000/api/user/" + item.getGroup_leader() + "/getNameById").build();
					try {
						Response response = client.newCall(request).execute();//发送请求
						String result = response.body().string();
						tl.append(result);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		name.append(item.getTask_name());
		tag.append(item.getStatus());
		ddl1.append(item.getExpected_time());
		ddl2.append(item.getExpected_exam_time());
		//tl.append(item.getGroup_leader());
		//checkman.append(item.getQuality_inspector());
		note.append(item.getComments());
		maketime.append(item.getCreate_time());
		finish1.append(item.getFinish_time());
		finish2.append(item.getFinish_exam_time());
		Button button=view.findViewById(R.id.add_button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				add();
			}
		});
		Button button1=view.findViewById(R.id.edit_button1);
		Button button2=view.findViewById(R.id.edit_button2);
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
					if (mTimePickerDialog == null) {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(DateUtils.getNowDate());
						mTimePickerDialog = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
							@Override
							public void onTimeSelected(Date date, View v) {
								ddl1.setText("预计完成时间："+DateUtils.date2String(date, DateUtils.yyyyMMddHHmmss.get()));
								SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
								String expected_time=df.format(date);
								RxHttp.postJson("http://10.0.2.2:8000/api/task/modifytask")
										.add("task_id",item.getTask_id()).add("expected_time",expected_time)
										.asString()
										.observeOn(AndroidSchedulers.mainThread()) //指定在主线程回调
										.subscribe(res -> {
											JSONObject j= new JSONObject(res);
											String message =j.getString("message");
											if(!message.equals("null")){
												Log.d("TAG", message);
												showSimpleWarningDialog(message);
											}else{
												showSimpleTipDialog("修改成功");
											}
										}, throwable -> {
											showSimpleWarningDialog("网络不良,请重试");
										});
							}
						})
								.setTimeSelectChangeListener(date -> Log.i("pvTime", "onTimeSelectChanged"))
								.setType(TimePickerType.ALL)
								.setTitleText("时间选择")
								.isDialog(true)
								.setOutSideCancelable(false)
								.setDate(calendar)
								.build();
					}
					mTimePickerDialog.show();
			}
		});
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mTimePickerDialog == null) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(DateUtils.getNowDate());
					mTimePickerDialog = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
						@Override
						public void onTimeSelected(Date date, View v) {
							ddl2.setText("预计质检完成时间："+DateUtils.date2String(date, DateUtils.yyyyMMddHHmmss.get()));
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
							String expected_exam_time=df.format(date);
							RxHttp.postJson("http://10.0.2.2:8000/api/task/modifytask")
									.add("task_id",item.getTask_id()).add("expected_exam_time",expected_exam_time)
									.asString()
									.observeOn(AndroidSchedulers.mainThread()) //指定在主线程回调
									.subscribe(res -> {
										JSONObject j= new JSONObject(res);
										String message =j.getString("message");
										if(!message.equals("null")){
											Log.d("TAG", message);
											showSimpleWarningDialog(message);
										}else{
											showSimpleTipDialog("修改成功");
										}
									}, throwable -> {
										showSimpleWarningDialog("网络不良,请重试");
									});
						}
					})
							.setTimeSelectChangeListener(date -> Log.i("pvTime", "onTimeSelectChanged"))
							.setType(TimePickerType.ALL)
							.setTitleText("时间选择")
							.isDialog(true)
							.setOutSideCancelable(false)
							.setDate(calendar)
							.build();
				}
				mTimePickerDialog.show();
			}
		});
		return view;
	}

    public static ManagerDetailFragment newInstance(Task item) {
    	ManagerDetailFragment fragmentDemo = new ManagerDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("task", item);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
	private void add(){
		String s=item.getComments();
		String id=item.getTask_id();
		Intent i=new Intent(getActivity(),NoteActivity.class);
		i.putExtra("note",s);
		i.putExtra("id",id);
		startActivity(i);
	}
	public void showSimpleWarningDialog(String message) {
		new MaterialDialog.Builder(getContext())
				.iconRes(R.drawable.icon_warning)
				.title("提示")
				.content(message)
				.positiveText("确定")
				.onPositive(new MaterialDialog.SingleButtonCallback() {
					@Override
					public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
						dialog.dismiss();
						getActivity().finish();
					}
				})
				.show();
	}
	public void showSimpleTipDialog(String message) {
		new MaterialDialog.Builder(getContext())
				.iconRes(R.drawable.icon_tip)
				.title("提示")
				.content(message)
				.positiveText("确定")
				.onPositive(new MaterialDialog.SingleButtonCallback() {
					@Override
					public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
						dialog.dismiss();
						getActivity().finish();
					}
				})
				.show();
	}
}
