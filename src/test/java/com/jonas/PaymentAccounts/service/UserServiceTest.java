package com.jonas.PaymentAccounts.service;

import com.jonas.PaymentAccounts.exceptions.BusinessException;
import com.jonas.PaymentAccounts.model.User;
import com.jonas.PaymentAccounts.model.enums.AccountType;
import com.jonas.PaymentAccounts.model.enums.UserRole;
import com.jonas.PaymentAccounts.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void ShouldCreateUser(){
        var fakeUser = buildUser();

        when(userRepository.save(any())).thenReturn(fakeUser);

        var actualUser =  userService.saveUser(fakeUser);

        assertNotNull(actualUser);
        Assertions.assertThat(actualUser).isSameAs(fakeUser);
        verify(userRepository,times(1)).save(fakeUser);
    }

    @Test
    public void ShouldGetAllUsers(){
        var fakeUser1 = buildUser();
        var fakeUser2 = buildUser();
        fakeUser2.setId(2L);

        when(userRepository.findAll()).thenReturn(List.of(fakeUser1,fakeUser2));

        var listUsers =  userService.getAllUser();

        Assertions.assertThat(listUsers).isNotEmpty();
        Assertions.assertThat(listUsers).size().isEqualTo(2);
        Assertions.assertThat(listUsers.get(0)).isSameAs(fakeUser1);
        verify(userRepository,times(1)).findAll();
    }
    @Test
    public void ShouldGetUserById(){
        var fakeUser = buildUser();
        Long id = fakeUser.getId();
        when(userRepository.findById(id)).thenReturn(Optional.of(fakeUser));

        var actual =  userService.getUserById((id));

        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual).isSameAs(fakeUser);
        verify(userRepository,times(1)).findById(id);
    }

    @Test
    public void ShouldNOtGetUserByIdAndThowsEntityNotFoundException(){
        var fakeId = new Random().nextLong();
        when(userRepository.findById(fakeId)).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> userService.getUserById(fakeId))
                .withMessage("user doesn't exists");

        verify(userRepository,times(1)).findById(fakeId);

    }

    public static User buildUser(){
        Long id =1L;
        String name = "Fake User";
        String CPForCNPJ = "12345678999";
        String email = "email@test.com";
        String password = "123456";
        AccountType type = AccountType.COMMON;
        BigDecimal balance = BigDecimal.valueOf(1000.00);

        return new User(id,name,CPForCNPJ,email, password,type,balance, UserRole.USER);
    }
}
