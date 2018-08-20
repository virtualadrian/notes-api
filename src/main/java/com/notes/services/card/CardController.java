package com.notes.services.card;

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
@RequestMapping("/card")
public class CardController extends BaseController {

    @Autowired
    private CardService cardService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CardModel> get(@PathVariable("id") long id) {
        return Ok(cardService.find(id));
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity createForCurrentUser(@RequestBody final CardModel creating) {

        CardModel created = cardService.createForCurrentUser(creating);
        return created != null ?
            Ok(created) :
            Conflict(new ApplicationMessage("Not created", "Couldn't Create Card."));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<CardModel> update(@RequestBody final CardModel updating) {
        return Ok(cardService.update(updating));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") long id) {
        cardService.delete(id);
        return Ok();
    }
}
