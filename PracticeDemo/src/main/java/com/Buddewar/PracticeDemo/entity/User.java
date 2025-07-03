package com.Buddewar.PracticeDemo.entity;

import jakarta.persistence.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="users")

public class User {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;



    @Column(name="username")
    private String username;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="created_at")
    private LocalDateTime created_at;

    @ManyToOne()
    @JoinColumn(name="role_id")
    private Role role;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private UserInfo userInfo;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Expense> expenses;


    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private AdminRequest adminRequest;


    public AdminRequest getAdminRequest() {
        return adminRequest;
    }

    public void setAdminRequest(AdminRequest adminRequest) {
        this.adminRequest = adminRequest;
    }

    public User(){}

    public User(String email, String password, LocalDateTime created_at, Role role) {
        this.email = email;
        this.password = password;
        this.created_at = created_at;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
