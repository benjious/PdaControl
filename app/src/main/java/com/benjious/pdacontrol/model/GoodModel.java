package com.benjious.pdacontrol.model;

import com.benjious.pdacontrol.interfazes.OnLoadGoodLisenter;

/**
 * Created by Benjious on 2017/10/12.
 */

public interface GoodModel {
    void  loadData(String url, OnLoadGoodLisenter dataListener,int type);
}
