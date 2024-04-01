package com.vincentdao.result;

import com.vincentdao.result.trace.DefaultFailure;
import com.vincentdao.result.trace.DefaultSuccess;
import com.vincentdao.result.trace.ExceptionalFailure;
import com.vincentdao.result.trace.Failure;
import com.vincentdao.result.trace.Reason;
import com.vincentdao.result.trace.Success;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

final class ResultTest {

    @Test
    void givenResult_whenSuccessfulCreate_thenValid() {
        final Result<Integer> result = Result.<Integer>successful()
                .withValue(1);
        assertThat(result)
                .isNotNull();
        assertThat(result.isSuccessful())
                .isTrue();
        assertThat(result.isFailed())
                .isFalse();
        assertThat(result.value())
                .isEqualTo(1);
    }

    @Test
    void givenResult_whenSuccessfulCreateWithNullValue_thenThrowException() {
        final Result.AbstractSuccessfulResult<Integer> abstractSuccessfulResult = Result.successful();
        assertThatThrownBy(() -> abstractSuccessfulResult.withValue(null))
                .isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    void givenResult_whenSuccessfulCreateWithNoValue_thenValid() {
        final Result<NoValue> result = Result.successful().withNoValue();
        assertThat(result)
                .isNotNull();
        assertThat(result.isSuccessful())
                .isTrue();
        assertThat(result.isFailed())
                .isFalse();
        assertThatThrownBy(result::value)
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("cannot get value from no-value result.");
    }

    @Test
    void givenResult_whenFailedCreate_thenValid() {
        final Result<Integer> integerResult = Result.failed();
        assertThat(integerResult)
                .isNotNull();
        assertThat(integerResult.isSuccessful())
                .isFalse();
        assertThat(integerResult.isFailed())
                .isTrue();
        assertThatThrownBy(integerResult::value)
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("Cannot get value while in failed state.");
    }

    @Test
    void givenResult_whenAddFailureMessage_thenSuccessful() {
        final String message = "Failure message.";
        final Result<NoValue> result = Result.successful()
                .withNoValue()
                .withFailureMessage(message);
        assertThat(result)
                .isNotNull();
        assertThat(result.reasons())
                .hasSize(1)
                .hasOnlyElementsOfType(DefaultFailure.class)
                .allMatch(f -> Objects.equals(f.message(), message));
    }

    @Test
    void givenResult_whenAddFailure_thenSuccessful() {
        final String message = "Failure message.";
        final Failure failure = new DefaultFailure(message);
        final Result<NoValue> result = Result.successful()
                .withNoValue()
                .withFailure(failure);
        assertThat(result)
                .isNotNull();
        assertThat(result.reasons())
                .hasSize(1)
                .hasOnlyElementsOfType(DefaultFailure.class)
                .allMatch(f -> Objects.equals(f.message(), message));
    }

    @Test
    void givenResult_whenAddExceptionalFailure_thenSuccessful() {
        final String message = "Failure message.";
        final Exception exception = new Exception(message);
        final Result<NoValue> result = Result.successful()
                .withNoValue()
                .withExceptionalFailure(exception);
        assertThat(result)
                .isNotNull();
        assertThat(result.reasons())
                .hasSize(1)
                .hasOnlyElementsOfType(ExceptionalFailure.class)
                .allMatch(f -> Objects.equals(f.message(), message));
    }

    @Test
    void givenResult_whenAddFailures_thenSuccessful() {
        final String message = "Failure message ";
        final Collection<Failure> failures = new LinkedList<>();
        for (int i = 1; i <= 5; i++) {
            failures.add(new DefaultFailure(message + i));
        }
        final Result<NoValue> result = Result.successful()
                .withNoValue()
                .withFailures(failures);
        assertThat(result)
                .isNotNull();
        assertThat(result.reasons())
                .hasSize(5)
                .hasOnlyElementsOfType(DefaultFailure.class)
                .allMatch(f -> f.message().contains(message));
    }

    @Test
    void givenResult_whenAddSuccessMessage_thenSuccessful() {
        final String message = "Success message.";
        final Result<NoValue> result = Result.successful()
                .withNoValue()
                .withSuccessMessage(message);
        assertThat(result)
                .isNotNull();
        assertThat(result.reasons())
                .hasSize(1)
                .hasOnlyElementsOfType(DefaultSuccess.class)
                .allMatch(f -> Objects.equals(f.message(), message));
    }

    @Test
    void givenResult_whenAddSuccess_thenSuccessful() {
        final String message = "Success message.";
        final Success success = new DefaultSuccess(message);
        final Result<NoValue> result = Result.successful()
                .withNoValue()
                .withSuccess(success);
        assertThat(result)
                .isNotNull();
        assertThat(result.reasons())
                .hasSize(1)
                .hasOnlyElementsOfType(DefaultSuccess.class)
                .allMatch(f -> Objects.equals(f.message(), message));
    }

    @Test
    void givenResult_whenAddSuccesses_thenSuccessful() {
        final String message = "Success message ";
        final Collection<Success> successes = new LinkedList<>();
        for (int i = 1; i <= 5; i++) {
            successes.add(new DefaultSuccess(message + i));
        }
        final Result<NoValue> result = Result.successful()
                .withNoValue()
                .withSuccesses(successes);
        assertThat(result)
                .isNotNull();
        assertThat(result.reasons())
                .hasSize(5)
                .hasOnlyElementsOfType(DefaultSuccess.class)
                .allMatch(f -> f.message().contains(message));
    }

    @Test
    void givenResult_whenAddReason_thenSuccessful() {
        final String message = "Success message.";
        final Reason reason = () -> message;
        final Result<NoValue> result = Result.successful()
                .withNoValue()
                .withReason(reason);
        assertThat(result)
                .isNotNull();
        assertThat(result.reasons())
                .hasSize(1)
                .hasOnlyElementsOfType(Reason.class)
                .allMatch(f -> Objects.equals(f.message(), message));
    }

    @Test
    void givenResult_whenAddReasons_thenSuccessful() {
        final String message = "Success message ";
        final Collection<Reason> reasons = new LinkedList<>();
        for (int i = 1; i <= 5; i++) {
            int tmp = i;
            reasons.add(() -> message + tmp);
        }
        final Result<NoValue> result = Result.successful()
                .withNoValue()
                .withReasons(reasons);
        assertThat(result)
                .isNotNull();
        assertThat(result.reasons())
                .hasSize(5)
                .hasOnlyElementsOfType(Reason.class)
                .allMatch(f -> f.message().contains(message));
    }

    @Test
    void givenResult_whenGetFailures_thenSuccessful() {
        final Collection<Reason> reasons = ResultTestFactory.createReasons();
        final Result<NoValue> result = Result.successful()
                .withNoValue()
                .withReasons(reasons);
        final Collection<Failure> failures = result.failures();
        assertThat(failures)
                .hasSize(10)
                .hasOnlyElementsOfTypes(DefaultFailure.class, ExceptionalFailure.class);
    }

    @Test
    void givenResult_whenGetSuccesses_thenSuccessful() {
        final Collection<Reason> reasons = ResultTestFactory.createReasons();
        final Result<NoValue> result = Result.successful()
                .withNoValue()
                .withReasons(reasons);
        final Collection<Success> successes = result.successes();
        assertThat(successes)
                .hasSize(5)
                .hasOnlyElementsOfType(DefaultSuccess.class);
    }

    @Test
    void givenResult_whenGetReasonsFiltered_thenSuccessful() {
        final Collection<Reason> reasons = ResultTestFactory.createReasons();
        final Result<NoValue> result = Result.successful()
                .withNoValue()
                .withReasons(reasons);
        final Collection<Reason> filteredReasons = result
                .reasonsFiltered(reason -> reason instanceof ExceptionalFailure);
        assertThat(filteredReasons)
                .hasSize(5)
                .hasOnlyElementsOfType(ExceptionalFailure.class);
    }
}
