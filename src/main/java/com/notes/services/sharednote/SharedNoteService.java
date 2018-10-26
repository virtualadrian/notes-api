package com.notes.services.sharednote;

import com.notes.core.BaseCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SharedNoteService extends BaseCrudService<SharedNoteModel, SharedNoteEntity, Long> {

    private final SharedNoteRepository sharedNoteRepository;

}
