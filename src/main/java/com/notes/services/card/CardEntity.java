package com.notes.services.card;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="card")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name="account_id")
    private Long accountId;

    @Column(name="deck_id")
    private Long deckId;

    @Column(name="card_question")
    private String cardQuestion;

    @Lob
    @Column(name="card_answer")
    private String cardAnswer;

    @Column(name="card_difficulty_id")
    private String cardDifficultyId;

    @Column(name="card_rating_id")
    private String cardRatingId;

    @Column(name="original_note_id")
    private Long originNoteId;

    @Column(name="created_ts")
    private LocalDateTime createdTime;
}
