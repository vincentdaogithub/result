package com.vincentdao.result;

public final class NoValue {

    @SuppressWarnings("InstantiationOfUtilityClass")
    private static final NoValue INSTANCE = new NoValue();

    private NoValue() {
    }

    static NoValue instance() {
        return INSTANCE;
    }
}
