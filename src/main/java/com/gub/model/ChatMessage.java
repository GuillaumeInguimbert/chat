package com.gub.model;

/**
 * Created by GUILLAUME.INGUIMBERT on 03/01/2017.
 */
public class ChatMessage extends Message{

    private String user;

    public ChatMessage() {
    }

    public ChatMessage(String user, String message, String type) {
        this.user = user;
        this.message = message;
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
