package com.nikhil.state;

import com.nikhil.Coin;
import com.nikhil.VendingMachine;

public class DispensingState implements VendingMachineState {
    @Override
    public void selectItem(VendingMachine machine, String itemId) {
        System.out.println("Please wait while dispensing item");
    }

    @Override
    public void insertCoin(VendingMachine machine, Coin coin) {
        System.out.println("Please wait while dispensing item");
    }

    @Override
    public void dispenseItem(VendingMachine machine) {
        machine.getInventory().removeStock(machine.getSelectedItemId(), 1);
        System.out.println("Item dispensed");
        machine.setMachineState(new IdleState());
    }

    @Override
    public void refund(VendingMachine machine) {
        System.out.println("Dispensing item, refund not allowed");
    }
}
