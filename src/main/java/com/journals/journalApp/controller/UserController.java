package com.journals.journalApp.controller;

import com.journals.journalApp.entity.User;
import com.journals.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userIndb = userService.findByUserName(userName);

        userIndb.setUserName(user.getUserName());
        userIndb.setPassword(user.getPassword());
        userService.saveNewUser(userIndb);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserByuserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            String userName = authentication.getName();
            userService.deleteByUserName(userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    
    
}
