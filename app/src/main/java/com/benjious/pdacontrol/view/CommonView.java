package com.benjious.pdacontrol.view;

import com.benjious.pdacontrol.been.Binsta;

import java.util.List;

/**
 * Created by Benjious on 2017/4/18.
 */

public interface CommonView {
    void showProgress();
    void hideProgress();
    void addData(List<Binsta> list);
    void showLoadFail();

}
