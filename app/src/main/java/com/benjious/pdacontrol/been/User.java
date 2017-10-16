package com.benjious.pdacontrol.been;

import java.io.Serializable;

import static android.R.attr.value;

/**
 * Created by Benjious on 2017/10/16.
 */

public class User implements Serializable{
    private static final long serialVersionUID =4849653612323265145L;

    int _userID = -1;
    int _roleID;
    String _userName;
    String _userCnName;
    String _roleName;
    String _roleDescription;

    public int get_userID() {
        return _userID;
    }

    public void set_userID(int _userID) {
        this._userID = _userID;
    }

    public int get_roleID() {
        return _roleID;
    }

    public void set_roleID(int _roleID) {
        this._roleID = _roleID;
    }

    public String get_userName() {
        return _userName;
    }

    public void set_userName(String _userName) {
        this._userName = _userName;
    }

    public String get_userCnName() {
        return _userCnName;
    }

    public void set_userCnName(String _userCnName) {
        this._userCnName = _userCnName;
    }

    public String get_roleName() {
        return _roleName;
    }

    public void set_roleName(String _roleName) {
        this._roleName = _roleName;
    }

    public String get_roleDescription() {
        return _roleDescription;
    }

    public void set_roleDescription(String _roleDescription) {
        this._roleDescription = _roleDescription;
    }


}
