package com.logan.socialnetwork.security;

import com.logan.socialnetwork.model.Users;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<Users, Long> {
    Users findByUsername(String username);
}
