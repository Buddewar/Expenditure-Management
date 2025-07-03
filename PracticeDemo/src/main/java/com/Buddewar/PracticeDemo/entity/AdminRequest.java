package com.Buddewar.PracticeDemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="admin_requests")
public class AdminRequest{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @OneToOne
    @JoinColumn(name="requested_user_id",referencedColumnName = "id")
    private User user;

    @Column(name="message")
    private String message;

    public AdminRequest(){}

    public AdminRequest(String message) {
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AdminRequest{" +
                "id=" + id +
                ", user=" + user +
                ", message='" + message + '\'' +
                '}';
    }
}
