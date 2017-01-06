package com.gub.repository;

import com.gub.model.User;
import com.gub.model.UserDashboard;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by GUILLAUME.INGUIMBERT on 04/01/2017.
 */
@Service
public class UserRepository {

    private Map<String, User> users = new ConcurrentHashMap<>();

    public void saveOrUpdate(StompHeaderAccessor message){
        String ip = String.valueOf(((Map) message.getMessageHeaders().get("simpSessionAttributes")).get("ip"));
        saveOrUpdate(new User(message.getLogin(), message.getSessionId(),ip));
    }


    public void saveOrUpdate(User user){
        users.put(user.getSession(), user);
    }

    public void remove(StompHeaderAccessor message){
        users.remove(message.getSessionId());
    }

    public boolean userLoginExists(StompHeaderAccessor message){
        return users.values().stream().anyMatch(user -> user.getLogin().equalsIgnoreCase(message.getLogin()));
    }

    public User findUserBySession(StompHeaderAccessor message){
        return users.get(message.getSessionId());
    }

    public String findLoginBySession(StompHeaderAccessor message){
        return Optional.ofNullable(findUserBySession(message)).get().getLogin();
    }

    public boolean exists(String session){
        return users.containsKey(session);
    }

    public int count(){
        return users.size();
    }

    public UserDashboard dashboard(){
        return new UserDashboard(count(), users.values().stream().collect(Collectors.toList()));
    }

}