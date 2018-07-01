package com.notes.security.repository;

import com.notes.security.entity.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    @Override
    void delete(User user);
}
