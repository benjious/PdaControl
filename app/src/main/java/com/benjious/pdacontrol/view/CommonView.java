package com.benjious.pdacontrol.view;

import com.benjious.pdacontrol.been.Binsta;

import java.util.List;

/**
 * Created by Benjious on 2017/4/18.
 */

public interface CommonView {
    //显示进度条
    void showProgress();
    //隐藏进度条
    void hideProgress();
    //加载数据
    void addData(List<Binsta> list);
    //加载失败
    void showLoadFail();

}
