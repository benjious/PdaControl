package com.benjious.pdacontrol.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.interfazes.OnUpdateProductLisenter;


/**
 * Created by Benjious on 2017/10/20.
 */

public class Product_FullFragment extends DialogFragment {
    public static final String[] CHOICE = {"满托标志"};
    private OnUpdateProductLisenter lisenter;
    private boolean checkNot;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        lisenter = (OnUpdateProductLisenter) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder .setMultiChoiceItems(CHOICE, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        switch (which) {
                            case 0:
                                checkNot = isChecked;
                                break;
                        }

                    }
                })


                .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lisenter.updateProduct(checkNot);
                    }
                })


                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })


                .setTitle("对以下操作进行选择");

        return builder.create();
    }

}
