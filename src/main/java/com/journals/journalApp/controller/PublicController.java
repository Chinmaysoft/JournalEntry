package com.journals.journalApp.controller;

import com.journals.journalApp.entity.User;
import com.journals.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public String createUser(@RequestBody User user){
        try {
            userService.saveEntry(user);
            return "User created successfully.";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
