package com.notes.services.note;

import com.notes.core.BaseCrudService;
import com.notes.services.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NoteService extends BaseCrudService<NoteModel, NoteEntity, Long>  {

    @Autowired
    private AccountService accountService;

    public NoteService() {
        super(NoteModel.class, NoteEntity.class);
    }

    public NoteModel createForCurrentUser(NoteModel newNote) {

        newNote.setAccountId(accountService.getCurrentUserAccountId());
        newNote.setNoteCreatedTime(LocalDateTime.now());

        return create(newNote);
    }
}
