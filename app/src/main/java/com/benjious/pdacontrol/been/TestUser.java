package com.benjious.pdacontrol.been;

/**
 * Created by Benjious on 2017/10/13.
 */

public class TestUser {
    private String username;

    public TestUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "TestUser{" +
                "username='" + username + '\'' +
                '}';
    }
}
