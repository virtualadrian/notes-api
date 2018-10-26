package com.notes.services.note;

import com.notes.core.BaseType;
import com.notes.core.Mapping;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Mapping(type = NoteEntity.class)
public class NoteModel extends BaseType {
    private Long id;
    private Long accountId;
    private String noteTitle;
    private String noteBody;
    private String noteTags;
    private Long favoriteIndex;
    private Long pinIndex;
    private Long noteOrderIndex;
    private Boolean isPrivate;
    private LocalDateTime archivedTime;
    private LocalDateTime deletedTime;
    private LocalDateTime createdTime;
    private Long clonedFromNoteId;
}
