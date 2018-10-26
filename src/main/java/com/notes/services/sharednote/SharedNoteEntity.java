package com.notes.services.sharednote;

import com.notes.core.BaseType;
import com.notes.core.Mapping;
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
@Table(name="shared_note")
@Mapping(type = SharedNoteModel.class)
public class SharedNoteEntity extends BaseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name="note_id")
    private Long noteId;

    @Column(name="share_expires")
    private LocalDateTime shareExpires;

    @Column(name="note_created_ts")
    private LocalDateTime noteCreatedTime;
}
