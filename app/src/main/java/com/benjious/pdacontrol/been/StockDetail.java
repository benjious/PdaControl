package com.benjious.pdacontrol.been;

import java.util.Date;


/**
 * Created by Benjious on 2017/10/17.
 */

public class StockDetail {
    public String get_product_name() {
        return _product_name;
    }

    public void set_product_name(String _product_name) {
        this._product_name = _product_name;
    }

    private String _product_name;

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

    public String get_iTEM_ID() {
        return _iTEM_ID;
    }

    public void set_iTEM_ID(String _iTEM_ID) {
        this._iTEM_ID = _iTEM_ID;
    }

    public String get_bARCODE_NO() {
        return _bARCODE_NO;
    }

    public void set_bARCODE_NO(String _bARCODE_NO) {
        this._bARCODE_NO = _bARCODE_NO;
    }

    public Date get_pROD_DATE() {
        return _pROD_DATE;
    }

    public void set_pROD_DATE(Date _pROD_DATE) {
        this._pROD_DATE = _pROD_DATE;
    }

    public Date get_eXPIRE_DATE() {
        return _eXPIRE_DATE;
    }

    public void set_eXPIRE_DATE(Date _eXPIRE_DATE) {
        this._eXPIRE_DATE = _eXPIRE_DATE;
    }

    public String get_bATCH_NO() {
        return _bATCH_NO;
    }

    public void set_bATCH_NO(String _bATCH_NO) {
        this._bATCH_NO = _bATCH_NO;
    }

    public String get_lIST_NO() {
        return _lIST_NO;
    }

    public void set_lIST_NO(String _lIST_NO) {
        this._lIST_NO = _lIST_NO;
    }



    public String get_oUT_LIST_NO() {
        return _oUT_LIST_NO;
    }

    public void set_oUT_LIST_NO(String _oUT_LIST_NO) {
        this._oUT_LIST_NO = _oUT_LIST_NO;
    }

    public double get_oUT_QTY() {
        return _oUT_QTY;
    }

    public void set_oUT_QTY(double _oUT_QTY) {
        this._oUT_QTY = _oUT_QTY;
    }

    public double get_sTOCK_QTY() {
        return _sTOCK_QTY;
    }

    public void set_sTOCK_QTY(double _sTOCK_QTY) {
        this._sTOCK_QTY = _sTOCK_QTY;
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

    public String get_gRADE() {
        return _gRADE;
    }

    public void set_gRADE(String _gRADE) {
        this._gRADE = _gRADE;
    }

    private int _oID;


    private int _sTOCK_OID;


    private String _iTEM_ID;


    private String _bARCODE_NO;


    private Date _pROD_DATE;


    private Date _eXPIRE_DATE;


    private String _bATCH_NO;

    public int get_qTY() {
        return _qTY;
    }

    public void set_qTY(int _qTY) {
        this._qTY = _qTY;
    }

    private String _lIST_NO;


    private int _qTY;


    private String _oUT_LIST_NO;


    private double _oUT_QTY;


    private double _sTOCK_QTY;


    private Date _cREATION_DATE;


    private int _cREATED_BY;


    private Date _lAST_UPDATE_DATE;


    private int _lAST_UPDATED_BY;


    private String _gRADE;

    @Override
    public String toString() {
        return "StockDetail{" +
                "_oID=" + _oID +
                ", _sTOCK_OID=" + _sTOCK_OID +
                ", _iTEM_ID='" + _iTEM_ID + '\'' +
                ", _bARCODE_NO='" + _bARCODE_NO + '\'' +
                ", _pROD_DATE=" + _pROD_DATE +
                ", _eXPIRE_DATE=" + _eXPIRE_DATE +
                ", _bATCH_NO='" + _bATCH_NO + '\'' +
                ", _lIST_NO='" + _lIST_NO + '\'' +
                ", _qTY=" + _qTY +
                ", _oUT_LIST_NO='" + _oUT_LIST_NO + '\'' +
                ", _oUT_QTY=" + _oUT_QTY +
                ", _sTOCK_QTY=" + _sTOCK_QTY +
                ", _cREATION_DATE=" + _cREATION_DATE +
                ", _cREATED_BY=" + _cREATED_BY +
                ", _lAST_UPDATE_DATE=" + _lAST_UPDATE_DATE +
                ", _lAST_UPDATED_BY=" + _lAST_UPDATED_BY +
                ", _gRADE='" + _gRADE + '\'' +
                '}';
    }
}
