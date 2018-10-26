package com.notes.services.card;

import com.notes.core.BaseCrudService;
import com.notes.security.util.SecurityUtil;
import com.notes.services.mail.MailService;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService extends BaseCrudService<CardModel, CardEntity, Long> {

    private final CardRepository cardRepository;
    private final MailService mailService;

    CardModel createForCurrentUser(CardModel creating) {
        creating.setAccountId(SecurityUtil.getCurrentUserAccountId());
        creating.setCreatedTime(LocalDateTime.now(ZoneOffset.UTC));
        return create(creating);
    }

    Iterable<CardModel> getAllForCurrent(int page, int pageSize) {
        CardModel search = new CardModel();
        search.setAccountId(SecurityUtil.getCurrentUserAccountId());
        return this.findall(search, page, pageSize);
    }

    Iterable<CardModel> getAllForCurrentUserDeck(Long deckId) {
        CardModel search = new CardModel();
        search.setDeckId(deckId);
        search.setAccountId(SecurityUtil.getCurrentUserAccountId());
        return this.findall(search);
    }
}
