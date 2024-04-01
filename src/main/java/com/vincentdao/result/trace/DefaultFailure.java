package com.vincentdao.result.trace;

import java.util.Objects;

/**
 * Default implementation of {@link Failure}.
 */
public final class DefaultFailure extends BaseFailure {

    private final String message;

    public DefaultFailure(String message) {
        super();
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
