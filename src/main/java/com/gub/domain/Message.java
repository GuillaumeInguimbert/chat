package com.gub.domain;

/**
 * Created by GUILLAUME.INGUIMBERT on 05/01/2017.
 */
public abstract class Message {

    protected MessageType type;

    protected String message;

    public String getMessage() {
        return message;
    }

    public MessageType getType() {
        return type;
    }
}
