package com.benjious.pdacontrol.interfazes;

/**
 * Created by Benjious on 2017/10/12.
 */

public interface OnLoadGoodLisenter {
    //请求成功有数据的情况下
    void onSuccess(String respone,int type);
    //请求出现异常的时候
    void onFailure(String str, Exception e);
    //请求成功，但是数据有问题或是 服务器不能工作 等其他原因
    void onFailure(int failnum);
}
