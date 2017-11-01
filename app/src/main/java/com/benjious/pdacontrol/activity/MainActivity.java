package com.benjious.pdacontrol.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.been.User;

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

    private User mUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mUser = (User) intent.getSerializableExtra(LoginActivity.USER);
        String userName = mUser.get_userCnName();
        mWelcome.setText("欢迎你 : " + userName);
        this.setTitle("登录界面");
    }


    @OnClick({R.id.MaduoInstore, R.id.goodInStore, R.id.numAck, R.id.emptyAck, R.id.someAck, R.id.repairAck, R.id.quitLogin, R.id.welcome})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.MaduoInstore:
                break;
            case R.id.goodInStore:
                intent.putExtra(LoginActivity.USER, mUser);
                intent.setClass(MainActivity.this, ProductReadyActivity.class);
                startActivity(intent);
                break;
            case R.id.numAck:
                intent.putExtra(LoginActivity.USER, mUser);
                intent.setClass(MainActivity.this, InventoryActivity.class);
                startActivity(intent);
                break;
            case R.id.emptyAck:
                intent.putExtra(LoginActivity.USER, mUser);
                intent.setClass(MainActivity.this, PalletEmptyInActivity.class);
                startActivity(intent);
                break;
            case R.id.someAck:
                intent.putExtra(LoginActivity.USER, mUser);
                intent.putExtra(LoginActivity.KIND, 1);
                intent.setClass(MainActivity.this, ProductConfigActivity.class);
                startActivity(intent);
                break;
            case R.id.repairAck:
                intent.putExtra(LoginActivity.USER, mUser);
                intent.putExtra(LoginActivity.KIND, 2);
                intent.setClass(MainActivity.this, ProductConfigActivity.class);
                startActivity(intent);
                break;
            case R.id.quitLogin:
                this.finish();
                break;

        }
    }
}
