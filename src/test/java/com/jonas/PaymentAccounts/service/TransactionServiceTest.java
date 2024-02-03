package com.jonas.PaymentAccounts.service;

import com.jonas.PaymentAccounts.exceptions.BusinessException;
import com.jonas.PaymentAccounts.model.DTO.TransactionDTO;
import com.jonas.PaymentAccounts.model.DTO.TransactionRequestDTO;
import com.jonas.PaymentAccounts.model.User;
import com.jonas.PaymentAccounts.model.enums.AccountType;
import com.jonas.PaymentAccounts.repository.TransactionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private UserService userService;
    @Mock
    RestTemplate restTemplate;
    @Mock
    NotificationService notificationService;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void dotransactionTest(){
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(1,2, BigDecimal.valueOf(350.00));

        User userPayer = UserServiceTest.buildUser();
        User userPayee = UserServiceTest.buildUser();

        userPayee.setId(2L);
        userPayee.setName("User payee");
        userPayee.setType(AccountType.SHOPKEEPER);
        userPayee.setBalance(BigDecimal.valueOf(100.99));

        BigDecimal initialPayeeBalance = userPayee.getBalance();
        BigDecimal initialPayerBalance = userPayer.getBalance();

        when(userService.getUserById(userPayer.getId())).thenReturn(userPayer);
        when(userService.getUserById(userPayee.getId())).thenReturn(userPayee);
        when(userService.saveUser(userPayer)).thenReturn(userPayer);
        when(userService.saveUser(userPayee)).thenReturn(userPayee);

        when(restTemplate.getForEntity(anyString(),eq(Map.class)))
                .thenReturn(
                        new ResponseEntity<>(Map.of("message","Autorizado"), HttpStatus.OK) );

        TransactionDTO result = transactionService.dotransaction(requestDTO);

        Assertions.assertThat(result.getNamePayee()).isEqualTo(userPayee.getName());
        Assertions.assertThat(result.getNamePayee()).isEqualTo("User payee");

        BigDecimal expectedPayeeBalance = initialPayeeBalance.add(requestDTO.getValue());
        BigDecimal expectedPayerBalance = initialPayerBalance.subtract(requestDTO.getValue());

        Assertions.assertThat(userPayee.getBalance()).isEqualTo(expectedPayeeBalance);
        Assertions.assertThat(userPayer.getBalance()).isEqualTo(expectedPayerBalance);

    }

    @Test
    void transactionNotAuthorizedAndThowBussinessException(){
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(1,2, BigDecimal.valueOf(350.00));

        User userPayer = UserServiceTest.buildUser();
        User userPayee = UserServiceTest.buildUser();

        userPayee.setId(2L);
        userPayee.setName("User payee");
        userPayee.setType(AccountType.SHOPKEEPER);
        userPayee.setBalance(BigDecimal.valueOf(100.99));

        when(userService.getUserById(userPayer.getId())).thenReturn(userPayer);
        when(userService.getUserById(userPayee.getId())).thenReturn(userPayee);
        when(restTemplate.getForEntity(anyString(),eq(Map.class)))
                .thenReturn(
                        new ResponseEntity<>(Map.of("message","Não Autorizado"), HttpStatus.BAD_REQUEST) );

        Assertions.assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> transactionService.dotransaction(requestDTO))
                .withMessage("Transaction Unauthorized");

    }

    @Test
    void transactionByAShopkeeperAndThowBussinessException(){
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(2,1, BigDecimal.valueOf(350.00));

        User userPayer = UserServiceTest.buildUser();
        User userPayee = UserServiceTest.buildUser();

        userPayer.setId(2L);
        userPayer.setName("User payer");
        userPayer.setType(AccountType.SHOPKEEPER);
        userPayer.setBalance(BigDecimal.valueOf(100.99));

        when(userService.getUserById(userPayer.getId())).thenReturn(userPayer);
        when(userService.getUserById(userPayee.getId())).thenReturn(userPayee);
        when(restTemplate.getForEntity(anyString(),eq(Map.class)))
                .thenReturn(
                        new ResponseEntity<>(Map.of("message","Autorizado"), HttpStatus.OK) );

        Assertions.assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> transactionService.dotransaction(requestDTO))
                .withMessage("Payer must be a Common User");

    }

    @Test
    void transactionWithouFoundsAndThowBussinessException(){
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(1,2, BigDecimal.valueOf(350.00));

        User userPayer = UserServiceTest.buildUser();
        User userPayee = UserServiceTest.buildUser();

        userPayer.setBalance( BigDecimal.valueOf(10.00));

        userPayee.setId(2L);
        userPayee.setName("User payer");
        userPayee.setType(AccountType.SHOPKEEPER);
        userPayee.setBalance(BigDecimal.valueOf(100.99));

        when(userService.getUserById(userPayer.getId())).thenReturn(userPayer);
        when(userService.getUserById(userPayee.getId())).thenReturn(userPayee);
        when(restTemplate.getForEntity(anyString(),eq(Map.class)))
                .thenReturn(
                        new ResponseEntity<>(Map.of("message","Autorizado"), HttpStatus.OK) );

        Assertions.assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> transactionService.dotransaction(requestDTO))
                .withMessage("The payer doesn't have enough funds for this transaction");
    }

}
