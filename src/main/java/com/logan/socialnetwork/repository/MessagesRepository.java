package com.logan.socialnetwork.repository;

import com.logan.socialnetwork.model.Messages;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessagesRepository extends MongoRepository<Messages, String> {
    List<Messages> findAllByChatRoomId(String id);
}
