package com.benjious.pdacontrol.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import com.benjious.pdacontrol.webService.WLoginService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {

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
    public static final String  USERNAME ="USERNAME";

    private static Handler sHandler = new Handler();
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
                new MyTask().execute();

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



    private class MyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            holdMsg = WLoginService.executeHttpGet(username, password);
            return holdMsg;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            mProgressBar.setVisibility(View.INVISIBLE);
            Log.d(TAG, "xyz  onPostExecute: 返回的数据是什么： "+ s);
            if (s == null || s.equals("")) {
                Toast toast = Toast.makeText(LoginActivity.this, "没有此账号用户", Toast.LENGTH_LONG);
                toast.show();
                mLoginButton.setEnabled(true);
                mAccountText.setText("");
                mPasswordText.setText("");

            }else {
                sHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mComeBackMsg.setText(s);
                        goToMain();
                    }
                });
            }
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
        Log.d(TAG, "xyz  onClick: ");
    }


}
