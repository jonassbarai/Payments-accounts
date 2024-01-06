package com.jonas.PaymentAccounts.Controller;

import com.jonas.PaymentAccounts.model.DTO.TransactionDTO;
import com.jonas.PaymentAccounts.model.DTO.TransactionRequestDTO;
import com.jonas.PaymentAccounts.model.Transaction;
import com.jonas.PaymentAccounts.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/transactions")
@Tag(name = "transações",description = "endpoint de transações")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity doTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO){

        TransactionDTO transactionDTO = service.dotransaction(transactionRequestDTO);

        return ResponseEntity.ok().body(transactionDTO);
    }
}
