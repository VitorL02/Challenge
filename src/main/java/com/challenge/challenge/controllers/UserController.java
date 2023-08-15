package com.challenge.challenge.controllers;


import com.challenge.challenge.dtos.UserDTO;
import com.challenge.challenge.models.User;
import com.challenge.challenge.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity userRegistry(@RequestBody @Valid UserDTO userDTO, UriComponentsBuilder uriBuilder){
        User user = userService.saveUser(userDTO);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(userDTO);
    }



}
