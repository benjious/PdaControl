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

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.benjious.pdacontrol.activity.GoodReadyActivity.IS_FINISHED;
import static com.squareup.okhttp.internal.http.HttpDate.format;

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
            goodPresenter.loadData(getSDetailUrl, GoodPresenterImpl.GET_STOCK_DETAIL);
        }
        user = (User) mBundle.getSerializable(GoodReadyActivity.USER_SEND);
        hideProgress();
    }

    @OnClick({R.id.AddBtn, R.id.NextBtn, R.id.BackBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.AddBtn:
                addProduct();
                break;
            case R.id.NextBtn:
                break;
            case R.id.BackBtn:
                break;
        }
    }

    private void addProduct() {
        if (mTextBoxProNo.getText().toString().equals("") || mTextBoxQty.getText().toString().equals("")) {
            super.showToast("物料编号或数量不能为空");
        } else {
//            String proNo_url = Url.PATH + "/CheckPro_No?pro_no=" + mTextBoxProNo.getText().toString();
            String proNo_url = Url.PATH + "/CheckPro_No?pro_no=" + "01.01.03.0008";
            GoodPresenterImpl checkProNoImpl = new GoodPresenterImpl(this);
            showProgress();
            mAddBtn.setEnabled(false);
            checkProNoImpl.loadData(proNo_url, GoodPresenterImpl.CHECK_PRO_NO);
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
                    Log.d(TAG, "xyz  addData: 显示四下数据 " + mStockDetails.toString() + " 标识 ：" + IS_PRODUCT_FINISH);
                }
                if (type == GoodPresenterImpl.CHECK_PRO_NO) {
                    String str_name = usersALL.getData();
                    IS_FINISHED.addAndGet(1);
                    Log.d(TAG, "xyz  addData: 显示五下数据 " + str_name + " 标识 ：" + IS_PRODUCT_FINISH);
                    afterCheckPro(str_name);
                }
            }

        } catch (Exception e) {
            IS_FINISHED.set(0);
            mAddBtn.setEnabled(true);
            mNextBtn.setEnabled(true);

            super.showToast("解析数据出现错误");
            Log.d(TAG, "xyz  addData: " + e);
        } finally {

        }
    }

    private void afterCheckPro(String str_name) {
        if (str_name.equals("")) {
            super.showToast("物料编号不存在，请重新输入");
        } else {
            double qty = Double.parseDouble(mTextBoxQty.getText().toString());
            if (qty <= 0) {
                super.showToast("请输入正确的实际数量");
            } else {
                boolean result = false;
                Log.d(TAG, "xyz  afterCheckPro: king "+ kind);
                if (kind == 1) {
                    StackingItem stackingItem = new StackingItem();
                    stackingItem.set_iTEM_ID(mTextBoxProNo.getText().toString());
                    stackingItem.set_sTACK_ID(stacking.get_sTACK_ID());
                    stackingItem.set_lIST_NO("");
                    stackingItem.set_qTY(qty);
                    //这里要补上，这里有个日期
                    stackingItem.set_cREATED_BY(user.get_userID());
                    stackingItem.set_lAST_UPDATE_DATE(new java.util.Date());
                    stackingItem.set_lAST_UPDATED_BY(user.get_userID());
                } else {
                    for (StockDetail stockDetail :
                            mStockDetails) {
                        if (mTextBoxProNo.getText().toString().equals(stockDetail.get_iTEM_ID())) {
                            result = true;
                            break;
                        }
                    }
                    if (!result) {
                        Log.d(TAG, "xyz  afterCheckPro: 这里");
                        StockDetail stockDetail = new StockDetail();
                        stockDetail.set_sTOCK_OID(mPickings.get(0).get_sTOCK_OID());
                        stockDetail.set_iTEM_ID(mTextBoxProNo.getText().toString());
                        stockDetail.set_bARCODE_NO("");
                        //  stockDetail.set_pROD_DATE();
//                        stockDetail.set_eXPIRE_DATE();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
                        stockDetail.set_bATCH_NO(simpleDateFormat.format(stockDetail.get_pROD_DATE()));
                        stockDetail.set_lIST_NO("");
                        stockDetail.set_qTY(qty);
                        stockDetail.set_oUT_LIST_NO("");
                        stockDetail.set_oUT_QTY(0);
                        stockDetail.set_sTOCK_OID(0);
                        stockDetail.set_cREATION_DATE(new java.util.Date());
                        stockDetail.set_lAST_UPDATE_DATE(new java.util.Date());
                        stockDetail.set_gRADE("");
                    }else {
                        super.showToast("添加失败！物料已存在该托盘上，若要补充该物料数量请返回上一个界面！");
                    }

                } if (!result) {
                    super.showToast("添加物料成功，查看或提交任务请按下一步");

                    mTextBoxProNo.setText("");
                    mTextBoxQty.setText("");

                }

            }


        }

    }

    @Override
    public void loadExecption(Exception e) {
        hideProgress();
        mNextBtn.setEnabled(true);
        mAddBtn.setEnabled(true);
        super.showToast("请求过程中出现异常！！");
    }

    @Override
    public void showLoadFail(int failNum) {
        hideProgress();
        mAddBtn.setEnabled(true);
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
