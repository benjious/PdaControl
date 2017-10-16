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
import com.benjious.pdacontrol.been.Stacking;
import com.benjious.pdacontrol.been.UsersALL;
import com.benjious.pdacontrol.presenter.GoodPresenter;
import com.benjious.pdacontrol.presenter.GoodPresenterImpl;
import com.benjious.pdacontrol.url.Url;
import com.benjious.pdacontrol.util.OkHttpUtils;
import com.benjious.pdacontrol.view.CommonView;
import com.google.gson.Gson;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

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
    public static AtomicInteger IS_FINISHED = new AtomicInteger();

    private GoodPresenter mPresenter;
    private GoodPresenter mGoodCheckPortpre;
    private GoodPresenter mGoodNextStackId;

    private CountDownLatch mCountDownLatch;
    private ExecutorService mExecutorService;

    private String comboKind;
    private Stacking mStacking= new Stacking();
    private String pallet_id;
    private String p_code;
    private String comboBIN = new String(" ");

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

        mCountDownLatch = new CountDownLatch(2);
        mExecutorService = Executors.newCachedThreadPool();

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
                    IS_FINISHED.addAndGet(1);

                    Log.d(TAG, "xyz  addData: 显示一下数据 " + checkPallet + " 标识 ：" + IS_FINISHED);
                } else if (type == GoodPresenterImpl.CHECK_PORT) {
                    checkPort = usersALL.isYesNo();
                    IS_FINISHED.addAndGet(1);
                    Log.d(TAG, "xyz  addData: 显示二下数据：" + checkPort + " 标识 ：" + IS_FINISHED);
                } else if (type == GoodPresenterImpl.GETNEWSTACK_ID) {
                    mStacking.set_sTACK_ID(usersALL.getData());
                    IS_FINISHED.addAndGet(1);
                    Log.d(TAG, "xyz  addData: 显示三下数据：" + usersALL.getData());
                }

                if (IS_FINISHED.get() == 2) {
                    if (checkPallet == -1 && checkPort) {

                        String newStack_url = Url.PATH + "/GetNewStackId?strKind=" + "SIR";
                        Log.d(TAG, "xyz  nextStep: newStack_id " + newStack_url);
                        GoodPresenterImpl.getGoodPresenter(this).loadData(newStack_url, GoodPresenterImpl.GETNEWSTACK_ID);
                    } else {
                        IS_FINISHED.set(0);
                        if (!checkPort) {
                            super.showToast("站口号不存在，请重新输入");
                            mInStorePointEdit.setText("");
                        } else if (checkPallet==0) {
                            super.showToast("托盘号不存在，请重新输入");
                            mStockIdEdit.setText("");
                        }
                    }
                }

                if (IS_FINISHED.get() == 3) {
                    mStacking.set_wH_NO("JL_ASRS_01");
                    mStacking.set_pALLET_ID(pallet_id);
                    mStacking.set_p_CODE(p_code);
                    mStacking.set_kIND(getKind(comboKind));
                    mStacking.set_bIN_NO(comboBIN);
                    mStacking.set_sTATUS(0);
                    IS_FINISHED.set(0);
                    Log.d(TAG, "xyz  addData: 这应该是最后执行的: "+mStacking.toString());

                }

            }
        } catch (Exception e) {
            IS_FINISHED.set(0);
            Toast.makeText(this, "解析数据出现错误" + e, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "xyz  addData: " + e);
        }

    }


    private short getKind(String string) {
        short kind = 0;
        switch (string) {
            case "原材料入库":
                kind = 1;
                break;
            case "半成品入库":
                kind = 2;
                break;

        }
        return kind;
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
        IS_FINISHED.set(0);
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
            comboKind = mStyleSpin.getSelectedItem().toString();
            pallet_id = mStockIdEdit.getText().toString();
            p_code = mInStorePointEdit.getText().toString();
//            comboBIN = mPointSpin.getSelectedItem().toString();
            if ((!(mInStorePointEdit.getText().toString().equals(""))) && (!(mStockIdEdit.getText().toString().equals(""))) && (!((mStyleSpin.getSelectedItem()).equals("")))) {

//                //进行数据库查询
                String checkUrl = Url.PATH + "/CheckPallet?pallet_id=" + pallet_id + "&status=" + 1;
                Log.d(TAG, "xyz  nextStep: checkUrl " + checkUrl);
                GoodPresenterImpl.getGoodPresenter(this).loadData(checkUrl, GoodPresenterImpl.CHECK_PALLET_ID);

                String checkPortUrl = Url.PATH + "/CheckPort?p_code=" + p_code;
                Log.d(TAG, "xyz  nextStep: checkPortUrl " + checkPortUrl);
                GoodPresenterImpl.getGoodPresenter(this).loadData(checkPortUrl, GoodPresenterImpl.CHECK_PORT);

                mNext.setEnabled(false);
                showProgress();
//
//                String newStack_url = Url.PATH + "/GetNewStackId?strKind=" + "SIR";
//                Log.d(TAG, "xyz  nextStep: newStack_id " + newStack_url);
//                GoodPresenterImpl.getGoodPresenter(this).loadData(newStack_url, GoodPresenterImpl.GETNEWSTACK_ID);

//                mExecutorService.execute(new CheckPallet(mCountDownLatch, pallet_id));
//                mExecutorService.execute(new CheckPort(mCountDownLatch, p_code));
//
//                try {
//                    mCountDownLatch.await();
//                    Log.d(TAG, "xyz  nextStep:  mCountDownLatch.await();");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                Log.d(TAG, "xyz  nextStep: 预先显示一下值 " + checkPallet + "  " + checkPort);


            } else {
                if (p_code.equals("")) {
                    Toast.makeText(this, "站口号不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (pallet_id.equals("")) {
                        Toast.makeText(this, "托盘号不能为空", Toast.LENGTH_SHORT).show();
                    } else if (comboKind.equals("")) {
                        Toast.makeText(this, "入库类型不能为空", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        } else {
            super.showToast("请先连接网络");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IS_FINISHED.set(0);
    }


//    private class CheckPallet implements Runnable {
//        private final CountDownLatch mLatch;
//        String pallet_id;
//
//
//        public CheckPallet(CountDownLatch latch, String pallet_id) {
//            mLatch = latch;
//            this.pallet_id = pallet_id;
//        }
//
//        @Override
//        public void run() {
//            String checkUrl = Url.PATH + "/CheckPallet?pallet_id=" + pallet_id + "&status=" + 1;
//            Log.d(TAG, "xyz  nextStep: checkUrl " + checkUrl);
//            GoodPresenterImpl.getGoodPresenter(GoodReadyActivity.this).loadData(checkUrl, GoodPresenterImpl.CHECK_PALLET_ID);
//            mLatch.countDown();
//        }
//
//    }
//
//
//    private class CheckPort implements Runnable {
//        private final CountDownLatch mLatch;
//        String p_code;
//
//        public CheckPort(CountDownLatch latch, String p_code) {
//            mLatch = latch;
//            this.p_code = p_code;
//        }
//
//        @Override
//        public void run() {
//            String checkPortUrl = Url.PATH + "/CheckPort?p_code=" + p_code;
//            Log.d(TAG, "xyz  nextStep: checkPortUrl " + checkPortUrl);
//            GoodPresenterImpl.getGoodPresenter(GoodReadyActivity.this).loadData(checkPortUrl, GoodPresenterImpl.CHECK_PORT);
//            mLatch.countDown();
//        }
//    }



}
