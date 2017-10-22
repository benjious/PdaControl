package com.benjious.pdacontrol.been;

import java.util.Date;

/**
 * Created by Benjious on 2017/10/18.
 */

public class Product {
    private String _kIND_ID;


    private String _kIND_NAME;


    private String _pARENT_KIND;


    private String _dESCRIBE;


    private Date _cREATION_DATE;


    private int _cREATED_BY;


    private Date _lAST_UPDATE_DATE;


    private int _lAST_UPDATED_BY;


    private int _sERVICE_FLAG;

    public String get_kIND_ID() {
        return _kIND_ID;
    }

    public void set_kIND_ID(String _kIND_ID) {
        this._kIND_ID = _kIND_ID;
    }

    public String get_kIND_NAME() {
        return _kIND_NAME;
    }

    public void set_kIND_NAME(String _kIND_NAME) {
        this._kIND_NAME = _kIND_NAME;
    }

    public String get_pARENT_KIND() {
        return _pARENT_KIND;
    }

    public void set_pARENT_KIND(String _pARENT_KIND) {
        this._pARENT_KIND = _pARENT_KIND;
    }

    public String get_dESCRIBE() {
        return _dESCRIBE;
    }

    public void set_dESCRIBE(String _dESCRIBE) {
        this._dESCRIBE = _dESCRIBE;
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

    public int get_sERVICE_FLAG() {
        return _sERVICE_FLAG;
    }

    public void set_sERVICE_FLAG(int _sERVICE_FLAG) {
        this._sERVICE_FLAG = _sERVICE_FLAG;
    }
}
