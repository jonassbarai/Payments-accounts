package com.jonas.PaymentAccounts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonas.PaymentAccounts.model.DTO.UserUpdateDTO;
import com.jonas.PaymentAccounts.model.User;
import com.jonas.PaymentAccounts.model.enums.AccountType;
import com.jonas.PaymentAccounts.model.enums.UserRole;
import com.jonas.PaymentAccounts.repository.UserRepository;
import com.jonas.PaymentAccounts.service.TokenService;
import com.jonas.PaymentAccounts.utils.configurations.SecurityFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
public class UserControllertest {

    private static final String URL = "/users";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;
    @BeforeEach
    public void setup(){
        userRepository.deleteAll();
    }

    @AfterEach
    public void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    public void shouldCreateUserAndReturnStatus201() throws Exception {
        User user = builderUser();
        user.setId(null);
        String userRequestBody =  mapper.writeValueAsString(user);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("fake user"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.CPForCNPJ").value("12345698789"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("fakeUser@Email.com"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void shouldNotCreateUserByEmptyNameAndReturnStatus400() throws Exception {
        User user = builderUser();
        user.setId(null);
        user.setName(null);
        String userRequestBody =  mapper.writeValueAsString(user);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userRequestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Validation Exception"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details.name").value("must not be blank"))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void shouldGetAllUserAndReturnStatus200() throws Exception {

        User user = builderUser();
        userRepository.save(user);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.user("user"))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void shouldGetUserByIdAndReturnStatus200() throws Exception {

        User user = builderUser();
        userRepository.save(user);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(URL +"/" + user.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.user("user"))
                                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("fake user"))
                .andDo(MockMvcResultHandlers.print());
    }
   @Test
    public void shouldUpdateUserAndReturnStatus200() throws Exception {

        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(1L,"fake name updated","fakeemail@update.com");
        String userUpdateJson = mapper.writeValueAsString(userUpdateDTO);

        User user = builderUser();
        userRepository.save(user);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put(URL +"/" + user.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userUpdateJson)
                                .with(SecurityMockMvcRequestPostProcessors.user("user"))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("fake name updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("fakeemail@update.com"))
                .andDo(MockMvcResultHandlers.print());
    }

    private User builderUser(){
        return new User(1L,
                "fake user",
                "12345698789",
                "fakeUser@Email.com",
                "123456",
                AccountType.SHOPKEEPER, BigDecimal.valueOf(100.00),
                UserRole.USER);
    }

}
