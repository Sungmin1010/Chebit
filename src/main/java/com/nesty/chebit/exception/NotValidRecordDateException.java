package com.nesty.chebit.exception;

public class NotValidRecordDateException extends RuntimeException{
    public NotValidRecordDateException() {
        super();
    }

    public NotValidRecordDateException(String message) {
        super(message);
    }

    public NotValidRecordDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidRecordDateException(Throwable cause) {
        super(cause);
    }

    protected NotValidRecordDateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
