package com.nikhil.entities;

public class Transaction {
    private Double amount;
    private User from;
    private User to;

    public Transaction(Double amount, User from, User to) {
        this.amount = amount;
        this.from = from;
        this.to = to;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }
}
