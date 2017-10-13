package com.benjious.pdacontrol.been;

import java.util.ArrayList;

public class UserAll {
    private ArrayList<User> Users;
    private int total;

    public UserAll() {

    }

    public UserAll(ArrayList<User> userArrayList, int total) {
        Users = userArrayList;
        this.total = total;
    }

    public ArrayList<User> getUsers() {
        return Users;
    }

    public void setUsers(ArrayList<User> users) {
        Users = users;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
