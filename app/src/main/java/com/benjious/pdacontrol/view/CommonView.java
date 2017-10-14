package com.benjious.pdacontrol.view;

import com.benjious.pdacontrol.been.Binsta;

import java.util.List;

import static android.R.attr.type;

/**
 * Created by Benjious on 2017/4/18.
 */

public interface CommonView {
    //显示进度条
    void showProgress();
    //隐藏进度条
    void hideProgress();
    //加载数据
    void addData(String response,int type);
    //请求到数据，但是出现异常
    void loadExecption(Exception e);
    //请求成功，但数据有问题
    void showLoadFail(int failNum);


}
