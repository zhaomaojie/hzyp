package com.wzkj.hzyp.controller;

import com.alibaba.fastjson.JSONObject;
import com.wzkj.hzyp.common.AjaxResponse;
import com.wzkj.hzyp.common.ResponseCode;
import com.wzkj.hzyp.entity.AuserInfo;
import com.wzkj.hzyp.entity.BuserInfo;
import com.wzkj.hzyp.service.AUserService;
import com.wzkj.hzyp.service.AliyunSMSService;
import com.wzkj.hzyp.service.BuserService;
import com.wzkj.hzyp.utils.*;
import com.wzkj.hzyp.vo.AUserVO;
import com.wzkj.hzyp.vo.BuserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Date;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@RequestMapping("/login")
@RestController
@Api(value = "用户登录注册的接口",tags = "用户登录注册的controller")
public class LoginController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AUserService aUserService;

    @Autowired
    private BuserService buserService;

    @Autowired
    private AliyunSMSService aliyunSMSService;

    @Autowired
    private ImageConfig imageConfig;


    @Value("${project.weixin.mp_app_id}")
    private String appid;

    @Value("${project.weixin.mp_app_secret}")
    private String appsecret;



    /* *
     * 微信第一次进入系统授权接口
     * @author zhaoMaoJie
     * @date 2019/8/15 0015
     */
    @RequestMapping(value = "/wxLogin",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse wxLogin(String code){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + appsecret + "&js_code=" + code + "&grant_type=authorization_code";
        if (StringUtils.isEmpty(code)) {
            return new AjaxResponse(ResponseCode.APP_FAIL, "code不能为空");
        }
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取解密结果 openid session_key
        JSONObject jsonObject = (JSONObject) JSONObject.parse(resultString);
        String session_key = jsonObject.get("session_key").toString();
        String openId = jsonObject.getString("openid");
        System.out.println(openId+"\t");
        AuserInfo auserInfo = aUserService.getAuserInfoByOpenId(openId);
        //未注册的情况下 生成用户 返回token(第一次进入)
        if(auserInfo == null){
            AuserInfo newAuserInfo = new AuserInfo();
            newAuserInfo.setCreateTime(new Date());
            newAuserInfo.setOpenId(openId);
            newAuserInfo.setLastLoginTime(new Date());
            aUserService.saveUser(newAuserInfo);
            String newId = newAuserInfo.getId();
            String token = RSAUtil.getToken(newId);
            AUserVO aUserVO = new AUserVO();
            BeanUtils.copyProperties(newAuserInfo, aUserVO);
            aUserVO.setToken(token);
            aUserVO.setIsLogin(0);
            aUserVO.setIsRegister(-1);
            redis.set(USER_REDIS_SESSION + ":" + openId,session_key);
            return new AjaxResponse(ResponseCode.APP_SUCCESS,aUserVO);
        }else {
            //已注册的情况下 根据openid获取到用户数据后 更新session_key 返回到前端
            auserInfo.setLastLoginTime(new Date());
            String id = auserInfo.getId();
            String token = RSAUtil.getToken(id);
            AUserVO aUserVO = new AUserVO();
            BeanUtils.copyProperties(auserInfo, aUserVO);
            aUserVO.setToken(token);
            String phone = auserInfo.getEmpowerPhone();
            String name = auserInfo.getName();
            Integer age = auserInfo.getAge();
            if(phone == null || phone.equals("") || StringUtils.isBlank(name) || age == null){
                aUserVO.setIsRegister(-1);
            }else {
                aUserVO.setIsRegister(0);
            }
            aUserVO.setIsLogin(0);
            redis.set(USER_REDIS_SESSION + ":" + auserInfo.getOpenId(),session_key);
            return new AjaxResponse(ResponseCode.APP_SUCCESS,aUserVO);
        }
    }

    /* *
     * 获取用户手机号
     * @author zhaoMaoJie
     * @date 2019/8/15 0015
     */
    @RequestMapping(value = "/getPhoneNumber",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "code",value = "微信获取到的数据",paramType = "query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "encryptedData",value = "微信获取到的数据",paramType = "query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "iv",value = "微信获取到的数据",paramType = "query",required = true,dataType = "string")})
    @ApiOperation(value = "获取用户电话",notes = "用于系统登录")
    private AjaxResponse getPhoneNumber(String encryptedData, String iv) throws Exception {
        if (StringUtils.isEmpty(encryptedData)) {
            return new AjaxResponse(ResponseCode.APP_FAIL, "encryptedData不能为空");
        }
        if (StringUtils.isEmpty(iv)) {
            return new AjaxResponse(ResponseCode.APP_FAIL, "iv不能为空");
        }
        //获取解密结果 openid session_key
        AuserInfo auserInfo = getLoginUser();
        String openId = auserInfo.getOpenId();
        String session_key = redis.get(USER_REDIS_SESSION + ":" + openId);
        String result = AesUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
        // 解析含有电话信息的json
        JSONObject json = (JSONObject) JSONObject.parse(result);
        String phone = json.getString("phoneNumber");

        //如果手机号为空
        if (StringUtils.isEmpty(phone)) {
            return new AjaxResponse(ResponseCode.APP_FAIL, "用户未绑定手机号");
        } else {
            return new AjaxResponse(ResponseCode.APP_SUCCESS,"获取成功",phone);
        }
    }

    /* *
     * 用于使用微信 快捷登录
     * @author zhaoMaoJie
     * @date 2019/8/15 0015
     */
    @RequestMapping(value = "/fastLogin",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "phoneNumeber",value = "手机号码",paramType = "query",required = true,dataType = "string")})
    @ApiOperation(value = "使用手机号登录",notes = "用于系统登录，,该方法暂时废弃 使用手机号均需要验证码")
    public AjaxResponse fastLogin(String phoneNumber) {
        if (StringUtils.isEmpty(phoneNumber) || phoneNumber.equals("undefined")) {
            return new AjaxResponse(ResponseCode.APP_FAIL, "手机号输入有误");
        } else {
            //手机号不为空 判断是否是已注册用户
            boolean isExist = aUserService.queryUserIsExist(phoneNumber);
            //不为空 表示该用户已经注册过,已存在该用户
            if (isExist) {
                AuserInfo aUserInfoEntity = aUserService.getUserInfoByPhone(phoneNumber);
                aUserInfoEntity.setLastLoginTime(new Date());
                String id = aUserInfoEntity.getId();
                String token = RSAUtil.getToken(id);
                AUserVO aUserVO = new AUserVO();
                BeanUtils.copyProperties(aUserInfoEntity, aUserVO);
                aUserVO.setToken(token);
                String name = aUserInfoEntity.getName();
                Integer age = aUserInfoEntity.getAge();
                if(StringUtils.isBlank(name) || age == null){
                    aUserVO.setIsRegister(-1);
                }else {
                    aUserVO.setIsRegister(0);
                }
                aUserVO.setIsLogin(0);
                aUserVO.setType(0);
                redis.set(USER_REDIS_SESSION + ":" + id, token);
                return new AjaxResponse(ResponseCode.APP_SUCCESS, "登录成功", aUserVO);
            } else {
                //信息写入
                AuserInfo aUserInfoEntity = getLoginUser();
                aUserInfoEntity.setEmpowerPhone(phoneNumber);
                Date now = new Date();
                aUserInfoEntity.setCreateTime(now);
                aUserInfoEntity.setUpdateTime(now);
                aUserInfoEntity.setDelFlag(0);
                aUserInfoEntity.setEmpowerPhone(phoneNumber);
                aUserService.saveUser(aUserInfoEntity);
                String id = aUserInfoEntity.getId();
                String token = RSAUtil.getToken(id);
                redis.set(USER_REDIS_SESSION + ":" + id, token);
                AUserVO aUserVO = new AUserVO();
                BeanUtils.copyProperties(aUserInfoEntity, aUserVO);
                aUserVO.setToken(token);
                aUserVO.setIsLogin(0);
                aUserVO.setIsRegister(-1);
                aUserVO.setType(0);
                return new AjaxResponse(ResponseCode.APP_SUCCESS, "登录成功", aUserVO);
            }
        }
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "name",value = "姓名",paramType = "query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "age",value = "年龄",paramType = "query",required = true,dataType = "integer")})
    @ApiOperation(value = "更改用户信息",notes = "当用户通过验证码注册时使用，或更改用户信息时")
    public AjaxResponse updateUser(Integer age,String name,Integer gender,String avatar){
        AuserInfo aUserInfo = getLoginUser();
        //注册页面 首先判断验证码是否正确 是否超时
        aUserInfo.setAge(age);
        aUserInfo.setName(name);
        aUserInfo.setGender(gender);
        if(StringUtils.isNotBlank(avatar)){
            aUserInfo.setAvatar(avatar);
        }else {
            //如果没有主动上传 那么根据性别设置一个默认的图片 男士头像
            if(gender == 0){
                aUserInfo.setAvatar(imageConfig.getManAvatarForA());
            }else {
                aUserInfo.setAvatar(imageConfig.getWomanAvatarForA());
            }
        }
        aUserInfo.setLastLoginTime(new Date());
        aUserService.updateUserInfo(aUserInfo);
        String id = aUserInfo.getId();
        String token = RSAUtil.getToken(id);
        AUserVO aUserVO = new AUserVO();
        BeanUtils.copyProperties(aUserInfo, aUserVO);
        aUserVO.setIsLogin(0);
        aUserVO.setIsRegister(0);
        aUserVO.setToken(token);
        aUserVO.setVerifyCode("");
        return new AjaxResponse(ResponseCode.APP_SUCCESS,aUserVO);
    }


    /* *
     * 用于A端发送验证码
     * @author zhaoMaoJie
     * @date 2019/8/15 0015
     */
    @RequestMapping(value = "/sendVerdifyCode",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "phone",value = "手机号",paramType = "query",required = true,dataType = "string")})
    @ApiOperation(value = "发送验证码",notes = "用于系统登录")
    public AjaxResponse sendVerdifyCode(String phone) {
        if (StringUtils.isEmpty(phone) || phone.equals("undefined")) {
            return new AjaxResponse(ResponseCode.APP_FAIL, "手机号不能为空");
        } else {
            boolean isExist = aUserService.queryUserIsExist(phone);
            //当用户存在时 openid不为空
            if (isExist) {
                AuserInfo aUserInfoEntity = aUserService.getUserInfoByPhone(phone);
                String code = StringUtils.createRandomCode();
                aUserInfoEntity.setVerifyCode(code);
                aUserInfoEntity.setVerdifyCodeTime(new Date());
                aUserService.saveUser(aUserInfoEntity);
                boolean isSuccess = aliyunSMSService.sendVerdifyCode(phone,code);
                //返回到前端
                AUserVO aUserVO = new AUserVO();
                BeanUtils.copyProperties(aUserInfoEntity, aUserVO);
                aUserVO.setIsLogin(0);
                String name = aUserInfoEntity.getName();
                Integer age = aUserInfoEntity.getAge();
                if(StringUtils.isBlank(name) || age == null){
                    aUserVO.setIsRegister(-1);
                }else {
                    aUserVO.setIsRegister(0);
                }
                aUserVO.setVerifyCode("");
                return new AjaxResponse(ResponseCode.APP_SUCCESS,aUserVO);
            } else {
                //当用户不存在时 首先执行注册 然后再生成验证码
                AuserInfo aUserInfoEntity = getLoginUser();
//                AuserInfo aUserInfoEntity = new AuserInfo();
                aUserInfoEntity.setEmpowerPhone(phone);
                Date now = new Date();
                aUserInfoEntity.setCreateTime(now);
                aUserInfoEntity.setUpdateTime(now);
                aUserInfoEntity.setLastLoginTime(now);
                aUserInfoEntity.setDelFlag(0);
                String code = StringUtils.createRandomCode();
                aUserInfoEntity.setVerifyCode(code);
                aUserInfoEntity.setVerdifyCodeTime(now);
                //保存用户信息
                aUserService.saveUser(aUserInfoEntity);
                // 发送验证码
//                AliyunSMSUtil.sendVerdifyCode(phone, code);
                boolean isSuccess = aliyunSMSService.sendVerdifyCode(phone,code);
                if(!isSuccess){
                    return new AjaxResponse(ResponseCode.APP_FAIL,"验证码发送失败！");
                }
                AUserVO aUserVO = new AUserVO();
                BeanUtils.copyProperties(aUserInfoEntity, aUserVO);
                aUserVO.setIsLogin(0);
                aUserVO.setIsRegister(-1);
                aUserVO.setVerifyCode("");
                return new AjaxResponse(ResponseCode.APP_SUCCESS,aUserVO);
            }
        }
    }

    /* *
     * A端 使用验证码方登录
     * @author zhaoMaoJie
     * @date 2019/8/15 0015
     */
    @RequestMapping(value = "/loginByCode",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "phone",value = "手机号",paramType = "query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "code",value = "验证码",paramType = "query",required = true,dataType = "string")})
    @ApiOperation(value = "用户使用手机验证码登录",notes = "验证码有效期为五分钟")
    public AjaxResponse loginByCode(String phone, String code) {
        if(StringUtils.isBlank(phone) || StringUtils.isBlank(code)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"数据输入有误！");
        }
        AuserInfo aUserInfoEntity = aUserService.getUserInfoByPhone(phone);
        String verifyCode = aUserInfoEntity.getVerifyCode();
        Date verdifyDate = aUserInfoEntity.getVerdifyCodeTime();
        System.out.println(verdifyDate);
        int difference = DateUtil.getTimeDifferent(verdifyDate, new Date());
        if(!code.equals(verifyCode)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"验证码输入不正确！");
        }else {
            if(difference >= 5 * 60){
                return new AjaxResponse(ResponseCode.APP_FAIL, "验证码超时，请重新获取!");
            }else {
                //更新用户最近登陆时间
                aUserInfoEntity.setLastLoginTime(new Date());
                aUserService.saveUser(aUserInfoEntity);
                //设置用户返回数据
                String id = aUserInfoEntity.getId();
                String token = RSAUtil.getToken(id);
                redis.set(USER_REDIS_SESSION + ":" + id, token);
                AUserVO aUserVO = new AUserVO();
                BeanUtils.copyProperties(aUserInfoEntity, aUserVO);
                // 使用验证码方式登录 刷新token
                aUserVO.setToken(token);
                String name = aUserInfoEntity.getName();
                Integer age = aUserInfoEntity.getAge();
                if(StringUtils.isBlank(name) || age == null){
                    aUserVO.setIsRegister(-1);
                }else {
                    aUserVO.setIsRegister(0);
                }
                aUserVO.setIsLogin(0);
                aUserVO.setType(0);
                return new AjaxResponse(ResponseCode.APP_SUCCESS, "登录成功", aUserVO);
            }
        }
    }



    /* *
     *  B端首次登录验证
     * @author zhaoMaoJie
     * @date 2019/9/2 0002
     */
    @RequestMapping(value = "/loginForB",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse loginForB(){
        AuserInfo auserInfo = getLoginUser();
        String openId = auserInfo.getOpenId();
        BuserInfo buserInfo = buserService.getBuserInfoByOpenId(openId);
        //用户未注册的情况下下 直接返回 先执行企业注册
        if(buserInfo == null){
            return new AjaxResponse(ResponseCode.APP_FAIL,"请先注册！");
        }
        buserInfo.setLastLoginTime(new Date());
        buserService.saveUser(buserInfo);
        String id = buserInfo.getId();
        String token = RSAUtil.getToken(id);
        BuserVO buserVO = new BuserVO();
        buserInfo.setOpenId("");
        BeanUtils.copyProperties(buserInfo, buserVO);
        buserVO.setToken(token);
        buserVO.setId("");
        buserVO.setIsLogin(0);
//        boolean isRegister = buserService.isReister(buserInfo.getId());
//        if(isRegister){
//            buserVO.setIsRegister(0);
//        }else {
//            buserVO.setIsRegister(-1);
//        }
        return new AjaxResponse(ResponseCode.APP_SUCCESS,buserVO);
    }


    /* *
     * B端发送验证码
     * @author zhaoMaoJie
     * @date 2019/9/2 0002
     */
    @RequestMapping(value = "/sendVerdifyCodeForB",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "phone",value = "手机号",paramType = "query",required = true,dataType = "string")})
    @ApiOperation(value = "发送验证码",notes = "用于系统登录")
    public AjaxResponse sendVerdifyCodeForB(String phone) {
        if (StringUtils.isEmpty(phone) || phone.equals("undefined")) {
            return new AjaxResponse(ResponseCode.APP_FAIL, "手机号或验证码输入错误");
        } else {
            boolean isExist = buserService.queryUserIsExist(phone);
            //当用户存在时
            if (isExist) {
                BuserInfo buserInfo = buserService.getUserInfoByPhone(phone);
                String code = StringUtils.createRandomCode();
                buserInfo.setVerifyCode(code);
                buserInfo.setVerdifyCodeTime(new Date());
                buserService.saveUser(buserInfo);
                boolean isSuccess = aliyunSMSService.sendVerdifyCode(phone,code);
                if(!isSuccess){
                    return new AjaxResponse(ResponseCode.APP_FAIL,"验证码发送失败！");
                }
                BuserVO buserVO = new BuserVO();
                BeanUtils.copyProperties(buserInfo, buserVO);
                //是否需要注册
                String name = buserInfo.getName();
                Integer age = buserInfo.getAge();
                if(StringUtils.isBlank(name) || age == null){
                    buserVO.setIsRegister(-1);
                    buserVO.setIsLogin(-1);
                }else {
                    buserVO.setIsRegister(0);
                    buserVO.setIsLogin(0);
                }
                buserVO.setType(1);
                return new AjaxResponse(ResponseCode.APP_SUCCESS,buserVO);
            } else {
                //当用户不存在时 首先执行注册 然后再生成验证码
                AuserInfo auserInfo = getLoginUser();
                String openId = auserInfo.getOpenId();
                //写入相关信息
                BuserInfo buserInfo = new BuserInfo();
                buserInfo.setOpenId(openId);
                buserInfo.setEmpowerPhone(phone);
                Date now = new Date();
                buserInfo.setCreateTime(now);
                buserInfo.setUpdateTime(now);
                buserInfo.setLastLoginTime(now);
                buserInfo.setDelFlag(0);
                String code = StringUtils.createRandomCode();
                buserInfo.setVerifyCode(code);
                buserInfo.setVerdifyCodeTime(now);
                //保存用户信息
                buserService.saveUser(buserInfo);
                // 发送验证码
                boolean isSuccess = aliyunSMSService.sendVerdifyCode(phone,code);
                if(!isSuccess){
                    return new AjaxResponse(ResponseCode.APP_FAIL,"验证码发送失败！");
                }
                BuserVO buserVO = new BuserVO();
                BeanUtils.copyProperties(buserInfo, buserVO);
                buserVO.setIsLogin(-1);
                buserVO.setIsRegister(-1);
                buserVO.setType(1);
                return new AjaxResponse(ResponseCode.APP_SUCCESS);
            }
        }
    }

    /* *
     * B端登录
     * @author zhaoMaoJie
     * @date 2019/9/2 0002
     */
    @RequestMapping(value = "/loginByCodeforB",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "phone",value = "手机号",paramType = "query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "code",value = "验证码",paramType = "query",required = true,dataType = "string")})
    @ApiOperation(value = "B端用户使用验证码登录",notes = "验证码有效期为五分钟")
    public AjaxResponse loginByCodeforB(String phone, String code) {
        if (!StringUtils.isEmpty(phone) && !StringUtils.isEmpty(code)) {
            BuserInfo buserInfo = buserService.getUserInfoByPhone(phone);
            String verifyCode = buserInfo.getVerifyCode();
            Date verdifyDate = buserInfo.getVerdifyCodeTime();
            int difference = DateUtil.getTimeDifferent(verdifyDate, new Date());
            System.out.println(difference);
            if (code.equals(verifyCode)) {
                if (difference < 5 * 60) {
                    String id = buserInfo.getId();
                    String token = RSAUtil.getToken(id);
                    redis.set(USER_REDIS_SESSION + ":" + id, token);
                    buserInfo.setLastLoginTime(new Date());
                    buserService.saveUser(buserInfo);
                    BuserVO buserVO = new BuserVO();
                    BeanUtils.copyProperties(buserInfo, buserVO);
                    buserVO.setToken(token);
                    //是否需要注册
                    String name = buserInfo.getName();
                    Integer age = buserInfo.getAge();
                    if(StringUtils.isBlank(name) || age == null){
                        buserVO.setIsRegister(-1);
                    }else {
                        buserVO.setIsRegister(0);
                    }
                    buserVO.setIsLogin(0);
                    buserVO.setType(1);
                    return new AjaxResponse(ResponseCode.APP_SUCCESS, "登录成功", buserVO);
                } else {
                    return new AjaxResponse(ResponseCode.APP_FAIL, "验证码超时，请重新获取!");
                }
            } else {
                return new AjaxResponse(ResponseCode.APP_FAIL, "验证码输入有误！");
            }
        } else {
            return new AjaxResponse(ResponseCode.APP_FAIL, "手机号或验证码输入不正确！");
        }
    }

    /* *
     * 切换用户
     * @author zhaoMaoJie
     * @date 2019/9/2 0002
     */
    @RequestMapping(value = "/switchAccount",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse switchAccount(String phone,String code){
        if(StringUtils.isBlank(phone) || StringUtils.isBlank(code)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"手机号或验证码输入不正确！");
        }
        AuserInfo auserInfo = aUserService.getUserInfoByPhone(phone);
        String name = auserInfo.getName();
        if(StringUtils.isBlank(name)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"该手机号未注册！");
        }else {
//            AuserInfo auserInfo = aUserService.getUserInfoByPhone(phone);
            //判断是否当前用户
            String empowerPhone = auserInfo.getEmpowerPhone();
            if(phone.equals(empowerPhone)){
                return new AjaxResponse(ResponseCode.APP_FAIL,"当前账户和手机号重合，无需切换");
            }
            //判断验证码
            String verdifyCode = auserInfo.getVerifyCode();
            if(!code.equals(verdifyCode)){
                return new AjaxResponse(ResponseCode.APP_FAIL,"验证码输入错误！");
            }
            int difference = DateUtil.getTimeDifferent(auserInfo.getVerdifyCodeTime(), new Date());
            //判断验证码是否超时
            if(difference > 5 * 60){
                return new AjaxResponse(ResponseCode.APP_FAIL,"验证码超时，请重新输入！");
            }
            String id = auserInfo.getId();
            String token = RSAUtil.getToken(id);
            AUserVO aUserVO = new AUserVO();
            BeanUtils.copyProperties(auserInfo,aUserVO);
            aUserVO.setToken(token);
            Integer age = auserInfo.getAge();
            Integer gender = auserInfo.getGender();
            if(StringUtils.isBlank(name) || age == null || gender == null){
                aUserVO.setIsRegister(-1);
            }else {
                aUserVO.setIsRegister(0);
            }
            aUserVO.setType(0);
            aUserVO.setIsLogin(0);
            return new AjaxResponse(ResponseCode.APP_SUCCESS,aUserVO);
        }
    }

    /* *
     * 切换用户
     * @author zhaoMaoJie
     * @date 2019/9/2 0002
     */
    @RequestMapping(value = "/switchAccountForB",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse switchAccountForB(String phone,String code){
        if(StringUtils.isBlank(phone) || StringUtils.isBlank(code)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"手机号或验证码输入不正确！");
        }
        boolean isExist = buserService.queryUserIsExist(phone);
        if(!isExist){
            return new AjaxResponse(ResponseCode.APP_FAIL,"该手机号未注册！");
        }
        BuserInfo buserInfo = buserService.getUserInfoByPhone(phone);
        String empowerPhone = buserInfo.getEmpowerPhone();
        if(phone.equals(empowerPhone)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"当前账户和手机号重合，无需切换");
        }
        String verdifyCode = buserInfo.getVerifyCode();
        //验证验证码输入是否正确
        if(!verdifyCode.equals(code)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"验证码输入错误！");
        }
        //验证超时
        int difference = DateUtil.getTimeDifferent(buserInfo.getVerdifyCodeTime(), new Date());
        //判断验证码是否超时
        if(difference > 5 * 60){
            return new AjaxResponse(ResponseCode.APP_FAIL,"验证码超时，请重新输入！");
        }
        String id = buserInfo.getId();
        String token = RSAUtil.getToken(id);
        BuserVO buserVO = new BuserVO();
        BeanUtils.copyProperties(buserInfo,buserVO);
        buserVO.setToken(token);
        buserVO.setIsRegister(0);
        buserVO.setIsLogin(0);
        return new AjaxResponse(ResponseCode.APP_SUCCESS,buserVO);
    }


}
