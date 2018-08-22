package com.notes.services.note;

import com.notes.core.BaseCrudService;
import com.notes.security.util.SecurityUtil;
import java.time.ZoneOffset;
import java.util.List;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class NoteService extends BaseCrudService<NoteModel, NoteEntity, Long> {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        super(NoteModel.class, NoteEntity.class);
        this.noteRepository = noteRepository;
    }

    NoteModel createForCurrentUser(NoteModel newNote) {
        newNote.setAccountId(SecurityUtil.getCurrentUserAccountId());
        newNote.setCreatedTime(LocalDateTime.now(ZoneOffset.UTC));
        return create(newNote);
    }

    Iterable<NoteModel> findAllForCurrentUser(int page, int pageSize) {
        NoteModel search = new NoteModel();
        search.setAccountId(SecurityUtil.getCurrentUserAccountId());
        return this.findall(search, page, pageSize);
    }

    NoteModel findSharedNote(final Long noteId) {
        return toModel(noteRepository.findByIdAndIsPrivateIsTrue(noteId));
    }

    Page<NoteModel> findAllForCurrentUserByTerm(String term, int page, int pageSize) {
        Pageable pageRequest = new PageRequest(page, pageSize);
        Iterable<NoteEntity> noteEntityList = this.noteRepository
            .findAllByAccountIdAndNoteBodyContainsOrNoteTitleContains(
                SecurityUtil.getCurrentUserAccountId(), term, term,
                pageRequest);
        return toPageModels(noteEntityList);
    }

    NoteModel cloneNote(NoteModel from) {
        from.setClonedFromNoteId(from.getId());
        from.setAccountId(SecurityUtil.getCurrentUserAccountId());
        from.setId(null);
        return create(from);
    }
}
