package com.benjious.pdacontrol.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.been.Inventory;
import com.benjious.pdacontrol.been.InventoryBeen;
import com.benjious.pdacontrol.been.ProductBeen;
import com.benjious.pdacontrol.been.User;
import com.benjious.pdacontrol.been.UsersALL;
import com.benjious.pdacontrol.fragment.InventoryFragment;
import com.benjious.pdacontrol.fragment.ProductDialogFragment;
import com.benjious.pdacontrol.interfazes.OnUpdateInventoryStore;
import com.benjious.pdacontrol.model.GoodModelImpl;
import com.benjious.pdacontrol.presenter.GoodPresenterImpl;
import com.benjious.pdacontrol.url.Url;
import com.benjious.pdacontrol.util.OkHttpUtils;
import com.benjious.pdacontrol.view.CommonView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static org.apache.http.HttpHeaders.IF;

/**
 * Created by Benjious on 2017/10/24.
 */

public class InventoryActivity extends BaseActivity implements CommonView, OnUpdateInventoryStore {

    @Bind(R.id.pallet_id)
    TextView mPalletId;
    @Bind(R.id.pallet_id_edit)
    EditText mPalletIdEdit;
    @Bind(R.id.find_product_btn)
    Button mFindProductBtn;
    @Bind(R.id.BackBtn)
    Button mBackBtn;

    private int rowNowIndex;
    private User mUser;
    private ProductInventAdapter adapter;
    private List<InventoryBeen> mInventoryBeens = new ArrayList<>();
    private List<Inventory> mInventories;
    public static final String[] HEAD_DATA = {"物料名称", "物料编号", "托盘编号", "库存数量"};
    public static final String TAG = "InventoryActivity xyz =";

    private TableView<InventoryBeen> mProductTableView;
    private String check_list_no;
    private int oid = -1;
    private int updateStoreNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inverntory);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mUser = (User) intent.getSerializableExtra(LoginActivity.USER);
        setTableView();
    }

    private void setContent() {

//        for (int i = 0; i < 10; i++) {
//            InventoryBeen been = new InventoryBeen("a", "b", "c", 33);
//            mInventoryBeens.add(been);
//        }
//        tableviewFresh();

        if (mPalletIdEdit.getText().toString().equals("")) {
            super.showToast("请输入托盘编号!");
        } else {
            mFindProductBtn.setEnabled(false);
            String invenUrl = Url.PATH + "/GetInventorys?pallet_id=" + mFindProductBtn.getText();
            GoodPresenterImpl invenImp = new GoodPresenterImpl(this);
            invenImp.loadData(invenUrl, GoodPresenterImpl.GET_INVENTORYS);
            Log.d(TAG, "xyz  setContent: invenurl : " + invenUrl);
        }

    }

    private void setTableView() {
        mProductTableView = (TableView<InventoryBeen>) findViewById(R.id.product_tableView);
        adapter = new ProductInventAdapter(this, mInventoryBeens);
        mProductTableView.addDataClickListener(new ProductDatalistener());
        mProductTableView.setDataAdapter(adapter);

        TableColumnWeightModel columnWeightModel = new TableColumnWeightModel(4);
        columnWeightModel.setColumnWeight(1, 1);
        columnWeightModel.setColumnWeight(2, 1);
        columnWeightModel.setColumnWeight(3, 1);
        columnWeightModel.setColumnWeight(4, 1);
        mProductTableView.setColumnModel(columnWeightModel);
        mProductTableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, HEAD_DATA));
    }


    @Override
    public void showProgress() {
        // ((ViewStub)findViewById(R.id.viewStub)).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
//        ((ViewStub)findViewById(R.id.viewStub)).setVisibility(View.INVISIBLE);
    }

    @Override
    public void addData(String response, int type) {
        hideProgress();
        mFindProductBtn.setEnabled(true);
        try {
            Gson gson = new Gson();
            UsersALL usersALL = gson.fromJson(response, UsersALL.class);
            if (usersALL == null) {
                showLoadFail(OkHttpUtils.NO_REAL_DATA);
            } else {
                if (type == GoodPresenterImpl.GET_INVENTORYS) {
                    if (usersALL.getInventories() == null) {
                        super.showToast("没有该托盘的盘点任务!");
                    } else {
                        //把数据封装到 InventoryBeen
                        mInventories = usersALL.getInventories();
                        for (Inventory inventory :
                                mInventories) {
                            InventoryBeen inventoryBeen = new InventoryBeen(inventory.get_pALLET_ID(), inventory.get_pRODUCT_ID(), inventory.get_pRODUCT_NAME(), (int) inventory.get_oLD_STOCK_QTY());
                            mInventoryBeens.add(inventoryBeen);
                        }
                        //数据刷新
                        tableviewFresh();
                    }
                }
                if (type == GoodPresenterImpl.UPDATE_INVENTORYS) {
                    boolean result = usersALL.getYesNo();
                    if (result) {
                        mInventories.get(rowNowIndex).set_oLD_STOCK_QTY(updateStoreNum);
                        mInventoryBeens.get(rowNowIndex).setStoreNum(updateStoreNum);
                        tableviewFresh();
                        super.showToast("成功修改了库存数量!! ");

                    } else {
                        super.showToast("修改出现错误!!! ");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            hideProgress();
            super.showToast("解析数据出现错误" + e);
            Log.d(TAG, "xyz  addData: " + e);
        }
    }

    @Override
    public void loadExecption(Exception e) {
        mFindProductBtn.setEnabled(true);
        hideProgress();
        super.showToast("请求过程中出现异常！！" + e);
    }

    @Override
    public void showLoadFail(int failNum) {
        mFindProductBtn.setEnabled(true);
        hideProgress();
        if (failNum == OkHttpUtils.SERVER_OFFLINE) {
            Toast.makeText(this, "请求服务器出现错误！！", Toast.LENGTH_SHORT).show();
        } else if (failNum == OkHttpUtils.NO_REAL_DATA) {
            Toast.makeText(this, "获取数据成功，但数据为空！！", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.find_product_btn, R.id.BackBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.find_product_btn:
                setContent();
                break;
            case R.id.BackBtn:
//                this.finish();

                break;
        }
    }

    @Override
    public void upInventoryStore(int num) {
        updateStoreNum = num;
        check_list_no = mInventories.get(rowNowIndex).get_cHECK_LIST_NO();
        oid = mInventories.get(rowNowIndex).get_oID();
        String url = Url.PATH + "/UpdateInventorys?last_updated_by=" + mUser.get_userID() + "&check_list_no=" + check_list_no + "&oid=" + oid + "&qty=" + num;
        GoodPresenterImpl upStoreImpl = new GoodPresenterImpl(this);
        showProgress();
        upStoreImpl.loadData(url, GoodPresenterImpl.UPDATE_INVENTORYS);

    }


    private class ProductInventAdapter extends TableDataAdapter<InventoryBeen> {


        public ProductInventAdapter(Context context, List<InventoryBeen> data) {
            super(context, data);
        }

        @Override
        public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
            final InventoryBeen been = getRowData(rowIndex);
            rowNowIndex = rowIndex;
            View renderedView = null;
            switch (columnIndex) {
                case 0:
                    renderedView = renderString(been.getPallet_id());
                    break;
                case 1:
                    renderedView = renderString(been.getProduct_id());
                    break;
                case 2:
                    renderedView = renderString(been.getProduct_name());
                    break;
                case 3:
                    renderedView = renderString(String.valueOf(been.getStoreNum()));
                    break;
                case 4:
                    break;
            }
            return renderedView;
        }
    }

    private void tableviewFresh() {
        adapter.notifyDataSetChanged();
    }

    private class ProductDatalistener implements TableDataClickListener<InventoryBeen> {

        @Override
        public void onDataClicked(int rowIndex, InventoryBeen clickedData) {
            Log.d(TAG, "xyz  onDataClicked: 这是没有执行吗??????");
            rowNowIndex = rowIndex;
            InventoryFragment newFragment = new InventoryFragment();
            newFragment.show(getFragmentManager(), "对话框");

        }
    }

    private View renderString(final String value) {
        final TextView textView = new TextView(this);
        textView.setText(value);
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(14);
        return textView;
    }
}
