package com.notes.services.sharednote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedNoteRepository extends JpaRepository<SharedNoteEntity, Long> {

}
