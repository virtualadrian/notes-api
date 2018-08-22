package com.notes.services.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProfileModel {
    private Long id;
    private Long accountId;
    private String profileBiography;
    private String profileAvatar;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
}
