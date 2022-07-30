package org.brp.exception;

public class AlreadyExistEmailException extends RuntimeException{
    private static final String MESSAGE = "email already exists.";
    public AlreadyExistEmailException () {
        super(MESSAGE);
    }
}
