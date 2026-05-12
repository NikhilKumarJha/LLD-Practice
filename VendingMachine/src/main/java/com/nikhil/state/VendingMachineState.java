package com.nikhil.state;

import com.nikhil.Coin;
import com.nikhil.VendingMachine;

public interface VendingMachineState {
    void selectItem(VendingMachine machine, String itemId);
    void insertCoin(VendingMachine machine, Coin coin);
    void dispenseItem(VendingMachine machine);
    void refund(VendingMachine machine);
}
