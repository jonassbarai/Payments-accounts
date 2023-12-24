package com.jonas.PaymentAccounts.Controller;

import com.jonas.PaymentAccounts.model.DTO.UserUpdateDTO;
import com.jonas.PaymentAccounts.model.User;
import com.jonas.PaymentAccounts.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Tag(name = "usuarios",description = "endpoint de usuários")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity getAllUsers(){
        List<User> Users = service.getAllUser();
        return ResponseEntity.ok().body(Users);
    }

    @GetMapping("/{id}")
    public ResponseEntity getAllUsers(@PathVariable long id){
        User user = service.getUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity saveUser(@Valid @RequestBody User userToCreate){
        User user =  service.saveUser(userToCreate);

        URI location = URI.create(String.format("/users/%s", userToCreate.getId()));

        return ResponseEntity.created(location).body(user);
    }
    @PutMapping("/{id}")
    public ResponseEntity updateUser(@RequestBody UserUpdateDTO userDTO, @PathVariable long id){
        User user =  service.updateUser(userDTO);
        return ResponseEntity.ok().body(user);
    }


}
