package com.benjious.pdacontrol.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.been.ProductBeen;
import com.benjious.pdacontrol.been.Stacking;
import com.benjious.pdacontrol.been.StackingItem;
import com.benjious.pdacontrol.been.StockDetail;
import com.benjious.pdacontrol.been.User;
import com.benjious.pdacontrol.been.UsersALL;
import com.benjious.pdacontrol.fragment.ProductDialogFragment;
import com.benjious.pdacontrol.fragment.Product_FullFragment;
import com.benjious.pdacontrol.interfazes.OnUpdateProductLisenter;
import com.benjious.pdacontrol.presenter.GoodPresenterImpl;
import com.benjious.pdacontrol.util.DateUtil;
import com.benjious.pdacontrol.url.Url;
import com.benjious.pdacontrol.util.OkHttpUtils;
import com.benjious.pdacontrol.view.CommonView;
import com.google.gson.Gson;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;


/**
 * Created by Benjious on 2017/10/19.
 */

public class ProductsInActivity extends BaseActivity implements OnUpdateProductLisenter, CommonView {
    public static final String TAG = "productIn xyz =";
    public static AtomicInteger INSERT_STOCK_DETAIL = new AtomicInteger();
    public static final String[] HEAD_DATA = {"编号", "名称", "数量", "日期",};
    private User user;
    private Stacking mStacking;
    private List<StackingItem> mStackingItems;
    private int kind;
    private int rowNowIndex;
    private List<StockDetail> mStockDetails = new ArrayList<>();

    private int insertStacking;
    private int insertStackingItem;
    private int updatePallet;
    private int updateStock;


    private ViewGroup tvViewGroup;

    private TableView<ProductBeen> tableView;
    private ProductBeenAdapter adapter;

    private List<ProductBeen> mBeens = new ArrayList<>();
    private Boolean checkNot;

//    prvivate AtomicInteger IS_INSERT_STOCK_DETAIL =;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);

        Intent intent = getIntent();
        kind = intent.getIntExtra(ProductReadyActivity.KIND_SEND, 0);
        user = (User) intent.getSerializableExtra(ProductReadyActivity.USER_SEND);
        if (kind == 1) {
            mStacking = (Stacking) intent.getSerializableExtra(ProductReadyActivity.STACKING_SEND);
            mStackingItems = (List<StackingItem>) intent.getSerializableExtra(ProductReadyActivity.STACKING_ITEM_LIST);

        } else {
            mStockDetails = (List<StockDetail>) intent.getSerializableExtra(ProductReadyActivity.STOCKING_DETAIL_LIST);
        }
        setProductBeen();
        setTableView();
    }


    @Override
    public void goToFullCheck() {
        Product_FullFragment checkFragment = new Product_FullFragment();
        checkFragment.show(getFragmentManager(), "是否标记满托标志");
    }

    @Override
    public void updateProduct(Boolean checkNot) {
        this.checkNot = checkNot;
        if (mStackingItems == null && mStockDetails == null) {
            super.showToast("请先返回添加物料,提交无法操作");
        } else {
            if (kind == 1) {
                super.showToast("正在提交物料");
                String checkPalletUrl = Url.PATH + "/CheckPallet?pallet_id=" + mStacking.get_pALLET_ID() + "&status=" + 0;
                Log.d(TAG, "xyz  upProduct: checkPalletUrl " + checkPalletUrl);
                GoodPresenterImpl mPresenter = new GoodPresenterImpl(this);
                mPresenter.loadData(checkPalletUrl, GoodPresenterImpl.CHECK_PALLET_ID);

            } else {
                super.showToast("正在向该托盘添加物料");
                String url = String.format(Url.PATH + "/UpdateStock?Last_update_by=%s&Stock_oid=%s&Full_Flag=%s", user.get_userID(), mStockDetails.get(1).get_sTOCK_OID(), checkNot);
                Log.d(TAG, "xyz  updateProduct: updateStockUrl" + url);
                GoodPresenterImpl mPresenter = new GoodPresenterImpl(this);
                mPresenter.loadData(url, GoodPresenterImpl.UPDATE_STOCK);
            }
        }
    }

    @Override
    public void addData(String response, int type) {
        Log.d(TAG, "xyz  addData: 这里这里");
        hideProgress();
        try {
            Gson gson = new Gson();
            UsersALL usersALL = gson.fromJson(response, UsersALL.class);
            if (usersALL == null) {
                showLoadFail(OkHttpUtils.NO_REAL_DATA);
            } else {
                if (type == GoodPresenterImpl.CHECK_PALLET_ID) {
                    int havanot = -1;
                    havanot = usersALL.getNumber();
                    Log.d(TAG, "xyz  addData: check_pallet_id " + havanot);
                    if (havanot == 0) {
                        mStacking.set_fULL_FLAG(this.checkNot);
                        mStacking.set_cREATION_DATE(new Date());
                        mStacking.set_cREATED_BY(user.get_userID());
                        mStacking.set_lAST_UPDATE_DATE(new Date());
                        mStacking.set_lAST_UPDATED_BY(user.get_userID());
                        String checkNotString = null;
                        if (this.checkNot) {
                            checkNotString = "1";
                        } else {
                            checkNotString = "0";
                        }
//                        String url = Url.PATH + "/InsertStacking?STACK_ID='" + mStacking.get_sTACK_ID() + "'" + "&WH_NO='" + mStacking.get_wH_NO() + "'" + "&PALLET_ID='" + mStacking.get_pALLET_ID() + "'" + "&P_CODE=" + mStacking.get_p_CODE() + "&KIND=" + mStacking.get_kIND() + "&BIN_NO=" + mStacking.get_bIN_NO() + "&FULL_FLAG=" + mStacking.get_fULL_FLAG() + "&STATUS=" + mStacking.get_sTATUS() + "&CREATION_DATE='" + DateUtil.converToString(mStacking.get_cREATION_DATE()) + "'" + "&CREATED_BY=" + mStacking.get_cREATED_BY() + "&LAST_UPDATE_DATE='" + DateUtil.converToString( mStacking.get_lAST_UPDATE_DATE()) + "'" + "&LAST_UPDATED_BY='" + mStacking.get_lAST_UPDATED_BY() + "'";
                        String url = Url.PATH + "/InsertStacking?STACK_ID=" + mStacking.get_sTACK_ID() + "&WH_NO=" + mStacking.get_wH_NO() + "&PALLET_ID=" + mStacking.get_pALLET_ID() + "&P_CODE=" + mStacking.get_p_CODE() + "&KIND=" + mStacking.get_kIND() + "&BIN_NO=" + mStacking.get_bIN_NO() + "&FULL_FLAG=" + checkNotString + "&STATUS=" + mStacking.get_sTATUS() + "&CREATION_DATE=" + DateUtil.converToString(mStacking.get_cREATION_DATE()) + "&CREATED_BY=" + mStacking.get_cREATED_BY() + "&LAST_UPDATE_DATE=" + DateUtil.converToString(mStacking.get_lAST_UPDATE_DATE()) + "&LAST_UPDATED_BY=" + mStacking.get_lAST_UPDATED_BY();

                        Log.d(TAG, "xyz  addData: insertStacking url " + url);
                        GoodPresenterImpl presentImp = new GoodPresenterImpl(this);
                        presentImp.loadData(url, GoodPresenterImpl.INSERT_STACKING);
                    } else {
                        if (havanot == 1) {
                            super.showToast("该任务已经存在,请勿重复插入");
                        }
                    }
                }

                if (type == GoodPresenterImpl.INSERT_STACKING) {
                    insertStacking = usersALL.getNumber();
                    Log.d(TAG, "xyz  addData: 显示六下数据 " + insertStacking);
                    Log.d(TAG, "xyz  addData: ------------------------------------------------------");
                    if (insertStacking > 0) {
                        String url = Url.PATH + "/InsertStackingItem?ITEM_ID=" + mStackingItems.get(rowNowIndex).get_iTEM_ID() + "&" + "STACK_ID=" + mStackingItems.get(rowNowIndex).get_sTACK_ID() + "&" + "LIST_NO=" + mStackingItems.get(rowNowIndex).get_lIST_NO()
                                + "&" + "QTY=" + mStackingItems.get(rowNowIndex).get_qTY() + "&" + "PROD_DATE=" + DateUtil.converToString(mStackingItems.get(rowNowIndex).get_pROD_DATE())
                                + "&" + "CREATION_DATE=" + DateUtil.converToString(mStackingItems.get(rowNowIndex).get_cREATION_DATE()) + "&" + "CREATED_BY=" + mStackingItems.get(rowNowIndex).get_cREATED_BY() +
                                "&" + "LAST_UPDATE_DATE=" + DateUtil.converToString(mStackingItems.get(rowNowIndex).get_lAST_UPDATE_DATE()) + "&" + "LAST_UPDATED_BY=" + mStackingItems.get(rowNowIndex).get_lAST_UPDATED_BY();
                        Log.d(TAG, "xyz  addData: 显示六下数据的url " + url);
                        GoodPresenterImpl presenter = new GoodPresenterImpl(this);
                        presenter.loadData(url, GoodPresenterImpl.INSERT_STACKINGITEM);
                    }
                }
                if (type == GoodPresenterImpl.INSERT_STACKINGITEM) {
                    insertStackingItem = usersALL.getNumber();
                    Log.d(TAG, "xyz  addData: 显示七下数据 " + insertStacking);
                    Log.d(TAG, "xyz  addData: ------------------------------------------------------");
                    if (insertStackingItem > 0) {
                        String url = Url.PATH + "/UpdatePallet?LAST_UPDATE_DATE=" + DateUtil.converToString(mStacking.get_lAST_UPDATE_DATE()) + "&" + "LAST_UPDATED_BY=" + mStacking.get_lAST_UPDATED_BY() + "&" + "PALLET_ID=" + mStacking.get_pALLET_ID();
                        Log.d(TAG, "xyz  addData: 显示七下数据的url " + url);
                        GoodPresenterImpl presenter = new GoodPresenterImpl(this);
                        presenter.loadData(url, GoodPresenterImpl.UPDATE_PALLET);
                    }
                }

                if (type == GoodPresenterImpl.UPDATE_PALLET) {
                    updatePallet = usersALL.getNumber();
                    Log.d(TAG, "xyz  addData: 显示八下数据 " + updatePallet);
                    Log.d(TAG, "xyz  addData: ------------------------------------------------------");
                    if (updatePallet > 0) {
                        super.showToast("提交成功!");
                        mStackingItems.remove(rowNowIndex);
                        tableviewFresh();
                    } else {
                        super.showToast("提交失败");
                    }
                }

                if (type == GoodPresenterImpl.UPDATE_STOCK) {
                    updateStock = usersALL.getNumber();
                    Log.d(TAG, "xyz  addData: 显示九下数据 " + updateStock);
                    Log.d(TAG, "xyz  addData: ------------------------------------------------------");

                    if (updateStock > 0) {
                        //insertStockDetail();
                        for (StockDetail stockDetail : mStockDetails) {
                            String url = String.format(Url.PATH + "/InsertStockDetail?STOCK_OID=%s&ITEM_ID=%s&PROD_DATE=%s&EXPIRE_DATE=%s&BATCH_NO=%s&QTY=%s&OUT_QTY=%s&STOCK_QTY=%s&CREATION_DATE=%s&CREATED_BY=%s&LAST_UPDATE_DATE=%s&LAST_UPDATED_BY=%s", stockDetail.get_sTOCK_OID(),
                                    stockDetail.get_iTEM_ID(), DateUtil.converToString(stockDetail.get_pROD_DATE()),
                                    DateUtil.converToString(stockDetail.get_eXPIRE_DATE()), stockDetail.get_bATCH_NO(), stockDetail.get_qTY(), stockDetail.get_oUT_QTY(), stockDetail.get_sTOCK_QTY(), DateUtil.converToString(stockDetail.get_cREATION_DATE()),
                                    stockDetail.get_cREATED_BY(), DateUtil.converToString(stockDetail.get_lAST_UPDATE_DATE()), stockDetail.get_cREATED_BY());
                            GoodPresenterImpl insertpre = new GoodPresenterImpl(this);
                            insertpre.loadData(url, GoodPresenterImpl.INSERT_STOCK_DETAIL);
                        }
                    }
                }

                if (type == GoodPresenterImpl.INSERT_STOCK_DETAIL) {
                    INSERT_STOCK_DETAIL.addAndGet(usersALL.getNumber());
                    if (INSERT_STOCK_DETAIL.get() == mStackingItems.size()) {
                        mStockDetails.clear();
                        mBeens.clear();
                        tableviewFresh();
                        super.showToast("添加物料成功");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            super.showToast("解析数据出现错误" + e);
            Log.d(TAG, "xyz  addData: " + e);
        }

    }

    @Override
    public void deleteRowindex() {
        mBeens.remove(rowNowIndex);
        if (kind == 1) {
            mStackingItems.remove(rowNowIndex);
        } else {
            mStockDetails.remove(rowNowIndex);
        }
        if (mBeens.size() == 0) {
            goBackProductAdd();
        }
        tableviewFresh();
    }

    @Override
    public void goBackProductAdd() {
        mBeens.clear();
        this.finish();
        Intent intent = new Intent(this, ProductsAddActivity.class);
        startActivity(intent);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }


    @Override
    public void loadExecption(Exception e) {
        super.showToast("请求过    程中出现异常！！" + e);
    }

    @Override
    public void showLoadFail(int failNum) {
        if (failNum == OkHttpUtils.SERVER_OFFLINE) {
            Toast.makeText(this, "请求服务器出现错误！！", Toast.LENGTH_SHORT).show();
        } else if (failNum == OkHttpUtils.NO_REAL_DATA) {
            Toast.makeText(this, "获取数据成功，但数据为空！！", Toast.LENGTH_SHORT).show();
        }
    }


    private class ProductDatalistener implements TableDataClickListener<ProductBeen> {
        @Override
        public void onDataClicked(int rowIndex, ProductBeen clickedData) {
            rowNowIndex = rowIndex;
            ProductDialogFragment newFragment = new ProductDialogFragment();
            newFragment.show(getFragmentManager(), "对话框");

        }
    }


    private void tableviewFresh() {
        adapter.notifyDataSetChanged();
//        tableView.notify();
    }


    private class ProductBeenAdapter extends TableDataAdapter<ProductBeen> {


        public ProductBeenAdapter(Context context, List<ProductBeen> data) {
            super(context, data);
        }

        @Override
        public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
            final ProductBeen been = getRowData(rowIndex);
            tvViewGroup = parentView;
            View renderedView = null;
            switch (columnIndex) {
                case 0:
                    renderedView = renderString(been.getProductId());
                    break;
                case 1:
                    renderedView = renderString(been.getProductName());
                    break;
                case 2:
                    renderedView = renderString(String.valueOf(been.getProductNum()));
                    break;
                case 3:
                    renderedView = renderString(DateUtil.converToString(been.getProductDate()));
                    break;
                case 4:
//                    renderedView =renderCheck();
                    break;
            }
            return renderedView;
        }
    }

    private View renderString(final String value) {
        final TextView textView = new TextView(this);
        textView.setText(value);
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(10);
        return textView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fragment_menu_list, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_to_main:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return true;

    }

    private void setProductBeen() {
        if (kind == 1) {
            for (StackingItem item : mStackingItems) {
                ProductBeen been = new ProductBeen(item.get_qTY(), item.get_product_name(), item.get_iTEM_ID(), item.get_pROD_DATE());
                mBeens.add(been);
            }
        } else {
            for (StockDetail item : mStockDetails) {
                ProductBeen been = new ProductBeen(item.get_qTY(), item.get_product_name(), item.get_iTEM_ID(), item.get_pROD_DATE());
                mBeens.add(been);
            }
        }
    }

    private void setTableView() {
        tableView = (TableView<ProductBeen>) findViewById(R.id.tableView11);
        adapter = new ProductBeenAdapter(this, mBeens);
        tableView.addDataClickListener(new ProductDatalistener());
        tableView.setDataAdapter(adapter);

        TableColumnWeightModel columnWeightModel = new TableColumnWeightModel(4);
        columnWeightModel.setColumnWeight(1, 1);
        columnWeightModel.setColumnWeight(2, 1);
        columnWeightModel.setColumnWeight(3, 1);
        columnWeightModel.setColumnWeight(4, 1);
        tableView.setColumnModel(columnWeightModel);
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, HEAD_DATA));

    }


}
