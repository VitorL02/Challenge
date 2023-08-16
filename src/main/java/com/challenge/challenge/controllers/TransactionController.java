package com.challenge.challenge.controllers;


import com.challenge.challenge.dtos.TransactionDTO;
import com.challenge.challenge.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
   private TransactionService transactionService;

    @PostMapping
    public ResponseEntity transactionSave(@RequestBody @Valid TransactionDTO transactionDTO){
        var transaction = transactionService.transactionSave(transactionDTO);
        return  ResponseEntity.ok(transaction);

    }



}
