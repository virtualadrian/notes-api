package com.notes.services.carddeck;

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
@RequestMapping("/carddeck")
public class CardDeckController extends BaseController {

    @Autowired
    private CardDeckService cardDeckService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CardDeckModel> get(@PathVariable("id") long id) {
        return Ok(cardDeckService.find(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<CardDeckModel>> getAllForCurrentUser() {
        Iterable<CardDeckModel> userDecks = cardDeckService.getAllForCurrentUser();
        return Ok(userDecks);
    }

    @RequestMapping(value = "/{page}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<CardDeckModel>> getAllForCurrentUserPaged(
        @PathVariable("page") int page,
        @PathVariable("pageSize") int pageSize) {
        Iterable<CardDeckModel> userDecks = cardDeckService
            .getAllForCurrentUserPaged(page, pageSize);
        return Ok(userDecks);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createForCurrentUser(@RequestBody final CardDeckModel creating) {
        CardDeckModel created = cardDeckService.createForCurrentUser(creating);
        return created != null ?
            Ok(created) :
            Conflict(new ApplicationMessage("Not created", "Couldn't Create Deck."));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<CardDeckModel> update(@RequestBody final CardDeckModel updating) {
        return Ok(cardDeckService.update(updating));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") long id) {
        cardDeckService.delete(id);
        return Ok();
    }
}
