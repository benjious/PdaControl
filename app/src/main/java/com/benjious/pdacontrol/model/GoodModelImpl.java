package com.benjious.pdacontrol.model;

import com.benjious.pdacontrol.interfazes.OnLoadGoodLisenter;
import com.benjious.pdacontrol.presenter.GoodPresenterImpl;
import com.benjious.pdacontrol.util.OkHttpUtils;

import static com.benjious.pdacontrol.util.OkHttpUtils.SERVER_OFFLINE;


/**
 * Created by Benjious on 2017/10/13.
 */

public class GoodModelImpl implements GoodModel {
    public static final String TAG = "GoodModelImpl xyz =";

    @Override
    public void loadData(String url, final OnLoadGoodLisenter lisenter, final int style) {
        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                if (response == null) {
                    lisenter.onFailure(SERVER_OFFLINE);
                } else {
                    if (style == GoodPresenterImpl.CHECK_PALLET_ID) {

                        lisenter.onSuccess(response, GoodPresenterImpl.CHECK_PALLET_ID);

                    } else if (style == GoodPresenterImpl.CHECK_PORT) {

                        lisenter.onSuccess(response, GoodPresenterImpl.CHECK_PORT);

                    } else if (style == GoodPresenterImpl.GET_NEWSTACK_ID) {

                        lisenter.onSuccess(response, GoodPresenterImpl.GET_NEWSTACK_ID);

                    } else if (style == GoodPresenterImpl.GET_STOCK_DETAIL) {
                        lisenter.onSuccess(response, GoodPresenterImpl.GET_STOCK_DETAIL);

                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                lisenter.onFailure("请求出现错误", e);
            }
        };
        OkHttpUtils.get(url, resultCallback);
    }
}
