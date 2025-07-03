package com.Buddewar.PracticeDemo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="user_info")
public class UserInfo {

    //    id int auto_increment primary key,
    //    user_id int not null,
    //    name varchar(255) not null,
    //    income decimal(10,2),
    //    savings decimal(10,2),
    //    financial_notes varchar(255),
    //    created_at datetime default current_timestamp,
    //    updated_at datetime default current_timestamp on update current_timestamp,
    //    last_expense_date datetime

        @Id
        @Column(name="id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name="user_id")
        private User user;

        @Column(name="name")
        private String name;

        @Column(name="income")
        private double income;

        @Column(name="savings")
        private double savings;

        @Column(name="financial_notes")
        private String financial_notes;

        @Column(name="last_expense_date")
        private LocalDateTime last_expense_date;

        public UserInfo(){}

        public UserInfo(String name, double income, double savings, String financial_notes, LocalDateTime last_expense_date) {
            this.name = name;
            this.income = income;
            this.savings = savings;
            this.financial_notes = financial_notes;
            this.last_expense_date = last_expense_date;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getIncome() {
            return income;
        }

        public void setIncome(double income) {
            this.income = income;
        }

        public double getSavings() {
            return savings;
        }

        public void setSavings(double savings) {
            this.savings = savings;
        }

        public String getFinancial_notes() {
            return financial_notes;
        }

        public void setFinancial_notes(String financial_notes) {
            this.financial_notes = financial_notes;
        }

        public LocalDateTime getLast_expense_date() {
            return last_expense_date;
        }

        public void setLast_expense_date(LocalDateTime last_expense_date) {
            this.last_expense_date = last_expense_date;
        }
}
