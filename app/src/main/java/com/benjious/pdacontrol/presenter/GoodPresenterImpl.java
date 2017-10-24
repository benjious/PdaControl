package com.benjious.pdacontrol.presenter;

import com.benjious.pdacontrol.interfazes.OnLoadGoodLisenter;
import com.benjious.pdacontrol.model.GoodModel;
import com.benjious.pdacontrol.model.GoodModelImpl;
import com.benjious.pdacontrol.view.CommonView;

/**
 * Created by Benjious on 2017/10/13.
 */

public class GoodPresenterImpl implements GoodPresenter, OnLoadGoodLisenter {
    //GoodReady
    //检查是否有这个托盘
    public static final int CHECK_PALLET_ID = 1;
    //检查是否有这个端口
    public static final int CHECK_PORT = 2;
    //获取最新的 stack
    public static final int GET_NEWSTACK_ID = 3;


    //ProductAdd
    //
    public static final int GET_STOCK_DETAIL = 4;
    //获取物料的名字
    public static final int CHECK_PRO_NO = 5;


    //ProductIn
    //插入原材料
    public static final int INSERT_STACKING = 6;

    public static final int INSERT_STACKINGITEM = 7;
    public static final int UPDATE_PALLET =8 ;
    public static final int UPDATE_STOCK =9 ;
    public static final int INSERT_STOCK_DETAIL =10 ;

    private CommonView mCommonView;
    private GoodModel mGoodModel;

    public GoodPresenterImpl(CommonView commonView) {
        mCommonView = commonView;
        mGoodModel = new GoodModelImpl();
    }


    @Override
    public void onSuccess(String respone, int type) {
        mCommonView.addData(respone, type);
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
        mGoodModel.loadData(url, this, style);

    }
}
