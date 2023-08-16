package com.challenge.challenge.models;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;

@Embeddable
public class UserBalance {
    private double balance;

    public UserBalance() {
    }

    public UserBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
