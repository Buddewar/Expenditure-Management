package com.Buddewar.PracticeDemo.service;

import com.Buddewar.PracticeDemo.entity.Category;
import com.Buddewar.PracticeDemo.entity.Expense;
import com.Buddewar.PracticeDemo.repository.CategoryRepository;
import com.Buddewar.PracticeDemo.repository.ExpenseRepository;
import com.Buddewar.PracticeDemo.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService{
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    @Transactional
    public List<Expense> findAllExpensesOfUser(int userId) {
        List<Expense> expenseList=expenseRepository.findAllByUserId(userId);
//        System.out.println("Expense list: "+expenseList);
        return expenseList;
    }

    public Map<String,Double> getCategoryTotals(int userId)
    {
        List<Object[]> results=expenseRepository.getCategoryTotals(userId);
        Map<String,Double> totals=new LinkedHashMap<>();


        for(Object[] dummy:results)
        {
            Double amount;
            String category=(String) dummy[0];
            if(dummy[1]!=null )
            {
                amount=((Number)dummy[1]).doubleValue();
            }
            else
            {
                amount=0.0;
            }
            totals.put(category,amount);
        }
        return totals;
    }

    public Map<String,Double>getPaymentTypeStats(int userId)
    {
        List<Object[]> results =expenseRepository.getPaymentTypeStats(userId);
        Map<String,Double> new_results=new LinkedHashMap<>();
        for(Object[] dummy:results)
        {
            Double amount;
            String paymentMethod=(String) dummy[0];
            if(dummy[1]!=null )
            {
                amount=((Number)dummy[1]).doubleValue();
            }
            else
            {
                amount=0.0;
            }
            new_results.put(paymentMethod,amount);
        }
        return new_results;
    }

    public Map<String,Double>getMonthlyStats(int userId)
    {
        List<Object[]> results =expenseRepository.getMonthlyStats(userId);
        Map<String,Double> new_results=new LinkedHashMap<>();
        for(Object[] dummy:results)
        {
            Double amount;

            String month= String.valueOf(Month.of((Integer) dummy[0]));
            if(dummy[1]!=null )
            {
                amount=((Number)dummy[1]).doubleValue();
            }
            else
            {
                amount=0.0;
            }
            new_results.put(month,amount);
        }
        return new_results;
    }


}
