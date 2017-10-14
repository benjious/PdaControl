package com.benjious.pdacontrol.presenter;

import com.benjious.pdacontrol.been.Binsta;
import com.benjious.pdacontrol.interfazes.OnLoadGoodLisenter;
import com.benjious.pdacontrol.model.GoodModel;
import com.benjious.pdacontrol.view.CommonView;

import java.util.List;

/**
 * Created by Benjious on 2017/10/13.
 */

public class GoodPresenterImpl implements GoodPresenter ,OnLoadGoodLisenter{
    private CommonView mCommonView;
    private GoodModel mGoodModel;

    @Override
    public void loadData(int page) {
    }



    @Override
    public void onSuccess(String respone) {

    }

    @Override
    public void onFailure(String str, Exception e) {

    }

    @Override
    public void onFailure() {

    }
}
