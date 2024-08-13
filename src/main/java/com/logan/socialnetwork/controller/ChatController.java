package com.logan.socialnetwork.controller;

import com.logan.socialnetwork.model.ChatRooms;
import com.logan.socialnetwork.model.Messages;
import com.logan.socialnetwork.repository.ChatMembersRepository;
import com.logan.socialnetwork.repository.ChatRoomsRepository;
import com.logan.socialnetwork.repository.MessagesRepository;
import com.logan.socialnetwork.repository.ProfileRepository;
import com.logan.socialnetwork.security.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/chats", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
public class ChatController {
    private ChatRoomsRepository chatRoomsRepository;
    private ChatMembersRepository chatMembersRepository;
    private UserRepository userRepository;
    private MessagesRepository messagesRepository;
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/list")
    public ModelAndView findAllChatRooms(String username){
        List<ChatRooms> userChatRooms = chatRoomsRepository.findAllByChatRoomId(chatMembersRepository.findAllByUsername(username));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("chatList");
        modelAndView.addObject("chatRooms", userChatRooms);
        return modelAndView;
    }

    @MessageMapping("/ws/{roomId}")
    public ModelAndView getChatRoomWithUser(@DestinationVariable @PathVariable String roomId,
                                            SimpMessageHeaderAccessor headerAccessor,
                                            Authentication authentication){
        ChatRooms chatRoom = chatRoomsRepository.findChatRoomsByChatRoomId(Long.valueOf(roomId));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("chatRoom");
        modelAndView.addObject("oUser", userRepository.findByUsername(chatMembersRepository.findByChatRoomIdAndUsernameIsNotLike(
                        roomId, authentication.getName()).getUsername()));

        modelAndView.addObject("messageHandler", messagesRepository.findAllByChatRoomId(
                String.valueOf(
                        chatRoom.getChatRoomId())));

        headerAccessor.getSessionAttributes().put("username", userRepository.findByUsername(authentication.getName()));
        return modelAndView;
    }

    @GetMapping
    public ModelAndView showIndex(Authentication authentication){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("chat");
        modelAndView.addObject("currentUser", userRepository.findByUsername(authentication.getName()));
        return modelAndView;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Messages sendMessage(@Payload Messages chatMessage){
        messagesRepository.save(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Messages addUser(
            @Payload Messages chatMessage,
            SimpMessageHeaderAccessor headerAccessor, Authentication authentication) {
        headerAccessor.getSessionAttributes().put("username", userRepository.findByUsername(authentication.getName()));
        return chatMessage;
    }
}
