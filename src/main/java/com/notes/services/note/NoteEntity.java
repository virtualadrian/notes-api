package com.notes.services.note;

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
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name="note")
@Where(clause="deleted_ts IS NULL")
@Mapping(type = NoteModel.class)
public class NoteEntity extends BaseType {

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

    @Column(name="favorite_index")
    private Long favoriteIndex;

    @Column(name="pin_index")
    private Long pinIndex;

    @Column(name="note_order_index")
    private Long noteOrderIndex;

    @Column(name="is_private")
    private Boolean isPrivate;

    @Column(name="archived_ts")
    private LocalDateTime archivedTime;

    @Column(name="deleted_ts")
    private LocalDateTime deletedTime;

    @Column(name="created_ts")
    private LocalDateTime createdTime;

    @Column(name="cloned_from_note_id")
    private Long clonedFromNoteId;

}
