package com.example.my.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.chapter3.demo.R;
import com.example.my.activity.NoteActivity;
import com.example.my.listview.Task;
import com.example.my.utils.XToastUtils;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.configure.TimePickerType;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener;
import com.xuexiang.xutil.data.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class ManagerDetailFragment extends Fragment {
	private Task item;
	private static final int REQUEST_CODE_ADD = 1002;
	private TimePickerView mTimePickerDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		item = (Task) getArguments().getSerializable("task");
		Log.d(item.getTag(), "here!!");
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
		TextView note=view.findViewById(R.id.note);
		TextView maketime=view.findViewById(R.id.makeTime);
		name.append(item.getName());
		tag.append(item.getTag());
		ddl1.append(item.getDdl1());
		ddl2.append(item.getDdl2());
		tl.append(item.getTeamleader());
		checkman.append(item.getCheckman());
		note.append(item.getNote());
		maketime.append(item.getMaketime());
		Button button=view.findViewById(R.id.add_button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivityForResult(
						new Intent(getActivity(), NoteActivity.class),
						REQUEST_CODE_ADD);
			}
		});
		Button button1=view.findViewById(R.id.edit_button1);
		Button button2=view.findViewById(R.id.edit_button2);
		button1.setOnClickListener(new View.OnClickListener() {
			//在此编辑修改完成截止时间的操作
			@Override
			public void onClick(View view) {
					if (mTimePickerDialog == null) {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(DateUtils.string2Date("2013-07-08 12:32:46", DateUtils.yyyyMMddHHmmss.get()));
						mTimePickerDialog = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
							@Override
							public void onTimeSelected(Date date, View v) {
								XToastUtils.toast(DateUtils.date2String(date, DateUtils.yyyyMMddHHmmss.get()));
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
			//在此编辑修改质检截止时间的操作
			@Override
			public void onClick(View view) {
				if (mTimePickerDialog == null) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(DateUtils.string2Date("2013-07-08 12:32:46", DateUtils.yyyyMMddHHmmss.get()));
					mTimePickerDialog = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
						@Override
						public void onTimeSelected(Date date, View v) {
							XToastUtils.toast(DateUtils.date2String(date, DateUtils.yyyyMMddHHmmss.get()));
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

    //后续在此传递参数，创建新表单行
    public static ManagerDetailFragment newInstance(Task item) {
    	ManagerDetailFragment fragmentDemo = new ManagerDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("task", item);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
}