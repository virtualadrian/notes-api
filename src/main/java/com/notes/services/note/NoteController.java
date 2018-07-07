package com.notes.services.note;

import com.notes.core.ApplicationMessage;
import com.notes.core.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/note")
public class NoteController extends BaseController {

    @Autowired
    private NoteService noteService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<NoteModel>> getAll() {
        return Ok(noteService.findall(0, 100));
    }

    @RequestMapping(value = "/{page}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<NoteModel>> getAll(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        return Ok(noteService.findall(page, pageSize));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<NoteModel> get(@PathVariable("id") long id) {
        return Ok(noteService.find(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody final NoteModel creating) {

        NoteModel created = noteService.createForCurrentUser(creating);

        return created != null ?
            Ok(created) :
            Conflict(new ApplicationMessage("Not created",  "Couldn't Create Note."));

    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<NoteModel> update(@RequestBody final NoteModel updating) {
        return Ok(noteService.update(updating));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") long id) {
        noteService.delete(id);
        return Ok();
    }
}
