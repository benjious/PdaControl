package com.benjious.pdacontrol.been;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by Benjious on 2017/10/16.
 */

public class Stacking implements Serializable {
    private static final long serialVersionUID =484965361683265145L;

    private String _sTACK_ID;


    private String _wH_NO;


    private String _pALLET_ID;


    private String _p_CODE;


    private int _kIND;


    private String _bIN_NO;



    private Boolean _fULL_FLAG;


    private int _sTATUS;


    private Date _cREATION_DATE;


    private int  _cREATED_BY;


    private Date _lAST_UPDATE_DATE;


    private int  _lAST_UPDATED_BY;


    public String get_sTACK_ID() {
        return _sTACK_ID;
    }

    public void set_sTACK_ID(String _sTACK_ID) {
        this._sTACK_ID = _sTACK_ID;
    }

    public String get_wH_NO() {
        return _wH_NO;
    }

    public void set_wH_NO(String _wH_NO) {
        this._wH_NO = _wH_NO;
    }

    public String get_pALLET_ID() {
        return _pALLET_ID;
    }

    public void set_pALLET_ID(String _pALLET_ID) {
        this._pALLET_ID = _pALLET_ID;
    }

    public String get_p_CODE() {
        return _p_CODE;
    }

    public void set_p_CODE(String _p_CODE) {
        this._p_CODE = _p_CODE;
    }

    public int get_kIND() {
        return _kIND;
    }

    public void set_kIND(int _kIND) {
        this._kIND = _kIND;
    }

    public String get_bIN_NO() {
        return _bIN_NO;
    }

    public void set_bIN_NO(String _bIN_NO) {
        this._bIN_NO = _bIN_NO;
    }

    public Boolean get_fULL_FLAG() {
        return _fULL_FLAG;
    }

    public void set_fULL_FLAG(Boolean _fULL_FLAG) {
        this._fULL_FLAG = _fULL_FLAG;
    }

    public int get_sTATUS() {
        return _sTATUS;
    }

    public void set_sTATUS(int _sTATUS) {
        this._sTATUS = _sTATUS;
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

    @Override
    public String toString() {
        return "Stacking{" +
                "_sTACK_ID='" + _sTACK_ID + '\'' +
                ", _wH_NO='" + _wH_NO + '\'' +
                ", _pALLET_ID='" + _pALLET_ID + '\'' +
                ", _p_CODE='" + _p_CODE + '\'' +
                ", _kIND=" + _kIND +
                ", _bIN_NO='" + _bIN_NO + '\'' +
                ", _fULL_FLAG=" + _fULL_FLAG +
                ", _sTATUS=" + _sTATUS +
                ", _cREATION_DATE=" + _cREATION_DATE +
                ", _cREATED_BY=" + _cREATED_BY +
                ", _lAST_UPDATE_DATE=" + _lAST_UPDATE_DATE +
                ", _lAST_UPDATED_BY=" + _lAST_UPDATED_BY +
                '}';
    }
}
