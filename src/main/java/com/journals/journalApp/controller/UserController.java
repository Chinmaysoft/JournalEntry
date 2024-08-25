package com.journals.journalApp.controller;

import com.journals.journalApp.entity.JournalEntry;
import com.journals.journalApp.entity.User;
import com.journals.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<User> all= userService.getAllEntrys();
        return new ResponseEntity<>(all,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        try{
            userService.saveEntry(user);
            return new ResponseEntity<>(user,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable String userName){
        User userIndb = userService.findByUserName(userName);
        if(userIndb!=null){
           userIndb.setUserName(user.getUserName());
           userIndb.setPassword(user.getPassword());
            userService.saveEntry(userIndb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    
    
}
