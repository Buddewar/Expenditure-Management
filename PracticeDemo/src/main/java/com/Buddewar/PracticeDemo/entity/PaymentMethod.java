package com.Buddewar.PracticeDemo.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="payment_methods")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "paymentMethod")
    private List<Expense> expenses;

    public void addExpenses(Expense tempExpense)
    {
        if(expenses==null)
        {
            expenses=new ArrayList<>();
        }
        expenses.add(tempExpense);
    }

    public PaymentMethod(){}


    public PaymentMethod(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "id=" + id +
                ", payment_method='" + name + '\'' +
                '}';
    }
}
