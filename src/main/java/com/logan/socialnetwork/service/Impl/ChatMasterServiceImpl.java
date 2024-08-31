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
import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Primary
public class ChatMasterServiceImpl implements ChatMasterService {
    @NonNull
    private ChatRoomsRepository chatRoomsRepository;
    @NonNull
    private ChatMembersRepository chatMembersRepository;
    @NonNull
    private UserRepository userRepository;
    @NonNull
    private MessagesRepository messagesRepository;
    @NonNull
    private ProfileRepository profileRepository;
    private SimpMessageSendingOperations messageSendingOperations;

    public ModelAndView findAllChatRooms(Authentication authentication, ModelAndView modelAndView, @ModelAttribute("errorDisplay") String errorDisplay){
        List<ChatRooms> userChatRooms =
                chatMembersRepository.findAllByUserlogin(authentication.getName()).stream().map(x -> chatRoomsRepository.findByChatRoomId(x.getChatRoomId())).collect(Collectors.toList());
        modelAndView.setViewName("chatList");
        modelAndView.addObject("profile", profileRepository.findByUserlogin(authentication.getName()));
        modelAndView.addObject("chatRooms", userChatRooms);
        modelAndView.addObject("errorDisplay", errorDisplay);
        return modelAndView;
    }

    public ModelAndView createOrFindChatWithUser(String target, Authentication authentication, RedirectAttributes redirectAttributes) {
        ChatRooms chatRooms = chatRoomsRepository.findFirstByChatRoomIdOrChatRoomId((target + "-" + authentication.getName()), (authentication.getName() + "-" + target));
        if (chatRooms == null && userRepository.findByUsername(target) != null){
            chatRooms = new ChatRooms();
            chatRooms.setChatRoomName(target + "-" + authentication.getName());
            chatRooms.setChatRoomId(target + "-" + authentication.getName());
            chatRoomsRepository.save(chatRooms);
            chatMembersRepository.save(new ChatMembers(target, target + "-" + authentication.getName(), "CHAT"));
            chatMembersRepository.save(new ChatMembers(authentication.getName(), target + "-" + authentication.getName(), "CHAT"));
            return new ModelAndView("redirect:/api/v1/chats/getRoom/" + chatRooms.getChatRoomId());
        } else if (chatRooms != null) {
            return new ModelAndView("redirect:/api/v1/chats/getRoom/" + chatRooms.getChatRoomId());
        }else if(userRepository.findByUsername(target) == null){
            redirectAttributes.addAttribute("errorDisplay", "User doesn't exist!" );
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/api/v1/chats");
            modelAndView.addObject("errorDisplay", "User doesn't exist!" );
            return modelAndView;
        }
        return null;
    }

    public ModelAndView getChatRoomWithUser(Authentication authentication, String roomId){
        ChatRooms chatRoom = chatRoomsRepository.findFirstByChatRoomId(roomId);
        List<Messages> messageHandler = messagesRepository.findAllByChatRoomId(chatRoom.getChatRoomId());
        Profiles targetProfile = profileRepository.findByUserlogin(userRepository.findByUsername(chatMembersRepository.findByChatRoomIdAndUserloginIsNotLike(
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
