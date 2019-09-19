package com.wzkj.hzyp.controller;

import com.github.pagehelper.PageInfo;
import com.wzkj.hzyp.common.AjaxResponse;
import com.wzkj.hzyp.common.ResponseCode;
import com.wzkj.hzyp.entity.AuserInfo;
import com.wzkj.hzyp.entity.BuserInfo;
import com.wzkj.hzyp.entity.CashoutDetail;
import com.wzkj.hzyp.entity.StoreInfo;
import com.wzkj.hzyp.service.CommonService;
import com.wzkj.hzyp.service.StoreInfoService;
import com.wzkj.hzyp.utils.ImageConfig;
import com.wzkj.hzyp.utils.RSAUtil;
import com.wzkj.hzyp.utils.StringUtils;
import com.wzkj.hzyp.vo.AUserVO;
import com.wzkj.hzyp.vo.CashOutListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@RequestMapping("/user")
@Controller
@Api(value = "查看用户信息的接口",tags = "查看用户信息的controller")
public class UserController extends BaseController {

    @Autowired
    private StoreInfoService storeInfoService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ImageConfig imageConfig;

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取A端用户信息",notes = "name：姓名 totalMoney：佣金总收入 totalNumber：共推荐人数" +
            " successNum：应聘成功人数 noCashout：未提现金额")
    public AjaxResponse getUserInfo(){
        AuserInfo userInfo = getLoginUser();
        String id = userInfo.getId();
        //name: "赵茂杰"
        //totalMoney: 0 累计佣金收入
        //totalNumber: 3 成功推荐人数
        //noCashout: 0 未提现金额
        //successNum: 3 成功入职人数
        //type: 0
        String name = userInfo.getName();
        String avatar = userInfo.getAvatar();
        Integer totalNumber = aUserService.getRecommenNumber(id);
        Integer successNum = aUserService.getEntrySuccessNumber(id);
        Integer noCashout = aUserService.getNoCashNumber(id);
        Integer noCashoutFormat = noCashout == null ? 0 : noCashout;
        Integer totalMoney = aUserService.getTotalMoney(id);
        Integer totalMoneyFormat = totalMoney == null ? 0 : totalMoney;
        Map<String,Object> map = new HashMap();
        map.put("avatar",avatar);
        map.put("name",name);
        map.put("totalNumber",totalNumber);
        map.put("successNum",successNum);
        map.put("noCashout",noCashoutFormat);
        map.put("totalMoney",totalMoneyFormat);
        map.put("type",0);
        return new AjaxResponse(ResponseCode.APP_SUCCESS,map);
    }

    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "name",value = "姓名",paramType = "query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "age",value = "年龄",paramType = "query",required = true,dataType = "integer")})
    @ApiOperation(value = "更改用户信息",notes = "当用户通过验证码注册时使用，或更改用户信息时")
    public AjaxResponse updateUser(Integer age,String name){
        AuserInfo aUserInfo = getLoginUser();
        aUserInfo.setAge(age);
        aUserInfo.setName(name);
        aUserInfo.setLastLoginTime(new Date());
        aUserService.updateUserInfo(aUserInfo);
        return new AjaxResponse(ResponseCode.APP_SUCCESS);
    }



    /* *
     * B端 获取用户信息
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    @RequestMapping(value = "/getStoreInfo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取B端用户信息",notes = "storeName：商户名称 totalMoney：胜率履约金 jobNum：发布岗位数 resumeNum：收到的简历数" +
            " sumMoney：花费的金额")
    public AjaxResponse getStoreInfo(){
        BuserInfo buserInfo = getBuser();
        String userId = buserInfo.getId();
        StoreInfo storeInfo = storeInfoService.getStoreByUserId(buserInfo.getId());
        String storeName = storeInfo.getName();
        Integer totalMoney = buserService.getAmountBalance(userId);
        Integer totalMoneyFormat = totalMoney == null ? 0 :totalMoney;
        Integer jobNum = buserService.getJobNum(userId);
        Integer resumeNum = buserService.getResumeNum(userId);
        Integer sumMoney = buserService.getSumMoney(userId);
        Integer sumMoneyFormat = sumMoney == null ? 0 : sumMoney;
        String avatar = storeInfo.getAvatar();
        Map<String,Object> map = new HashMap();
        map.put("avatar",avatar);
        map.put("storeName",storeName);
        map.put("totalMoney",totalMoneyFormat);
        map.put("jobNum",jobNum);
        map.put("resumeNum",resumeNum);
        map.put("sumMoney",sumMoneyFormat);
        map.put("type",1);
        return new AjaxResponse(ResponseCode.APP_SUCCESS,map);
    }

    /* *
     * A端获取提现信息列表
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    @RequestMapping(value = "/getCashoutList",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse getCashoutList(){
        AuserInfo auserInfo = getLoginUser();
        String userId = auserInfo.getId();
        List<CashOutListVO> list = aUserService.getCashoutList(userId);
        PageInfo page = new PageInfo(list);
        Map map = commonService.getMapByList(page,list);
        return new AjaxResponse(ResponseCode.APP_SUCCESS,map);
    }

    /* *
     * A端获取提现信息列表
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    @RequestMapping(value = "/cashoutHistary",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse cashoutHistary(){
        AuserInfo auserInfo = getLoginUser();
        String userId = auserInfo.getId();
        List<CashOutListVO> list = aUserService.getCashoutHistary(userId);
        PageInfo page = new PageInfo(list);
        Map map = commonService.getMapByList(page,list);
        return new AjaxResponse(ResponseCode.APP_SUCCESS,map);
    }


    /* *
     * A端 申请提现
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    @RequestMapping(value = "/cashout",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "A端用户申请提现",notes = "点击申请提现时调用")
    public AjaxResponse cashout(){
        AuserInfo aUserInfo = getLoginUser();
        String id = aUserInfo.getId();
        //首先改变岗位 金额 然后改变入职表中是否提现 iscash字段
        boolean isSuccess = aUserService.cashout(id);
        if(isSuccess){
            return new AjaxResponse(ResponseCode.APP_SUCCESS);
        }else {
            return new AjaxResponse(ResponseCode.APP_FAIL,"操作失败！");
        }

    }


    /* *
     * B端 获取花费详情
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    @RequestMapping(value = "/getCashoutDetail",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "B端用户获取花费详情",notes = "用户点击详情时调用")
    public AjaxResponse getCashoutDetail(){
        BuserInfo buserInfo = getBuser();
        String userId = buserInfo.getId();
        List<CashOutListVO> list = buserService.getCashoutDetail(userId);
        return new AjaxResponse(ResponseCode.APP_SUCCESS,list);
    }

    /* *
     * A端 申请提现 该方法暂时废弃
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    @RequestMapping(value = "/cashout1",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "A端用户申请提现",notes = "点击申请提现时调用")
    public AjaxResponse cashout1(){
        AuserInfo aUserInfo = getLoginUser();
        String id = aUserInfo.getId();
        //首先改变岗位 金额 然后改变入职表中是否提现 iscash字段
        List<Map<String,Object>> list = aUserService.getChangeJobInfo(id);
        aUserService.changeJobMoney(list,id);
        // 改变入职表字段
        List<String> stringList = aUserService.getNeedChangeIds(id);
        aUserService.updateEntryIsCashById(stringList);
        return new AjaxResponse(ResponseCode.APP_SUCCESS);
    }


    /* *
     * B端 获取公司信息
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    @RequestMapping(value = "/getCompanyInfo",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse getCompanyInfo(){
        BuserInfo buserInfo = getBuser();
        String bUserId = buserInfo.getId();
        StoreInfo storeInfo = storeInfoService.getStoreByUserId(bUserId);
        return new AjaxResponse(ResponseCode.APP_SUCCESS,storeInfo);
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

    @RequestMapping(value = "/uploadAvatar",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse uploadAvatar(MultipartFile file){
        if(file.isEmpty()){
            return new AjaxResponse(ResponseCode.APP_FAIL,"未能获取到图片参数！");
        }
        String url = commonService.getImgWebUrl(file,"user");
        Map map = new HashMap();
        if(StringUtils.isNotBlank(url)){
            map.put("url",url);
            return new AjaxResponse(ResponseCode.APP_SUCCESS,map);
        }else {
            return new AjaxResponse(ResponseCode.APP_FAIL,"上传失败！");
        }
    }








}
