package com.benjious.pdacontrol.activity;

import android.content.Intent;
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
import com.benjious.pdacontrol.been.Picking;
import com.benjious.pdacontrol.been.Stacking;
import com.benjious.pdacontrol.been.StackingItem;
import com.benjious.pdacontrol.been.User;
import com.benjious.pdacontrol.been.UsersALL;
import com.benjious.pdacontrol.presenter.GoodPresenter;
import com.benjious.pdacontrol.presenter.GoodPresenterImpl;
import com.benjious.pdacontrol.url.Url;
import com.benjious.pdacontrol.util.OkHttpUtils;
import com.benjious.pdacontrol.view.CommonView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 测试；
 * 入库站口：0001
 * 托盘编号： P0001
 * <p>
 * <p>
 * Created by Benjious on 2017/10/12.
 */

public class ProductReadyActivity extends BaseActivity implements CommonView {
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

    public static final String TAG = " xyz =";
    public static AtomicInteger IS_FINISHED = new AtomicInteger();

    public static final String USER_SEND = "USER_SEND";
    public static final String STACKING_SEND = "STACKING_SEND";
    public static final String STACKING_ITEM_LIST = "STACKING_ITEM_LIST";
    public static final String STOCKING_DETAIL_LIST ="STOCJING_ITEM_LIST";
    public static final String PICKING_SEND = "PICKING_SEND";
    public static final String KIND_SEND = "KING_SEND";

    private GoodPresenter mPresenter;
    private GoodPresenter mGoodCheckPortpre;
    private GoodPresenter mGoodNextStackId;

    private String comboKind;
    private Stacking mStacking = new Stacking();
    private String pallet_id;
    private String p_code;

    //这个值使为了以后二维码扫描用的
    private String comboBIN = new String(" ");

    private boolean checkPort = false;
    private int checkPallet = -2;

    private User mUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_ready);
        ButterKnife.bind(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.good_style, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mStyleSpin.setAdapter(adapter);
        Intent intent = getIntent();
        mUser = (User) intent.getSerializableExtra(LoginActivity.USER);

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
        Log.d(TAG, "addData: 标识的值" + IS_FINISHED);
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
                    Log.d(TAG, "xyz  addData: ------------------------------------------------------");


                } else if (type == GoodPresenterImpl.CHECK_PORT) {
                    checkPort = usersALL.isYesNo();
                    IS_FINISHED.addAndGet(1);
                    Log.d(TAG, "xyz  addData: 显示二下数据：" + checkPort + " 标识 ：" + IS_FINISHED);
                    Log.d(TAG, "xyz  addData: ------------------------------------------------------");


                } else if (type == GoodPresenterImpl.GET_NEWSTACK_ID) {
                    mStacking.set_sTACK_ID(usersALL.getData());
                    IS_FINISHED.addAndGet(1);
                    Log.d(TAG, "xyz  addData: 显示三下数据：" + usersALL.getData());
                    Log.d(TAG, "xyz  addData: ------------------------------------------------------");
                }

                if (IS_FINISHED.get() == 2) {
                    if (checkPallet == 1 && checkPort) {
                        String newStack_url = Url.PATH + "/GetNewStackId?strKind=" + "SIR";
                        Log.d(TAG, "xyz  nextStep: newStack_id " + newStack_url);
                        mGoodNextStackId = new GoodPresenterImpl(this);
                        mGoodNextStackId.loadData(newStack_url,GoodPresenterImpl.GET_NEWSTACK_ID);
                    } else {
                        IS_FINISHED.set(0);
                        if (!checkPort) {
                            super.showToast("站口号不存在，请重新输入");
                            mInStorePointEdit.setText("");
                        } else if (checkPallet == 0) {
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
                    Intent mIntent = new Intent(this, ProductsAddActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(USER_SEND,mUser);
                    bundle.putSerializable(STACKING_SEND, mStacking);
                    bundle.putSerializable(STACKING_ITEM_LIST, new ArrayList<StackingItem>());
                    bundle.putSerializable(PICKING_SEND, new ArrayList<Picking>());
                    bundle.putInt(KIND_SEND, 1);
                    mIntent.putExtras(bundle);
                    startActivity(mIntent);
                    Log.d(TAG, "xyz  addData: 这应该是最后执行的: " + mStacking.toString());
                    IS_FINISHED.set(0);
                }

            }
        } catch (Exception e) {
            IS_FINISHED.set(0);
            Toast.makeText(this, "解析数据出现错误" + e, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "xyz  addData: " + e);
        } finally {
            hideProgress();
            mNext.setEnabled(true);
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

                //进行数据库查询
                String checkPalletUrl = Url.PATH + "/CheckPallet?pallet_id=" + pallet_id + "&status=" + 1;
//                               String checkPalletUrl = Url.PATH + "/CheckPallet?pallet_id=" + "P0001" + "&status=" + 1;
                Log.d(TAG, "xyz  nextStep: checkPalletUrl " + checkPalletUrl);
                mPresenter =new GoodPresenterImpl(this);
                mPresenter.loadData(checkPalletUrl, GoodPresenterImpl.CHECK_PALLET_ID);



                String checkPortUrl = Url.PATH + "/CheckPort?p_code=" + p_code;
//                String checkPortUrl = Url.PATH + "/CheckPort?p_code=" + "0001";
                Log.d(TAG, "xyz  nextStep: checkPortUrl " + checkPortUrl);
                mGoodCheckPortpre = new GoodPresenterImpl(this);
                mGoodCheckPortpre.loadData(checkPortUrl,GoodPresenterImpl.CHECK_PORT);


                mNext.setEnabled(false);
                showProgress();

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

}
