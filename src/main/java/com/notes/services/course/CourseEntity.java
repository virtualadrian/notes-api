package com.notes.services.course;

import com.notes.core.BaseType;
import com.notes.core.Mapping;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="card_deck")
@Mapping(type = CourseModel.class)
public class CourseEntity extends BaseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name="account_id")
    private Long accountId;

    @Column(name="course_name")
    private String courseName;

    @Column(name="course_code")
    private String courseCode;

    @Column(name="created_ts")
    private LocalDateTime createdTime;
}
