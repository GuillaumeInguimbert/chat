package com.gub.model;

/**
 * Created by GUILLAUME.INGUIMBERT on 05/01/2017.
 */
public abstract class Message {

    protected String type = "info";

    protected String message;

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }
}
