package com.wzkj.hzyp.common;

import java.io.Serializable;

/* *
 *`ajax返回类型
 * @author zhaoMaoJie
 * @date 2019/7/21 0021
 */
public class AjaxResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;

    private String code;

    private Object data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public AjaxResponse(String code) {
        this.code = code;
    }

    public AjaxResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public AjaxResponse(String code, Object data) {
        this.code = code;
        this.data = data;
    }

    public AjaxResponse(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "AjaxReponse{" +
                "message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
