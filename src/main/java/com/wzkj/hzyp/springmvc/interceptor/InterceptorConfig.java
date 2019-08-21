package com.wzkj.hzyp.springmvc.interceptor;

import com.alibaba.fastjson.JSON;
import com.wzkj.hzyp.common.AjaxResponse;
import com.wzkj.hzyp.common.ResponseCode;
import com.wzkj.hzyp.utils.RSAUtil;
import com.wzkj.hzyp.utils.StringUtils;
import com.wzkj.hzyp.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@Configuration
public class InterceptorConfig implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(InterceptorConfig.class);


    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        //log.info("---------------视图渲染之后的操作-------------------------");
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        //log.info("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }

    /**
     * 进入controller请求之前拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String uri = request.getRequestURI();
        //过滤error请求
        if(!"/error".equalsIgnoreCase(uri)){
            //判断是否为预处理请求
            if(request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.toString())){
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.OK.value());
                response.getWriter().write(StringUtils.EMPTY);
                return false;
            }else {
                log.info("请求地址：{},method：{}", request.getRequestURL(), request.getMethod());
                String token = TokenUtils.getToken(request);
                if(StringUtils.isBlank(token)){
                    log.error("token验证获取当前用户失败");
                    AjaxResponse result = new AjaxResponse(ResponseCode.APP_TOKEN_INVALID, "token信息为空");
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpStatus.OK.value());
                    response.getWriter().write(JSON.toJSONString(result));
                    return false;
                }else {
                    String decryptData = RSAUtil.getDecryptToken(token);
                    String id =  RSAUtil.getId(decryptData);
                    request.setAttribute("userId",id);
                    return true;
                }

            }
        }
        return true;
    }


}
