package com.journals.journalApp.service;

import com.journals.journalApp.entity.JournalEntry;
import com.journals.journalApp.entity.User;
import com.journals.journalApp.repository.JournalEntryRepository;
import com.journals.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();


    public void saveEntry(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }

    public List<User> getAllEntrys(){
        return userRepository.findAll();
    }


    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }


    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public void deleteByUserName(String userName){
        userRepository.deleteByUserName(userName);
    }

}
