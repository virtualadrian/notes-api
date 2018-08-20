package com.notes.services.sharednote;

import com.notes.core.BaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SharedNoteService extends BaseCrudService<SharedNoteModel, SharedNoteEntity, Long> {

    private final SharedNoteRepository sharedNoteRepository;

    @Autowired
    public SharedNoteService(SharedNoteRepository noteRepository) {
        super(SharedNoteModel.class, SharedNoteEntity.class);
        this.sharedNoteRepository = noteRepository;
    }
}
