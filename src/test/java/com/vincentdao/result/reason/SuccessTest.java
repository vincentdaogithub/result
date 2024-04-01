package com.vincentdao.result.reason;

import com.vincentdao.result.trace.DefaultSuccess;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

final class SuccessTest {

    @Test
    void givenDefaultSuccess_whenCreatedWithNullMessage_thenInvalid() {
        assertThatThrownBy(() -> new DefaultSuccess(null))
                .isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    void givenDefaultSuccess_whenGetMessage_thenValid() {
        final String message = "Failure message.";
        final DefaultSuccess defaultSuccess = new DefaultSuccess(message);
        assertThat(defaultSuccess.message())
                .isEqualTo(message);
    }
}
