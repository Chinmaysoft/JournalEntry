package com.journals.journalApp.service;

import com.journals.journalApp.entity.User;
import com.journals.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @CsvSource({
            "soham",
            "anuved",
            "mick",
            "Chinmay",
            "bhai"
    })
    public void testFindByUserName(String name){
        try{
            assertNotNull(userRepository.findByUserName(name),"User Not Found => "+ name);
        }
        catch (Exception e){
            fail("User not found");
        }
    }

    @Disabled
    @Test
    public void testDeleteByUserName(){
        String userName = "ram";
        userRepository.deleteByUserName(userName);
        assertNull(userRepository.findByUserName(userName),"User not deleted => "+userName);
    }


    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "5,9,14",
            "7,7,13",
            "1,87,90",
            "10,2,12",
    })
    public void testnumbers(int a , int b , int expected){
        assertEquals(expected, a+b);
    }


}
