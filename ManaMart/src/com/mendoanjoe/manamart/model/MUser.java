package com.mendoanjoe.manamart.model;

import java.util.Date;

public class MUser {
    private int id;
    private String name;
    private String username;
    private String password;
    private String address;
    private Date dob;

    public MUser(int id, String name, String username, String password, String address, Date dob) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public Date getDob() {
        return dob;
    }
}
