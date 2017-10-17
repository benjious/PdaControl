package com.benjious.pdacontrol.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.been.Picking;
import com.benjious.pdacontrol.been.StockDetail;
import com.benjious.pdacontrol.been.Stacking;
import com.benjious.pdacontrol.been.StackingItem;
import com.benjious.pdacontrol.been.User;
import com.benjious.pdacontrol.been.UsersALL;
import com.benjious.pdacontrol.presenter.GoodPresenterImpl;
import com.benjious.pdacontrol.url.Url;
import com.benjious.pdacontrol.util.OkHttpUtils;
import com.benjious.pdacontrol.view.CommonView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.benjious.pdacontrol.activity.GoodReadyActivity.IS_FINISHED;

/**
 * Created by Benjious on 2017/10/16.
 */

public class ProductsAddActivity extends BaseActivity implements CommonView {
    @Bind(R.id.goodId)
    TextView mGoodId;
    @Bind(R.id.textBoxPro_No)
    EditText mTextBoxProNo;
    @Bind(R.id.InstoreNumText)
    TextView mInstoreNumText;
    @Bind(R.id.textBoxQty)
    EditText mTextBoxQty;
    @Bind(R.id.DateNow)
    TextView mDateNow;
    @Bind(R.id.button4)
    Button mButton4;
    @Bind(R.id.AddBtn)
    Button mAddBtn;
    @Bind(R.id.NextBtn)
    Button mNextBtn;
    @Bind(R.id.BackBtn)
    Button mBackBtn;
    @Bind(R.id.progressBar3)
    ProgressBar mProgressBar3;

    private User user;
    private Stacking stacking;
    private List<StackingItem> mStackingItems;
    private List<Picking> mPickings;
    private int kind;
    private List<StockDetail> mStockDetails = new ArrayList<>();
    public static final String TAG = "ProductsAddActivity";
    private AtomicInteger IS_PRODUCT_FINISH = new AtomicInteger();

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_add);
        ButterKnife.bind(this);

        Bundle mBundle = this.getIntent().getExtras();
        kind = (int) mBundle.getSerializable(GoodReadyActivity.KIND_SEND);

        if (kind == 1) {
            stacking = (Stacking) mBundle.getSerializable(GoodReadyActivity.STACKING_SEND);
            mStackingItems = (List<StackingItem>) mBundle.getSerializable(GoodReadyActivity.STACKING_ITEM_SEND);

        } else {
            mPickings = (List<Picking>) mBundle.getSerializable(GoodReadyActivity.PICKING_SEND);
//            String getSDetail = Url.PATH + "/GetStockDetail/?stock_oid=" + mPickings.get(0).get_sTOCK_OID();
            showProgress();
            String getSDetailUrl = Url.PATH + "/GetStockDetail?stock_oid=" + "6198";
            Log.d(TAG, "xyz  onCreate: URL " + getSDetailUrl);
            GoodPresenterImpl goodPresenter = new GoodPresenterImpl(this);
            goodPresenter.loadData(getSDetailUrl,GoodPresenterImpl.GET_STOCK_DETAIL);
        }
        user = (User) mBundle.getSerializable(GoodReadyActivity.USER_SEND);

    }

    @OnClick({R.id.AddBtn, R.id.NextBtn, R.id.BackBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.AddBtn:
                break;
            case R.id.NextBtn:
                break;
            case R.id.BackBtn:
                break;
        }
    }


    @Override
    public void showProgress() {
        mProgressBar3.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar3.setVisibility(View.INVISIBLE);

    }

    @Override
    public void addData(String response, int type) {
        Log.d(TAG, "xyz  addData: 这里这里");
        hideProgress();
        mNextBtn.setEnabled(false);
        try {
            Gson gson = new Gson();
            UsersALL usersALL = gson.fromJson(response, UsersALL.class);
            if (usersALL == null) {
                showLoadFail(OkHttpUtils.NO_REAL_DATA);
            } else {
                if (type == GoodPresenterImpl.GET_STOCK_DETAIL) {
                    mStockDetails = usersALL.getStockDetails();
                    IS_FINISHED.addAndGet(1);
                    Log.d(TAG, "xyz  addData: 显示四下数据 " + mStockDetails.toString() + " 标识 ：" + IS_PRODUCT_FINISH);
                }
            }

        } catch (Exception e) {
            IS_FINISHED.set(0);
            super.showToast("解析数据出现错误");
            Log.d(TAG, "xyz  addData: " + e);
        }
    }

    @Override
    public void loadExecption(Exception e) {
        hideProgress();
        mNextBtn.setEnabled(true);
        super.showToast("请求过程中出现异常！！");
    }

    @Override
    public void showLoadFail(int failNum) {
        hideProgress();
        mNextBtn.setEnabled(true);
        IS_FINISHED.set(0);
        if (failNum == OkHttpUtils.SERVER_OFFLINE) {
            Toast.makeText(this, "请求服务器出现错误！！", Toast.LENGTH_SHORT).show();
        } else if (failNum == OkHttpUtils.NO_REAL_DATA) {
            mProgressBar3.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "获取数据成功，但数据为空！！", Toast.LENGTH_SHORT).show();
        }
    }
}
