package com.benjious.pdacontrol.interfazes;

import com.benjious.pdacontrol.been.Binsta;

import java.util.List;

/**
 * Created by Benjious on 2017/10/12.
 */

public interface OnLoadGoodLisenter {
    void onSuccess(List<Binsta> list);

    void onFailure(String str, Exception e);

}
