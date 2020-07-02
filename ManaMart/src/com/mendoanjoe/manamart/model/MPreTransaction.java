package com.mendoanjoe.manamart.model;

public class MPreTransaction {
    private String id;
    private String name;
    private String price;
    private int qty;

    public MPreTransaction(String id, String name, String price, int qty) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }
}
