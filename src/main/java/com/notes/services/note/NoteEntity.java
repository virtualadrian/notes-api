package com.notes.services.note;

import com.notes.core.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="note")
public class NoteEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name="account_id")
    private long accountId;

    @Column(name="note_title", length = 512)
    private String noteTitle;

    @Lob
    @Column(name="note_body")
    private String noteBody;

    @Column(name="note_created_ts")
    private LocalDateTime noteCreatedTime;
}
