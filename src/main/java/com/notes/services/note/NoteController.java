package com.notes.services.note;

import com.notes.core.ApplicationMessage;
import com.notes.core.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.util.MimeTypeUtils.*;

@RestController
@RequestMapping("/note")
public class NoteController extends BaseController {

    @Autowired
    private NoteService noteService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<NoteModel>> getAll() {

        Iterable<NoteModel> userNotes = noteService.findAllForCurrentUser(0, 100);

        return Ok(userNotes);
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

    @RequestMapping(value = "upload/file", method = RequestMethod.POST)
    public ResponseEntity upload(@RequestParam("file") MultipartFile uploadfile) {
        if (uploadfile.isEmpty()) { return Ok(); }

        try {
            noteService.saveUploadedFiles(Collections.singletonList(uploadfile));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return Ok(String.format("/api/v1/note/images/%s", uploadfile.getOriginalFilename()));
    }

    @RequestMapping(value="images/{fileName:.+}",
        method = RequestMethod.GET,
        produces = {IMAGE_GIF_VALUE, IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> getImageFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = noteService.loadFileAsResource(fileName);
        if(resource == null) { return NotFound(); }

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }
}
