package com.jonas.PaymentAccounts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="tb_transaction")
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne @JoinColumn(nullable = false)
    @JsonProperty(required = true)
    @NotNull(message = "O pagador é obrigatório para realizar a transação")
    private User payer;

    @ManyToOne @JoinColumn(nullable = false)
    @JsonProperty(required = true)
    @NotNull(message = "O recebedor é obrigatório para realizar a transação")
    private User payee;

    @Column(name = "Tansaction_value")
    @JsonProperty(required = true)
    @NotNull(message = "não há valor para realizar a transação")
    @Min(value = 0L, message = "transação precisa ser maior que 0,00")
    private BigDecimal value;

    @Column(name = "Date_transaction")
    private LocalDateTime dateTime = LocalDateTime.now();

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private User payer;
        private User payee;
        private BigDecimal value;

        private Builder() {
            // Construtor privado para evitar instâncias diretas
        }

        public Builder payer(User payer) {
            this.payer = payer;
            return this;
        }

        public Builder payee(User payee) {
            this.payee = payee;
            return this;
        }

        public Builder value(BigDecimal value) {
            this.value = value;
            return this;
        }

        public Transaction build() {
            Transaction transaction = new Transaction();
            transaction.setPayer(this.payer);
            transaction.setPayee(this.payee);
            transaction.setValue(this.value);
            return transaction;
        }
    }
}
