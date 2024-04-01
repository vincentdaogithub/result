package com.vincentdao.result.trace;

import java.util.Objects;

public class ExceptionalFailure extends BaseFailure {

    private final Exception exception;

    public ExceptionalFailure(Exception exception) {
        super();
        if (Objects.isNull(exception)) {
            throw new NullPointerException("Exception must be defined.");
        }
        this.exception = exception;
    }

    @Override
    public String message() {
        return exception.getMessage();
    }

    public Exception exception() {
        return exception;
    }
}
