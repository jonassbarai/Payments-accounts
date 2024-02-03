package com.jonas.PaymentAccounts.service;

import com.jonas.PaymentAccounts.exceptions.BusinessException;
import com.jonas.PaymentAccounts.model.DTO.OperationDTO;
import com.jonas.PaymentAccounts.model.DTO.TransactionDTO;
import com.jonas.PaymentAccounts.model.DTO.TransactionRequestDTO;
import com.jonas.PaymentAccounts.model.Transaction;
import com.jonas.PaymentAccounts.model.User;
import com.jonas.PaymentAccounts.model.enums.AccountType;
import com.jonas.PaymentAccounts.repository.TransactionRepository;
import com.jonas.PaymentAccounts.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    NotificationService notificationService;

    @Autowired
    TokenService tokenService;
    @Value("${authorizationUrl:https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc}")
    private String urlAuthorization;

    public void doDeposit(OperationDTO operationDTO, String token){

        var email = tokenService.validateToken(token);
        User user = userService.findByEmail(email);

        if(user.getId() != operationDTO.id())
            throw new BusinessException("Something went wrong, contant the manager of your account");

        user.deposit(operationDTO.amount());
        userRepository.save(user);
    }

    public void doWithdraw(OperationDTO operationDTO, String token){

        var email = tokenService.validateToken(token);
        User user = userService.findByEmail(email);

        if(user.getId() != operationDTO.id())
            throw new BusinessException("Something went wrong, contant the manager of your account");

        user.withdraw(operationDTO.amount());

        userRepository.save(user);
    }

    @Transactional
    public TransactionDTO dotransaction(TransactionRequestDTO transactionRequestDTO) {

        User payee = userService.getUserById(transactionRequestDTO.getPayeeId());
        User payer = userService.getUserById(transactionRequestDTO.getPayerId());

        if (! authorizedTtransaction())
            throw new BusinessException("Transaction Unauthorized");

        if (!payerIsValid(payer))
            throw new BusinessException("Payer must be a Common User");

        if(! payerHaveSufficientfunds(payer,transactionRequestDTO))
            throw new BusinessException("The payer doesn't have enough funds for this transaction");

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

        notificationService.sendNotification(payer,"dinheiro enviado com sucesso");
        notificationService.sendNotification(payee,"dinheiro recebido com sucesso");

        return transactionDTO;
    }

    private boolean payerIsValid(User payer) {
        return payer.getType() == AccountType.COMMON;
    }

    private boolean payerHaveSufficientfunds(User payer, TransactionRequestDTO transactionRequestDTO) {
        int comparisonResult = payer.getBalance().compareTo(transactionRequestDTO.getValue());
        return comparisonResult >= 0;
    }

    private boolean authorizedTtransaction(){
        ResponseEntity<Map> response =  restTemplate.getForEntity(urlAuthorization,Map.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            String message = (String) response.getBody().get("message");
            return message.compareToIgnoreCase("Autorizado") == 0;
        } else
            return false;
    }
}
