package io.jsonsmith.exception;

public class PathParsingException extends RuntimeException {

    public PathParsingException(String message) {
        super(message);
    }

    public PathParsingException(String message, Throwable cause) {
        super(message, cause);
    }

}
