package com.benjious.pdacontrol.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.been.Binsta;
import com.benjious.pdacontrol.view.CommonView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Benjious on 2017/10/12.
 */

public class GoodReadyActivity extends AppCompatActivity implements CommonView {
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goodready);
        ButterKnife.bind(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.good_style, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mStyleSpin.setAdapter(adapter);
        mProgressBar2.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void addData(List<Binsta> list) {

    }

    @Override
    public void showLoadFail() {

    }

}
