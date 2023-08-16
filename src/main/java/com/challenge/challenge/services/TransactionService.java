package com.challenge.challenge.services;


import com.challenge.challenge.dtos.TransactionDTO;
import com.challenge.challenge.models.Message;
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

    @Autowired
    private AuthorizationService authorizationService;

    @Transactional
    public Transaction transactionSave(TransactionDTO transactionDTO) throws Exception {
        TransactionsAudit transactionsAudit = new TransactionsAudit();
        Transaction transaction = new Transaction();
        try{
            transactionsAudit.setTransactionDate(new Date());
            setDataTransaction(transactionDTO, transactionsAudit);
            Boolean amoutEnough = userService.userAmoutCheckTransaction(transactionDTO.getPayer(), transactionDTO.getAmount());
            Boolean userPermission = userService.userPermissionTransaction(transactionDTO.getPayer());

            if(userPermission && !amoutEnough){
                Message authorization = authorizationService.authorization();
                realizeTransaction(transactionDTO, transactionsAudit,authorization);
            }else{
                verifyMessageErrorReturn(transactionsAudit,userPermission,amoutEnough);
            }
            transactionsAudit = transactionAuditRepository.save(transactionsAudit);
            setTransactionData(transactionsAudit, transaction);

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        return transaction;

    }

    private void verifyMessageErrorReturn(TransactionsAudit transactionsAudit, Boolean userPermission, Boolean amoutEnough) throws Exception {
        if(userPermission && amoutEnough){
            transactionsAudit.setMenssageOperation(ConstantsChallenger.INSUFICCIENT_AMOUT);
            transactionAuditRepository.save(transactionsAudit);
            throw new Exception(ConstantsChallenger.INSUFICCIENT_AMOUT);
        } else if (!userPermission && amoutEnough ) {
            transactionsAudit.setMenssageOperation(new StringBuilder().append(ConstantsChallenger.INSUFICCIENT_AMOUT).append(" e ").append(ConstantsChallenger.NOT_PERMISSON_REALIZE_TRANSACTION).toString());
            transactionAuditRepository.save(transactionsAudit);
            throw new Exception(new StringBuilder().append(ConstantsChallenger.INSUFICCIENT_AMOUT).append(" e ").append(ConstantsChallenger.NOT_PERMISSON_REALIZE_TRANSACTION).toString());
        } else if (!userPermission && !amoutEnough ) {
            transactionsAudit.setMenssageOperation(ConstantsChallenger.NOT_PERMISSON_REALIZE_TRANSACTION);
            transactionAuditRepository.save(transactionsAudit);
            throw new Exception(ConstantsChallenger.NOT_PERMISSON_REALIZE_TRANSACTION);
        }
    }


    @Transactional
    private void realizeTransaction(TransactionDTO transactionDTO, TransactionsAudit transactionsAudit, Message authorization) {
        if(authorization.getMessage().equalsIgnoreCase("Autorizado")){
            transactionsAudit.setMenssageOperation(ConstantsChallenger.SUCESS_TRANSACTION_MENSSAGE);
            userService.removeValueUserAcountAndAddUser(transactionDTO);
        }else{
            transactionsAudit.setMenssageOperation(ConstantsChallenger.NOT_PERMISSON_REALIZE_TRANSACTION);
        }
    }


    private static void setDataTransaction(TransactionDTO transactionDTO, TransactionsAudit transactionsAudit) {
        transactionsAudit.setAmount(transactionDTO.getAmount());
        transactionsAudit.setPayee(transactionDTO.getPayee());
        transactionsAudit.setPayer(transactionDTO.getPayer());
    }



    private static void setTransactionData(TransactionsAudit transactionsAudit, Transaction transaction) {
        transaction.setAmountTransaction(transactionsAudit.getAmount());
        transaction.setMenssage(transactionsAudit.getMenssageOperation());
    }

}
