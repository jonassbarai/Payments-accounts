package com.jonas.PaymentAccounts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jonas.PaymentAccounts.model.enums.AccountType;
import com.jonas.PaymentAccounts.utils.CPForCNPJ;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @Column(nullable = false) @JsonProperty(required = true) @NotBlank
    private String name;

    @Column(unique = true, nullable = false, name = "CPF_CNPJ")
    @JsonProperty(value = "CPForCNPJ",required = true) @NotBlank @CPForCNPJ
    @Schema(name = "cpf ou cnpj", description = "aceita-se com ou sem pontuação")
    private String CPForCNPJ;

    @Column(unique = true, nullable = false) @JsonProperty(required = true) @NotBlank
    private String email;

    @JsonProperty(required = true) @NotBlank
    private String password;

    @Column(name = "accountType") @JsonProperty(required = true)
    private AccountType type;

    @JsonProperty(required = true)
    private BigDecimal balance;


}
