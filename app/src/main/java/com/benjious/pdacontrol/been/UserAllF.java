package com.benjious.pdacontrol.been;

import java.util.List;

/**
 * Created by Benjious on 2017/10/14.
 */

public class UserAllF<E> {
    private  List<User> E;
    private int total;

    public UserAllF(List<User> e, int total) {
        E = e;
        this.total = total;
    }

    public List<User> getE() {
        return E;
    }

    public void setE(List<User> e) {
        E = e;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
