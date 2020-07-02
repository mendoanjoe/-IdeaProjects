package com.mendoanjoe.manamart.model;

public class MTransaction {
    private int id;
    private int total;
    private int paying;
    private int paying_return;
    private int user_id;

    public MTransaction(int id, int total, int paying, int paying_return, int user_id) {
        this.id = id;
        this.total = total;
        this.paying = paying;
        this.paying_return = paying_return;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public int getTotal() {
        return total;
    }

    public int getPaying() {
        return paying;
    }

    public int getPaying_return() {
        return paying_return;
    }

    public int getUser_id() {
        return user_id;
    }
}
