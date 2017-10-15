package com.benjious.pdacontrol.presenter;

import com.benjious.pdacontrol.interfazes.OnLoadGoodLisenter;
import com.benjious.pdacontrol.model.GoodModel;
import com.benjious.pdacontrol.model.GoodModelImpl;
import com.benjious.pdacontrol.view.CommonView;

/**
 * Created by Benjious on 2017/10/13.
 */

public class GoodPresenterImpl implements GoodPresenter ,OnLoadGoodLisenter{
    public static final int CHECK_PALLET_ID =1 ;
    public static final int CHECK_PORT =2 ;
    private CommonView mCommonView;
    private GoodModel mGoodModel;



    public GoodPresenterImpl(CommonView commonView) {
        mCommonView = commonView;
        mGoodModel =new GoodModelImpl();
    }


    @Override
    public void onSuccess(String respone, int type) {
        mCommonView.addData(respone,type);
    }

    @Override
    public void onFailure(String str, Exception e) {
        mCommonView.loadExecption(e);
    }

    @Override
    public void onFailure(int failnum) {
      mCommonView.showLoadFail(failnum);
    }

    @Override
    public void loadData(String url, int style) {
        if (style==CHECK_PALLET_ID){
            mGoodModel.loadData(url, this);
        } else if (style==CHECK_PORT) {

        }
    }
}
