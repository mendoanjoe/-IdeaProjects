package com.mendoanjoe.manamart.model;

public class MTransactionItem {
    private int id;
    private String name;
    private int price;
    private int qty;
    private int transaction_id;
    private int product_id;

    public MTransactionItem(int id, String name, int price, int qty, int transaction_id, int product_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.transaction_id = transaction_id;
        this.product_id = product_id;
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

    public int getTransaction_id() {
        return transaction_id;
    }

    public int getProduct_id() {
        return product_id;
    }
}
