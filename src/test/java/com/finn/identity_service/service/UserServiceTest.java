package com.finn.identity_service.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.finn.identity_service.dto.request.UserCreationRequest;
import com.finn.identity_service.dto.response.UserResponse;
import com.finn.identity_service.entity.User;
import com.finn.identity_service.exception.AppException;
import com.finn.identity_service.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;
    private LocalDate dob;

    @BeforeEach
    void initData(){
        dob = LocalDate.of(2000, 1, 1);

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

        user = User.builder()
                .id("1fa3587d6084")
                .username("test")
                .firstName("Test")
                .lastName("User")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success(){
        // GIVEN
        when(userRepository.existsByUsername(anyString()))
                .thenReturn(false);
        when(userRepository.save(any()))
                .thenReturn(user);

        // WHEN
        var response = userService.createUser(request);

        // THEN
        Assertions.assertEquals("1fa3587d6084", response.getId());
        Assertions.assertEquals("test", response.getUsername());
        Assertions.assertEquals(dob, response.getDob());
    }

    @Test
    void createUser_userExists_fail(){
        when(userRepository.existsByUsername(anyString()))
                .thenReturn(true);

        var exception = Assertions.assertThrows(AppException.class,
                () -> userService.createUser(request));

        Assertions.assertEquals(3, exception.getErrorCode().getCode());

    }

}
