package com.wzkj.hzyp.controller;

import com.wzkj.hzyp.entity.AuserInfo;
import com.wzkj.hzyp.entity.BuserInfo;
import com.wzkj.hzyp.service.AUserService;
import com.wzkj.hzyp.service.BuserService;
import com.wzkj.hzyp.utils.RSAUtil;
import com.wzkj.hzyp.utils.RedisOperator;
import com.wzkj.hzyp.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@RestController
public class BaseController  {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    public static final String USER_REDIS_SESSION = "user-redis-session";

    @Autowired
    public AUserService aUserService;

    @Autowired
    public BuserService buserService;

    @Autowired
    public RedisOperator redis;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    public AuserInfo getLoginUser() {
        String token = TokenUtils.getToken(request);
        String decryptToken = RSAUtil.getDecryptToken(token);
        String id = RSAUtil.getId(decryptToken);
        String sessionId = (String) request.getAttribute("userId");
        AuserInfo aUserInfoEntity = aUserService.getUserById(id);
        return aUserInfoEntity;
    }

    public BuserInfo getBuser(){
        String token = TokenUtils.getToken(request);
        String decryptToken = RSAUtil.getDecryptToken(token);
        String id = RSAUtil.getId(decryptToken);
//        String sessionId = (String) request.getAttribute("userId");
        BuserInfo buserInfo = buserService.getBuserById(id);
        return buserInfo;
    }
}
