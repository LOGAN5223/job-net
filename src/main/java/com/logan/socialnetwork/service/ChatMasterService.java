package com.logan.socialnetwork.service;

import com.logan.socialnetwork.model.Messages;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;

public interface ChatMasterService {
    ModelAndView findAllChatRooms(Authentication authentication);
    ModelAndView createOrFindChatWithUser(String userlogin, Authentication authentication);
    ModelAndView getChatRoomWithUser(Authentication authentication, String roomId);
    Messages addUser(
            String roomId,
            Messages chatMessage,
            SimpMessageHeaderAccessor headerAccessor, Authentication authentication);
    Messages sendMessage(Messages chatMessage, String roomId, Authentication authentication);
}
