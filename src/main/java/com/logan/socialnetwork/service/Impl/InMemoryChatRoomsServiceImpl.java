package com.logan.socialnetwork.service.Impl;

import com.logan.socialnetwork.model.ChatRooms;
import com.logan.socialnetwork.repository.InMemoryChatRoomsDAO;
import com.logan.socialnetwork.service.ChatRoomsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InMemoryChatRoomsServiceImpl implements ChatRoomsService {
    private final InMemoryChatRoomsDAO inMemoryChatRoomsDAO;
    @Override
    public List<ChatRooms> findAllChatRooms() {
        return inMemoryChatRoomsDAO.findAllChatRooms();
    }
}
