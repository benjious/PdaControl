package com.benjious.pdacontrol.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.been.Binsta;
import com.benjious.pdacontrol.view.CommonView;

import java.util.List;

/**
 * Created by Benjious on 2017/10/12.
 */

public class GoodReadyActivity extends AppCompatActivity implements CommonView {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.goodready);
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
