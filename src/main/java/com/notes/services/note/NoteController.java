package com.notes.services.note;

import com.notes.core.ApplicationMessage;
import com.notes.core.BaseController;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController extends BaseController {

    private final NoteService noteService;

    @RequestMapping(value = "/public/{noteId}", method = RequestMethod.GET)
    public ResponseEntity<NoteModel> getPublicNote(@PathVariable("noteId") Long noteId) {
        return Ok(noteService.findSharedNote(noteId));
    }

    @RequestMapping(value = "/filter/{term}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<NoteModel>> getAll(@PathVariable final String term) {
        Iterable<NoteModel> userNotes =
            noteService.filterForCurrentUser(term, 0, 100);
        return Ok(userNotes);
    }

    @RequestMapping(value = "/{page}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<NoteModel>> getAll(@PathVariable("page") int page,
        @PathVariable("pageSize") int pageSize) {
        Iterable<NoteModel> userNotes = noteService.findNonArchivedForCurrentUser(page, pageSize);
        return Ok(userNotes);
    }

    @RequestMapping(value = "/search/{term}/{page}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<NoteModel>> search(@PathVariable("term") String term,
        @PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        Iterable<NoteModel> userNotes = noteService
            .findAllForCurrentUserByTerm(term, page, pageSize);
        return Ok(userNotes);
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
            Conflict(new ApplicationMessage("Not created", "Couldn't Create Note."));
    }

    @RequestMapping(value = "/duplicate", method = RequestMethod.POST)
    public ResponseEntity duplicate(@RequestBody final NoteModel from) {
        NoteModel clone = noteService.cloneNote(from);
        return clone != null ?
            Ok(clone) :
            Conflict(new ApplicationMessage("Not created", "Couldn't Clone Note."));
    }

    @RequestMapping(value = "/archive", method = RequestMethod.PUT)
    public ResponseEntity archive(@RequestBody final NoteModel archiving) {
        return Ok(noteService.archiveNote(archiving));
    }

    @RequestMapping(value = "/pin", method = RequestMethod.PUT)
    public ResponseEntity<NoteModel> createPinned(@RequestBody final NoteModel updating) {
        return Ok(noteService.createPinned(updating));
    }

    @RequestMapping(value = "/favorite", method = RequestMethod.PUT)
    public ResponseEntity<NoteModel> createFavorite(@RequestBody final NoteModel updating) {
        return Ok(noteService.createFavorite(updating));
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

    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    public ResponseEntity findTagsForCurrentUser() {
        return Ok(noteService.findTagsForCurrentUser());
    }

    @RequestMapping(value = "/tags/filter", method = RequestMethod.GET)
    public ResponseEntity findTagsForCurrentUser(
        @RequestParam(value = "tag") final List<String> tags) {
        List<NoteModel> notes = noteService.findAllForAccountWithTags(tags);
        return Ok(notes);
    }
}
