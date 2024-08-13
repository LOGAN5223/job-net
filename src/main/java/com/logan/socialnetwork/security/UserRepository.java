package com.logan.socialnetwork.security;

import com.logan.socialnetwork.model.Users;
import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<Users, Long> {
    Users findByEmail(String email);
    Users findByUsername(String username);
}
