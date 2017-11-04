package com.benjious.pdacontrol.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.been.SortPortInfoBeen;
import com.benjious.pdacontrol.been.User;
import com.benjious.pdacontrol.been.UsersALL;
import com.benjious.pdacontrol.presenter.GoodPresenterImpl;
import com.benjious.pdacontrol.url.Url;
import com.benjious.pdacontrol.util.OkHttpUtils;
import com.benjious.pdacontrol.view.CommonView;
import com.google.gson.Gson;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;



/**
 * Created by Benjious on 2017/11/3.
 */


public class SortPortActivity extends BaseActivity implements CommonView {


    @Bind(R.id.port)
    TextView mPort;
    @Bind(R.id.product_id)
    TextView mProductId;
    @Bind(R.id.port_edit)
    EditText mPortEdit;
    @Bind(R.id.prodcut_id_edit)
    EditText mProdcutIdEdit;
    @Bind(R.id.instoreBtn)
    Button mInstoreBtn;
    @Bind(R.id.backBtn)
    Button mBackBtn;
    private String port;
    public static final String TAG = "SortPortActivity xyz =";
    private String product_id;
    private User mUser;
    private boolean checkPort;
    private final AtomicInteger CHECK_SORT = new AtomicInteger();
    private String str_name;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_port);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mUser = (User) intent.getSerializableExtra(LoginActivity.USER);
        preferences = getSharedPreferences("test", MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
    }

    @Override
    public void beforeRequest() {
        super.showProgress();
    }

    @Override
    public void finishRequest() {
        super.hideProgress();
    }

    @Override
    public void addData(String response, int type) {
        try {
            Gson gson = new Gson();
            UsersALL usersALL = gson.fromJson(response, UsersALL.class);
            if (usersALL == null) {
                showLoadFail(OkHttpUtils.NO_REAL_DATA);
            } else {
                if (type == GoodPresenterImpl.CHECK_PORT) {
                    checkPort = usersALL.isYesNo();
                    CHECK_SORT.addAndGet(1);
                    Log.d(TAG, "xyz  addData: 显示二下数据：" + checkPort + "  " + CHECK_SORT);
                    Log.d(TAG, "xyz  addData: ------------------------------------------------------");
                }

                if (type == GoodPresenterImpl.CHECK_PRO_NO) {
                    str_name = usersALL.getData();
                    CHECK_SORT.addAndGet(1);
                    Log.d(TAG, "xyz  addData: 显示五下数据 " + str_name + " 标识 ：" +CHECK_SORT);
                    Log.d(TAG, "xyz  addData: ------------------------------------------------------");
                }

                if (type==GoodPresenterImpl.INSERT_SORT) {
                    int n = usersALL.getNumber();
                    if (n > 0) {
                        super.showToast("物料录入成功! ");
                    }else {
                        super.showToast("物料录入失败! ");
                    }
                    CHECK_SORT.set(0);
                    finishRequest();
                    mInstoreBtn.setEnabled(true);
                }

                if (CHECK_SORT.get() == 2) {
                    CHECK_SORT.set(0);
                    if (!checkPort) {
                        hideProgress();
                        CHECK_SORT.set(0);
                        super.showToast("捡料口不存在,请重新输入!");
                        mPortEdit.setText("");
                    } else if (str_name.equals("")) {
                        hideProgress();
                        CHECK_SORT.set(0);
                        super.showToast("物料编号不存在,请重新输入!");
                        mProdcutIdEdit.setText("");
                    } else {
                        int kind = preferences.getInt(product_id, -1);
                        Log.d(TAG, "xyz  addData: 输出物料编号 : "+kind);
                        if (kind != -1) {
                            Log.d(TAG, "xyz  addData: 执行到这里了");
                            SortPortInfoBeen been = new SortPortInfoBeen();
                            been.set_barcode(product_id);
                            been.set_dev_No(port);
                            been.set_kind(kind);
                            been.set_status(0);
                            been.set_created_By(mUser.get_userID());
                            been.set_creation_Date(new Date());
                            GoodPresenterImpl presenter = new GoodPresenterImpl(this);
                            String  url =Url.getInsertSortUrl(been);
                            Log.d(TAG, "xyz  addData: url : "+url);
                            presenter.loadData(url,GoodPresenterImpl.INSERT_SORT);

                        }else {
                            CHECK_SORT.set(0);
                            finishRequest();
                            mInstoreBtn.setEnabled(true);
                            super.showToast("物料种类不存在,请检查配置文件");
                        }
                    }
                }
            }
        } catch (Exception e) {
            finishRequest();
            CHECK_SORT.set(0);
            mInstoreBtn.setEnabled(true);
            e.printStackTrace();
        }
    }


    @Override
    public void loadExecption(Exception e) {
        finishRequest();
        mInstoreBtn.setEnabled(true);

        super.showToast("请求过程中出现异常！！" + e);
    }

    @Override
    public void showLoadFail(int failNum) {
        finishRequest();
        if (failNum == OkHttpUtils.SERVER_OFFLINE) {
            Toast.makeText(this, "请求服务器出现错误！！", Toast.LENGTH_SHORT).show();
        } else if (failNum == OkHttpUtils.NO_REAL_DATA) {
            Toast.makeText(this, "获取数据成功，但数据为空！！", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick({R.id.instoreBtn, R.id.backBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.instoreBtn:
                instore();
                break;
            case R.id.backBtn:
                this.finish();
                break;
        }
    }

    private void instore() {
        port = mPortEdit.getText().toString();
        product_id = mProdcutIdEdit.getText().toString();

        if (port.equals("") || product_id.equals("")) {
            super.showToast("捡料口或物料编号不能为空!");
        } else {
            beforeRequest();
//            mInstoreBtn.setEnabled(false);
            String checkPortUrl = Url.PATH + "/CheckPort?p_code=" + port;
            Log.d(TAG, "xyz  checkPortUrl " + checkPortUrl);
            GoodPresenterImpl mGoodCheckPortpre = new GoodPresenterImpl(this);
            mGoodCheckPortpre.loadData(checkPortUrl, GoodPresenterImpl.CHECK_PORT);

            String proNo_url = Url.PATH + "/CheckPro_No?pro_no=" + product_id;
            GoodPresenterImpl checkProNoImpl = new GoodPresenterImpl(this);
            checkProNoImpl.loadData(proNo_url, GoodPresenterImpl.CHECK_PRO_NO);

        }

    }


}
