package com.benjious.pdacontrol.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.been.ProductBeen;
import com.benjious.pdacontrol.been.Stacking;
import com.benjious.pdacontrol.been.StackingItem;
import com.benjious.pdacontrol.been.StockDetail;
import com.benjious.pdacontrol.been.User;
import com.benjious.pdacontrol.fragment.ProductDialogFragment;
import com.benjious.pdacontrol.fragment.Product_FullFragment;
import com.benjious.pdacontrol.interfazes.OnUpdateProductLisenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;


/**
 * Created by Benjious on 2017/10/19.
 */

public class ProductsInActivity extends BaseActivity implements OnUpdateProductLisenter {
    public static final String TAG = "ProductsInActivity xyz =";
    private User user;
    private Stacking mStacking;
    private List<StackingItem> mStackingItems;
    private int kind;
    private List<StockDetail> mStockDetails = new ArrayList<>();

    public static final String[] HEAD_DATA = {"编号", "名称", "数量", "日期","满托"};
    private FragmentTransaction mTransaction;


    private TableView<ProductBeen> tableView;

    private List<ProductBeen> mBeens = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);

        Intent intent = getIntent();
        intent.getIntExtra(ProductReadyActivity.KIND_SEND, 0);
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

    private void setProductBeen() {
//        if (kind==1) {
//            for (StackingItem item : mStackingItems) {
//                ProductBeen been = new ProductBeen(item.get_qTY(), item.get_product_name(), item.get_iTEM_ID(), item.get_pROD_DATE());
//                mBeens.add(been);
//
//            }
//        }else {
//            for (StockDetail  item : mStockDetails) {
//                ProductBeen been = new ProductBeen(item.get_qTY(), item.get_product_name(), item.get_iTEM_ID(), item.get_pROD_DATE());
//                mBeens.add(been);
//
//            }
//        }

        for (int i = 0; i < 10; i++) {
            ProductBeen been = new ProductBeen(12, "SJ_JDI-34", "HGOEJDD", new Date());
            mBeens.add(been);
        }
    }

    private void setTableView() {
        tableView = (TableView<ProductBeen>)findViewById(R.id.tableView11);
        tableView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        ProductBeenAdapter adapter = new ProductBeenAdapter(this, mBeens);
        tableView.addDataClickListener(new ProductDatalistener());
        tableView.setDataAdapter(adapter);

        TableColumnWeightModel columnWeightModel = new TableColumnWeightModel(5);
        columnWeightModel.setColumnWeight(1,1);
        columnWeightModel.setColumnWeight(2,1);
        columnWeightModel.setColumnWeight(3,1);
        columnWeightModel.setColumnWeight(4,1);
        columnWeightModel.setColumnWeight(5,1);
        tableView.setColumnModel(columnWeightModel);
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, HEAD_DATA));

    }

    @Override
    public void goToFullCheck() {
        Product_FullFragment checkFragment = new Product_FullFragment();
        checkFragment.show(getFragmentManager(),"是否标记满托标志");

    }

    @Override
    public void updateProduct(Boolean checkNot) {
        super.showToast(String.valueOf(checkNot));
    }


    private class ProductDatalistener implements TableDataClickListener<ProductBeen> {
        @Override
        public void onDataClicked(int rowIndex, ProductBeen clickedData) {
            ProductDialogFragment newFragment = new ProductDialogFragment();
            newFragment.show(getFragmentManager(), "对话框");
//            String Tag ="对话框";
//            FragmentManager fragmentManager = getSupportFragmentManager() ;
//            mTransaction= fragmentManager.beginTransaction();
//            mTransaction.add(newFragment,Tag);
//            mTransaction.commit();
        }
    }


    private class ProductBeenAdapter extends TableDataAdapter<ProductBeen> {


        public ProductBeenAdapter(Context context, List<ProductBeen> data) {
            super(context, data);
        }

        @Override
        public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
            final ProductBeen been = getRowData(rowIndex);
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
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra(LoginActivity.USERNAME, "admin");
                startActivity(intent);
                break;
        }
        return true;

    }

}
