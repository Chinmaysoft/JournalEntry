package com.journals.journalApp.service;

import com.journals.journalApp.entity.JournalEntry;
import com.journals.journalApp.entity.User;
import com.journals.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private  UserService userService;


    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try{
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry save = journalEntryRepository.save(journalEntry);
            user.getJournalEntrys().add(save);
            userService.saveUser(user);
        }catch(Exception e){
            throw new RuntimeException("Data not saved due to issue ",e);
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntrys(){
        return journalEntryRepository.findAll();
    }


    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }


    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean remove = false;
        try {
            User user = userService.findByUserName(userName);
            remove = user.getJournalEntrys().removeIf(x -> x.getId().equals(id));
            if(remove) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return remove;
    }

    public List<JournalEntry> findByUserName(String userName) {
        User user = userService.findByUserName(userName);
        return user.getJournalEntrys();
    }

}
