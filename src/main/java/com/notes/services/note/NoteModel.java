package com.notes.services.note;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import lombok.Data;

import java.time.LocalDateTime;

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
    private LocalDateTime createdTime;
    private Long clonedFromNoteId;
}
