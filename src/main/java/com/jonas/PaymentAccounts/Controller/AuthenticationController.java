package com.jonas.PaymentAccounts.Controller;

import com.jonas.PaymentAccounts.model.DTO.AuthenticationDTO;
import com.jonas.PaymentAccounts.model.User;
import com.jonas.PaymentAccounts.service.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "autenticação",description = "endpoint de autenticação de usuário")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO userData){
        //criptografia da senha
        var userNamePassword = new UsernamePasswordAuthenticationToken(userData.login(),userData.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);

        var token = tokenService.generateToken((User)auth.getPrincipal());

        return ResponseEntity.ok().body(token);
    }
}
