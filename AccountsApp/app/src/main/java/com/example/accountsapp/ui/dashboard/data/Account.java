package com.example.accountsapp.ui.dashboard.data;

public class Account {

    private String id;
    private String name;
    private String amount;

    public Account(String id,String name, String amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }
    public Double getAmountAsDouble() {
        return Double.valueOf(amount);
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
