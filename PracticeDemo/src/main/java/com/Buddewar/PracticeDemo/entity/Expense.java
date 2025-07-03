package com.Buddewar.PracticeDemo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private  int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="description")
    private String description;

    @Column(name="amount")
    private double amount;

    @Column(name="expense_date")
    private LocalDateTime expense_date;

    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH,
                    CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH,
                    CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name="payment_method_id")
    private PaymentMethod paymentMethod;

    public Expense(){}

    public Expense(User user, String description, double amount, LocalDateTime expense_date) {
        this.user = user;
        this.description = description;
        this.amount = amount;
        this.expense_date = expense_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getExpense_date() {
        return expense_date;
    }

    public void setExpense_date(LocalDateTime expense_date) {
        this.expense_date = expense_date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", expense_date=" + expense_date +
                ", category=" + category +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
