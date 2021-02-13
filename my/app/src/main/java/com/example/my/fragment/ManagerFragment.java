package com.example.my.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.my.R;
import butterknife.BindView;

public class ManagerFragment extends Fragment {
    @BindView(R.id.mspinner)
    Spinner spinner;

    private static final String TAG = "HelloManager";

    //后续在此传参给fragment
    public static ManagerFragment newInstance() {
        ManagerFragment mf = new ManagerFragment();
        Bundle args = new Bundle();
        mf.setArguments(args);
        return mf;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
//        String[] arr={"测试1","测试2","测试3"};
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this.getContext(),R.layout.support_simple_spinner_dropdown_item,arr);
//        spinner.setAdapter(adapter);
        return inflater.inflate(R.layout.fragment_manager, container, false);
    }
}
