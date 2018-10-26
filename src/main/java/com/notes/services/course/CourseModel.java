package com.notes.services.course;

import com.notes.core.BaseType;
import com.notes.core.Mapping;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Mapping(type = CourseEntity.class)
public class CourseModel extends BaseType {
    private Long id;
    private Long accountId;
    private String courseName;
    private String courseCode;
    private LocalDateTime createdTime;
}
