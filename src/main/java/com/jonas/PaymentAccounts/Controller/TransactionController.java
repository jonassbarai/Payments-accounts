package com.jonas.PaymentAccounts.Controller;

import com.jonas.PaymentAccounts.model.DTO.OperationDTO;
import com.jonas.PaymentAccounts.model.DTO.TransactionDTO;
import com.jonas.PaymentAccounts.model.DTO.TransactionRequestDTO;
import com.jonas.PaymentAccounts.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/deposit")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity doDeposity(@RequestBody OperationDTO operationDTO, @RequestHeader String token){

        service.doDeposit(operationDTO,token);

        return ResponseEntity.ok().body("Depósito realizado com sucesso");
    }
    @PatchMapping("/withdraw")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity doWithdraw(@RequestBody OperationDTO operationDTO,@RequestHeader String token){

        service.doWithdraw(operationDTO,token);

        return ResponseEntity.ok().body("Saque realizado com sucesso");
    }
}
