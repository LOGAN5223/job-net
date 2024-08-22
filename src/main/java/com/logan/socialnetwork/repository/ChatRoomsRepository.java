package com.logan.socialnetwork.repository;

import com.logan.socialnetwork.model.ChatRooms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomsRepository extends JpaRepository<ChatRooms, Long> {
    ChatRooms findFirstByChatRoomId(String id);
    ChatRooms findFirstByChatRoomIdOrChatRoomId(String chatRoomId, String chatRoomId2);
    ChatRooms findByChatRoomId(String chatRoomId);
}
