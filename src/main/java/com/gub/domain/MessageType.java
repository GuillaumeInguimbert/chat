package com.gub.domain;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by GUILLAUME.INGUIMBERT on 21/04/2017.
 */
public enum MessageType {

    INFO("info"),
    WARNING("warning"),
    ERROR("error");

    private String value;

    MessageType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
