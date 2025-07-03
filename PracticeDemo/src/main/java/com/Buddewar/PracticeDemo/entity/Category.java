package com.Buddewar.PracticeDemo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "category",
    cascade = {CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Expense> expenses;


    public Category() {}

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void addExpenses(Expense tempExpense)
    {
        if(expenses==null)
        {
            expenses=new ArrayList<>();
        }
        expenses.add(tempExpense);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", cat_name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
