package com.vincentdao.result.reason;

import com.vincentdao.result.trace.DefaultFailure;
import com.vincentdao.result.trace.ExceptionalFailure;
import com.vincentdao.result.trace.Failure;
import java.util.Collection;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

final class FailureTest {

    @Test
    void givenFailure_whenAddCausedBy_thenSuccessful() {
        final String message = "Failure message.";
        final Failure failure = new DefaultFailure(message);
        final Failure failureToAdd = new DefaultFailure(message);
        final Failure exceptionFailureToAdd = new ExceptionalFailure(new Exception(message));
        assertThatCode(() -> failure.causedBy(failureToAdd))
                .doesNotThrowAnyException();
        assertThatCode(() -> failure.causedBy(exceptionFailureToAdd))
                .doesNotThrowAnyException();
        assertThat(failure.reasons())
                .hasSize(2)
                .hasOnlyElementsOfTypes(DefaultFailure.class, ExceptionalFailure.class);
    }

    @Test
    void givenFailure_whenAddMultipleCausedBy_thenSuccessful() {
        final String message = "Failure message.";
        final Failure failure = new DefaultFailure(message);
        final Collection<Failure> failures = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            failures.add(new DefaultFailure(message));
        }
        assertThatCode(() -> failure.causedBy(failures))
                .doesNotThrowAnyException();
        assertThat(failure.reasons())
                .hasSize(5)
                .hasOnlyElementsOfTypes(DefaultFailure.class);
    }

    @Test
    void givenDefaultFailure_whenCreatedWithNullMessage_thenInvalid() {
        assertThatThrownBy(() -> new DefaultFailure(null))
                .isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    void givenDefaultFailure_whenGetMessage_thenValid() {
        final String message = "Failure message.";
        final DefaultFailure defaultFailure = new DefaultFailure(message);
        assertThat(defaultFailure.message())
                .isEqualTo(message);
    }

    @Test
    void givenExceptionalFailure_whenCreatedWithNullException_thenInvalid() {
        assertThatThrownBy(() -> new ExceptionalFailure(null))
                .isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    void givenExceptionalFailure_whenGetMessage_thenValid() {
        final String message = "Failure message.";
        final Exception exception = new Exception(message);
        final ExceptionalFailure defaultFailure = new ExceptionalFailure(exception);
        assertThat(defaultFailure.message())
                .isEqualTo(message);
    }

    @Test
    void givenExceptionalFailure_whenGetException_thenValid() {
        final String message = "Failure message.";
        final Exception exception = new Exception(message);
        final ExceptionalFailure defaultFailure = new ExceptionalFailure(exception);
        assertThat(defaultFailure.exception())
                .isEqualTo(exception);
    }
}
