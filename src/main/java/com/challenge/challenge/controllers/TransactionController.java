package com.challenge.challenge.controllers;


import com.challenge.challenge.dtos.TransactionDTO;
import com.challenge.challenge.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    public static final String TRANSACTION_DESCRIPTION = "Transações - Cadastro e Auditoria";
    public static final String TRANSACTIONS = "Transações";

    public static final String REGISTRY_TRANSACTION_DESC = "Realiza uma transação entre usuarios";
    public static final String  REGISTRY_TRANSACTION_SUM = "Realiza uma transação entre usuarios caso o perfil deles seja USER (Os perfis MERCHANTS podem somente receber transações)";
    @Autowired
   private TransactionService transactionService;

    @PostMapping
    @Tag(description = TRANSACTION_DESCRIPTION,name = TRANSACTIONS)
    @Operation(summary = REGISTRY_TRANSACTION_DESC,description = REGISTRY_TRANSACTION_SUM )
    public ResponseEntity transactionSave(@RequestBody @Valid TransactionDTO transactionDTO) throws  Exception{
        var transaction = transactionService.transactionSave(transactionDTO);
        return  ResponseEntity.ok(transaction);

    }



}
