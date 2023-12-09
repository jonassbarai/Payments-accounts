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
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public Transaction dotransaction(Transaction transaction) {

        Optional<User> payee = userRepository.findById(transaction.getPayee().getId());
        Optional<User> payer = userRepository.findById(transaction.getPayer().getId());

        if (!payerIsValid(payer.get()))
            throw new RuntimeException("Payer must be a Common User");

        if(! payerHaveSufficientfunds(payer.get(),transaction))
            throw new RuntimeException("The payer doesn't have enough funds for this transaction.");

        payee.get().setBalance(payee.get().getBalance().add(transaction.getValue()));
        payer.get().setBalance(payer.get().getBalance().subtract(transaction.getValue()));

        userRepository.save(payer.get());
        userRepository.save(payee.get());


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
