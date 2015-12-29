package org.rs.app.meam.common;

/**
 *
 * @author ra2ldip
 */
public class MEAMException extends Exception {

    public static final String NO_USER_FOUND = "No user found";

    public MEAMException() {
    }

    public MEAMException(String message) {
        super(message);
    }

    public MEAMException(Throwable cause) {
        super(cause);
    }

}
