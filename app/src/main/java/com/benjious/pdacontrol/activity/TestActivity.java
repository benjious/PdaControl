package com.benjious.pdacontrol.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.been.UsersALL;
import com.benjious.pdacontrol.util.OkHttpUtils;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Benjious on 2017/10/28.
 */

public class TestActivity extends Activity {
    public static final String TAG = "TestActivity xyz =";
    @Bind(R.id.button5)
    Button mButton5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.button5)
    public void onViewClicked() {
        String url = "http://192.168.137.1:8080/liku/UpdateStock?Last_update_by=1013&Stock_oid=6737&Full_Flag=0";
        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                try {
                    Gson gson = new Gson();
                    UsersALL usersALL = gson.fromJson(response, UsersALL.class);
                    Log.d(TAG, "xyz  onSuccess: 结果是 : " + usersALL.toString());
                    Toast.makeText(TestActivity.this, String.valueOf(usersALL.getNumber()), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "xyz  onSuccess: 出现错误!!!!" + e);
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "xyz  onFailure: 出现错误!!!");
            }
        };
        OkHttpUtils.get(url, resultCallback);
    }
}
