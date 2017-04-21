package com.gub.controller;

import com.gub.domain.ChatMessage;
import com.gub.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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


    private final Log logger = LogFactory.getLog(ChatController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessage chatMessage(String message, Message<?> messageObj) throws Exception {
        logger.debug("/chat/" + message);
        return new ChatMessage(userRepository.findLoginBySession(StompHeaderAccessor.wrap(messageObj)),"" + message + "", "info");
    }

    @MessageMapping("/events")
    @SendTo("/topic/events")
    public ChatMessage events(ChatMessage event) throws Exception {
        logger.debug("/events/" + event.getUser());
        refreshUserDashboard();
        return event;
    }

    public void refreshUserDashboard() {
        messagingTemplate.convertAndSend("/topic/dashboard", userRepository.buildDashboard());
    }
}
