package com.jonas.PaymentAccounts.repository;

import com.jonas.PaymentAccounts.model.User;
import com.jonas.PaymentAccounts.model.enums.AccountType;
import com.jonas.PaymentAccounts.model.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Mock
    private UserRepository repository;

    @BeforeEach
    public void setup() {
        User user = new User(
          1L,
          "usuario teste",
          "12345678987",
          "email@Test.com",
          "senha123",
          AccountType.COMMON,
          BigDecimal.valueOf(1000.00),
          UserRole.USER
        );

        when(repository.findByEmail("email@Test.com")).thenReturn(user);
    }
    @Test
    public void shouldFindUserByEmail(){
      var user = repository.findByEmail("email@Test.com");

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("usuario teste", user.getName());
    }

    @Test
    public void shouldNotFindUserByEmail(){
        var user = repository.findByEmail("wrong@Email.com");
        assertNull(user);
    }
}
