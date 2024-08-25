package com.journals.journalApp.repository;

import com.journals.journalApp.entity.JournalEntry;
import com.journals.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String userName);

}