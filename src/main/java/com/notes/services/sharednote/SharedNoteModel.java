package com.notes.services.sharednote;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.notes.core.BaseType;
import com.notes.core.Mapping;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Mapping(type = SharedNoteEntity.class)
public class SharedNoteModel extends BaseType {
    private Long id;
    private Long accountId;
    private String noteTitle;
    private String noteBody;
    private Boolean isPrivate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime noteCreatedTime;
}
