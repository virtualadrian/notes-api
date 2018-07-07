package com.notes.services.note;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteModel {
    private long id;
    private long accountId;
    private String noteTitle;
    private String noteBody;
    private LocalDateTime noteCreatedTime;
}
