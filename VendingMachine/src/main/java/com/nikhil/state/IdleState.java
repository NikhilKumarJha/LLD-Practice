package com.nikhil.state;

import com.nikhil.Coin;
import com.nikhil.VendingMachine;

public class IdleState implements VendingMachineState {
    @Override
    public void selectItem(VendingMachine machine, String itemId) {
        if (!machine.getInventory().isItemAvailable(itemId)){
            System.out.println("Item not available");
            return;
        }
        machine.setSelectedItemId(itemId);
        machine.setMachineState(new SelectedItemState());
    }

    @Override
    public void insertCoin(VendingMachine machine, Coin coin) {
        System.out.println("Please select item before inserting coin");
    }

    @Override
    public void dispenseItem(VendingMachine machine) {
        System.out.println("Please dispense item after selecting item");
    }

    @Override
    public void refund(VendingMachine machine) {
        System.out.println("Nothing to refund as no coins inserted");
    }
}
