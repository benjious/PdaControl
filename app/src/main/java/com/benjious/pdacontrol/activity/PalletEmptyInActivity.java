package com.benjious.pdacontrol.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.been.Stacking;
import com.benjious.pdacontrol.been.StackingItem;
import com.benjious.pdacontrol.been.User;
import com.benjious.pdacontrol.been.UsersALL;
import com.benjious.pdacontrol.fragment.ProcessDialogFragment;
import com.benjious.pdacontrol.presenter.GoodPresenterImpl;
import com.benjious.pdacontrol.url.Url;
import com.benjious.pdacontrol.util.DateUtil;
import com.benjious.pdacontrol.util.OkHttpUtils;
import com.benjious.pdacontrol.view.CommonView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.fragment;
import static android.R.attr.type;
import static com.benjious.pdacontrol.activity.ProductConfigActivity.CHECK_FINISHED;

/**
 * Created by Benjious on 2017/11/1.
 */

public class PalletEmptyInActivity extends BaseActivity implements CommonView {

    private static final AtomicInteger CHECK_FINISHED_PM = new AtomicInteger();

    @Bind(R.id.port_id)
    TextView mPortId;
    @Bind(R.id.pallet_id)
    TextView mPalletId;
    @Bind(R.id.inStorePoint)
    TextView mInStorePoint;
    @Bind(R.id.port_id_edit)
    EditText mPortIdEdit;
    @Bind(R.id.pallet_id_edit)
    EditText mPalletIdEdit;
    @Bind(R.id.spinner)
    Spinner mSpinner;
    @Bind(R.id.instoreBtn)
    Button mInstoreBtn;
    @Bind(R.id.backBtn)
    Button mBackBtn;
    private ProcessDialogFragment mFragment;
    private String port_id;
    private String pallet_id;
    public static final String TAG = "PalletEmptyInActivity ";

    int checkPallet = -1;
    boolean checkPort = false;
    private User mUser;
    private List<StackingItem> stackingItems =new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pallet_empty_in);
        ButterKnife.bind(this);
        mFragment = new ProcessDialogFragment();

        Intent intent = getIntent();
        mUser = (User) intent.getSerializableExtra(LoginActivity.USER);
        //这里还有个定时器获取空位
    }


    @Override
    public void addData(String response, int type) {

        try {
            Gson gson = new Gson();
            UsersALL usersALL = gson.fromJson(response, UsersALL.class);
            if (usersALL == null) {
                showLoadFail(OkHttpUtils.NO_REAL_DATA);
            } else {
                if (type == GoodPresenterImpl.CHECK_PALLET_ID) {
                    checkPallet = usersALL.getNumber();
                    CHECK_FINISHED_PM.addAndGet(1);
                    Log.d(TAG, "xyz  addData: 显示一下数据 " + checkPallet + "  " + CHECK_FINISHED_PM);
                    Log.d(TAG, "xyz  addData: ------------------------------------------------------");
                }

                if (type == GoodPresenterImpl.CHECK_PORT) {
                    checkPort = usersALL.isYesNo();
                    CHECK_FINISHED_PM.addAndGet(1);
                    Log.d(TAG, "xyz  addData: 显示一下数据 " + checkPort + "  " + CHECK_FINISHED_PM);
                    Log.d(TAG, "xyz  addData: ------------------------------------------------------");
                }

                if (type == GoodPresenterImpl.UPDATE_PALLET) {
                    if (usersALL.getNumber() > 0) {
                        super.showToast("提交任务成功!");
                        mPalletIdEdit.setText("");
                        mPortIdEdit.setText("");
                        //刷新spinner中的数据
                    }
                    hideProgress();
                }

                if (type == GoodPresenterImpl.INSERT_STACKINGITEM) {
                    if (usersALL.getNumber() > 0) {
                        String url = Url.PATH + "/UpdatePallet?LAST_UPDATE_DATE=" + DateUtil.converToString(new Date()) + "&" + "LAST_UPDATED_BY=" + mUser.get_userID() + "&" + "PALLET_ID=" + pallet_id;
                        Log.d(TAG, "xyz  UpdatePallet_url " + url);
                        GoodPresenterImpl presenter = new GoodPresenterImpl(this);
                        presenter.loadData(url, GoodPresenterImpl.UPDATE_PALLET);
                    } else {
                        super.showToast("插入STACKING失败");
                    }
                }


                if (type == GoodPresenterImpl.GET_NEWSTACK_ID) {
                    String stack_id = usersALL.getData();
                    if ((!stack_id.equals(""))) {
                        Date date = new Date();
                        Stacking stacking = new Stacking();
                        StackingItem item = new StackingItem();

                        stacking.set_sTACK_ID(stack_id);
                        stacking.set_wH_NO("JL_ASRS_01");
                        stacking.set_pALLET_ID(pallet_id);
                        stacking.set_kIND(0);
//                        stacking.set_bIN_NO();
                        stacking.set_fULL_FLAG(false);
                        stacking.set_sTATUS(0);
                        stacking.set_cREATION_DATE(date);
                        stacking.set_cREATED_BY(mUser.get_userID());
                        stacking.set_lAST_UPDATED_BY(mUser.get_userID());
                        stacking.set_lAST_UPDATE_DATE(date);

                        item.set_iTEM_ID(pallet_id);
                        item.set_sTACK_ID(stack_id);
                        item.set_lIST_NO("");
                        item.set_qTY(1);
                        item.set_pROD_DATE(date);
                        item.set_cREATION_DATE(date);
                        item.set_cREATED_BY(mUser.get_userID());
                        item.set_lAST_UPDATE_DATE(date);
                        item.set_lAST_UPDATED_BY(mUser.get_userID());
                        stackingItems.add(item);

                        GoodPresenterImpl instackingImpl = new GoodPresenterImpl(this);
                        instackingImpl.loadData(Url.getInsertStackUrl(stacking, false), GoodPresenterImpl.INSERT_STACKING);
                    }
                }

                if (type == GoodPresenterImpl.INSERT_STACKING) {
                    String url = Url.getInstackingItem(stackingItems,0);
                    Log.d(TAG, "xyz  addData: 显示六下数据的url " + url);
                    GoodPresenterImpl presenter = new GoodPresenterImpl(this);
                    presenter.loadData(url, GoodPresenterImpl.INSERT_STACKINGITEM);
                }


                if (CHECK_FINISHED_PM.get() == 2) {
                    if (checkPort && checkPallet == 1) {
                        String checkPalletUrl = Url.PATH + "/CheckPallet?pallet_id=" + pallet_id + "&status=" + 0;
                        GoodPresenterImpl preImpl = new GoodPresenterImpl(this);
                        preImpl.loadData(checkPalletUrl, GoodPresenterImpl.CHECK_PALLET_ID);
                        Log.d(TAG, "xyz  addData: 准备执行第三个");
                    } else if (!checkPort) {
                        super.showToast("入库站口不存在,请重新输入!");
                        hideProgress();
                        mPortIdEdit.setText("");
                        CHECK_FINISHED_PM.set(0);
                    } else if (checkPallet != 1) {
                        super.showToast("托盘编号不存在,请重新输入!");
                        mPalletIdEdit.setText("");
                        hideProgress();
                        CHECK_FINISHED_PM.set(0);
                    } else {
                       // super.showToast("怎么会是这里");
                    }
                }

                if (CHECK_FINISHED_PM.get() == 3) {
                    CHECK_FINISHED_PM.set(0);
                    if (checkPallet == 0) {
                        String newStack_url = Url.PATH + "/GetNewStackId?strKind=" + "SIR";
                        Log.d(TAG, "xyz  : newStack_id " + newStack_url);
                        GoodPresenterImpl mGoodNextStackId = new GoodPresenterImpl(this);
                        mGoodNextStackId.loadData(newStack_url, GoodPresenterImpl.GET_NEWSTACK_ID);

                    } else if (checkPallet == 1) {
                        super.showToast("该任务已经存在,请勿重复插入");
                        hideProgress();
                    } else {
                        super.showToast("请求的数据有问题");
                    }
                }
            }

        } catch (Exception e) {
            CHECK_FINISHED_PM.set(0);
            hideProgress();
            e.printStackTrace();
            Log.d(TAG, "xyz  addData: 解析出现错误!!" + e);
            super.showToast("解析出现错误!!");
        }finally {
            hideProgress();
        }
    }

    private void inTostore() {
        if ((!mPortIdEdit.getText().toString().equals("")) && (!mPalletIdEdit.getText().toString().equals(""))) {
            showProgress();
            port_id = mPortIdEdit.getText().toString();
            pallet_id = mPalletIdEdit.getText().toString();

            String checkPalletUrl = Url.PATH + "/CheckPallet?pallet_id=" + pallet_id + "&status=" + 1;
            Log.d(TAG, "xyz url " + checkPalletUrl);
            GoodPresenterImpl mPresenter = new GoodPresenterImpl(this);
            mPresenter.loadData(checkPalletUrl, GoodPresenterImpl.CHECK_PALLET_ID);

            String checkPortUrl = Url.PATH + "/CheckPort?p_code=" + port_id;
            Log.d(TAG, "xyz  checkPortUrl " + checkPortUrl);
            GoodPresenterImpl mGoodCheckPortpre = new GoodPresenterImpl(this);
            mGoodCheckPortpre.loadData(checkPortUrl, GoodPresenterImpl.CHECK_PORT);
        } else {
            super.showToast("入库站口或托盘编号不能为空!!");
        }
    }

    @Override
    public void loadExecption(Exception e) {
        hideProgress();
        mInstoreBtn.setEnabled(true);
        super.showToast("请求过程中出现异常！！" + e);

    }

    @Override
    public void showLoadFail(int failNum) {
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
                inTostore();
                break;
            case R.id.backBtn:
                this.finish();
                break;
        }
    }


    @Override
    public void showProgress() {
        mFragment.show(getFragmentManager(), "进度框");
    }

    @Override
    public void hideProgress() {
        mFragment.dismiss();
    }

}
