package com.journals.journalApp.service;

import com.journals.journalApp.entity.JournalEntry;
import com.journals.journalApp.entity.User;
import com.journals.journalApp.repository.JournalEntryRepository;
import com.journals.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public void saveEntry(User user){
        userRepository.save(user);
    }

    public List<User> getAllEntrys(){
        return userRepository.findAll();
    }


    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }


    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

}
