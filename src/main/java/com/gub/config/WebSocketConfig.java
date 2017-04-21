package com.gub.config;

import com.gub.controller.ChatController;
import com.gub.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.Objects;

import static java.lang.String.*;

/**
 * Created by GUILLAUME.INGUIMBERT on 03/01/2017.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig  extends AbstractWebSocketMessageBrokerConfigurer {

    private final Log logger = LogFactory.getLog(WebSocketConfig.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatController chatController;

    /**
     * Interceptor used to handle incoming messages from WebSocket clients.
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {
            @Override
            public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

                StompHeaderAccessor stompMessage = StompHeaderAccessor.wrap(message);

                // ignore non-STOMP messages like heartbeat messages
                if(Objects.isNull(stompMessage.getCommand())) {
                    return;
                }

                switch(stompMessage.getCommand()) {
                    case CONNECT:
                        logger.info(format("Connect %s", printConnectionInfo(stompMessage)));
                        if(!userRepository.userLoginExists(stompMessage)){
                            userRepository.saveOrUpdate(stompMessage);
                            chatController.refreshUserDashboard();
                        }
                        else {
                            throw new IllegalArgumentException("Login already in use.");
                        }
                        break;
                    case CONNECTED:
                        logger.info(format("Connected %s", printConnectionInfo(stompMessage)));
                        break;
                    case DISCONNECT:
                        logger.info(format("Disconnect %s", printConnectionInfo(stompMessage)));
                        userRepository.remove(stompMessage);
                        chatController.refreshUserDashboard();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/gs-guide-websocket")
                .addInterceptors(new HandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
                        map.put("ip", serverHttpRequest.getRemoteAddress().getAddress().getHostAddress());
                        return true;
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

                    }
                })
                .withSockJS();
    }

    private String printConnectionInfo(StompHeaderAccessor msg){
        return format("[sessionId: %s, login: %s]", msg.getSessionId(), msg.getLogin());
    }

}
