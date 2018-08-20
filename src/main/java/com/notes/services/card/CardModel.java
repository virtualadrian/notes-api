package com.notes.services.card;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.persistence.Column;
import lombok.Data;

@Data
public class CardModel {
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
