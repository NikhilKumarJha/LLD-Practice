package com.nikhil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Inventory {
    private Map<String, Item> itemIdVsItem = new ConcurrentHashMap<>();
    private Map<String, Integer> itemIdVsQuantity = new ConcurrentHashMap<>();

    public void addStock(String itemId, int quantity) {
        Item item = itemIdVsItem.get(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Invalid item id");
        }
        itemIdVsQuantity.merge(itemId, quantity, Integer::sum);
    }

    public void removeStock(String itemId, int quantity) {
        Item item = itemIdVsItem.get(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Invalid item id");
        }
        itemIdVsQuantity.compute(itemId, (_, currentQuantity) -> {
            if (currentQuantity == null || currentQuantity < quantity) {
                throw new IllegalArgumentException("Insufficient stock");
            }
            return currentQuantity - quantity;
        });
    }

    public Item registerItem(String itemName, int price){
        Item item = new Item(itemName, price);
        itemIdVsItem.put(item.getId(), item);
        itemIdVsQuantity.put(item.getId(), 0);
        return item;
    }

    public int getItemStock(String itemId){
        Item item = itemIdVsItem.get(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Invalid item id");
        }
        return itemIdVsQuantity.getOrDefault(itemId, 0);
    }

    public boolean isItemAvailable(String itemId){
        return itemIdVsItem.containsKey(itemId);
    }

    public int getItemPrice(String itemId){
        Item item = itemIdVsItem.get(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Invalid item id");
        }
        return item.getPrice();
    }
}
