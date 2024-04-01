package com.vincentdao.result;

import com.vincentdao.result.trace.DefaultFailure;
import com.vincentdao.result.trace.DefaultSuccess;
import com.vincentdao.result.trace.ExceptionalFailure;
import com.vincentdao.result.trace.Failure;
import com.vincentdao.result.trace.Reason;
import com.vincentdao.result.trace.Success;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Result pattern inspired by <a href="https://github.com/altmann/FluentResults">FluentResults</a>.
 * <p>
 * This Result pattern is utilized to reduce the reliance on exceptions as a means of controlling code flow (i.e.,
 * using try-catch blocks).
 * <p>
 * The current implementation of {@code Result} includes 3 main operations:
 * <ul>
 * <li> Denotes the status of {@code Result} - either successful or failed.
 * <li> Holds the return value of the operation, if defined. For scenarios with no return value (i.e., when the
 *      operation returns void), the {@link NoValue} class is used to represent this situation. Attempting to retrieve
 *      the value from {@code Result<NoValue>} will throw an exception.
 * <li> Contains the reasons that lead to the {@code Result}'s current status. Can be either {@link Success} or
 *      {@link Failure}, with customization available for dynamic {@link Reason} representations.
 * </ul>
 * <p>
 * Special case: Adding a {@link Failure} as a reason to a successful {@code Result} converts it into a failed one.
 * <p>
 * Note: As {@code Result} is primarily used to represent the status of an operation, it should be implemented
 * on a per-operation basis only. This implies that {@code Result} is NOT thread-safe.
 *
 * @param <T> The type of value that {@code Result} holds.
 */
public final class Result<T> {

    public static final class AbstractSuccessfulResult<T> {

        private static final String NULL_VALUE_MESSAGE = "Value must be defined. Consider using Result<NoValue> and" +
                " method withNoValue() instead.";

        private AbstractSuccessfulResult() {
        }

        public Result<NoValue> withNoValue() {
            return new Result<>(true, NoValue.instance());
        }

        public Result<T> withValue(T value) {
            if (Objects.isNull(value)) {
                throw new NullPointerException(NULL_VALUE_MESSAGE);
            }
            return new Result<>(true, value);
        }
    }

    public static <T> AbstractSuccessfulResult<T> successful() {
        return new AbstractSuccessfulResult<>();
    }

    public static <T> Result<T> failed() {
        return new Result<>(false, null);
    }

    private boolean isSuccessful;
    private final T value;
    private final Collection<Reason> reasons;

    private Result(boolean isSuccessful, T value) {
        this.isSuccessful = isSuccessful;
        this.value = value;
        this.reasons = new ArrayList<>();
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public boolean isFailed() {
        return !isSuccessful;
    }

    public T value() {
        if (!isSuccessful) {
            throw new IllegalStateException("Cannot get value while in failed state.");
        }
        if (value instanceof NoValue) {
            throw new IllegalStateException("cannot get value from no-value result.");
        }
        return value;
    }

    public Result<T> withFailureMessage(String message) {
        reasons.add(new DefaultFailure(message));
        changeSuccessStatusWhenAddingFailure();
        return this;
    }

    public Result<T> withFailure(Failure failure) {
        if (Objects.isNull(failure)) {
            throw new NullPointerException("Failure trace must be defined.");
        }
        reasons.add(failure);
        changeSuccessStatusWhenAddingFailure();
        return this;
    }

    public Result<T> withFailures(Collection<Failure> failures) {
        if (Objects.isNull(failures)) {
            throw new NullPointerException("Failure list must be defined.");
        }
        if (failures.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Failure list contains null.");
        }
        reasons.addAll(failures);
        changeSuccessStatusWhenAddingFailure();
        return this;
    }

    public Result<T> withExceptionalFailure(Exception exception) {
        if (Objects.isNull(exception)) {
            throw new NullPointerException("Exceptional failure must be defined.");
        }
        reasons.add(new ExceptionalFailure(exception));
        changeSuccessStatusWhenAddingFailure();
        return this;
    }

    private void changeSuccessStatusWhenAddingFailure() {
        if (isSuccessful) {
            isSuccessful = false;
        }
    }

    public Result<T> withSuccessMessage(String message) {
        reasons.add(new DefaultSuccess(message));
        return this;
    }

    public Result<T> withSuccess(Success success) {
        if (Objects.isNull(success)) {
            throw new NullPointerException("Success must be defined.");
        }
        reasons.add(success);
        return this;
    }

    public Result<T> withSuccesses(Collection<Success> successes) {
        if (Objects.isNull(successes)) {
            throw new NullPointerException("Success list must be defined.");
        }
        if (successes.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Success list contains null.");
        }
        reasons.addAll(successes);
        return this;
    }

    public Result<T> withReason(Reason reason) {
        if (Objects.isNull(reason)) {
            throw new NullPointerException("Reason must be defined.");
        }
        reasons.add(reason);
        return this;
    }

    public Result<T> withReasons(Collection<Reason> reasons) {
        if (Objects.isNull(reasons)) {
            throw new NullPointerException("Reason list must be defined.");
        }
        if (reasons.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Reason list contains null.");
        }
        this.reasons.addAll(reasons);
        return this;
    }

    public Collection<Reason> reasons() {
        return new ArrayList<>(reasons);
    }

    public Collection<Reason> reasonsFiltered(Predicate<? super Reason> filter) {
        return reasons.stream().filter(filter).collect(Collectors.toList());
    }

    public Collection<Failure> failures() {
        return reasons.stream()
                .filter(Failure.class::isInstance)
                .map(Failure.class::cast)
                .collect(Collectors.toList());
    }

    public Collection<Success> successes() {
        return reasons.stream()
                .filter(Success.class::isInstance)
                .map(Success.class::cast)
                .collect(Collectors.toList());
    }
}
