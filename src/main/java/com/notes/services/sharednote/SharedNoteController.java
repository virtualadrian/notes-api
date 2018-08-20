package com.notes.services.sharednote;

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
@RequestMapping("/sharednote")
public class SharedNoteController extends BaseController {

    @Autowired
    private SharedNoteService noteService;

    @RequestMapping(value = "/{page}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<SharedNoteModel>> getAll(@PathVariable("page") int page,
        @PathVariable("pageSize") int pageSize) {
        Iterable<SharedNoteModel> userNotes = noteService.findall(page, pageSize);
        return Ok(userNotes);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<SharedNoteModel> get(@PathVariable("id") long id) {
        return Ok(noteService.find(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody final SharedNoteModel creating) {

        SharedNoteModel created = noteService.create(creating);

        return created != null ?
            Ok(created) :
            Conflict(new ApplicationMessage("Not created", "Couldn't Create Note."));

    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<SharedNoteModel> update(@RequestBody final SharedNoteModel updating) {
        return Ok(noteService.update(updating));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") long id) {
        noteService.delete(id);
        return Ok();
    }
}
