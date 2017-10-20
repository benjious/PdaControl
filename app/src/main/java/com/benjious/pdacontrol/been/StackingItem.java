package com.benjious.pdacontrol.been;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Benjious on 2017/10/17.
 */

public class StackingItem implements Serializable {
    private static final long serialVersionUID =484864573616832645L;

    private String _iTEM_ID;


    private String _sTACK_ID;


    private String _lIST_NO;


    private int _qTY;


    private Date _pROD_DATE;


    private Date _cREATION_DATE;


    private int _cREATED_BY;


    private Date _lAST_UPDATE_DATE;


    private int _lAST_UPDATED_BY;

    public String get_product_name() {
        return _product_name;
    }

    public void set_product_name(String _product_name) {
        this._product_name = _product_name;
    }

    private String _product_name;

    public String get_iTEM_ID() {
        return _iTEM_ID;
    }

    public void set_iTEM_ID(String _iTEM_ID) {
        this._iTEM_ID = _iTEM_ID;
    }

    public String get_sTACK_ID() {
        return _sTACK_ID;
    }

    public void set_sTACK_ID(String _sTACK_ID) {
        this._sTACK_ID = _sTACK_ID;
    }

    public String get_lIST_NO() {
        return _lIST_NO;
    }

    public void set_lIST_NO(String _lIST_NO) {
        this._lIST_NO = _lIST_NO;
    }


    public int get_qTY() {
        return _qTY;
    }

    public void set_qTY(int _qTY) {
        this._qTY = _qTY;
    }

    public Date get_pROD_DATE() {
        return _pROD_DATE;
    }

    public void set_pROD_DATE(Date _pROD_DATE) {
        this._pROD_DATE = _pROD_DATE;
    }

    public Date get_cREATION_DATE() {
        return _cREATION_DATE;
    }

    public void set_cREATION_DATE(Date _cREATION_DATE) {
        this._cREATION_DATE = _cREATION_DATE;
    }

    public int get_cREATED_BY() {
        return _cREATED_BY;
    }

    public void set_cREATED_BY(int _cREATED_BY) {
        this._cREATED_BY = _cREATED_BY;
    }

    public Date get_lAST_UPDATE_DATE() {
        return _lAST_UPDATE_DATE;
    }

    public void set_lAST_UPDATE_DATE(Date _lAST_UPDATE_DATE) {
        this._lAST_UPDATE_DATE = _lAST_UPDATE_DATE;
    }

    public int get_lAST_UPDATED_BY() {
        return _lAST_UPDATED_BY;
    }

    public void set_lAST_UPDATED_BY(int _lAST_UPDATED_BY) {
        this._lAST_UPDATED_BY = _lAST_UPDATED_BY;
    }


}
