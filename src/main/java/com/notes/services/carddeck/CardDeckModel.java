package com.notes.services.carddeck;

import com.notes.core.BaseType;
import com.notes.core.Mapping;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Mapping(type = CardDeckEntity.class)
public class CardDeckModel extends BaseType {
    private Long id;
    private Long accountId;
    private String courseId;
    private String deckName;
    private String deckDescription;
    private LocalDateTime createdTime;
}
