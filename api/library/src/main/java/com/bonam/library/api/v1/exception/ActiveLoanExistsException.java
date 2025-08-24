package com.bonam.library.api.v1.exception;

public class ActiveLoanExistsException extends RuntimeException {
    public ActiveLoanExistsException(String resource, String identifier) {
        super(String.format("%s already has an active loan with identifier: %s", resource, identifier));
    }
}
