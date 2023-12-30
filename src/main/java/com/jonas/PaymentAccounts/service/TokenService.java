package com.jonas.PaymentAccounts.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.jonas.PaymentAccounts.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    public String generateToken(User user){
        try{
            Algorithm algorithms = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.getExpireLocalDate())
                    .sign(algorithms);
            return token;
        }catch (JWTCreationException exception){
            throw new RuntimeException("Error While Generate Token",exception);
        }
    }

    public String validateToken(String token){
        Algorithm algorithms = Algorithm.HMAC256(secret);
        try {
            return JWT.require(algorithms)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    private Instant getExpireLocalDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
