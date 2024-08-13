package com.logan.socialnetwork.repository;

import com.logan.socialnetwork.model.Profiles;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profiles, String> {
    Profiles findByUserlogin(String userlogin);
    Profiles findByUserName(String username);
}
