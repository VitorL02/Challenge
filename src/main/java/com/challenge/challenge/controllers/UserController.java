package com.challenge.challenge.controllers;


import com.challenge.challenge.dtos.UserDTO;
import com.challenge.challenge.models.User;
import com.challenge.challenge.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity userRegistry(@RequestBody @Valid UserDTO userDTO, UriComponentsBuilder uriBuilder) throws Exception{
        User user = userService.saveUser(userDTO);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(userDTO);
    }

    @GetMapping("/all-users")
    public ResponseEntity getAllUsers() throws Exception {
        List<User>  userList = userService.findAllUsersRegistry();
        return ResponseEntity.ok(userList);
    }




}
