package com.notes.services.course;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CourseModel {
    private Long id;
    private Long accountId;
    private String courseName;
    private String courseCode;
    private LocalDateTime createdTime;
}
