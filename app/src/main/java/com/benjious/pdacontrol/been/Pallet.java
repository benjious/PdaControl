package com.benjious.pdacontrol.been;


import java.util.Date;

/**
 * Created by Benjious on 2017/10/13.
 */

public class Pallet {
    private String _pALLET_ID;


    private String _bARCODE_TOP;
    private String _bARCODE_BOTTOM;
    private Date _cREATION_DATE;
    private int _cREATED_BY;
    private Date _lAST_UPDATE_DATE;
    private int _lAST_UPDATED_BY;
    private String _pALLET_TYPE;
    private int _sTACK_PLATE_QTY;
    private char _uSE_SIGN;
    private Boolean _lOCK_FLAG;

    public String get_pALLET_ID() {
        return _pALLET_ID;
    }

    public void set_pALLET_ID(String _pALLET_ID) {
        this._pALLET_ID = _pALLET_ID;
    }

    public String get_bARCODE_TOP() {
        return _bARCODE_TOP;
    }

    public void set_bARCODE_TOP(String _bARCODE_TOP) {
        this._bARCODE_TOP = _bARCODE_TOP;
    }

    public String get_bARCODE_BOTTOM() {
        return _bARCODE_BOTTOM;
    }

    public void set_bARCODE_BOTTOM(String _bARCODE_BOTTOM) {
        this._bARCODE_BOTTOM = _bARCODE_BOTTOM;
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

    public String get_pALLET_TYPE() {
        return _pALLET_TYPE;
    }

    public void set_pALLET_TYPE(String _pALLET_TYPE) {
        this._pALLET_TYPE = _pALLET_TYPE;
    }

    public int get_sTACK_PLATE_QTY() {
        return _sTACK_PLATE_QTY;
    }

    public void set_sTACK_PLATE_QTY(int _sTACK_PLATE_QTY) {
        this._sTACK_PLATE_QTY = _sTACK_PLATE_QTY;
    }

    public char get_uSE_SIGN() {
        return _uSE_SIGN;
    }

    public void set_uSE_SIGN(char _uSE_SIGN) {
        this._uSE_SIGN = _uSE_SIGN;
    }

    public Boolean get_lOCK_FLAG() {
        return _lOCK_FLAG;
    }

    public void set_lOCK_FLAG(Boolean _lOCK_FLAG) {
        this._lOCK_FLAG = _lOCK_FLAG;
    }



}
