package com.logan.socialnetwork.repository;

import com.logan.socialnetwork.model.ChatRooms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomsRepository extends JpaRepository<ChatRooms, Long> {
    ChatRooms findChatRoomsByChatRoomId(Long id);
    List<ChatRooms> findAllByChatRoomId(Long chatRoomId);
}
