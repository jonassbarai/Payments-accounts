package com.jonas.PaymentAccounts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jonas.PaymentAccounts.model.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false, name = "CPF_CNPJ")
    @JsonProperty("CPForCNPJ")
    private String CPForCNPJ;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @Column(name = "accountType")
    private AccountType type;
    private BigDecimal balance;

}
