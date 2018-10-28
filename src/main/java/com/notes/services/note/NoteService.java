package com.notes.services.note;

import com.notes.core.BaseCrudService;
import com.notes.security.util.SecurityUtil;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteService extends BaseCrudService<NoteModel, NoteEntity, Long> {

    private final NoteRepository noteRepository;

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

    Page<NoteModel> filterForCurrentUser(String term, int page, int pageSize) {
        Long accountId = SecurityUtil.getCurrentUserAccountId();
        switch (NoteFilter.valueOf(term)) {
            case ARCHIVED:
                return toPageModels(noteRepository
                    .findAllByAccountIdAndArchivedTimeIsNotNullOrderByNoteOrderIndexDesc(accountId,
                        PageRequest.of(page, pageSize)));
            case FAVORITES:
                Page<NoteEntity> entities = noteRepository
                    .findAllByAccountIdAndArchivedTimeIsNullAndFavoriteIndexIsNotNullOrderByNoteOrderIndexDesc(
                        accountId,
                        PageRequest.of(page, pageSize));
                return toPageModels(entities);
            case PINNED:
                return toPageModels(noteRepository
                    .findAllByAccountIdAndArchivedTimeIsNullAndPinIndexIsNotNullOrderByNoteOrderIndexDesc(
                        accountId,
                        PageRequest.of(page, pageSize)));
            case TRASH:
                return toPageModels(noteRepository
                    .findAllByAccountIdDeleted(accountId,
                        PageRequest.of(page, pageSize)));
            default:
                return toPageModels(noteRepository
                    .findAllByAccountIdAndArchivedTimeIsNullOrderByNoteOrderIndexDesc(accountId,
                        PageRequest.of(page, pageSize)));
        }
    }

    Page<NoteModel> findNonArchivedForCurrentUser(int page, int pageSize) {
        Long accountId = SecurityUtil.getCurrentUserAccountId();
        return toPageModels(noteRepository
            .findAllByAccountIdAndArchivedTimeIsNullOrderByNoteOrderIndexDesc(accountId,
                PageRequest.of(page, pageSize)));
    }

    NoteModel findSharedNote(final Long noteId) {
        return toModel(noteRepository
            .findByIdAndIsPrivateIsTrueOrderByNoteOrderIndexDescPinIndexAscIdDesc(noteId));
    }

    Page<NoteModel> findAllForCurrentUserByTerm(String term, int page, int pageSize) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<NoteEntity> noteEntityList = this.noteRepository
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

    List<String> findTagsForCurrentUser() {
        Long accountId = SecurityUtil.getCurrentUserAccountId();
        return noteRepository.findAllByAccountIdAndArchivedTimeIsNull(accountId).stream()
            .map(NoteEntity::getNoteTags)
            .map(tags -> tags.split(","))
            .flatMap(Arrays::stream)
            .sorted()
            .distinct()
            .collect(Collectors.toList());
    }

    List<NoteModel> findAllForAccountWithTags(List<String> tags) {
        Long accountId = SecurityUtil.getCurrentUserAccountId();
        StringJoiner tagJoiner = new StringJoiner(" +", "+", "");
        tags.forEach(tagJoiner::add);
        return toModels(noteRepository.findAllForAccountWithTags(accountId, tagJoiner.toString()));
    }
}
