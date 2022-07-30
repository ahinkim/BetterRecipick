package org.brp.exception;

import java.sql.SQLInvalidAuthorizationSpecException;

public class MismatchedIPException extends RuntimeException {
    private static final String MESSAGE = "User IP do not match.";
    public MismatchedIPException () {
        super(MESSAGE);
    }
}
