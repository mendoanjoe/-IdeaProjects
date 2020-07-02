package com.mendoanjoe.manamart.model;

public class MTransactionItem extends MPreTransaction {
    private int transaction_id;
    private int product_id;

    public MTransactionItem(int id, String name, int price, int qty, int transaction_id, int product_id) {
        super(id, name, price, qty);
        this.transaction_id = transaction_id;
        this.product_id = product_id;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public int getProduct_id() {
        return product_id;
    }
}
