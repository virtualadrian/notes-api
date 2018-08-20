package com.notes.services.sharednote;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SharedNoteModel {
    private Long id;
    private Long accountId;
    private String noteTitle;
    private String noteBody;
    private Boolean isPrivate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime noteCreatedTime;
}
