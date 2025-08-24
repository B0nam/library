package com.bonam.library.api.v1.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ActiveLoanExistsExceptionTest {

    @Test
    void shouldFormatMessageCorrectly() {
        var resource = "Book";
        var identifier = "123";
        var exception = new ActiveLoanExistsException(resource, identifier);

        assertThat(exception)
                .hasMessage("Book already has an active loan");
    }
}
