package com.vincentdao.result;

import com.vincentdao.result.trace.DefaultFailure;
import com.vincentdao.result.trace.DefaultSuccess;
import com.vincentdao.result.trace.ExceptionalFailure;
import com.vincentdao.result.trace.Reason;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

final class ResultTestFactory {

    private ResultTestFactory(){
    }

    static Collection<Reason> createReasons() {
        final List<Reason> reasons = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            reasons.add(new DefaultFailure("Failure message " + i));
        }
        for (int i = 0; i < 5; i++) {
            reasons.add(new DefaultSuccess("Success message " + i));
        }
        for (int i = 0; i < 5; i++) {
            Exception exception = new Exception("Exception message " + i);
            reasons.add(new ExceptionalFailure(exception));
        }
        Collections.shuffle(reasons);
        return reasons;
    }
}
