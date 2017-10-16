package com.benjious.pdacontrol.been;

import java.io.Serializable;

import static android.R.attr.value;

/**
 * Created by Benjious on 2017/10/16.
 */

public class Picking implements Serializable{
    private static final long serialVersionUID =4849653612323265145L;

    private int _oID;

    public int get_oID() {
        return _oID;
    }

    public void set_oID(int _oID) {
        this._oID = _oID;
    }

    public int get_sTOCK_OID() {
        return _sTOCK_OID;
    }

    public void set_sTOCK_OID(int _sTOCK_OID) {
        this._sTOCK_OID = _sTOCK_OID;
    }

    public String get_pRODUCT_ID() {
        return _pRODUCT_ID;
    }

    public void set_pRODUCT_ID(String _pRODUCT_ID) {
        this._pRODUCT_ID = _pRODUCT_ID;
    }

    public String get_pRODUCT_NAME() {
        return _pRODUCT_NAME;
    }

    public void set_pRODUCT_NAME(String _pRODUCT_NAME) {
        this._pRODUCT_NAME = _pRODUCT_NAME;
    }

    public double get_oUT_QTY() {
        return _oUT_QTY;
    }

    public void set_oUT_QTY(double _oUT_QTY) {
        this._oUT_QTY = _oUT_QTY;
    }

    public String get_uOM() {
        return _uOM;
    }

    public void set_uOM(String _uOM) {
        this._uOM = _uOM;
    }

    private int _sTOCK_OID;


    private String _pRODUCT_ID;


    private String _pRODUCT_NAME;


    private double _oUT_QTY;


    private String _uOM;


}
