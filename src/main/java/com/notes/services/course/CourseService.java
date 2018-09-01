package com.notes.services.course;

import com.notes.core.BaseCrudService;
import com.notes.security.util.SecurityUtil;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService extends BaseCrudService<CourseModel, CourseEntity, Long> {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        super(CourseModel.class, CourseEntity.class);
        this.courseRepository = courseRepository;
    }

    public CourseModel createForCurrentUser(CourseModel creating) {
        creating.setAccountId(SecurityUtil.getCurrentUserAccountId());
        creating.setCreatedTime(LocalDateTime.now(ZoneOffset.UTC));
        return create(creating);
    }

    Iterable<CourseModel> findAllForCurrentUser(int page, int pageSize) {
        CourseModel search = new CourseModel();
        search.setAccountId(SecurityUtil.getCurrentUserAccountId());
        return this.findall(search, page, pageSize);
    }
}
