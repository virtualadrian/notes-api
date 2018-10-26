package com.notes.services.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.notes.core.BaseType;
import com.notes.core.Mapping;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Mapping(type = ProfileEntity.class)
public class ProfileModel extends BaseType {
    private Long id;
    private Long accountId;
    private String profileBiography;
    private String profileAvatar;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
}
