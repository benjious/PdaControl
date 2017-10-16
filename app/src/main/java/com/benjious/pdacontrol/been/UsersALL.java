package com.benjious.pdacontrol.been;

import java.util.List;

/**
 * Created by Benjious on 2017/10/13.
 */

public class UsersALL {
    private List<TestUser> mTestUsers;

    private int number;
    private String data;
    private boolean yesNo;

    public boolean isYesNo() {
        return yesNo;
    }

    public void setYesNo(boolean yesNo) {
        this.yesNo = yesNo;
    }

    public List<TestUser> getTestUsers() {
        return mTestUsers;
    }

    public void setTestUsers(List<TestUser> testUsers) {
        mTestUsers = testUsers;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UsersALL{" +
                "mTestUsers=" + mTestUsers +
                '}';
    }
}
