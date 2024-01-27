package com.jonas.PaymentAccounts.model.DTO;

import com.jonas.PaymentAccounts.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateDTO {
    private long id;
    private String name;
    private String email;

    public User toEntity(User user){
        user.setName(this.name);
        user.setEmail(this.email);
        return user;
    }
}
