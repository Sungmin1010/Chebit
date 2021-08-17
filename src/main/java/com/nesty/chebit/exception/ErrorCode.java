package com.nesty.chebit.exception;

public enum ErrorCode {

    RECORD_NOT_FOUND(1001, "기록을 찾을 수 없습니다."),
    RECORD_DATE_IS_NOT_VALID(1002, "기록일의 날짜가 유효하지 않습니다."),
    HABIT_NOT_FOUND(2001, "습관을 찾을 수 없습니다.");

    private int errorCode;
    private String errorMessage;

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    ErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
