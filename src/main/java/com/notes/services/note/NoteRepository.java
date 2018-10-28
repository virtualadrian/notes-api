package com.notes.services.note;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    Page<NoteEntity> findAllByAccountIdAndNoteBodyContainsOrNoteTitleContains(final Long accountId,
        final String searchTerm, final String searchTitle, Pageable pageable);

    @Query(value = "SELECT N.* FROM note N "
        + "WHERE N.deleted_ts IS NOT NULL AND N.account_id = :accountId "
        + "ORDER BY ?#{#pageable}",
        countQuery = "SELECT count(1) FROM note N WHERE N.account_id = :accountId",
        nativeQuery = true)
    Page<NoteEntity> findAllByAccountIdDeleted(@Param("accountId") final Long accountId,
        Pageable pageable);

    Page<NoteEntity> findAllByAccountIdAndArchivedTimeIsNullAndPinIndexIsNotNullOrderByNoteOrderIndexDesc(
        final Long accountId, Pageable pageable);

    Page<NoteEntity> findAllByAccountIdAndArchivedTimeIsNullAndFavoriteIndexIsNotNullOrderByNoteOrderIndexDesc(
        final Long accountId, Pageable pageable);

    Page<NoteEntity> findAllByAccountIdAndArchivedTimeIsNotNullOrderByNoteOrderIndexDesc(
        final Long accountId, Pageable pageable);

    Page<NoteEntity> findAllByAccountIdAndArchivedTimeIsNullOrderByNoteOrderIndexDesc(
        final Long accountId, Pageable pageable);

    NoteEntity findByIdAndIsPrivateIsTrueOrderByNoteOrderIndexDescPinIndexAscIdDesc(final Long Id);

    List<NoteEntity> findAllByAccountIdAndArchivedTimeIsNull(final Long accountId);

    List<NoteEntity> findAllByAccountIdAndNoteTagsContains(final Long accountId, final String tag);

    @Query(nativeQuery = true, value = "" +
        "SELECT N.* " +
        "FROM note N " +
        "WHERE account_id = :accountId " +
        "AND archived_ts IS NULL " +
        "AND MATCH(note_tags) AGAINST(:tagList IN BOOLEAN MODE);")
    List<NoteEntity> findAllForAccountWithTags(@Param("accountId") final Long accountId,
        @Param("tagList") final String tagList);
}
