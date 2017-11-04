package com.benjious.pdacontrol.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.benjious.pdacontrol.util.OkHttpUtils;

/**
 * Created by Benjious on 2017/11/1.
 */

public class ProcessDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog progressdia = new ProgressDialog(getActivity()){
            @Override
            public void onBackPressed() {
                OkHttpUtils.stopRequest();

                super.onBackPressed();
            }
        };
        setCancelable(true);
        return progressdia;
    }
}
