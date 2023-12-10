package com.jonas.PaymentAccounts.model.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private String namePayer;
    private String namePayee;
    private BigDecimal value;
    private final LocalDateTime date = LocalDateTime.now();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String namePayer;
        private String namePayee;
        private BigDecimal value;

        private Builder() {
        }

        public Builder namePayer(String namePayer) {
            this.namePayer = namePayer;
            return this;
        }

        public Builder namePayee(String namePayee) {
            this.namePayee = namePayee;
            return this;
        }

        public Builder value(BigDecimal value) {
            this.value = value;
            return this;
        }

        public TransactionDTO build() {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setNamePayer(this.namePayer);
            transactionDTO.setNamePayee(this.namePayee);
            transactionDTO.setValue(this.value);
            return transactionDTO;
        }
    }
}
