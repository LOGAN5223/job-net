package com.logan.socialnetwork.repository;

import com.logan.socialnetwork.model.NewsFeeds;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NewsFeedRepository extends JpaRepository<NewsFeeds, Long> {

    NewsFeeds findByUserlogin(String userlogin);
}
