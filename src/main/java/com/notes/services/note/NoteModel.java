package com.notes.services.note;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteModel {
    private long id;
    private long accountId;
    private String noteTitle;
    private String noteBody;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime noteCreatedTime;
}
