package com.jonas.PaymentAccounts.service;

import com.jonas.PaymentAccounts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository reposioty;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return reposioty.findByEmail(username);
    }
}
