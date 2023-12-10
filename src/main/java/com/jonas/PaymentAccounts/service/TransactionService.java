package com.jonas.PaymentAccounts.service;

import com.jonas.PaymentAccounts.model.Transaction;
import com.jonas.PaymentAccounts.model.User;
import com.jonas.PaymentAccounts.model.enums.AccountType;
import com.jonas.PaymentAccounts.repository.TransactionRepository;
import com.jonas.PaymentAccounts.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public Transaction dotransaction(Transaction transaction) {

        User payee = userService.getUserById(transaction.getPayee().getId());
        User payer = userService.getUserById(transaction.getPayer().getId());


        if (!payerIsValid(payer))
            throw new RuntimeException("Payer must be a Common User");

        if(! payerHaveSufficientfunds(payer,transaction))
            throw new RuntimeException("The payer doesn't have enough funds for this transaction.");

        payee.setBalance(payee.getBalance().add(transaction.getValue()));
        payer.setBalance(payer.getBalance().subtract(transaction.getValue()));

        userService.saveUser(payer);
        userService.saveUser(payee);


        return null;
    }

    private boolean payerIsValid(User payer) {
        return payer.getType() == AccountType.COMMON;

    }

    private boolean payerHaveSufficientfunds(User payer, Transaction transaction) {
        int comparisonResult = payer.getBalance().compareTo(transaction.getValue());

        return comparisonResult >= 0;
    }
}
