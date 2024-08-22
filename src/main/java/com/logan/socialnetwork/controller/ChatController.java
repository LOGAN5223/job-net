package com.logan.socialnetwork.controller;

import com.logan.socialnetwork.model.Messages;
import com.logan.socialnetwork.service.ChatMasterService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RestController
@AllArgsConstructor
@Data
@RequestMapping("/api/v1/chats")
public class ChatController {
    private ChatMasterService chatMasterService;

    @GetMapping
    public ModelAndView findAllChatRooms(Authentication authentication){
        return chatMasterService.findAllChatRooms(authentication);
    }

    @PostMapping("/findTargetUser")
    public ModelAndView createOrFindChatWithUser(String userlogin, Authentication authentication) {
        return chatMasterService.createOrFindChatWithUser(userlogin, authentication);
    }

    @GetMapping("/getRoom/{roomId}")
    public ModelAndView getChatRoomWithUser(Authentication authentication, @PathVariable String roomId){
        return chatMasterService.getChatRoomWithUser(authentication, roomId);
    }

    @MessageMapping("/{roomId}/chat.addUser")
    public Messages addUser(
            @DestinationVariable String roomId,
            @Payload Messages chatMessage,
            SimpMessageHeaderAccessor headerAccessor, Authentication authentication) {
        return chatMasterService.addUser(roomId, chatMessage, headerAccessor, authentication);
    }

    @MessageMapping("/{roomId}/chat.sendMessage")
    public Messages sendMessage(@Payload Messages chatMessage, @DestinationVariable String roomId, Authentication authentication){
        return chatMasterService.sendMessage(chatMessage, roomId, authentication);
    }

}
