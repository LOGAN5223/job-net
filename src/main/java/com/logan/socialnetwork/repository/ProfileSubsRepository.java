package com.logan.socialnetwork.repository;

import com.logan.socialnetwork.model.ProfileSubscribers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileSubsRepository extends JpaRepository<ProfileSubscribers, Long> {
        Optional<ProfileSubscribers> findByProfileLoginAndSubscriberLogin(String profileLogin, String subsLogin);
        List<ProfileSubscribers> findByProfileLogin(String profileLogin);
        List<ProfileSubscribers> findBySubscriberLogin(String sub);
}
