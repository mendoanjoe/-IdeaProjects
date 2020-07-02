package com.mendoanjoe.manamart.model;

public class MPreTransaction {
    private int id;
    private String code;
    private String name;
    private int price;
    private int qty;

    public MPreTransaction(String code, String name, int price, int qty) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public MPreTransaction(int id, String name, int price, int qty) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public String getCode() {
        return code;
    }
}
