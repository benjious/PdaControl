package com.benjious.pdacontrol.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.benjious.pdacontrol.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Benjious on 2017/10/7.
 */

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "MainActivity xyz =";
    @Bind(R.id.MaduoInstore)
    Button mMaduoInstore;
    @Bind(R.id.goodInStore)
    Button mGoodInStore;
    @Bind(R.id.numAck)
    Button mNumAck;
    @Bind(R.id.emptyAck)
    Button mEmptyAck;
    @Bind(R.id.someAck)
    Button mSomeAck;
    @Bind(R.id.repairAck)
    Button mRepairAck;
    @Bind(R.id.quitLogin)
    Button mQuitLogin;
    @Bind(R.id.welcome)
    TextView mWelcome;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String userName = "欢迎您： "+ intent.getStringExtra(LoginActivity.USERNAME);
        mWelcome.setText(userName);
        Log.d(TAG, "xyz  onCreate: 启动了几次");
    }


    @OnClick({R.id.MaduoInstore, R.id.goodInStore, R.id.numAck, R.id.emptyAck, R.id.someAck, R.id.repairAck, R.id.quitLogin, R.id.welcome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.MaduoInstore:
                break;
            case R.id.goodInStore:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ProductReadyActivity.class);
                startActivity(intent);
                break;
            case R.id.numAck:
                break;
            case R.id.emptyAck:
                break;
            case R.id.someAck:
                break;
            case R.id.repairAck:
                break;
            case R.id.quitLogin:
                break;
            case R.id.welcome:
                break;
        }
    }
}
