package com.notes.services.note;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NoteModel {
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
