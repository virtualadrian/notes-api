package com.notes.services.card;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.notes.core.BaseType;
import com.notes.core.Mapping;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Mapping(type = CardEntity.class)
public class CardModel extends BaseType {
    private Long id;
    private Long accountId;
    private Long deckId;
    private String cardQuestion;
    private String cardAnswer;
    private String cardDifficultyId;
    private String cardRatingId;
    private Long originNoteId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;
}
