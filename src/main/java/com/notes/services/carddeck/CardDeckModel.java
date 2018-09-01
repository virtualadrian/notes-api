package com.notes.services.carddeck;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CardDeckModel {
    private Long id;
    private Long accountId;
    private String courseId;
    private String deckName;
    private String deckDescription;
    private LocalDateTime createdTime;
}
