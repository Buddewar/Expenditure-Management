package com.Buddewar.PracticeDemo.controller;

import com.Buddewar.PracticeDemo.entity.*;
import com.Buddewar.PracticeDemo.repository.CategoryRepository;
import com.Buddewar.PracticeDemo.repository.ExpenseRepository;
import com.Buddewar.PracticeDemo.repository.PaymentMethodRepository;
import com.Buddewar.PracticeDemo.repository.UserRepository;
import com.Buddewar.PracticeDemo.service.ExpenseService;
import com.Buddewar.PracticeDemo.service.ExpenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseServiceImpl expenseService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    // getting all the expenses based on user_id
    @Transactional
    @GetMapping("/user/expenses")
    public String AllExpenses(Model theModel)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user=userRepository.findByUsername(username);

        int id=user.getId();

        List<Expense> elist =expenseService.findAllExpensesOfUser(id);

        for(Expense e : elist)
        {
            System.out.println("Payment Method: "+e.getPaymentMethod());
        }
        theModel.addAttribute("expenses",elist);

        return "expenses";
    }

    @GetMapping("/user/addexpense")
    public String addExpense(Model theModel)
    {
        List<Category> category=categoryRepository.findAll();
        List<PaymentMethod> paymentMethod=paymentMethodRepository.findAll();

        theModel.addAttribute("categories",category);
        theModel.addAttribute("paymentmethods",paymentMethod);

        return "addExpense";
    }

    @PostMapping("/user/saveExpense")
    public  String saveExpense(
                            @RequestParam(value = "id", required = false) Integer id,
                            @RequestParam("description") String description,
                            @RequestParam("amount") double amount,
                            @RequestParam("expense_date")
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                LocalDate expense_date,
                            @RequestParam("category_id") String category_id,
                            @RequestParam("payment_method_id") String payment_method_id)
    {
        // instead of sending all the fields using @RequestParam
        // i can also send a model object by binding it to the expense object

        Expense expense;

        if (id != null) {
            expense = expenseRepository.findById(id).orElse(new Expense());
        } else {
            expense = new Expense();
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username);
        expense.setUser(user);


        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setExpense_date(expense_date.atStartOfDay());

        int cat_id=Integer.parseInt(category_id);
        Category category=categoryRepository.findById(cat_id).orElse(null);
        expense.setCategory(category);

        int method_id=Integer.parseInt(payment_method_id);
        PaymentMethod paymentMethod=paymentMethodRepository.findById(method_id).orElse(null);
        expense.setPaymentMethod(paymentMethod);

        expenseRepository.saveLastExpenseDate(user.getId());

        expenseRepository.save(expense);

        return "redirect:/user";
    }

    @GetMapping("user/editOrDelete")
    public String editOrDelete(Model theModel)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user=userRepository.findByUsername(username);

        int id=user.getId();

        List<Expense> elist =expenseService.findAllExpensesOfUser(id);

        theModel.addAttribute("expenses",elist);
        return "edit-or-delete-expense";
    }


    @GetMapping("/user/editExpense")
    public String editExpense(@RequestParam("id") String id,Model model)
    {
        int eId=Integer.parseInt(id);
        Optional<Expense> expenseO=expenseRepository.findById(eId);
        if(expenseO.isPresent())
        {
            Expense expense=expenseO.get();
            model.addAttribute("expense",expense);
        }
        List<Category> category=categoryRepository.findAll();
        List<PaymentMethod> paymentMethod=paymentMethodRepository.findAll();

        model.addAttribute("categories",category);
        model.addAttribute("paymentmethods",paymentMethod);



        return "edit-expense-form";
    }

    @GetMapping("/user/deleteExpense")
    public String deleteExpense(@RequestParam("id") int id)
    {
        expenseRepository.deleteById(id);
        return "redirect:/user/editOrDelete";
    }






}
