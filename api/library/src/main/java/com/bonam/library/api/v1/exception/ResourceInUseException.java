package com.bonam.library.api.v1.exception;

public class ResourceInUseException extends RuntimeException {
    public ResourceInUseException(String message) {
        super(message);
    }
}
