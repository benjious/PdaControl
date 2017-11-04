package com.benjious.pdacontrol.been;

import java.util.Date;

import static android.R.attr.value;

/**
 * Created by Benjious on 2017/11/3.
 */

public class SortPortInfoBeen {
    private int _oID;
    private String _barcode;


    private String _dev_No;


    private int _kind;


    private int _status;


    private int _created_By;

    public int get_oID() {
        return _oID;
    }

    public void set_oID(int _oID) {
        this._oID = _oID;
    }

    public String get_barcode() {
        return _barcode;
    }

    public void set_barcode(String _barcode) {
        this._barcode = _barcode;
    }

    public String get_dev_No() {
        return _dev_No;
    }

    public void set_dev_No(String _dev_No) {
        this._dev_No = _dev_No;
    }

    public int get_kind() {
        return _kind;
    }

    public void set_kind(int _kind) {
        this._kind = _kind;
    }

    public int get_status() {
        return _status;
    }

    public void set_status(int _status) {
        this._status = _status;
    }

    public int get_created_By() {
        return _created_By;
    }

    public void set_created_By(int _created_By) {
        this._created_By = _created_By;
    }

    public Date get_creation_Date() {
        return _creation_Date;
    }

    public void set_creation_Date(Date _creation_Date) {
        this._creation_Date = _creation_Date;
    }

    private Date _creation_Date;



}
