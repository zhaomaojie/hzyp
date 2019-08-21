package com.wzkj.hzyp.common.exception;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String code;
    private String message;

    public BaseException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    public BaseException(String code, String message, Throwable throwable, boolean enableSuppression, boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
        this.code = code;
        this.message = message;
    }

    public BaseException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
    }

    public BaseException(Throwable throwable) {
        super(throwable);
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
