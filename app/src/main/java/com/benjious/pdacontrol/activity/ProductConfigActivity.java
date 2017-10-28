package com.benjious.pdacontrol.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.benjious.pdacontrol.interfazes.OnUpdateInventoryStore;
import com.benjious.pdacontrol.view.CommonView;

/**
 * Created by Benjious on 2017/10/28.
 */

public class ProductConfigActivity extends BaseActivity implements CommonView, OnUpdateInventoryStore {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void upInventoryStore(int num) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void addData(String response, int type) {

    }

    @Override
    public void loadExecption(Exception e) {

    }

    @Override
    public void showLoadFail(int failNum) {

    }
}
