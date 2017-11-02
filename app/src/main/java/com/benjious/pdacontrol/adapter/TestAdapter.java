package com.benjious.pdacontrol.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.benjious.pdacontrol.been.Binsta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjious on 2017/11/2.
 */

public class TestAdapter extends ArrayAdapter<Binsta> {



    public TestAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }


    public TestAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Binsta> objects) {
        super(context, resource, objects);

    }


}
