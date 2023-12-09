package com.jonas.PaymentAccounts.Controller;

import com.jonas.PaymentAccounts.model.Transaction;
import com.jonas.PaymentAccounts.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping
    public ResponseEntity doTransaction(Transaction transaction){
        return null;
    }
}
