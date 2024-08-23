package com.logan.socialnetwork.repository;

import com.logan.socialnetwork.model.ProfileContent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProfileContentRepository extends MongoRepository<ProfileContent, String> {
    List<ProfileContent> findByUserlogin(String userlogin);
}
