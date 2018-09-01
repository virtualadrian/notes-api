package com.notes.services.carddeck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDeckRepository extends JpaRepository<CardDeckEntity, Long> {
}
