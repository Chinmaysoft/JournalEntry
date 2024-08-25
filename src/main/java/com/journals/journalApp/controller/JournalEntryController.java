package com.journals.journalApp.controller;

import com.journals.journalApp.entity.JournalEntry;
import com.journals.journalApp.entity.User;
import com.journals.journalApp.service.JournalEntryService;
import com.journals.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntryOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntry> all= user.getJournalEntrys();
        return new ResponseEntity<>(all,HttpStatus.OK);
    }

    @PostMapping("{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myentry,@PathVariable String userName) {
        try{
            journalEntryService.saveEntry(myentry,userName);
            return new ResponseEntity<>(myentry,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<?> getJornalEntryById(@PathVariable ObjectId myid){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myid);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{userName}/{myid}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myid,@PathVariable String userName){
        journalEntryService.deleteById(myid,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{userName}/{myid}")
    public ResponseEntity<JournalEntry> updateJournalEntryById
            (@PathVariable ObjectId myid,
             @RequestBody JournalEntry newentry,
             @PathVariable String userName) {
        JournalEntry old = journalEntryService.findById(myid).orElse(null);
        if(old!=null){
            old.setTitle(newentry.getTitle() !=null && !newentry.getTitle().equals("") ? newentry.getTitle() : old.getTitle());
            old.setContent(newentry.getContent() !=null && !newentry.getContent().equals("") ? newentry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
