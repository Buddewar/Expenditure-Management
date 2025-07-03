package com.Buddewar.PracticeDemo.service;

import com.Buddewar.PracticeDemo.entity.Expense;

import java.util.List;

public interface ExpenseService {

    List<Expense> findAllExpensesOfUser(int userId);

}
