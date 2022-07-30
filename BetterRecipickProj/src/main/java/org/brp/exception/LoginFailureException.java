package org.brp.exception;

import java.sql.SQLInvalidAuthorizationSpecException;

public class LoginFailureException extends RuntimeException {
    private static final String MESSAGE = "email or password do not match.";
    public LoginFailureException () {
        super(MESSAGE);
    }
}
