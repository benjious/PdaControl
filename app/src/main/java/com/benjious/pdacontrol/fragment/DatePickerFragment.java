package com.benjious.pdacontrol.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Benjious on 2017/10/18.
 */

public class DatePickerFragment extends DialogFragment {

    private Date date;
    private int year;
    private int month;
    private int day;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        date = (Date) bundle.getSerializable("DATE");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (date !=null) {
            year = date.getYear();
            month = date.getMonth();
            day = date.getDay();
        }else {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }
        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year,month,day);
    }

}
