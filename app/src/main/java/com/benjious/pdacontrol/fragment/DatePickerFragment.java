package com.benjious.pdacontrol.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Benjious on 2017/10/18.
 */

public class DatePickerFragment extends DialogFragment {

    private int year;
    private int month;
    private int day;
    public static final String TAG = "DatePickerFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
    }

}
