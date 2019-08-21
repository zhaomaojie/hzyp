package com.wzkj.hzyp.common.exception;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public class TokenException extends BaseException{

    private static final long serialVersionUID = 3202124849738374214L;

    public TokenException() {
        super("1006");
    }

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String code, String message) {
        super(code, message);
    }

    public TokenException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public TokenException(Throwable throwable) {
        super(throwable);
    }
}
