package com.nikhil;

import com.nikhil.state.VendingMachineState;

public class VendingMachine {
    private VendingMachineState machineState;
    private final Inventory inventory;
    private static VendingMachine instance;

    public String getSelectedItemId() {
        return selectedItemId;
    }

    private String selectedItemId;
    private int balance;

    private VendingMachine() {
        inventory = new Inventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void addBalance(int balance) {
        this.balance += balance;
    }

    public void setMachineState(VendingMachineState machineState) {
        this.machineState = machineState;
    }

    public static VendingMachine getInstance() {
        if (instance == null) {
            synchronized (VendingMachine.class) {
                if (instance == null) {
                    instance = new VendingMachine();
                }
            }
        }
        return instance;
    }

    public void selectItem(String itemId){
        machineState.selectItem(this, itemId);
    }

    public void insertCoin(Coin coin){
        machineState.insertCoin(this, coin);
    }

    public void dispense(){
        machineState.dispenseItem(this);
    }

    public void refund(){
        machineState.refund(this);
    }

    public Item registerItem(String itemName, int price){
        return inventory.registerItem(itemName, price);
    }

    public void addStockForItem(String itemId, int quantity){
        inventory.addStock(itemId, quantity);
    }

    public void setSelectedItemId(String selectedItemId) {
        this.selectedItemId = selectedItemId;
    }

    public int getBalance() {
        return balance;
    }

    public void reset() {
        selectedItemId = null;
        balance = 0;
    }
}
