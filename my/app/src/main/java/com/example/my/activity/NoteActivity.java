package com.example.my.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter3.demo.R;
import com.example.my.fragment.CheckManagerDetailFragment_1;
import com.example.my.fragment.CheckManagerDetailFragment_2;
import com.example.my.fragment.CheckmanDetailFragment;
import com.example.my.fragment.ExecutorDetailFragment;
import com.example.my.fragment.ManagerDetailFragment;
import com.example.my.fragment.TeamLeaderDetailFragment;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import org.json.JSONObject;
import rxhttp.RxHttp;

public class NoteActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle("修改备注");

        editText = findViewById(R.id.edit_text);
        String current=getIntent().getStringExtra("note");
        String id=getIntent().getStringExtra("id");
        editText.setFocusable(true);
        editText.requestFocus();
        editText.setText(current);
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }

        Button addBtn = findViewById(R.id.add_note);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence content = editText.getText();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NoteActivity.this,
                            "No content to add", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    saveNote2Database(content.toString().trim(),id);
                }catch (Exception e){
                    Log.d("TAG", e.toString());
                }

//                if (succeed) {
//                    Toast.makeText(NoteActivity.this,
//                            "Note added", Toast.LENGTH_SHORT).show();
//                    setResult(Activity.RESULT_OK);
//                } else {
//                    Toast.makeText(NoteActivity.this,
//                            "Error", Toast.LENGTH_SHORT).show();
//                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void saveNote2Database(String content,String id) {

        RxHttp.postJson("http://3s784625n5.qicp.vip:80/api/task/modifytask")
                .add("task_id",id).add("comments",content)
                .asString()
                .observeOn(AndroidSchedulers.mainThread()) //指定在主线程回调
                .subscribe(res -> {
                    JSONObject j= new JSONObject(res);
                    String message =j.getString("message");
                    if(!message.equals("null")){
                        Log.d("TAG", message);
                        showSimpleWarningDialog(message);
                    }else{
                        //global.addNote=editText.getText().toString();
                        showSimpleTipDialog("修改成功");

                    }
                }, throwable -> {
                    showSimpleWarningDialog("网络不良,请重试");
                });
    }

    public void showSimpleWarningDialog(String message) {
        new MaterialDialog.Builder(this)
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
    public void showSimpleTipDialog(String message) {
        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.icon_tip)
                .title("提示")
                .content(message)
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .show();
    }
}
