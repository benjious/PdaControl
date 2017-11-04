package com.benjious.pdacontrol.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.benjious.pdacontrol.been.User;
import com.benjious.pdacontrol.been.UsersALL;
import com.benjious.pdacontrol.url.Url;
import com.benjious.pdacontrol.util.MD5Util;
import com.benjious.pdacontrol.util.OkHttpUtils;
import com.benjious.pdacontrol.view.CommonView;
import com.benjious.pdacontrol.webService.WLoginService;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.benjious.pdacontrol.R.string.ip;


//这是master
public class LoginActivity extends AppCompatActivity implements CommonView {

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
    public static final String USER = "USER";
    public static final String KIND = "KIND";

    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.out)
    Button mOut;

    private String username = "";
    private String password = "";
    private User mUser;

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        mProgressBar.setVisibility(View.INVISIBLE);
        setListener();
        getUrlFromXml();
    }

    private void getUrlFromXml() {
        mPreferences = getSharedPreferences("test", MODE_PRIVATE);
        String ip_address = mPreferences.getString("IP_ADDRESS", "");
        String service_name = mPreferences.getString("SERVICE_NAME", "");
        Log.d(TAG, "xyz  getUrlFromXml: 输出ip 和 服务器地址看一下 : "+ip_address+" "+service_name);
        Url.getPathUrl(ip_address,service_name);
//        Url.getPathUrl("a","b");
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
                beforeRequest();
                password = MD5Util.createMD5Hash(password);
                WLoginService.executeHttpGet(username, password, LoginActivity.this);
            }
        });

        mOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });

        mNetSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, NetworkActivity.class);
                startActivity(intent);

            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAccountText.setText("");
                mPasswordText.setText("");
            }
        });

    }


    private void noCount() {
        Toast toast = Toast.makeText(LoginActivity.this, "没有此账号用户", Toast.LENGTH_LONG);
        toast.show();
        mLoginButton.setEnabled(true);
        mAccountText.setText("");
        mPasswordText.setText("");

    }

    //检查网络
    public boolean checkNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null) {
            return connectivityManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }


    private void goToMain() {
        Intent intent = new Intent();
        intent.putExtra(USER, mUser);
        intent.setClass(LoginActivity.this, MainActivity.class);
        startActivity(intent);

    }


    @Override
    public void beforeRequest() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishRequest() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void addData(final String response, int type) {
        finishRequest();
        try {
            Gson gson = new Gson();
            UsersALL usersALL = gson.fromJson(response, UsersALL.class);
            if (usersALL == null || (usersALL.getUsers().size() == 0)) {
                showLoadFail(OkHttpUtils.NO_REAL_DATA);
            } else {
                mUser = usersALL.getUsers().get(0);
                goToMain();
            }
            mLoginButton.setEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "解析出现错误!!!" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loadExecption(Exception e) {
        finishRequest();
        mLoginButton.setEnabled(true);
        Toast.makeText(this, "请求服务器出现错误！！" + e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadFail(int failNum) {
        finishRequest();
        mLoginButton.setEnabled(true);
        if (failNum == OkHttpUtils.SERVER_OFFLINE) {
            Toast.makeText(this, "请求服务器出现错误！！", Toast.LENGTH_SHORT).show();
        } else if (failNum == OkHttpUtils.NO_REAL_DATA) {
            noCount();
        }
    }


}
