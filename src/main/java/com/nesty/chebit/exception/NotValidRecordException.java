package com.nesty.chebit.exception;

public class NotValidRecordException extends RuntimeException{

    public final static int RECORD_NOT_FOUND = 1001;
    public final static int RECORD_DATE_IS_NOT_VALID = 1002;

    private final int code;

    public int getCode() {
        return code;
    }

    public NotValidRecordException(int code){
        this.code = code;
    }

    public NotValidRecordException(int code, String message){
        super(message);
        this.code = code;
    }

}
