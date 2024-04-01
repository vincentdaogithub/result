package com.vincentdao.result.trace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Implements the operations for working with {@link Failure}'s own failed reasons.
 */
public abstract class BaseFailure implements Failure {

    protected final Collection<Failure> reasons;

    protected BaseFailure() {
        this.reasons = new ArrayList<>();
    }

    @Override
    public Collection<Failure> reasons() {
        return reasons;
    }

    @Override
    public void causedBy(Failure failure) {
        if (Objects.isNull(failure)) {
            throw new NullPointerException("Failure reason must be defined.");
        }
        reasons.add(failure);
    }

    @Override
    public void causedBy(Collection<Failure> failures) {
        if (Objects.isNull(failures)) {
            throw new NullPointerException("Failure list must be defined.");
        }
        if (failures.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Failure list contains null.");
        }
        reasons.addAll(failures);
    }
}
