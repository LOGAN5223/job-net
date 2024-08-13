package com.logan.socialnetwork.service.Impl;

import com.logan.socialnetwork.model.ChatRooms;
import com.logan.socialnetwork.repository.ChatRoomsRepository;
import com.logan.socialnetwork.service.ChatRoomsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class ChatRoomsServiceImpl implements ChatRoomsService {
    private final ChatRoomsRepository chatRoomsRepository;
    @Override
    public List<ChatRooms> findAllChatRooms() {
        return chatRoomsRepository.findAll();
    }
}
