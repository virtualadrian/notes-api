package com.notes.security.repository;

import com.notes.security.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);

    @Override
    void delete(Role role);
}
