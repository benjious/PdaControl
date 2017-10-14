package com.benjious.pdacontrol.been;

import java.util.List;

/**
 * Created by Benjious on 2017/10/13.
 */

public class UsersALL<User> {
    private List<User> Users;
    private int total;

    public UsersALL(List<User> users, int total) {
        Users = users;
        this.total = total;
    }

    public List<User> getUsers() {
        return Users;
    }

    public void setUsers(List<User> users) {
        Users = users;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "UsersALL{" +
                "Users=" + Users +
                '}';
    }
}
