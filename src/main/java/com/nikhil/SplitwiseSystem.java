package com.nikhil;

import com.nikhil.observers.BalanceSheet;
import com.nikhil.entities.Expense;
import com.nikhil.entities.Transaction;
import com.nikhil.entities.User;
import com.nikhil.observers.ExpenseManager;
import com.nikhil.splitAlgos.Split;
import com.nikhil.splitAlgos.SplitFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// testing token
public class SplitwiseSystem {
    static void main(String[] args) {
        User alice = new User("1", "Alice", "alice@gmail.com");
        User bob = new User("2", "Bob", "bob@gmail.com");
        User charlie = new User("3", "Charlie", "charlie@gmail.com");

        ExpenseManager expenseManager = new ExpenseManager();
        BalanceSheet balanceSheet = new BalanceSheet();

        expenseManager.addObserver(balanceSheet);

        List<User> participants = new ArrayList<>();
        participants.add(alice);
        participants.add(bob);
        participants.add(charlie);

        Split equalSplit = SplitFactory.createSplit("equal");
        Map<String, Object> splitDetails = new HashMap<>();
        Map<User, Double> userVsAmount = equalSplit.getAmount(100.0, participants, splitDetails);
        Expense expense = new Expense.Builder()
                .setId("e1")
                .setPayer(alice)
                .setAmount(100.0)
                .setDescription("Dinner")
                .setParticipants(participants)
                .setUserVsAmount(userVsAmount)
                .build();
        expenseManager.addExpense(expense);

        Split percentageSplit = SplitFactory.createSplit("percentage");
        Map<String, Object> percentageSplitDetails = new HashMap<>();
        Map<User, Double> userVsPercentage = new HashMap<>();
        userVsPercentage.put(alice, 40.0);
        userVsPercentage.put(bob, 30.0);
        userVsPercentage.put(charlie, 30.0);
        percentageSplitDetails.put("percentage", userVsPercentage);
        Map<User, Double> userVsAmountForPercentageSplit = percentageSplit.getAmount(100.0, participants, percentageSplitDetails);
        Expense expense2 = new Expense.Builder()
                .setId("e2")
                .setPayer(bob)
                .setAmount(100.0)
                .setDescription("Dinner")
                .setParticipants(participants)
                .setUserVsAmount(userVsAmountForPercentageSplit)
                .build();
        expenseManager.addExpense(expense2);

        System.out.println("Individual Balances:");
        System.out.println("Alice balance: " + balanceSheet.getUserBalance(alice));
        System.out.println("Bob balance: " + balanceSheet.getUserBalance(bob));
        System.out.println("Charlie balance: " + balanceSheet.getUserBalance(charlie));

        List<Transaction> simplifiedSettlements = balanceSheet.getSimplifiedSettlement();

        System.out.println("Optimal Minimum Settlements:"+ balanceSheet.getMinimumSettlements());
        System.out.println("Settlements: ");
        for (Transaction transaction :  simplifiedSettlements){
            System.out.println(transaction.getFrom().getName() + " owes " + transaction.getTo().getName() + " " + transaction.getAmount());
        }
    }
}
