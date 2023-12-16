package com.jonas.PaymentAccounts.Controller;

import com.jonas.PaymentAccounts.model.DTO.UserUpdateDTO;
import com.jonas.PaymentAccounts.model.User;
import com.jonas.PaymentAccounts.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
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
        return ResponseEntity.ok().body(user);
    }
    @PutMapping("/{id}")
    public ResponseEntity updateUser(@RequestBody UserUpdateDTO userDTO, @PathVariable long id){
        User user =  service.updateUser(userDTO);
        return ResponseEntity.ok().body(user);
    }


}
