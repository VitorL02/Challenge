package com.challenge.challenge.controllers;


import com.challenge.challenge.dtos.TransactionDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {


    public ResponseEntity transactionSave(@RequestBody @Valid TransactionDTO transactionDTO){



        return  ResponseEntity.ok(transactionDTO);

    }



}
