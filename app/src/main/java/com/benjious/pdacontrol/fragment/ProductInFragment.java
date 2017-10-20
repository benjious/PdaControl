package com.benjious.pdacontrol.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.been.ProductBeen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;


/**
 * Created by Benjious on 2017/10/12.
 */

public class ProductInFragment extends Fragment {
    private RecyclerView mProductRecyclerView;
    private List<ProductBeen> mBeens =new ArrayList<>();

    public static final String TAG="ProductInFragment xyz =";
    public static final String[] HEAD_DATA ={ "This", "is", "a", "test" };

    private static final String[][] DATA_TO_SHOW = { { "This", "is", "a", "test" },
            { "and", "a", "second", "test" } };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.product_in, container, false);



        addData();
        mProductRecyclerView = (RecyclerView) view.findViewById(R.id.product_recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mProductRecyclerView.setLayoutManager(linearLayoutManager);
        mProductRecyclerView.setAdapter(new GoodAdapter(mBeens));
//        mEmptyViewLinearLayout = (LinearLayout) view.findViewById(R.id.empty_view);



        Log.d(TAG, "xyz  onCreate: "+DATA_TO_SHOW[1][2]);

        return view;
    }



    private void addData() {
        for (int i = 0; i < 10; i++) {
            ProductBeen been = new ProductBeen(1, "2", "3", new Date());
            mBeens.add(been);
        }
    }

    public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.ProductViewHolder> {

        private List<ProductBeen> mBeens;

        public GoodAdapter(List<ProductBeen> beens) {
            mBeens = beens;
        }

        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(
                            R.layout.list_item_prodct, parent, false);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductViewHolder holder, int position) {
            ProductBeen been = mBeens.get(position);
            holder.bindDate(been);
        }

        @Override
        public int getItemCount() {
            return mBeens.size();
        }


        public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView productNum;
            private TextView productName;
            private TextView productId;
            private TextView productDate;
            private ProductBeen mBeen;

            public ProductViewHolder(View itemView) {
                super(itemView);
                productNum = (TextView) itemView.findViewById(R.id.good_number_text);
                productId = (TextView) itemView.findViewById(R.id.good_id_text);
                productName = (TextView) itemView.findViewById(R.id.good_name_text);
                productDate = (TextView) itemView.findViewById(R.id.good_date_text);
                itemView.setOnClickListener(this);
            }


            private void bindDate(ProductBeen productBeen) {
                productId.setText(productBeen.getProductId());
                productName.setText(productBeen.getProductName());
                productDate.setText(productBeen.getProductDate().toString());
                productNum.setText(String.valueOf(productBeen.getProductNum()));
            }

            @Override
            public void onClick(View v) {

            }
        }
    }



}
