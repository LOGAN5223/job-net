package com.logan.socialnetwork.repository;

import com.logan.socialnetwork.model.ChatMembers;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMembersRepository extends MongoRepository<ChatMembers, String> {

    ChatMembers findByChatRoomIdAndUsernameIsNotLike(String id, String username);
    Long findAllByUsername(String username);
}
