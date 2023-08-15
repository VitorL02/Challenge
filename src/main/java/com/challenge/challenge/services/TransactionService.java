package com.challenge.challenge.services;


import com.challenge.challenge.dtos.TransactionDTO;
import com.challenge.challenge.models.Transaction;
import com.challenge.challenge.models.TransactionsAudit;
import com.challenge.challenge.repositories.TransactionAuditRepository;
import com.challenge.challenge.utils.ConstantsChallenger;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;
    @Autowired
    private TransactionAuditRepository transactionAuditRepository;

    @Transactional
    public Transaction transactionSave(TransactionDTO transactionDTO){
        TransactionsAudit transactionsAudit = new TransactionsAudit();
        Transaction transaction = new Transaction();
        transactionsAudit.setTransactionDate(new Date());
        setDataTransaction(transactionDTO, transactionsAudit);
        verifyPermissionsUser(transactionDTO, transactionsAudit);
        verifyAmoutUser(transactionDTO, transactionsAudit);
        transactionsAudit.setMenssageOperation(ConstantsChallenger.SUCESS_TRANSACTION_MENSSAGE);

        userService.removeValueUserAcountAndAddUser(transactionDTO);

        transactionsAudit = transactionAuditRepository.save(transactionsAudit);


        transaction.setAmountTransaction(transactionsAudit.getAmount());
        transaction.setMenssage(transactionsAudit.getMenssageOperation());

        return transaction;

    }

    private static void setDataTransaction(TransactionDTO transactionDTO, TransactionsAudit transactionsAudit) {
        transactionsAudit.setAmount(transactionDTO.getAmount());
        transactionsAudit.setPayee(transactionDTO.getPayee());
        transactionsAudit.setPayer(transactionDTO.getPayer());
    }

    private void verifyAmoutUser(TransactionDTO transactionDTO, TransactionsAudit transactionsAudit) {
        Boolean amoutEnough = userService.userAmoutCheckTransaction(transactionDTO.getPayer(), transactionDTO.getAmount());
        if(!amoutEnough){
            transactionsAudit.setMenssageOperation(ConstantsChallenger.INSUFICCIENT_AMOUT);
        }
    }

    private void verifyPermissionsUser(TransactionDTO transactionDTO, TransactionsAudit transactionsAudit) {
        Boolean userPermission = userService.userPermissionTransaction(transactionDTO.getPayer());
        if(!userPermission){
            transactionsAudit.setMenssageOperation(ConstantsChallenger.NOT_PERMISSON_REALIZE_TRANSACTION);
        }
    }


}
