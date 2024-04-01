package com.vincentdao.result.trace;

/**
 * An interface that represents a {@code Reason} within the {@link com.vincentdao.result.Result} framework.
 * <p>
 * In this implementation, there are two types of {@code Reason}: {@link Success} and {@link Failure}, each
 * denoting a successful or failed outcome, respectively.
 * <p>
 * While users can implement this interface for their custom {@code Reason} types, it is generally not recommended.
 * Instead, extending or implementing the {@link Success} and {@link Failure} interfaces is advised for
 * compatibility with the {@link com.vincentdao.result.Result} framework.
 */
public interface Reason {

    String message();
}
