package com.benjious.pdacontrol.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.been.Product;
import com.benjious.pdacontrol.been.ProductBeen;

import java.util.Date;
import java.util.List;

/**
 * Created by Benjious on 2017/10/19.
 */

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.ProductViewHolder> {

    private List<ProductBeen> mBeens;

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        ProductBeen been = mBeens.get(position);
        holder.bindDate(been);
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{

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
        }


        private void bindDate(ProductBeen productBeen) {
            productId.setText(productBeen.getProductId());
            productName.setText(productBeen.getProductName());
            productDate.setText(productBeen.getProductDate().toString());
            productNum.setText(productBeen.getProductNum());
        }
    }
}
