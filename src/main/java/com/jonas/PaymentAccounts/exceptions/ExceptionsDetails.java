package com.jonas.PaymentAccounts.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

import java.time.LocalDateTime;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionsDetails {

    private String title;
    private LocalDateTime  timestamp;
    private Integer  status;
    private String  exception;
    private Map<String,String>  details;
}
