package com.nikhil.observers;

import com.nikhil.entities.Expense;
import com.nikhil.entities.Transaction;
import com.nikhil.entities.User;
import com.nikhil.entities.UserPair;

import java.util.*;

public class BalanceSheet implements ExpenseObserver {
    private Map<UserPair, Double> userPairVsBalance = new HashMap<>();


    @Override
    public void onExpenseAdded(Expense expense) {
        updateBalance(expense);
    }

    @Override
    public void onExpenseUpdated(Expense expense) {
        updateBalance(expense);
    }

    private void updateBalance(Expense expense) {
        User payer = expense.getPayer();
        Map<User, Double> userVsAmount = expense.getUserVsAmount();
        for (Map.Entry<User, Double> entry : userVsAmount.entrySet()) {
            User user = entry.getKey();
            Double amount = entry.getValue();
            if (!payer.getId().equals(user.getId())) {
                UserPair userPair = new UserPair(payer, user);
                userPairVsBalance.put(userPair, userPairVsBalance.getOrDefault(userPair, 0.0) + amount);
            }
        }
    }

    public Double getBalance(User user1, User user2) {
        return userPairVsBalance.getOrDefault(new UserPair(user1, user2), 0.0) - userPairVsBalance.getOrDefault(new UserPair(user2, user1), 0.0);
    }

    public Double getUserBalance(User user) {
        Double balance = 0.0;
        for (Map.Entry<UserPair, Double> entry : userPairVsBalance.entrySet()) {
            User user1 = entry.getKey().getUser1();
            User user2 = entry.getKey().getUser2();
            if (user.getId().equals(user1.getId())) {
                balance += entry.getValue();
            } else if (user.getId().equals(user2.getId())) {
                balance -= entry.getValue();
            }
        }
        return balance;
    }

    // this two pointer approach does not give minimum number of transactions.
    public List<Transaction> getSimplifiedSettlement() {
        List<Transaction> simplifiedTransactions = new ArrayList<>();
        Map<User, Double> netBalances = new HashMap<>();
        for (Map.Entry<UserPair, Double> entry : userPairVsBalance.entrySet()) {
            User creditor = entry.getKey().getUser1();
            User debtor = entry.getKey().getUser2();
            Double amount = entry.getValue();
            netBalances.put(creditor, netBalances.getOrDefault(creditor, 0.0) + amount);
            netBalances.put(debtor, netBalances.getOrDefault(debtor, 0.0) - amount);
        }
        List<User> creditors = new ArrayList<>();
        List<User> debtors = new ArrayList<>();
        for (Map.Entry<User, Double> entry : netBalances.entrySet()) {
            User user = entry.getKey();
            Double amount = entry.getValue();
            if (amount < 0) {
                debtors.add(user);
            } else if (amount > 0) {
                creditors.add(user);
            }
        }
        int creditorIdx = 0, debtorIdx = 0;
        while (creditorIdx < creditors.size() && debtorIdx < debtors.size()) {
            User creditor = creditors.get(creditorIdx);
            User debtor = debtors.get(debtorIdx);
            Double creditorAmount = netBalances.get(creditor);
            Double debtorAmount = netBalances.get(debtor);
            Double transferAmount = Math.min(creditorAmount, Math.abs(debtorAmount));
            simplifiedTransactions.add(new Transaction(transferAmount, debtor, creditor));
            netBalances.put(debtor, debtorAmount + transferAmount);
            netBalances.put(creditor, creditorAmount - transferAmount);
            if (Math.abs(netBalances.get(debtor)) < 0.001) {
                debtorIdx++;
            }
            if (Math.abs(netBalances.get(creditor)) < 0.001) {
                creditorIdx++;
            }
        }
        return simplifiedTransactions;
    }

    // this gives minimum number of transactions.
    public int getMinimumSettlements() {
        Map<User, Double> netBalances = new HashMap<>();
        for (Map.Entry<UserPair, Double> entry : userPairVsBalance.entrySet()) {
            User creditor = entry.getKey().getUser1();
            User debtor = entry.getKey().getUser2();
            Double amount = entry.getValue();
            netBalances.put(creditor, netBalances.getOrDefault(creditor, 0.0) + amount);
            netBalances.put(debtor, netBalances.getOrDefault(debtor, 0.0) - amount);
        }
        List<Double> creditList = new ArrayList<>();
        for (Map.Entry<User, Double> entry : netBalances.entrySet()) {
            Double amount = entry.getValue();
            if (Math.abs(amount) > 0.001) { // not zero keep them , else ignore zero
                creditList.add(amount);
            }
        }
        int n = creditList.size();
        int[] dp=new int[1<<n];
        Arrays.fill(dp, -1);
        return n - dfs((1 << n) - 1, creditList, dp);
    }

    private Double getSum(int mask, List<Double> creditList) {
        Double sum = 0.0;
        for (int i = 0; i < creditList.size(); i++) {
            if ((mask & (1 << i)) != 0) {
                sum += creditList.get(i);
            }
        }
        return sum;
    }

    private int dfs(int mask, List<Double> creditList, int[] dp) {
        if (mask == 0) {
            return 0;
        }
        if(dp[mask]!=-1){
            return dp[mask];
        }
        int maxSubGroups = 0;
        for (int subMask = mask; subMask > 0; subMask = (subMask - 1) & mask) {
            if (Math.abs(getSum(subMask, creditList)) < 0.001) {
                maxSubGroups = Math.max(maxSubGroups, 1 + dfs(subMask ^ mask, creditList, dp));
            }
        }
        return dp[mask] = maxSubGroups;
    }
}
