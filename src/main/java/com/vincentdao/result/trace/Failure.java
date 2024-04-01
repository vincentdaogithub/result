package com.vincentdao.result.trace;

import java.util.Collection;

public interface Failure extends Reason {

    Collection<Failure> reasons();

    void causedBy(Failure failure);

    void causedBy(Collection<Failure> failures);
}
