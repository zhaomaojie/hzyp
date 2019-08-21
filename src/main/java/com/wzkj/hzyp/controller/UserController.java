package com.wzkj.hzyp.controller;

import com.wzkj.hzyp.common.AjaxResponse;
import com.wzkj.hzyp.common.ResponseCode;
import com.wzkj.hzyp.entity.AuserInfo;
import com.wzkj.hzyp.entity.BuserInfo;
import com.wzkj.hzyp.entity.CashoutDetail;
import com.wzkj.hzyp.entity.StoreInfo;
import com.wzkj.hzyp.service.StoreInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取A端用户信息",notes = "name：姓名 totalMoney：佣金总收入 totalNumber：共推荐人数" +
            " successNum：应聘成功人数 noCashout：未提现金额")
    public AjaxResponse getUserInfo(){
        AuserInfo userInfo = getLoginUser();
        String id = userInfo.getId();
        String name = userInfo.getName();
        Integer totalNumber = aUserService.getRecommenNumber(id);
        Integer successNum = aUserService.getEntrySuccessNumber(id);
        Integer noCashout = aUserService.getNoCashNumber(id);
        Integer totalMoney = aUserService.getTotalMoney(id);
        Map<String,Object> map = new HashMap();
        map.put("name",name);
        map.put("totalNumber",totalNumber);
        map.put("successNum",successNum);
        map.put("noCashout",noCashout);
        map.put("totalMoney",totalMoney);
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
        aUserService.updateUserInfo(aUserInfo);
        return new AjaxResponse(ResponseCode.APP_SUCCESS);
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
        List<Map<String,Object>> list = aUserService.getChangeJobInfo(id);
        aUserService.changeJobMoney(list,id);
        // 改变入职表字段
        List<String> stringList = aUserService.getNeedChangeIds(id);
        aUserService.updateEntryIsCashById(stringList);
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
        Integer jobNum = buserService.getJobNum(userId);
        Integer resumeNum = buserService.getResumeNum(userId);
        Integer sumMoney = buserService.getSumMoney(userId);
        Map<String,Object> map = new HashMap();
        map.put("storeName",storeName);
        map.put("totalMoney",totalMoney);
        map.put("jobNum",jobNum);
        map.put("resumeNum",resumeNum);
        map.put("sumMoney",sumMoney);
        return new AjaxResponse(ResponseCode.APP_SUCCESS,map);
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
        List<CashoutDetail> list = buserService.getCashoutDetail(userId);
        return new AjaxResponse(ResponseCode.APP_SUCCESS,list);
    }









}
