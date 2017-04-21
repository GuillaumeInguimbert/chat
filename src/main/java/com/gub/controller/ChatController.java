package com.gub.controller;

import com.gub.domain.ChatMessage;
import com.gub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * Created by GUILLAUME.INGUIMBERT on 03/01/2017.
 */
@Controller
public class ChatController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessage chatMessage(String message, Message<?> messageObj) throws Exception {
        System.out.println("/chat/" + message);
        return new ChatMessage(userRepository.findLoginBySession(StompHeaderAccessor.wrap(messageObj)),"" + message + "", "info");
    }

    @MessageMapping("/events")
    @SendTo("/topic/events")
    public ChatMessage events(ChatMessage event) throws Exception {
        System.out.println("/events/" + event.getUser());
        nbUsers();
        return event;
    }

    public void nbUsers() {
        messagingTemplate.convertAndSend("/topic/nbusers", userRepository.dashboard());
    }
}
