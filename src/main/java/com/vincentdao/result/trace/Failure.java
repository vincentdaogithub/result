package com.vincentdao.result.trace;

import java.util.Collection;

/**
 * Represents a failed {@link Reason}.
 * <p>
 * Each {@code Failure} will also have its own set of reasons that lead to it, detailing the specific causes of the
 * failure.
 */
public interface Failure extends Reason {

    Collection<Failure> reasons();

    void causedBy(Failure failure);

    void causedBy(Collection<Failure> failures);
}
