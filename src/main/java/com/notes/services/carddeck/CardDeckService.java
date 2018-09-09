package com.notes.services.carddeck;

import com.notes.core.BaseCrudService;
import com.notes.security.util.SecurityUtil;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardDeckService extends BaseCrudService<CardDeckModel, CardDeckEntity, Long> {

    private final CardDeckRepository cardDeckRepository;

    @Autowired
    public CardDeckService(CardDeckRepository cardDeckRepository) {
        super(CardDeckModel.class, CardDeckEntity.class);
        this.cardDeckRepository = cardDeckRepository;
    }

    CardDeckModel createForCurrentUser(CardDeckModel creating) {
        creating.setAccountId(SecurityUtil.getCurrentUserAccountId());
        creating.setCreatedTime(LocalDateTime.now(ZoneOffset.UTC));
        return create(creating);
    }

    Iterable<CardDeckModel> getAllForCurrentUserPaged(int page, int pageSize) {
        CardDeckModel search = new CardDeckModel();
        search.setAccountId(SecurityUtil.getCurrentUserAccountId());
        return this.findall(search, page, pageSize);
    }

    Iterable<CardDeckModel> getAllForCurrentUser() {
        CardDeckModel search = new CardDeckModel();
        search.setAccountId(SecurityUtil.getCurrentUserAccountId());
        return this.findall(search);
    }
}
