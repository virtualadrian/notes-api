package com.notes.services.verification;

import com.notes.core.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verification")
public class VerificationController extends BaseController {

    private VerificationService verificationService;

    public VerificationController(VerificationService service)
    {
        this.verificationService = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<VerificationModel>> getAll() {
        return Ok(verificationService.findall(0, 1));
    }

    @RequestMapping(value = "/{page}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<VerificationModel>> getAll(
        @PathVariable("page") int page,
        @PathVariable("pageSize") int pageSize) {
        return Ok(verificationService.findall(page, pageSize));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<VerificationModel> get(@PathVariable("id") long id) {
        return Ok(verificationService.find(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<VerificationModel> create(@RequestBody final VerificationModel creating) {
        return Ok(verificationService.create(creating));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<VerificationModel> update(@RequestBody final VerificationModel updating) {
        return Ok(verificationService.update(updating));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") long id) {
        verificationService.delete(id);
        return Ok();
    }
}
