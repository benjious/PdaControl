package com.benjious.pdacontrol.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.benjious.pdacontrol.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Benjious on 2017/10/16.
 */

public class ProductsAddActivity extends BaseActivity {
    @Bind(R.id.goodId)
    TextView mGoodId;
    @Bind(R.id.textBoxPro_No)
    EditText mTextBoxProNo;
    @Bind(R.id.InstoreNumText)
    TextView mInstoreNumText;
    @Bind(R.id.textBoxQty)
    EditText mTextBoxQty;
    @Bind(R.id.DateNow)
    TextView mDateNow;
    @Bind(R.id.button4)
    Button mButton4;
    @Bind(R.id.AddBtn)
    Button mAddBtn;
    @Bind(R.id.NextBtn)
    Button mNextBtn;
    @Bind(R.id.BackBtn)
    Button mBackBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_add);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.AddBtn, R.id.NextBtn, R.id.BackBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.AddBtn:
                break;
            case R.id.NextBtn:
                break;
            case R.id.BackBtn:
                break;
        }
    }
}
