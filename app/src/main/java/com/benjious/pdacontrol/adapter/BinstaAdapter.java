package com.benjious.pdacontrol.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.been.Binsta;

import java.util.List;

/**
 * Created by Benjious on 2017/11/2.
 */

public class BinstaAdapter extends BaseAdapter {
    public static final String TAG = "BinstaAdapter";
    private List<Binsta> mBinstas;
    private Context mContext;


    public BinstaAdapter(List<Binsta> binstas, Context context) {
        mBinstas = binstas;
        mContext = context;
    }

    public List<Binsta> getBinstas() {
        return mBinstas;
    }

    @Override
    public int getCount() {
        return mBinstas.size();
    }

    @Override
    public Object getItem(int position) {
        return mBinstas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.pallet_empty_binsta, null);
        if (convertView != null) {
            TextView textView = (TextView) convertView.findViewById(R.id.pallet_empty_binsta);
            textView.setText(mBinstas.get(position).get_bIN_NO());
        }
        return convertView;
    }

    public void setBinstas(List<Binsta> binstas) {
        mBinstas = binstas;
    }
}
