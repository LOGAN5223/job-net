package com.logan.socialnetwork.service.Impl;

import com.logan.socialnetwork.model.ChatMembers;
import com.logan.socialnetwork.model.ChatRooms;
import com.logan.socialnetwork.model.Messages;
import com.logan.socialnetwork.model.Profiles;
import com.logan.socialnetwork.repository.ChatMembersRepository;
import com.logan.socialnetwork.repository.ChatRoomsRepository;
import com.logan.socialnetwork.repository.MessagesRepository;
import com.logan.socialnetwork.repository.ProfileRepository;
import com.logan.socialnetwork.security.UserRepository;
import com.logan.socialnetwork.service.ChatMasterService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatMasterServiceImpl implements ChatMasterService {
    private ChatRoomsRepository chatRoomsRepository;
    private ChatMembersRepository chatMembersRepository;
    private UserRepository userRepository;
    private MessagesRepository messagesRepository;
    private ProfileRepository profileRepository;
    private SimpMessageSendingOperations messageSendingOperations;

    public ModelAndView findAllChatRooms(Authentication authentication, ModelAndView modelAndView, @ModelAttribute("errorDisplay") String errorDisplay){
        List<ChatRooms> userChatRooms =
                chatMembersRepository.findAllByUserlogin(authentication.getName()).stream()
                        .map(x -> chatRoomsRepository.findByChatRoomId(x.getChatRoomId()))
                        .collect(Collectors.toList());
        modelAndView.setViewName("chatList");
        modelAndView.addObject("profile", profileRepository.findByUserlogin(authentication.getName()));
        modelAndView.addObject("chatRooms", userChatRooms);
        modelAndView.addObject("errorDisplay", errorDisplay);
        return modelAndView;
    }

    public ModelAndView createOrFindChatWithUser(String targetUser, Authentication authentication, RedirectAttributes redirectAttributes) {
        ChatRooms chatRooms = chatRoomsRepository.findFirstByChatRoomIdOrChatRoomId(
                (targetUser + "-" + authentication.getName()),
                (authentication.getName() + "-" + targetUser));
        if (Optional.ofNullable(chatRooms).isEmpty() && Optional.ofNullable(userRepository.findByUsername(targetUser)).isPresent()) {

            chatRooms = new ChatRooms();
            chatRooms.setChatRoomName(targetUser + "-" + authentication.getName());
            chatRooms.setChatRoomId(targetUser + "-" + authentication.getName());

            chatRoomsRepository.save(chatRooms);
            chatMembersRepository.save(new ChatMembers(targetUser, targetUser + "-" + authentication.getName(), "CHAT"));
            chatMembersRepository.save(new ChatMembers(authentication.getName(), targetUser + "-" + authentication.getName(), "CHAT"));

            return new ModelAndView("redirect:/api/v1/chats/getRoom/" + chatRooms.getChatRoomId());
        } else if (Optional.ofNullable(chatRooms).isPresent()) {
            return new ModelAndView("redirect:/api/v1/chats/getRoom/" + chatRooms.getChatRoomId());
        } else if (Optional.ofNullable( userRepository.findByUsername(targetUser)).isEmpty()) {
            redirectAttributes.addAttribute("errorDisplay", "User doesn't exist!");

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/api/v1/chats");
            modelAndView.addObject("errorDisplay", "User doesn't exist!");

            return modelAndView;
        }
        return null;
    }

    public ModelAndView getChatRoomWithUser(Authentication authentication, String roomId){
        ChatRooms chatRoom = chatRoomsRepository.findFirstByChatRoomId(roomId);
        List<Messages> messageHandler = messagesRepository.findAllByChatRoomId(chatRoom.getChatRoomId());

        Profiles targetProfile = profileRepository.findByUserlogin(
                userRepository.findByUsername(
                        chatMembersRepository.findByChatRoomIdAndUserloginIsNotLike(
                                roomId, authentication.getName()).getUserlogin()).getUsername());

        Profiles currentProfile = profileRepository.findByUserlogin(authentication.getName());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("chatRoom");
        modelAndView.addObject("tProfile", targetProfile);
        modelAndView.addObject("cProfile", currentProfile);
        modelAndView.addObject("messageHandler", messageHandler);

        return modelAndView;
    }

    public Messages addUser(
            String roomId,
            Messages chatMessage,
            SimpMessageHeaderAccessor headerAccessor, Authentication authentication) {

        headerAccessor.getSessionAttributes().put("username", userRepository.findByUsername(authentication.getName()));

        chatMessage.setSender(authentication.getName());
        chatMessage.setChatRoomId(roomId);
        messageSendingOperations.convertAndSend("/queue/chat/" + roomId, chatMessage);
        return chatMessage;
    }

    public Messages sendMessage(Messages chatMessage, String roomId, Authentication authentication){
        chatMessage.setSender(authentication.getName());
        chatMessage.setChatRoomId(roomId);

        messagesRepository.save(chatMessage);
        messageSendingOperations.convertAndSend("/queue/chat/" + roomId, chatMessage);

        return chatMessage;
    }
}
