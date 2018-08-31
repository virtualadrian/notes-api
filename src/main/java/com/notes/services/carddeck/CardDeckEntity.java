package com.notes.services.carddeck;

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
@Table(name="card_deck")
public class CardDeckEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name="account_id")
    private Long accountId;

    @Column(name="course_id")
    private String courseId;

    @Column(name="deck_name")
    private String cardQuestion;

    @Column(name="deck_description")
    private String deckDescription;

    @Column(name="created_ts")
    private LocalDateTime createdTime;
}
