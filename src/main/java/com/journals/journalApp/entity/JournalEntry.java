package com.journals.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "journals")
public class JournalEntry {

    @Id
    private ObjectId id;

    private String title;

    private String content;

    private LocalDateTime Date;

}
