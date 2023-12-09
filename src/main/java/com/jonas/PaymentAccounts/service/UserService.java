package com.jonas.PaymentAccounts.service;

import com.jonas.PaymentAccounts.model.DTO.UserUpdateDTO;
import com.jonas.PaymentAccounts.model.User;
import com.jonas.PaymentAccounts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> getAllUser(){
        return repository.findAll();
    }

    public User getUserById(long id){
         User user =  repository.findById(id).orElse(null);
          if (user == null)
              throw new RuntimeException("user doesn't exists");

          return user;
    }

    public User saveUser(User user){

       User userSaved = repository.save(user);
        return userSaved;
    }

    public User updateUser(UserUpdateDTO userUpdateDTO){
      var userToUpdate = repository.findById(userUpdateDTO.getId()).orElse(null);

      if (userUpdateDTO == null)
          throw new RuntimeException("user doesn't exists");

      var userUpdated =  userUpdateDTO.toEntity(userToUpdate);

      return userUpdated;
    }
}
