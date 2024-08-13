package com.logan.socialnetwork.repository;

import com.logan.socialnetwork.model.ChatRooms;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryChatRoomsDAO {
    private final List<ChatRooms> CHATROOMS = new ArrayList<>();

    public List<ChatRooms> findAllChatRooms(){
        return CHATROOMS;
    }
}
