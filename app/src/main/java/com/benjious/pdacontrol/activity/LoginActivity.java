package com.benjious.pdacontrol.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.interfazes.OnLoadGoodLisenter;
import com.benjious.pdacontrol.webService.WLoginService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//这是master
public class LoginActivity extends AppCompatActivity implements OnLoadGoodLisenter {

    @Bind(R.id.textView)
    TextView mTextView;
    @Bind(R.id.textView2)
    TextView mTextView2;
    @Bind(R.id.editText)
    EditText mAccountText;
    @Bind(R.id.editText2)
    EditText mPasswordText;
    @Bind(R.id.button)
    Button mLoginButton;
    @Bind(R.id.button2)
    Button mCancelButton;
    @Bind(R.id.button3)
    Button mNetSettingButton;
    @Bind(R.id.comeBackMsg)
    TextView mComeBackMsg;
    public static final String TAG = "LoginActivity xyz =";
    public static final String USERNAME = "USERNAME";

    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.out)
    Button mOut;

    private String username = "";
    private String password = "";
    private String holdMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        mProgressBar.setVisibility(View.INVISIBLE);
        setListener();
    }

    private void setListener() {
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mAccountText.getText().toString();
                password = mPasswordText.getText().toString();
                if (!checkNetwork()) {
                    Toast toast = Toast.makeText(LoginActivity.this, "网络未连接", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mLoginButton.setEnabled(true);
                    return;
                }
                if (username.equals("") || password.equals("")) {
                    Toast toast = Toast.makeText(LoginActivity.this, "用户名或密码不为空", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mAccountText.setText("");
                    mPasswordText.setText("");
                    return;
                }
                mLoginButton.setEnabled(false);
                WLoginService.executeHttpGet(username, password, LoginActivity.this);

            }
        });

        mOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
                System.out.println("sss");

            }
        });
    }

    @OnClick(R.id.progressBar)
    public void onViewClicked() {

    }


    @Override
    public void onSuccess(final String respone) {
        holdMsg = respone;
        mProgressBar.setVisibility(View.INVISIBLE);
        Log.d(TAG, "xyz  onPostExecute: 返回的数据是什么： " + respone);
        if (respone == null || respone.equals("")) {
            noCount();
        } else {

            Log.d(TAG, "xyz  run: respone: " + respone);
            mComeBackMsg.setText(respone);
            goToMain();
        }

    }

    private void noCount() {
        Toast toast = Toast.makeText(LoginActivity.this, "没有此账号用户", Toast.LENGTH_LONG);
        toast.show();
        mLoginButton.setEnabled(true);
        mAccountText.setText("");
        mPasswordText.setText("");

    }


    @Override
    public void onFailure(String str, Exception e) {
        mProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "请求服务器出现错误！！" + e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(int failnum) {
        if (failnum == WLoginService.SERVER_OFFLINE) {
            mProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "请求服务器出现错误！！", Toast.LENGTH_SHORT).show();
        } else if (failnum == WLoginService.NO_COUNTER) {
            noCount();
        }
    }

    //检查网络
    private boolean checkNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null) {
            return connectivityManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }


    private void goToMain() {
        Intent intent = new Intent();
        intent.putExtra(USERNAME, holdMsg);
        intent.setClass(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }


}
