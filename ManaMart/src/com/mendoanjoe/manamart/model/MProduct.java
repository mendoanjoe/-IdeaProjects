package com.mendoanjoe.manamart.model;

public class MProduct {
    private int id;
    private String code;
    private String name;
    private int price;

    public MProduct(int id, String code, String name, int price) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
