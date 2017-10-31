package com.benjious.pdacontrol.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.been.InventoryBeen;
import com.benjious.pdacontrol.been.Picking;
import com.benjious.pdacontrol.been.ProductBeen;
import com.benjious.pdacontrol.been.ProductConfirmBeen;
import com.benjious.pdacontrol.been.User;
import com.benjious.pdacontrol.been.UsersALL;
import com.benjious.pdacontrol.fragment.ProductConfirmJianFragment;
import com.benjious.pdacontrol.fragment.ProductDialogFragment;
import com.benjious.pdacontrol.interfazes.OnUpdateInventoryStore;
import com.benjious.pdacontrol.interfazes.OnUpdateProductConfirm;
import com.benjious.pdacontrol.interfazes.OnUpdateProductLisenter;
import com.benjious.pdacontrol.presenter.GoodPresenterImpl;
import com.benjious.pdacontrol.url.Url;
import com.benjious.pdacontrol.util.DateUtil;
import com.benjious.pdacontrol.util.OkHttpUtils;
import com.benjious.pdacontrol.view.CommonView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;


/**
 * Created by Benjious on 2017/10/28.
 * k==1 时 ,为捡料确认
 *    测试用例:
 *       入库站口: 0012
 *       托盘编号: P0305
 * k==2时,为补料确认
 */

public class ProductConfigActivity extends BaseActivity implements CommonView, OnUpdateProductConfirm {
    @Bind(R.id.port_id)
    TextView mPortId;
    @Bind(R.id.port_id_edit)
    EditText mPortIdEdit;
    @Bind(R.id.pallet_id)
    TextView mPalletId;
    @Bind(R.id.pallet_id_edit)
    EditText mPalletIdEdit;
    @Bind(R.id.find_product_btn)
    Button mFindProductBtn;
    @Bind(R.id.BackBtn)
    Button mBackBtn;
    @Bind(R.id.add_product)
    Button mAddProduct;


    private List<Picking> mPickings;
    private Picking mPicking = new Picking();
    int kind;
    public static final String TAG = "ProConfigActivity xyz =";
    public static AtomicInteger CHECK_FINISHED = new AtomicInteger();
    public static final String[] JIAN_HEAD = {"物料编号", "物料名称", "出库数量", "单位",};
    public static final String[] BU_HEAD = {"物料编号", "物料名称", "已添加的数量", "单位",};
    private boolean checkPort = false;
    private int checkPallet = -2;

    private String p_code;
    private String pallet_id;
    private List<ProductConfirmBeen> mProductConfirmBeens = new ArrayList<>();
    private ProductConfirmBeenAdapter adapter;
    private int rowNowIndex;
    private User mUser;
    private TableView<ProductConfirmBeen> mProductTableView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_confirm);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mUser = (User) intent.getSerializableExtra(LoginActivity.USER);
        kind = intent.getIntExtra(LoginActivity.KIND, 0);
        if (kind == 2) {
            mAddProduct.setVisibility(View.VISIBLE);
        }
        setTableView();


    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

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
                    CHECK_FINISHED.addAndGet(1);
                    Log.d(TAG, "xyz  addData: 显示一下数据 " + checkPallet + "  " + CHECK_FINISHED);
                    Log.d(TAG, "xyz  addData: ------------------------------------------------------");


                } else if (type == GoodPresenterImpl.CHECK_PORT) {
                    checkPort = usersALL.getYesNo();
                    CHECK_FINISHED.addAndGet(1);
                    Log.d(TAG, "xyz  addData: 显示二下数据：" + checkPort + "  " + CHECK_FINISHED);
                    Log.d(TAG, "xyz  addData: ------------------------------------------------------");


                } else if (type == GoodPresenterImpl.GET_PICKING) {
                    Log.d(TAG, "xyz  addData: 是不是这里就没了");
                    mPickings = usersALL.getPickings();
                    Log.d(TAG, "xyz  addData: 显示三下数据 " + mPickings);
                    Log.d(TAG, "xyz  addData: ------------------------------------------------------");
                    if (mPicking != null) {
                        //将数据装进tableview中
                        setTableViewContent(mPickings);
                    } else {
                        showLoadFail(OkHttpUtils.NO_REAL_DATA);
                    }


                } else if (type == GoodPresenterImpl.UPDATE_STOCK) {
                    int finished = usersALL.getNumber();
                    if (finished == 1) {
                        String url = Url.PATH + "/UpdateStockDetail?Last_update_by=" + mUser.get_userID() + "&oid=" + mPicking.get_oID() + "&qty=" + mPicking.get_oUT_QTY();
                        GoodPresenterImpl updateImpl = new GoodPresenterImpl(this);
                        updateImpl.loadData(url, GoodPresenterImpl.UPDATE_STOCK_DETAIL);
                    } else {
                        super.showToast("更新stock失败!!!");
                    }


                } else if (type == GoodPresenterImpl.UPDATE_STOCK_DETAIL) {
                    boolean isfinished = usersALL.getYesNo();
                    if (isfinished) {
                        super.showToast("更新完成!!!");
                    } else {
                        super.showToast("更新失败!!!");
                    }
                }

                if (CHECK_FINISHED.get() == 2) {
                    mFindProductBtn.setEnabled(true);
                    if (checkPort && checkPallet == 1) {
                        String pickingUrl = Url.PATH + "/GetPickings?p_code=" + p_code + "&pallet_id=" + pallet_id;
                        GoodPresenterImpl pickingImpl = new GoodPresenterImpl(this);
                        pickingImpl.loadData(pickingUrl, GoodPresenterImpl.GET_PICKING);
                        Log.d(TAG, "xyz  addData: 三下URL " + pickingUrl);
                    } else if (!checkPort) {
                        super.showToast("站口号不存在,请重新输入!!!");
                        mPortIdEdit.getText().clear();
                    } else {
                        super.showToast("托盘号不存在,请重新输入!!!");
                        mPalletIdEdit.getText().clear();
                    }
                    CHECK_FINISHED.set(0);
                }
            }
        } catch (Exception e) {
            mFindProductBtn.setEnabled(true);
            e.printStackTrace();
        }

    }

    @Override
    public void loadExecption(Exception e) {
        mFindProductBtn.setEnabled(true);
        super.showToast("请求过程中出现异常！！" + e);

    }

    @Override
    public void showLoadFail(int failNum) {
        mFindProductBtn.setEnabled(true);
        if (failNum == OkHttpUtils.SERVER_OFFLINE) {
            Toast.makeText(this, "请求服务器出现错误！！", Toast.LENGTH_SHORT).show();
        } else if (failNum == OkHttpUtils.NO_REAL_DATA) {
            Toast.makeText(this, "获取数据成功，但数据为空！！", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick({R.id.find_product_btn, R.id.BackBtn, R.id.add_product})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.find_product_btn:
                showDetail();
                break;
            case R.id.BackBtn:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.add_product:
                break;
        }
    }

    private void showDetail() {
        p_code = mPortIdEdit.getText().toString();
        pallet_id = mPalletIdEdit.getText().toString();
        if ((!p_code.equals("")) && (!pallet_id.equals(""))) {
            //进行数据库查询
            mFindProductBtn.setEnabled(false);
            String checkPalletUrl = Url.PATH + "/CheckPallet?pallet_id=" + pallet_id + "&status=" + 1;
            Log.d(TAG, "xyz url " + checkPalletUrl);
            GoodPresenterImpl mPresenter = new GoodPresenterImpl(this);
            mPresenter.loadData(checkPalletUrl, GoodPresenterImpl.CHECK_PALLET_ID);

            String checkPortUrl = Url.PATH + "/CheckPort?p_code=" + p_code;
            Log.d(TAG, "xyz  checkPortUrl " + checkPortUrl);
            GoodPresenterImpl mGoodCheckPortpre = new GoodPresenterImpl(this);
            mGoodCheckPortpre.loadData(checkPortUrl, GoodPresenterImpl.CHECK_PORT);
        } else {
            showToast("托盘编号或站口号不能为空!");
        }
    }

    public void setTableViewContent(List<Picking> tableViewContent) {
        int qty = 0;
        for (Picking picking :
                tableViewContent) {
            ProductConfirmBeen been = new ProductConfirmBeen();
            been.setProduct_name(picking.get_pRODUCT_NAME());
            been.setProduct_id(picking.get_pRODUCT_ID());
            if (kind == 2) {
                if (picking.get_oUT_QTY() >= 0) {
                    qty = 0;
                } else {
                    qty = (int) ((-1) * picking.get_oUT_QTY());
                }
            }
            been.setOut_storeNum(qty);
            been.setProduct_unit(picking.get_uOM());
            mProductConfirmBeens.add(been);
        }
        setTableView();
        tableviewFresh();
    }

    private void setTableView() {
        mProductTableView = (TableView<ProductConfirmBeen>) findViewById(R.id.product_tableView);
        adapter = new ProductConfirmBeenAdapter(this, mProductConfirmBeens);
        mProductTableView.addDataClickListener(new ProductConfirmlistener());
        mProductTableView.setDataAdapter(adapter);

        TableColumnWeightModel columnWeightModel = new TableColumnWeightModel(4);
        columnWeightModel.setColumnWeight(1, 1);
        columnWeightModel.setColumnWeight(2, 1);
        columnWeightModel.setColumnWeight(3, 1);
        columnWeightModel.setColumnWeight(4, 1);
        mProductTableView.setColumnModel(columnWeightModel);
        if (kind == 2) {
            mProductTableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, BU_HEAD));
        } else {
            mProductTableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, JIAN_HEAD));
        }
    }


    private void tableviewFresh() {
        adapter.notifyDataSetChanged();
//        tableView.notify();
    }

    @Override
    public void updateProductConfirm(int num, boolean isChecked) {
        if (kind == 1) {
            mPicking.set_oUT_QTY(num);
        } else if (kind == 2) {
            mPicking.set_oUT_QTY(mPicking.get_oUT_QTY() - num);
        }
        String url = String.format(Url.PATH + "/UpdateStock?Last_update_by=%s&Stock_oid=%s&Full_Flag=%s", mUser.get_userID(), mPicking.get_sTOCK_OID(), isChecked);
        GoodPresenterImpl updateConfirm = new GoodPresenterImpl(this);
        updateConfirm.loadData(url, GoodPresenterImpl.UPDATE_STOCK);
    }

    private class ProductConfirmBeenAdapter extends TableDataAdapter<ProductConfirmBeen> {


        public ProductConfirmBeenAdapter(Context context, List<ProductConfirmBeen> data) {
            super(context, data);
        }

        @Override
        public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
            final ProductConfirmBeen been = getRowData(rowIndex);
            View renderedView = null;
            switch (columnIndex) {
                case 0:
                    renderedView = renderString(been.getProduct_id());
                    break;
                case 1:
                    renderedView = renderString(been.getProduct_name());
                    break;
                case 2:
                    renderedView = renderString(String.valueOf(been.getOut_storeNum()));
                    break;
                case 3:
                    renderedView = renderString(been.getProduct_unit());
                    break;
            }
            return renderedView;
        }
    }

    private View renderString(final String value) {
        final TextView textView = new TextView(this);
        textView.setText(value);
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(12);
        return textView;
    }

    private class ProductConfirmlistener implements TableDataClickListener<ProductConfirmBeen> {


        @Override
        public void onDataClicked(int rowIndex, ProductConfirmBeen clickedData) {
            rowNowIndex = rowIndex;

            mPicking.set_oID(mPickings.get(rowNowIndex).get_oID());
            mPicking.set_sTOCK_OID(mPickings.get(rowNowIndex).get_sTOCK_OID());
            mPicking.set_oUT_QTY(mPickings.get(rowNowIndex).get_oUT_QTY());

            if (kind == 1) {
                ProductConfirmJianFragment fragment = new ProductConfirmJianFragment();
                fragment.show(getFragmentManager(), "对话框");
            } else {
                if (mPickings.get(rowNowIndex).get_oUT_QTY() >= 0) {
                    mPicking.set_oUT_QTY(0);
                }
                ProductConfirmJianFragment fragment = new ProductConfirmJianFragment();
                fragment.show(getFragmentManager(), "对话框");
            }

        }
    }
}
