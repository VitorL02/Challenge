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
}
