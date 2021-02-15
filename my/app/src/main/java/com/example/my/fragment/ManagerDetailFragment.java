package com.example.my.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.chapter3.demo.R;
import com.example.my.listview.Task;

public class ManagerDetailFragment extends Fragment {
	private Task item;

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