package com.notes.services.note;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    Page<NoteEntity> findAllByAccountIdAndNoteBodyContainsOrNoteTitleContains(final Long accountId,
        final String searchTerm, final String searchTitle, Pageable pageable);

    NoteEntity findByIdAndIsPrivateIsTrue(final Long id);
}
