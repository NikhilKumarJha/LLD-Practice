package com.nikhil.observers;

import com.nikhil.entities.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseManager implements ExpenseSubject {

    private List<ExpenseObserver> observers = new ArrayList<>();
    private List<Expense> expenses = new ArrayList<>();

    @Override
    public void addObserver(ExpenseObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(ExpenseObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyExpenseAdded(Expense expense) {
        for (ExpenseObserver expenseObserver : observers) {
            expenseObserver.onExpenseAdded(expense);
        }
    }

    @Override
    public void notifyExpenseUpdated(Expense expense) {
        for (ExpenseObserver expenseObserver : observers) {
            expenseObserver.onExpenseUpdated(expense);
        }
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
        notifyExpenseAdded(expense);
    }

    public void updateExpense(Expense expense) {
        for (int i = 0; i < expenses.size(); i++) {
            if (expense.getId().equals(expenses.get(i).getId())) {
                expenses.set(i, expense);
                notifyExpenseUpdated(expense);
                return;
            }
        }
        throw new IllegalArgumentException("Expense not found" + expense.getId());
    }

    public List<Expense> getExpenses() {
        return expenses;
    }
}
