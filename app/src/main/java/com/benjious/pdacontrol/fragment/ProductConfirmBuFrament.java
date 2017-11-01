package com.benjious.pdacontrol.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.benjious.pdacontrol.R;
import com.benjious.pdacontrol.interfazes.OnUpdateProductConfirm;

/**
 * Created by Benjious on 2017/10/30.
 */

public class ProductConfirmBuFrament extends DialogFragment {
    private OnUpdateProductConfirm mOnUpdateProductConfirm;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mOnUpdateProductConfirm = (OnUpdateProductConfirm) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.product_confirm_bu, null);
        builder.setView(dialogView);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText editText = (EditText) dialogView.findViewById(R.id.ready_store_num);
                CheckBox checkBox = (CheckBox) dialogView.findViewById(R.id.checkBox);
                if ((!editText.getText().toString().equals("")) && (Integer.parseInt(editText.getText().toString()) > 0)) {
                    mOnUpdateProductConfirm.updateProductConfirm(Integer.parseInt(String.valueOf(editText.getText())),checkBox.isChecked());
                } else {
                    Toast.makeText(getActivity(), "请输入正确的货存数量!", Toast.LENGTH_SHORT).show();
                }
            }
        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });


        return builder.create();

    }
}

