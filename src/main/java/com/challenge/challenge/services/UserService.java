package com.challenge.challenge.services;


import com.challenge.challenge.dtos.TransactionDTO;
import com.challenge.challenge.dtos.UserDTO;
import com.challenge.challenge.enums.Roles;
import com.challenge.challenge.models.User;
import com.challenge.challenge.models.UserBalance;
import com.challenge.challenge.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public User saveUser(UserDTO userDTO) throws Exception {
        User user = new User(userDTO);
        try{
            if(user != null){
                userRepository.save(user);
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        return user;
    }

   public Boolean userPermissionTransaction(String payeeUUID) throws Exception {
        if(payeeUUID != null ){
            try{
                if (permissionVerify(payeeUUID)) return true;
            }catch (Exception e){
                throw new Exception("Erro ao recuperar usuario ");
            }
        }

        return false;
   }

    public Boolean userAmoutCheckTransaction(String payeeUUID,double payment) throws Exception {
        if(payeeUUID != null ){
            try{
                if (amoutVerify(payeeUUID,payment)) return true;
            }catch (Exception e){
                throw new Exception("Erro ao recuperar usuario ");
            }
        }

        return false;
    }

    private boolean permissionVerify(String payeeUUID) {
        Optional<User> userPayee = userRepository.findById(UUID.fromString(payeeUUID));
        if(userPayee.isPresent()){
            return userPayee.get().getRole().equals(Roles.USER);
        }
        return false;
    }

    private boolean amoutVerify(String payerUUID, double payment) {
        Optional<User> userPayer = userRepository.findById(UUID.fromString(payerUUID));
        if(userPayer.isPresent()){
            return userPayer.get().getBalance().getBalance() < payment;
        }
        return false;
    }

    @Transactional
    public void removeValueUserAcountAndAddUser(TransactionDTO transactionDTO) {
        try{

            Optional<User> userPayer = userRepository.findById(UUID.fromString(transactionDTO.getPayer()));
            Optional<User> userPayee = userRepository.findById(UUID.fromString(transactionDTO.getPayee()));
            if(userPayer.isPresent() && userPayee.isPresent() ){

                double newAmoutPayer = userPayer.get().getBalance().getBalance() - transactionDTO.getAmount();
                double newAmoutPayee = userPayee.get().getBalance().getBalance() + transactionDTO.getAmount();


                UserBalance userBalancePayer = new UserBalance(newAmoutPayer);
                userPayer.get().setBalance(userBalancePayer);
                UserBalance userBalancePayee = new UserBalance(newAmoutPayee);
                userPayee.get().setBalance(userBalancePayee);

                userRepository.save(userPayer.get());
                userRepository.save(userPayee.get());

            }else{
                throw new Exception("Erro ao encontrar usuario de recebimento ou usuario de pagamento");
            }



        }catch (Exception e){
            throw new RuntimeException("Erro ao realizar transação");
        }


    }

    public List<User> findAllUsersRegistry() throws Exception {
        List<User> allUsers = new ArrayList<>();
        try {
            allUsers  = userRepository.findAll();
        }catch (Exception e){
            throw new Exception("Erro ao encontrar lista de usuarios");
        }


        return allUsers;
    }
}
