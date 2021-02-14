package com.example.chapter3.demo.manager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chapter3.demo.R;

public class ManagerFragment extends Fragment {
    //后续在此传参给fragment
    public static ManagerFragment newInstance() {
        ManagerFragment mf = new ManagerFragment();
        Bundle args = new Bundle();
        mf.setArguments(args);
        return mf;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_manager, container, false);
        Spinner spinner=view.findViewById(R.id.mspinner);
        //后续在此编辑选择条件
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        Toast.makeText(getActivity(),"全部",Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(),"未提交",Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(),"已提交",Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }
}
