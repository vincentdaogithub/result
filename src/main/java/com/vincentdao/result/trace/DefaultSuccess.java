package com.vincentdao.result.trace;

import java.util.Objects;

public final class DefaultSuccess implements Success {

    private final String message;

    public DefaultSuccess(String message) {
        if (Objects.isNull(message)) {
            throw new NullPointerException("Message must be defined.");
        }
        this.message = message.trim();
    }

    @Override
    public String message() {
        return message;
    }
}
