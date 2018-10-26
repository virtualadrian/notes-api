package com.notes.services.note;

import com.notes.core.BaseCrudService;
import com.notes.security.util.SecurityUtil;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

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
        newNote.setNoteOrderIndex(Instant.now().getEpochSecond());

        return create(newNote);
    }

    Iterable<NoteModel> findAllForCurrentUser(int page, int pageSize) {
        NoteModel search = new NoteModel();
        search.setAccountId(SecurityUtil.getCurrentUserAccountId());
        search.setArchivedTime(null);
        return this.findSortAll(search, page, pageSize,
            Direction.DESC, "noteOrderIndex", "id");
    }

    Iterable<NoteModel> filterForCurrentUser(String term, int page, int pageSize) {
        Long accountId = SecurityUtil.getCurrentUserAccountId();
        switch (NoteFilter.valueOf(term)) {
            case ARCHIVED:
                return toModels(noteRepository
                    .findAllByAccountIdAndArchivedTimeIsNotNullOrderByNoteOrderIndexDesc(accountId,
                        new PageRequest(page, pageSize)));
            case FAVORITES:
                Iterable<NoteEntity> entities = noteRepository
                    .findAllByAccountIdAndArchivedTimeIsNullAndFavoriteIndexIsNotNullOrderByNoteOrderIndexDesc(
                        accountId,
                        new PageRequest(page, pageSize));
                return toModels(entities);
            case PINNED:
                return toModels(noteRepository
                    .findAllByAccountIdAndArchivedTimeIsNullAndPinIndexIsNotNullOrderByNoteOrderIndexDesc(
                        accountId,
                        new PageRequest(page, pageSize)));
            case TRASH:
                return toModels(noteRepository
                    .findAllByAccountIdDeleted(accountId,
                        new PageRequest(page, pageSize)));
            default:
                return toModels(noteRepository
                    .findAllByAccountIdAndArchivedTimeIsNullOrderByNoteOrderIndexDesc(accountId,
                        new PageRequest(page, pageSize)));
        }
    }

    Iterable<NoteModel> findNonArchivedForCurrentUser(int page, int pageSize) {
        Long accountId = SecurityUtil.getCurrentUserAccountId();
        return toModels(noteRepository
            .findAllByAccountIdAndArchivedTimeIsNullOrderByNoteOrderIndexDesc(accountId,
                new PageRequest(page, pageSize)));
    }

    NoteModel findSharedNote(final Long noteId) {
        return toModel(noteRepository
            .findByIdAndIsPrivateIsTrueOrderByNoteOrderIndexDescPinIndexAscIdDesc(noteId));
    }

    Page<NoteModel> findAllForCurrentUserByTerm(String term, int page, int pageSize) {
        Pageable pageRequest = new PageRequest(page, pageSize);
        Iterable<NoteEntity> noteEntityList = this.noteRepository
            .findAllByAccountIdAndNoteBodyContainsOrNoteTitleContains(
                SecurityUtil.getCurrentUserAccountId(), term, term,
                pageRequest);
        return toPageModels(noteEntityList);
    }

    NoteModel createFavorite(NoteModel updating) {
        updating.setAccountId(SecurityUtil.getCurrentUserAccountId());

        updating.setFavoriteIndex(
            updating.getFavoriteIndex() == null ? Instant.now().getEpochSecond() : null);
        return update(updating);
    }

    NoteModel createPinned(NoteModel updating) {
        updating.setAccountId(SecurityUtil.getCurrentUserAccountId());
        updating
            .setPinIndex(updating.getPinIndex() == null ? Instant.now().getEpochSecond() : null);
        return update(updating);
    }

    NoteModel archiveNote(NoteModel updating) {
        updating.setAccountId(SecurityUtil.getCurrentUserAccountId());
        updating.setArchivedTime(LocalDateTime.now());
        return update(updating);
    }

    NoteModel cloneNote(NoteModel from) {
        from.setId(null);
        from.setNoteTitle(from.getNoteTitle() + " [copy]");
        from.setClonedFromNoteId(from.getId());
        from.setAccountId(SecurityUtil.getCurrentUserAccountId());
        from.setNoteOrderIndex(from.getNoteOrderIndex() - 1);
        return create(from);
    }
}
