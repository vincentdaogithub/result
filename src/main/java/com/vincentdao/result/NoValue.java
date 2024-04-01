package com.vincentdao.result;

/**
 * Dummy class to represent a no-value {@link Result}.
 * <p>
 * This class is utilized in situations where an operation returns a {@code Result} but does not actually return a
 * value (i.e., the operation would traditionally return {@code void}). It serves as a placeholder within the
 * {@code Result} framework to indicate the absence of a return value.
 */
public final class NoValue {

    @SuppressWarnings("InstantiationOfUtilityClass")
    private static final NoValue INSTANCE = new NoValue();

    private NoValue() {
    }

    static NoValue instance() {
        return INSTANCE;
    }
}
