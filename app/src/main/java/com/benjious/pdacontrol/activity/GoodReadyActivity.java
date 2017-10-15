package com.benjious.pdacontrol.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.been.UsersALL;
import com.benjious.pdacontrol.presenter.GoodPresenter;
import com.benjious.pdacontrol.presenter.GoodPresenterImpl;
import com.benjious.pdacontrol.url.Url;
import com.benjious.pdacontrol.util.OkHttpUtils;
import com.benjious.pdacontrol.view.CommonView;
import com.google.gson.Gson;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Benjious on 2017/10/12.
 */

public class GoodReadyActivity extends BaseActivity implements CommonView {
    @Bind(R.id.inStoreSite)
    TextView mInStoreSite;
    @Bind(R.id.inStorePointEdit)
    EditText mInStorePointEdit;

    @Bind(R.id.stockId)
    TextView mStockId;
    @Bind(R.id.stockIdEdit)
    EditText mStockIdEdit;

    @Bind(R.id.inStoreStyle)
    TextView mInStoreStyle;
    @Bind(R.id.StyleSpin)
    Spinner mStyleSpin;

    @Bind(R.id.inStorePoint)
    TextView mInStorePoint;
    @Bind(R.id.pointSpin)
    Spinner mPointSpin;

    @Bind(R.id.next)
    Button mNext;
    @Bind(R.id.back)
    Button mBack;

    @Bind(R.id.textView3)
    TextView mTextView3;
    @Bind(R.id.progressBar2)
    ProgressBar mProgressBar2;

    public static final String TAG = "GoodReadyActivity xyz =";
    private GoodPresenter mPresenter;
    private GoodPresenter mGoodCheckPortpre;
    private GoodPresenter mGoodNextStackId;

    private boolean checkPort = false;
    private int checkPallet = -2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goodready);
        ButterKnife.bind(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.good_style, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mStyleSpin.setAdapter(adapter);
        hideProgress();
    }

    @Override
    public void showProgress() {
        mProgressBar2.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar2.setVisibility(View.INVISIBLE);
    }

    @Override
    public void addData(String response, int type) {
        hideProgress();
        mNext.setEnabled(true);
        try {
            Gson gson = new Gson();
            UsersALL usersALL = gson.fromJson(response, UsersALL.class);
            if (usersALL == null) {
                showLoadFail(OkHttpUtils.NO_REAL_DATA);
            } else {
                if (type == GoodPresenterImpl.CHECK_PALLET_ID) {
                    checkPallet = usersALL.getNumber();
                    Toast.makeText(this, "请求成功这一次", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "xyz  addData: 显示一下数据 " + checkPallet);
                } else if (type == GoodPresenterImpl.CHECK_PORT) {
                    checkPort = usersALL.isYesNo();
                    Log.d(TAG, "xyz  addData: 显示二下数据：" + checkPort);
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "解析数据出现错误", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void loadExecption(Exception e) {
        hideProgress();
        mNext.setEnabled(true);
        Toast.makeText(this, "请求过程中出现异常！！" + e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadFail(int failNum) {
        hideProgress();
        mNext.setEnabled(true);
        if (failNum == OkHttpUtils.SERVER_OFFLINE) {
            Toast.makeText(this, "请求服务器出现错误！！", Toast.LENGTH_SHORT).show();
        } else if (failNum == OkHttpUtils.NO_REAL_DATA) {
            mProgressBar2.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "获取数据成功，但数据为空！！", Toast.LENGTH_SHORT).show();
        }

    }


    @OnClick({R.id.next, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.next:
                nextStep();
                break;
            case R.id.back:
                break;
        }
    }

    private void nextStep() {
        if (super.checkNetworkState()) {
            if ((!(mInStorePointEdit.getText().toString().equals(""))) && (!(mStockIdEdit.getText().toString().equals(""))) && (!((mStyleSpin.getSelectedItem()).equals("")))) {
                String pallet_id = mStockIdEdit.getText().toString();
                String p_code = mInStorePointEdit.getText().toString();

                //进行数据库查询
//            mPresenter = new GoodPresenterImpl(this);
                String checkUrl = Url.PATH + "/CheckPallet?pallet_id=" + pallet_id + "&status=" + 1;
                Log.d(TAG, "xyz  nextStep: checkUrl " + checkUrl);
//            mPresenter.loadData(checkUrl, GoodPresenterImpl.CHECK_PALLET_ID);
                GoodPresenterImpl.getGoodPresenter(this).loadData(checkUrl, GoodPresenterImpl.CHECK_PALLET_ID);

                String checkPortUrl = Url.PATH + "/CheckPort?p_code=" + p_code;
                Log.d(TAG, "xyz  nextStep: checkPortUrl " + checkPortUrl);
//            mGoodCheckPortpre = new GoodPresenterImpl(this);
                GoodPresenterImpl.getGoodPresenter(this).loadData(checkPortUrl, GoodPresenterImpl.CHECK_PORT);
//            mGoodCheckPortpre.loadData(checkPortUrl, GoodPresenterImpl.CHECK_PORT);
                showProgress();
                mNext.setEnabled(false);


                Log.d(TAG, "xyz  nextStep: 预先显示一下值 " + checkPallet + "  " + checkPort);
                if (checkPallet == 1 && checkPort) {
                    String newStack_url= Url.PATH + "/GetNewStackId?strKind=" + "SIR";
                    GoodPresenterImpl.getGoodPresenter(this).loadData(checkPortUrl, GoodPresenterImpl.GETNEWSTACK_ID);
                }

            } else {
                Toast.makeText(this, "请输入入库站口和托盘编号！", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.showToast("请先连接数据");
        }

    }
}
