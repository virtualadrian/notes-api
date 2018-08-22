package com.notes.services.note;

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
@Table(name="note")
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name="account_id")
    private Long accountId;

    @Column(name="note_title", length = 512)
    private String noteTitle;

    @Lob
    @Column(name="note_body")
    private String noteBody;

    @Lob
    @Column(name="note_tags")
    private String noteTags;

    @Column(name="is_private")
    private Boolean isPrivate;

    @Column(name="created_ts")
    private LocalDateTime createdTime;

    @Column(name="cloned_from_note_id")
    private Long clonedFromNoteId;

}
