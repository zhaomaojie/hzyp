package com.wzkj.hzyp.common;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public interface ResponseCode {

    String API_TOKEN_INVALID = "40102";
    String APP_SUCCESS = "0";
    String APP_FAIL = "-1";
    String WZ_ERROR = "9999";
    String CONTROLLER_ERROR = "1001";
    String SERVICE_ERROR = "1002";
    String UTILS_ERROR = "1003";
    String PARAMS_ERROR = "1004";
    String LOGIN_ERROR = "1005";
    String TOKEN_ERROR = "1006";
    String APP_TOKEN_INVALID = "1007";
    String OPEN_TOKEN_ERROR = "1009";
    String OPEN_USER_ERROR = "1008";
    String OPEN_COIN_LIST_ERROR = "1010";
    String OPEN_COIN_PRICE_ERROR = "1011";
    String METHOD_NOT_SUPPORTED = "1012";
    String REPEAT_SUBMIT = "1013";
    String BLACK_USER = "1014";

}
