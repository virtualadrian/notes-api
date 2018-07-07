package com.notes.services.note;

import com.notes.core.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="note")
public class NoteEntity extends BaseEntity {

    @Id
    @Column(name="id")
    private long id;

    @Column(name="account_id")
    private long accountId;

    @Column(name="note_title")
    private String noteTitle;

    @Column(name="note_body")
    private String noteBody;

    @Column(name="note_created_ts")
    private LocalDateTime noteCreatedTime;
}
