package com.challenge.challenge.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TB_TRANSACTION_AUDIT")
public class TransactionsAudit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String payer;
    @Column(nullable = false)
    private String payee;
    @Column(nullable = false)
    private Date transactionDate;
    @Column(nullable = true)
    private String menssageOperation;
    @Column(nullable = false)
    private double amount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getMenssageOperation() {
        return menssageOperation;
    }

    public void setMenssageOperation(String menssageOperation) {
        this.menssageOperation = menssageOperation;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
