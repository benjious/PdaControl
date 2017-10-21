package com.benjious.pdacontrol.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;


import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.interfazes.OnUpdateProductLisenter;


/**
 * Created by Benjious on 2017/10/20.
 */

public class ProductDialogFragment extends DialogFragment {
    // Use the Builder class for convenient dialog construction

    private OnUpdateProductLisenter updateProductLisenter;
    public static final String[] STRING = {"AA", "TTT", "EEE"};
    public static final String TAG = "xyz =";
    private Handler mHandler=new Handler(Looper.getMainLooper());;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        updateProductLisenter = (OnUpdateProductLisenter) getActivity();
        builder.setItems(R.array.product_choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    //删除该项
                    case 0:
                       mHandler.post(new Runnable() {
                           @Override
                           public void run() {
                              updateProductLisenter.deleteRowindex();
                           }
                       });
                        break;

                    //返回修改
                    case 1:
                        updateProductLisenter.goBackProductAdd();
                        break;

                    //提交
                    case 2:
                        updateProductLisenter.goToFullCheck();
                        break;

                }
            }
        })

                .setTitle("对以下操作进行选择")
        ;
        // Create the AlertDialog object and return it
//        builder.setView(R.layout.product_choice_dialog).create();
        return builder.create();

    }
}