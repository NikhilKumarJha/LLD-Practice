package com.nikhil.observers;

import com.nikhil.entities.Expense;

public interface ExpenseSubject {
    void addObserver(ExpenseObserver observer);

    void removeObserver(ExpenseObserver observer);

    void notifyExpenseAdded(Expense expense);

    void notifyExpenseUpdated(Expense expense);
}
