package com.benjious.pdacontrol.been;

import java.util.Date;
import java.util.List;

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

    //返回 List<Pallet>
    public static  String GetPallets(User user)
    {
        String  commString = "select PALLET_ID,BARCODE_TOP,BARCODE_BOTTOM,LOCK_FLAG from WMS_BA_PALLET_MAPPING where LOCK_FLAG = 1";
        //  string commString = "select PALLET_ID,BARCODE_TOP,BARCODE_BOTTOM,LOCK_FLAG from WMS_BA_PALLET_MAPPING where LOCK_FLAG = 0";
//        DataTable dt = SqlHelper.ExcuteTable(commString);
//        if (dt.Rows.Count == 0 || dt == null)
//            return null;
//        else
//        {
//            List<Pallet> pallets = new List<Pallet>();
//            foreach (DataRow dr in dt.Rows)
//            {
//                Pallet pallet = new Pallet();
//                pallet.PALLET_ID = dr["PALLET_ID"].ToString();
//                pallet.BARCODE_TOP = dr["BARCODE_TOP"].ToString();
//                pallet.BARCODE_BOTTOM = dr["BARCODE_BOTTOM"].ToString();
//                pallet.LOCK_FLAG = (bool)dr["LOCK_FLAG"];
//                pallets.Add(pallet);
//            }
//            return pallets;
//        }
        return commString;

    }


    //返回int
    public static String CheckPallet(String pallet_id, int status)
    //status 为0时,查找组盘入库表白WMS_STACKING 里状态为0或者为1且托盘编号为pallet_id的任务是否存在
    //status不为0时则查找托盘号为pallet_id的托盘是否存在托盘表WMS_BA_PALLET_MAPPING 中
    {
        String commandString;

        if (status == 0)
            commandString = "select * from WMS_STACKING where PALLET_ID = '" + pallet_id + "'and STATUS = 0 or PALLET_ID = '" + pallet_id + "'and STATUS = 1";
        else
            commandString = "select * from WMS_BA_PALLET_MAPPING where PALLET_ID = '" + pallet_id + "'";
            return commandString;
    }

    //返回boolean
//    public static String UpdatePallet(String pallet_id,Date last_update_date,int lase_updated_by)
//    {
//        String sqlcommand = "update WMS_BA_PALLET_MAPPING set LOCK_FLAG = 1,LAST_UPDATE_DATE = @last_update_date, LAST_UPDATED_BY = @last_updated_by where PALLET_ID = @pallet_id";
//        SqlParameter[] parameters = {
//                new SqlParameter("@last_update_date",SqlDbType.DateTime ,8),
//                new SqlParameter ("@last_updated_by",SqlDbType.Int ,4),
//                new SqlParameter ("@pallet_id",SqlDbType.NVarChar ,30)};
//        parameters[0].Value = last_update_date;
//        parameters[1].Value = lase_updated_by;
//        parameters[2].Value = pallet_id;
//
//        int n = SqlHelper.ExecuteNonQuery(sqlcommand, CommandType.Text, parameters);
//        if (n > 0)
//            return true;
//        else
//            return false;
//    }

}
