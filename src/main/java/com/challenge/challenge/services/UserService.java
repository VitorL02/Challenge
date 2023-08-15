package com.challenge.challenge.services;


import com.challenge.challenge.dtos.UserDTO;
import com.challenge.challenge.models.User;
import com.challenge.challenge.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public User saveUser(UserDTO userDTO){
        User user = new User(userDTO);
        try{
            if(user != null){
                userRepository.save(user);
            }
        }catch (Exception e){
            throw new RuntimeException("Erro ao Cadastrar um usuario");
        }

        return user;
    }


}
