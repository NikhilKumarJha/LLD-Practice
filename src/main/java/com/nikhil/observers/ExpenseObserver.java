package com.nikhil.observers;

import com.nikhil.entities.Expense;

public interface ExpenseObserver {
    void onExpenseAdded(Expense expense);

    void onExpenseUpdated(Expense expense);
}
