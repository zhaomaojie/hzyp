package com.wzkj.hzyp.utils;

import com.alibaba.fastjson.JSONObject;
import com.wzkj.hzyp.common.exception.BaseException;
import com.wzkj.hzyp.common.ResponseCode;
import com.wzkj.hzyp.common.exception.TokenException;
import io.jsonwebtoken.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public class TokenUtils {

    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);

    public static final String USERID = "userId";
    public static final String OPENID = "openId";
    public static final String TOKEN_KEY = "authorization";
    public static final String CURRENT_USER_KEY = "dapp_qwsd_user";

    //应用生成token，前后缀
    private static String PREFIX = "dapp";
    private static String SECRET = "qwsd";

    /* *
     *  创建token
     * @author zhaoMaoJie
     * @date 2019/7/29 0029
     */
    public static String creatToken(String id, String subject, long ttlMillis) {
        //加密方式
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long currentTime = System.currentTimeMillis();
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(new Date(currentTime))  //签发时间
                .setSubject(subject)    //说明
                .signWith(SignatureAlgorithm.HS256, key); //加密方式
        if (ttlMillis >= 0) {
            long expMillis = currentTime + ttlMillis;
            Date date = new Date(currentTime);
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();

//        return Jwts.builder()
//                .setId(UUID.randomUUID().toString())
//                .setIssuedAt(new Date(currentTime))  //签发时间
//                .setSubject("system")  //说明
//                .setIssuer("shenniu003") //签发者信息
//                .setAudience("custom")  //接收用户
//                .compressWith(CompressionCodecs.GZIP)  //数据压缩方式
//
//                .signWith(SignatureAlgorithm.HS256, encryKey) //加密方式
//                .setExpiration(new Date(currentTime + secondTimeOut * 1000))  //过期时间戳
//                .addClaims(claimMaps) //cla信息
//                .compact();
    }

    /**
     * 获取加密Key
     */
    private static SecretKey generalKey() {
        String stringKey = PREFIX + SECRET;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }


    /**
     * cookie 中查找token
     */
    private static String getTokenFromCookie(HttpServletRequest request) {
        String token = null;
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (TOKEN_KEY.equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            log.warn("get token from cookie An error has occurred." + e.getMessage());
        }

        return token;
    }

    /**
     * heaser中查找token
     */
    private static String getTokenFromHeader(HttpServletRequest request) {
        String token = null;
        try {
            token = request.getHeader(TOKEN_KEY);
        } catch (Exception e) {
            log.warn("get token from header An error has occurred." + e.getMessage());
        }
        return token;
    }

    /**
     * 参数中查找token
     */
    private static String getTokenFromParameter(HttpServletRequest request) {
        String token = null;
        try {
            token = request.getParameter(TOKEN_KEY);
        } catch (Exception e) {
            log.warn("get token from parameter An error has occurred." + e.getMessage());
        }
        return token;
    }

    /**
     * 获取token
     *
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        String token = getTokenFromCookie(request);
        if (StringUtils.isBlank(token)) {
            token = getTokenFromHeader(request);
            if (StringUtils.isBlank(token)) {
                token = getTokenFromParameter(request);
            }
        }

        return token;
    }

    /**
     * 解析token
     */
    public static Claims parseToken(String token) throws BaseException {
        Claims claims = null;
        try {
            SecretKey key = generalKey();
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("无效的令牌!!!", e);
            throw new TokenException(ResponseCode.TOKEN_ERROR,"无效的令牌");
        }
        return claims;
    }

    /**
     * 生成subject信息
     * @param map
     * @return
     */
    public static String generateSubject(Map<String, Object> map) {
        String jsonString = JSONObject.toJSONString(map);
        return jsonString;
    }
    /**
     * 解析subject信息
     * @param subject
     * @return Map<String, Object>
     */
    public static Map<String, Object> conversionSubject(String subject) {
        Map<String, Object> res = new HashMap<>();
        JSONObject parseObject = JSONObject.parseObject(subject);
        parseObject.forEach((key, value) -> {
            res.put(key, (Object) value);
        });
        return res;
    }

    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(generalKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }











}
