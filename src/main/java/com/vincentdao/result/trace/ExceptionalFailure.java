package com.vincentdao.result.trace;

import java.util.Objects;

/**
 * An implementation of {@link Failure} that encapsulates a Java {@link Exception}.
 * <p>
 * This implementation provides an additional method for retrieving the encapsulated {@link Exception}, allowing for
 * detailed inspection of the underlying error. It enables easier access to exception details for debugging or logging
 * purposes.
 * <p>
 * The message retrieved by the <code>message()</code> method originates from the {@link Exception}'s
 * <code>getMessage()</code> method. The return value of <code>message()</code> may be {@code null} if no message was
 * provided when the {@link Exception} was created.
 */
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
