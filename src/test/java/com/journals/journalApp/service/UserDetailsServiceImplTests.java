package com.journals.journalApp.service;

import com.journals.journalApp.entity.User;
import com.journals.journalApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;

@SpringBootTest
public class UserDetailsServiceImplTests {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

   /* @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }*/

    @ParameterizedTest
    @CsvSource({
            "ram",
            "Rohan",
            "soham",
            "hari",
            "Chinmay"
    })
    public void loadUserByUsernameTest(String testnames) {
        // Given: Mocking the userRepository behavior to return a User entity
        User mockUser = new User();
                mockUser.setUserName(testnames); // Assuming your custom User entity has this field
                mockUser.setPassword("qwertygcfd123dfvb"); // Password for mock user
                mockUser.setRoles(new ArrayList<>()); // Mock roles, assuming `roles` is a Set

        Mockito.when(userRepository.findByUserName(testnames)).thenReturn(mockUser);

        // When: Calling loadUserByUsername on userDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(testnames);

    }
}
