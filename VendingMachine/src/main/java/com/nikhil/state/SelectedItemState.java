package com.nikhil.state;

import com.nikhil.Coin;
import com.nikhil.VendingMachine;

public class SelectedItemState implements VendingMachineState {
    @Override
    public void selectItem(VendingMachine machine, String itemId) {
        System.out.println("Item already selected");
    }

    @Override
    public void insertCoin(VendingMachine machine, Coin coin) {
        machine.addBalance(coin.getValue());
        System.out.println("Coin inserted: "+ coin.getValue());
        if (machine.getBalance() >= machine.getInventory().getItemPrice(machine.getSelectedItemId())){
            System.out.println("Sufficient money received");
            machine.setMachineState(new DispensingState());
        }
    }

    @Override
    public void dispenseItem(VendingMachine machine) {
        System.out.println("Please insert sufficient money");
    }

    @Override
    public void refund(VendingMachine machine) {
        machine.setMachineState(new IdleState());
        machine.reset();
    }
}
