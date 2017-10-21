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
import com.benjious.pdacontrol.url.Url;
import com.benjious.pdacontrol.util.OkHttpUtils;
import com.benjious.pdacontrol.view.CommonView;
import com.google.gson.Gson;

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
    public static final String TAG = " xyz =";
    public static AtomicInteger UP_FINISHED = new AtomicInteger();
    public static final String[] HEAD_DATA = {"编号", "名称", "数量", "日期",};
    public static final String PRODUCT_ID = "PRODUCT_ID";
    public static final String PRODUCT_NUMBER = "PRODUCT_NUMBER";
    public static final String PRODUCT_DATE = "PRODUCT_DATE";

    private User user;
    private Stacking mStacking;
    private List<StackingItem> mStackingItems;
    private int kind;
    private int rowNowIndex;
    private List<StockDetail> mStockDetails = new ArrayList<>();
    private FragmentTransaction mTransaction;

    private int insertStacking;
    private int insertStackingItem;
    private int updatePalletid;


    private ViewGroup tvViewGroup;

    private TableView<ProductBeen> tableView;
    private ProductBeenAdapter adapter;

    private List<ProductBeen> mBeens = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);

//        Intent intent = getIntent();
//       kind =  intent.getIntExtra(ProductReadyActivity.KIND_SEND, 0);
//        if (kind == 1) {
//            mStacking = (Stacking) intent.getSerializableExtra(ProductReadyActivity.STACKING_SEND);
//            mStackingItems = (List<StackingItem>) intent.getSerializableExtra(ProductReadyActivity.STACKING_ITEM_LIST);
//
//        } else {
//            mStockDetails = (List<StockDetail>) intent.getSerializableExtra(ProductReadyActivity.STOCKING_DETAIL_LIST);
//        }

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
        if (mStackingItems.size() == 0 && mStockDetails.size() == 0) {
            super.showToast("没有物料信息,无法提交任务!!");
        } else {
            if (kind == 1) {

                String checkPalletUrl = Url.PATH + "/CheckPallet?pallet_id=" + mStacking.get_pALLET_ID() + "&status=" + 0;
                Log.d(TAG, "xyz  nextStep: checkPalletUrl " + checkPalletUrl);
                GoodPresenterImpl mPresenter = new GoodPresenterImpl(this);
                mPresenter.loadData(checkPalletUrl, GoodPresenterImpl.CHECK_PALLET_ID);

            } else {
                String url = String.format(Url.PATH + "/UpdatePallet?Last_update_by=%s&Stock_oid=%s&Full_Flag=%s", new Date().toString(), user.get_userID(), checkNot);
                Log.d(TAG, "xyz  updateProduct: updateStockUrl" + url);
                GoodPresenterImpl mPresenter = new GoodPresenterImpl(this);
                mPresenter.loadData(url, GoodPresenterImpl.UPDATE_STOCK);
            }
        }
        super.showToast(String.valueOf(checkNot));
    }

    @Override
    public void deleteRowindex() {
        mBeens.remove(rowNowIndex);
        tableviewFresh();
    }

    @Override
    public void goBackProductAdd() {
        Intent intent = new Intent(this, ProductsAddActivity.class);
        intent.putExtra(PRODUCT_ID, mBeens.get(rowNowIndex).getProductId());
        intent.putExtra(PRODUCT_NUMBER, mBeens.get(rowNowIndex).getProductNum());
        intent.putExtra(PRODUCT_DATE, mBeens.get(rowNowIndex).getProductDate());
        startActivity(intent);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

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
                    if (havanot == 0) {
                        mStacking.set_cREATION_DATE(new Date());
                        mStacking.set_cREATED_BY(user.get_userID());
                        mStacking.set_lAST_UPDATE_DATE(new Date());
                        mStacking.set_lAST_UPDATED_BY(user.get_userID());

                        String url = Url.PATH + "/InsertStacking?STACK_ID='" + mStacking.get_sTACK_ID() + "'" + "&WH_NO=" + mStacking.get_wH_NO() + "'" + "&PALLET_ID='" + mStacking.get_pALLET_ID() + "'" + "&P_CODE=" + mStacking.get_p_CODE() + "&KIND=" + mStacking.get_kIND() + "&BIN_NO=" + mStacking.get_bIN_NO() + "&FULL_FLAG=" + mStacking.get_fULL_FLAG() + "&STATUS=" + mStacking.get_sTATUS() + "&CREATION_DATE='" + mStacking.get_cREATION_DATE() + "'" + "&CREATED_BY=" + mStacking.get_cREATED_BY() + "&LAST_UPDATE_DATE='" + mStacking.get_lAST_UPDATE_DATE() + "'" + "&LAST_UPDATED_BY='" + mStacking.get_lAST_UPDATED_BY() + "'";
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
                    UP_FINISHED.addAndGet(1);
                    Log.d(TAG, "xyz  addData: 显示六下数据 " + insertStacking);
                    if (insertStacking > 0) {
                        String url = Url.PATH + "/InsertStackingItem?ITEM_ID='" + mStackingItems.get(rowNowIndex - 1).get_iTEM_ID() + "'&" + "STACK_ID='" + mStackingItems.get(rowNowIndex - 1).get_sTACK_ID() + "'&" + "LIST_NO='" + mStackingItems.get(rowNowIndex - 1).get_lIST_NO()
                                + "'&" + "QTY='" + mStackingItems.get(rowNowIndex - 1).get_qTY() + "'&" + "PROD_DATE='" + mStackingItems.get(rowNowIndex - 1).get_pROD_DATE()
                                + "'&" + "CREATION_DATE='" + mStackingItems.get(rowNowIndex - 1).get_cREATION_DATE() + "'&" + "CREATED_BY='" + mStackingItems.get(rowNowIndex - 1).get_cREATED_BY() +
                                "'&" + "LAST_UPDATE_DATE='" + mStackingItems.get(rowNowIndex - 1).get_lAST_UPDATE_DATE() + "'&" + "LAST_UPDATED_BY='" + mStackingItems.get(rowNowIndex - 1).get_lAST_UPDATED_BY() + "'";
                        GoodPresenterImpl presenter = new GoodPresenterImpl(this);
                        presenter.loadData(url, GoodPresenterImpl.INSERT_STACKINGITEM);
                    }
                }
                if (type == GoodPresenterImpl.INSERT_STACKINGITEM) {
                    insertStackingItem = usersALL.getNumber();
                    UP_FINISHED.addAndGet(1);
                    Log.d(TAG, "xyz  addData: 显示七下数据 " + insertStacking);
                    if (insertStackingItem > 0) {
                        String url = Url.PATH + "/UpdatePallet?LAST_UPDATE_DATE='" + mStacking.get_lAST_UPDATE_DATE() + "'&" + "LAST_UPDATED_BY='" + mStacking.get_lAST_UPDATED_BY() + "'&" + "PALLET_ID='" + mStacking.get_pALLET_ID();
                        GoodPresenterImpl presenter = new GoodPresenterImpl(this);
                        presenter.loadData(url, GoodPresenterImpl.INSERT_STACKINGITEM);
                    }
                }
                if (type == GoodPresenterImpl.UPDATE_PALLETID) {
                    updatePalletid = usersALL.getNumber();
                    UP_FINISHED.addAndGet(1);
                    Log.d(TAG, "xyz  addData: 显示八下数据 " + insertStacking);
                    if (updatePalletid > 0) {
                        super.showToast("提交成功!");
                        mStackingItems.remove(rowNowIndex - 1);
                        tableviewFresh();
                    }
                }

                if (type== GoodPresenterImpl.UPDATE_STOCK) {

                }
            }

        } catch (Exception e) {
            super.showToast("解析数据出现错误");
            Log.d(TAG, "xyz  addData: " + e);
        } finally {

        }

    }

    @Override
    public void loadExecption(Exception e) {
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


    private class ProductDatalistener implements TableDataClickListener<ProductBeen> {
        @Override
        public void onDataClicked(int rowIndex, ProductBeen clickedData) {
            rowNowIndex = rowIndex;
            ProductDialogFragment newFragment = new ProductDialogFragment();
            newFragment.show(getFragmentManager(), "对话框");

//            String Tag ="对话框";
//            FragmentManager fragmentManager = getSupportFragmentManager() ;
//            mTransaction= fragmentManager.beginTransaction();
//            mTransaction.add(newFragment,Tag);
//            mTransaction.commit();
//            for (int i = 0; i < 10; i++) {
//                ProductBeen been = new ProductBeen(12, "SJ_JDI-34", "HGOEJDD", new Date());
//                mBeens.add(been);
//            }
//
//            if (mBeens.size()>10) {
////                tvViewGroup.setClickable(false);
////                tableView.setClickable(false);
////                tableView.setEnabled(false);
//            }

            // tableviewFresh();
        }
    }


    private void tableviewFresh() {
        adapter.notifyDataSetChanged();
        tableView.notify();
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
                    renderedView = renderString(been.getProductDate().toString());
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

    private View renderCheck() {
        final CheckBox checkBox = new CheckBox(this);
        checkBox.setPadding(20, 10, 5, 10);
        return checkBox;
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
//        if (kind==1) {
//            for (StackingItem item : mStackingItems) {
//                ProductBeen been = new ProductBeen(item.get_qTY(), item.get_product_name(), item.get_iTEM_ID(), item.get_pROD_DATE());
//                mBeens.add(been);
//            }
//        }else {
//            for (StockDetail  item : mStockDetails) {
//                ProductBeen been = new ProductBeen(item.get_qTY(), item.get_product_name(), item.get_iTEM_ID(), item.get_pROD_DATE());
//                mBeens.add(been);
//            }
//        }

        for (int i = 0; i < 2; i++) {
            ProductBeen been = new ProductBeen(12, "SJ_JDI-34", "HGOEJDD", new Date());
            mBeens.add(been);
        }
    }

    private void setTableView() {
        tableView = (TableView<ProductBeen>) findViewById(R.id.tableView11);
        tableView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
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
