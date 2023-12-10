package com.jonas.PaymentAccounts.service;

import com.jonas.PaymentAccounts.model.DTO.TransactionDTO;
import com.jonas.PaymentAccounts.model.DTO.TransactionRequestDTO;
import com.jonas.PaymentAccounts.model.Transaction;
import com.jonas.PaymentAccounts.model.User;
import com.jonas.PaymentAccounts.model.enums.AccountType;
import com.jonas.PaymentAccounts.repository.TransactionRepository;
import com.jonas.PaymentAccounts.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public TransactionDTO dotransaction(TransactionRequestDTO transactionRequestDTO) {

        User payee = userService.getUserById(transactionRequestDTO.getPayeeId());
        User payer = userService.getUserById(transactionRequestDTO.getPayerId());

        if (!payerIsValid(payer))
            throw new RuntimeException("Payer must be a Common User");

        if(! payerHaveSufficientfunds(payer,transactionRequestDTO))
            throw new RuntimeException("The payer doesn't have enough funds for this transaction.");

        payee.setBalance(payee.getBalance().add(transactionRequestDTO.getValue()));
        payer.setBalance(payer.getBalance().subtract(transactionRequestDTO.getValue()));

        userService.saveUser(payer);
        userService.saveUser(payee);

        Transaction transaction = Transaction.builder()
                .payee(payee)
                .payer(payer)
                .value(transactionRequestDTO.getValue()).build();

        transactionRepository.save(transaction);

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .namePayer(payer.getName())
                .namePayee(payee.getName())
                .value(transactionRequestDTO.getValue())
                .build();

        return transactionDTO;
    }

    private boolean payerIsValid(User payer) {
        return payer.getType() == AccountType.COMMON;

    }

    private boolean payerHaveSufficientfunds(User payer, TransactionRequestDTO transactionRequestDTO) {
        int comparisonResult = payer.getBalance().compareTo(transactionRequestDTO.getValue());

        return comparisonResult >= 0;
    }
}
