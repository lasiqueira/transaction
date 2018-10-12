package com.lasiqueira.transaction.api.exception.v1;

public class ApiError {
    private String errorMsg;

    public ApiError(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
