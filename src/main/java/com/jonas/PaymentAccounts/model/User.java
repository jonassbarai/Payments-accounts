package com.jonas.PaymentAccounts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jonas.PaymentAccounts.model.enums.AccountType;
import com.jonas.PaymentAccounts.model.enums.UserRole;
import com.jonas.PaymentAccounts.utils.CPForCNPJ;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_user")
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) @JsonProperty(required = true) @NotBlank
    private String name;

    @Column(unique = true, nullable = false, name = "CPF_CNPJ")
    @JsonProperty(value = "CPForCNPJ",required = true) @NotBlank @CPForCNPJ
    @Schema(description = "aceita-se com ou sem pontuação")
    private String CPForCNPJ;

    @Column(unique = true, nullable = false) @JsonProperty(required = true) @NotBlank
    private String email;

    @JsonProperty(required = true) @NotBlank
    private String password;

    @Column(name = "accountType") @JsonProperty(required = true)
    private AccountType type;

    @JsonProperty(required = true)
    private BigDecimal balance;

    @Schema(hidden = true)
    private UserRole role = UserRole.USER;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else
            return  List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
    @JsonIgnore
    @Override
    public String getUsername() {
        return this.name;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
