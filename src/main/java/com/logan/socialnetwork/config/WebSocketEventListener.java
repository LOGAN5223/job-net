package com.logan.socialnetwork.config;

import com.logan.socialnetwork.model.Messages;
import com.logan.socialnetwork.model.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageSendingOperations;

    @EventListener
    public void handleWebSocketDisconnectListener(
            SessionDisconnectEvent event
    ){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Users user = (Users) headerAccessor.getSessionAttributes().get("username");
        if (user != null){
            log.info("USER DISCONNECTED: {}", user);
            var chatMessage = Messages.builder()
                    .type(Messages.MessageType.LEAVER)
                    .sender(user.getUsername())
                    .build();
            messageSendingOperations.convertAndSend("/topic/public", chatMessage);
        }
    }
}
