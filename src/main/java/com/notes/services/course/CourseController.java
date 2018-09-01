package com.notes.services.course;

import com.notes.core.ApplicationMessage;
import com.notes.core.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CourseModel> get(@PathVariable("id") long id) {
        return Ok(courseService.find(id));
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity createForCurrentUser(@RequestBody final CourseModel creating) {

        CourseModel created = courseService.createForCurrentUser(creating);
        return created != null ?
            Ok(created) :
            Conflict(new ApplicationMessage("Not created", "Couldn't Create Deck."));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<CourseModel> update(@RequestBody final CourseModel updating) {
        return Ok(courseService.update(updating));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") long id) {
        courseService.delete(id);
        return Ok();
    }
}
