package com.finn.identity_service.controller;

import com.finn.identity_service.dto.request.UserCreationRequest;
import com.finn.identity_service.dto.response.UserResponse;
import com.finn.identity_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private UserCreationRequest request;
    private UserResponse userResponse;

    @BeforeEach
    void initData(){
        LocalDate dob = LocalDate.of(2000, 1, 1);

        request = UserCreationRequest.builder()
                .username("test")
                .password("12345678")
                .firstName("Test")
                .lastName("User")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("1fa3587d6084")
                .username("test")
                .firstName("Test")
                .lastName("User")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.createUser(ArgumentMatchers.any()))
                        .thenReturn(userResponse);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/users")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id")
                        .value("1fa3587d6084"));

    }

    @Test
    void createUser_invalidUsername_fail() throws Exception {
        // GIVEN
        request.setUsername("te");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value("5"))
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("Username must be at least 3 characters"));

    }

    @Test
    void createUser_invalidPassword_fail() throws Exception {
        request.setPassword("1234");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value("6"))
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("Password must be at least 8 characters"));
    }

    @Test
    void createUser_invalidDob_fail() throws Exception {
        request.setDob(LocalDate.of(2020, 1, 1));
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value("7"))
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("User must be at least 16 years old"));
    }

}
