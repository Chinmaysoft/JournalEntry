package com.journals.journalApp.controller;

import com.journals.journalApp.entity.JournalEntry;
import com.journals.journalApp.entity.User;
import com.journals.journalApp.service.JournalEntryService;
import com.journals.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntryOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> all= user.getJournalEntrys();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myentry) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry(myentry,userName);
            return new ResponseEntity<>(myentry,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<?> getJornalEntryById(@PathVariable ObjectId myid){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user = userService.findByUserName(userName);
            List<JournalEntry> collect = user.getJournalEntrys().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());

            if (!collect.isEmpty()) {
                Optional<JournalEntry> journalEntry = journalEntryService.findById(myid);
                if (journalEntry.isPresent()) {
                    return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
                }
        }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean remove = journalEntryService.deleteById(myid, userName);
        if (remove) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @PutMapping("id/{myid}")
    public ResponseEntity<JournalEntry> updateJournalEntryById
            (@PathVariable ObjectId myid,
             @RequestBody JournalEntry newentry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntrys().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myid);
            if (journalEntry.isPresent()) {
                JournalEntry old = journalEntry.get();
                old.setTitle(newentry.getTitle() !=null && !newentry.getTitle().equals("") ? newentry.getTitle() : old.getTitle());
                old.setContent(newentry.getContent() !=null && !newentry.getContent().equals("") ? newentry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
