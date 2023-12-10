package com.jonas.PaymentAccounts.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {
    @JsonProperty("payer")
    private long payerId;

    @JsonProperty("payee")
    private long payeeId;

    @JsonProperty("value")
    private BigDecimal value;
}
