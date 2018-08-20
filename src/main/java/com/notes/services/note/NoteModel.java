package com.notes.services.note;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteModel {
    private Long id;
    private Long accountId;
    private String noteTitle;
    private String noteBody;
    private String noteTags;
    private Boolean isPrivate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;
}
