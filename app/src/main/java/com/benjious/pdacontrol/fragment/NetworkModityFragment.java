package com.benjious.pdacontrol.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.interfazes.OnNetworkModityListener;

/**
 * Created by Benjious on 2017/11/2.
 */

public class NetworkModityFragment extends DialogFragment {
    private OnNetworkModityListener mListener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mListener = (OnNetworkModityListener) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("是否恢复默认网络设置?")
                .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.recover();
                    }
                })
                .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
