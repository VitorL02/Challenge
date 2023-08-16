package com.challenge.challenge.controllers;


import com.challenge.challenge.dtos.UserDTO;
import com.challenge.challenge.models.User;
import com.challenge.challenge.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    public static final String  USER_DESCRIPTION = "Usuarios - Cadastro e Listagem";
    public static final String  USERS = "Usuarios";


    public static final String  ALL_USERS_DESC = "Retorna todos os usuario registrados sem nenhum tipo de filtragem";
    public static final String  ALL_USERS_SUM = "Retorna todos os usuarios cadastrados";

    public static final String  SAVE_USERS_DESC = "Registra um usuario no banco de dados caso os dados estejam validos";
    public static final String  SAVE_USERS_SUM = "Registra um usuario no banco de dados";

    @Autowired
    private UserService userService;

    @PostMapping
    @Tag(description = USER_DESCRIPTION,name = USERS)
    @Operation(summary = SAVE_USERS_SUM ,description = SAVE_USERS_DESC)
    public ResponseEntity userRegistry(@RequestBody @Valid UserDTO userDTO, UriComponentsBuilder uriBuilder) throws Exception{
        User user = userService.saveUser(userDTO);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(userDTO);
    }

    @GetMapping("/all-users")
    @Tag(description = USER_DESCRIPTION,name = USERS)
    @Operation(summary = ALL_USERS_SUM,description = ALL_USERS_DESC)
    public ResponseEntity getAllUsers() throws Exception {
        List<User>  userList = userService.findAllUsersRegistry();
        return ResponseEntity.ok(userList);
    }




}
