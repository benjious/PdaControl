package com.benjious.pdacontrol.model;

import android.util.Log;

import com.benjious.pdacontrol.been.UsersALL;
import com.benjious.pdacontrol.interfazes.OnLoadGoodLisenter;
import com.benjious.pdacontrol.util.OkHttpUtils;
import com.benjious.pdacontrol.view.CommonView;
import com.google.gson.Gson;

import static com.benjious.pdacontrol.webService.WLoginService.NO_REAL_DATA;
import static com.benjious.pdacontrol.webService.WLoginService.SERVER_OFFLINE;

/**
 * Created by Benjious on 2017/10/13.
 */

public class GoodModelImpl implements GoodModel {
    public static final String TAG = "GoodModelImpl xyz =";

    @Override
    public void loadData(String url,final OnLoadGoodLisenter lisenter) {
        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                if (response == null) {
                    return;
                }
                Gson gson = new Gson();
                UsersALL usersALL = gson.fromJson(response, UsersALL.class);
                if (usersALL == null) {
                    lisenter.onFailure(SERVER_OFFLINE);
                } else {
                    if (usersALL.getUsers().size() != 0) {
                        Log.d(TAG, "xyz  onSuccess: ----" + usersALL.getUsers().get(0).getUsername());

                    } else {
                        lisenter.onFailure(NO_REAL_DATA);
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        OkHttpUtils.get(url, resultCallback);
    }
}
