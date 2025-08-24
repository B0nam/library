package com.bonam.library.api.v1.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceInUseExceptionTest {

    @Test
    void shouldCreateExceptionWithCorrectMessage() {
        String errorMessage = "Resource is currently in use";

        var exception = new ResourceInUseException(errorMessage);

        assertThat(exception)
                .hasMessage(errorMessage);
        assertEquals(RuntimeException.class, exception.getClass().getSuperclass());
    }

    @Test
    void shouldCreateExceptionWithSpecificResourceMessage() {
        String resourceType = "Book";
        String identifier = "ISBN 123456789";
        String errorMessage = resourceType + " with identifier " + identifier + " is currently in use";

        var exception = new ResourceInUseException(errorMessage);

        assertThat(exception)
                .hasMessage(errorMessage);
    }
}
