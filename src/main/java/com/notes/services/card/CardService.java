package com.notes.services.card;

import com.notes.core.BaseCrudService;
import com.notes.security.util.SecurityUtil;
import com.notes.services.note.NoteModel;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService extends BaseCrudService<CardModel, CardEntity, Long> {

    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository noteRepository) {
        super(CardModel.class, CardEntity.class);
        this.cardRepository = noteRepository;
    }

    public CardModel createForCurrentUser(CardModel creating) {
        creating.setAccountId(SecurityUtil.getCurrentUserAccountId());
        creating.setCreatedTime(LocalDateTime.now(ZoneOffset.UTC));
        return create(creating);
    }

    Iterable<CardModel> findAllForCurrentUser(int page, int pageSize) {
        CardModel search = new CardModel();
        search.setAccountId(SecurityUtil.getCurrentUserAccountId());
        return this.findall(search, page, pageSize);
    }
}
