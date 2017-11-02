package com.benjious.pdacontrol.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.interfazes.OnNetworkModityListener;
import com.benjious.pdacontrol.url.Url;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Benjious on 2017/11/2.
 */

public class NetworkActivity extends AppCompatActivity implements OnNetworkModityListener {
    @Bind(R.id.ip_address)
    TextView mIpAddress;
    @Bind(R.id.service_name)
    TextView mServiceName;
    @Bind(R.id.ip_edit)
    EditText mIpEdit;
    @Bind(R.id.service_name_edit)
    EditText mServiceNameEdit;
    @Bind(R.id.save_setting)
    Button mSaveSetting;
    @Bind(R.id.backBtn)
    Button mBackBtn;
    @Bind(R.id.recover)
    Button mRecover;

    private String ip_adress;
    private String service_name;

    private final String matchStr = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d[1-9])$";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_setting);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.save_setting, R.id.backBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.save_setting:
                saveSetting();
                break;
            case R.id.backBtn:
                this.finish();
                break;
        }
    }

    private void saveSetting() {
        ip_adress = mIpEdit.getText().toString();
        service_name = mServiceNameEdit.getText().toString();
        if (ip_adress.equals("") && service_name.equals("")) {
            Toast.makeText(this, "请填写IP地址或服务器名字", Toast.LENGTH_SHORT).show();
        } else {
            if ((!ip_adress.equals("")) && (!mServiceNameEdit.equals(""))) {
                //同时修改
                if (checkIP(ip_adress)) {
                    Url.IP=ip_adress;
                    Url.SERVICE_NAME=mServiceNameEdit.getText().toString();
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "IP地址输入有误", Toast.LENGTH_SHORT).show();
                }
            } else if (ip_adress.equals("")) {
                //修改服务器
                Url.SERVICE_NAME=mServiceNameEdit.getText().toString();
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
            } else {
                //修改IP
                if (checkIP(ip_adress)) {
                    Url.IP=ip_adress;
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "IP地址输入有误", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @OnClick(R.id.recover)
    public void onViewClicked() {

    }

    private boolean checkIP(String IP) {
        boolean isTrue = IP.matches(matchStr);
        return isTrue;
    }


    @Override
    public void recover() {
        Url.IP = Url.IP_FINAL;
        Url.SERVICE_NAME =Url.SERVICE_NAME_FINAL;
    }
}
