package com.nikhil;

import java.util.UUID;

public class Item {
    private String id;
    private String name;
    private int price;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Item(String name, int price) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
    }
}
