package com.journals.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
public class User {

    @Id
    private ObjectId id;

    @NonNull
    @Indexed(unique = true)
    private String userName;

    @NonNull
    private String password;

    public User() {}

    @DBRef
    private List<JournalEntry> journalEntrys=new ArrayList<>();

    private List<String> roles;

}
