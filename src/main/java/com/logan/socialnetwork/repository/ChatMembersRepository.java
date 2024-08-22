package com.logan.socialnetwork.repository;

import com.logan.socialnetwork.model.ChatMembers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMembersRepository extends JpaRepository<ChatMembers, Long> {

    ChatMembers findByChatRoomIdAndUserloginIsNotLike(String id, String username);
    List<ChatMembers> findAllByUserlogin(String username);

}
