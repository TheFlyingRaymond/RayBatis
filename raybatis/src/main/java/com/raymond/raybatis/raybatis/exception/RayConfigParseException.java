package com.raymond.raybatis.raybatis.exception;

public class RayConfigParseException extends RuntimeException{
    public RayConfigParseException() {
    }

    public RayConfigParseException(String message) {
        super(message);
    }

    public RayConfigParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public RayConfigParseException(Throwable cause) {
        super(cause);
    }

    public RayConfigParseException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
