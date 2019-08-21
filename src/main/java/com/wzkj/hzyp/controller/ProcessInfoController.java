package com.wzkj.hzyp.controller;

import com.wzkj.hzyp.common.AjaxResponse;
import com.wzkj.hzyp.common.ResponseCode;
import com.wzkj.hzyp.entity.*;
import com.wzkj.hzyp.entity.wxTemplate.TemplateData;
import com.wzkj.hzyp.entity.wxTemplate.WxMssVo;
import com.wzkj.hzyp.service.*;
import com.wzkj.hzyp.utils.DateUtil;
import com.wzkj.hzyp.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import me.chanjar.weixin.common.error.WxErrorException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@RequestMapping("/process")
@Controller
@Api(value = "流程相关的接口",tags = "流程相关的controller")
public class ProcessInfoController extends BaseController {

    @Autowired
    private ProcessInfoService processInfoService;

    @Autowired
    private JobInfoService jobInfoService;

    @Autowired
    private ResumeInfoService resumeInfoService;

    @Autowired
    private EntryInfoService entryInfoService;

    @Autowired
    private ReceviedInfoService receviedInfoService;

    @Autowired
    private WxTemplateService wxTemplateService;


    /* *
     * 测试获取accessToken
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse getAccessToken(){
        String accessToken = wxTemplateService.getAccessToken();
        return new AjaxResponse(ResponseCode.APP_SUCCESS,"获取成功",accessToken);
    }

    @RequestMapping(value = "/push",method = RequestMethod.POST)
    @ResponseBody
    public String push(String formId) {
        //1,配置小程序信息
        WxMaInMemoryConfig wxConfig = wxTemplateService.getWxConfig();
        WxMaService wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(wxConfig);
        //2,设置模版信息（keyword1：类型，keyword2：内容）
        List<WxMaTemplateData> templateDataList = new ArrayList<>(2);
        WxMaTemplateData data1 = new WxMaTemplateData("keyword1", "张三");
        WxMaTemplateData data2 = new WxMaTemplateData("keyword2", "男");
        WxMaTemplateData data3 = new WxMaTemplateData("keyword3", "15");
        WxMaTemplateData data4 = new WxMaTemplateData("keyword4", "招聘专员");
        WxMaTemplateData data5 = new WxMaTemplateData("keyword5", DateUtil.dateToString(new Date()));
        templateDataList.add(data1);
        templateDataList.add(data2);
        templateDataList.add(data3);
        templateDataList.add(data4);
        templateDataList.add(data5);

        //3，设置推送消息
//        BuserInfo buserInfo = buserService.getBuserById(bUserId);
//        String openId = buserInfo.getOpenId();
        String openId = "o2xfN4vXOvQQZrFItU4vBdCgSr1M";
        boolean isSuccess = wxTemplateService.pushResumeTemplate(openId,formId,templateDataList,wxMaService);
        if(isSuccess){
            return "成功0";
        }else {
            return "s失败";
        }
    }

    /* *
     * 测试获取accessToken
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    @RequestMapping(value = "/testPush",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse testPush(String formId){
        String accessToken = wxTemplateService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send" +
                "?access_token=" + accessToken;
        String openId = "o2xfN4vXOvQQZrFItU4vBdCgSr1M";
//        String templateId = "TS3G8FWhMVvtvX1wxg25yPnNHxsR6wN8WIRHioIykFM";
        String templateId = "GBBN6tT_SlLqaBNWjNhrCBUOF1Lz-6fpGOR7g1U8RhM";
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setTouser(openId);
        wxMssVo.setPage("pages/index/index");
//        wxMssVo.setEmphasisKeyword();
        wxMssVo.setFormIdd(formId);
        wxMssVo.setTemplateId(templateId);

        Map<String, TemplateData> m = new HashMap<>(2);
        TemplateData keyword1 = new TemplateData();
        keyword1.setValue("新下单待抢单");
        m.put("keyword1", keyword1);

        TemplateData keyword2 = new TemplateData();
        keyword2.setValue("这里填下单金额的值");
        m.put("keyword2", keyword2);
        wxMssVo.setData(m);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(url, wxMssVo, String.class);
        return new AjaxResponse(ResponseCode.APP_SUCCESS,"获取成功",responseEntity);
    }



    /* *
     * 根据recevied获取简历推送流程表
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    @RequestMapping(value = "/processList",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "receviedId",value = "接收简历id（不是简历id）",paramType = "query",required = true,dataType = "String")})
    @ApiOperation(value = "根据接收简历获取流程信息",notes = "点击接收简历时触发")
    public AjaxResponse processList(String receviedId){
        if(StringUtils.isBlank(receviedId)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"id不能为空！");
        }else {
            List<ProcessInfo> processInfoList = processInfoService.getProcessInfoByReceviedId(receviedId);
            return new AjaxResponse(ResponseCode.APP_SUCCESS,processInfoList);
        }
    }

    /* *
     * A端 取消简历推送
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    @RequestMapping(value = "/cancelResume",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "id",value = "流程id",paramType = "query",required = true,dataType = "String")})
    @ApiOperation(value = "A端取消简历",notes = "发出简历后 想取消 在商户未作出回应前可以取消")
    public AjaxResponse cancelResume(String id){
        if(StringUtils.isBlank(id)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"id不能为空！");
        }else {
            ProcessInfo processInfo = processInfoService.getProcessInfoById(id);
            Integer isFeedback = processInfo.getIsFeedback();
            // 不等于0 表示已经有反馈
            if(isFeedback != 0){
                return new AjaxResponse(ResponseCode.APP_FAIL,"商家已确认，无法取消！");
            }else {
                ReceviedInfo receviedInfo = receviedInfoService.getReceviedInfoById(processInfo.getReceviedId());
                String jobId = receviedInfo.getJobId();
                JobInfo jobInfo = jobInfoService.getJobInfoById(jobId);
                //岗位收到的简历减1
                jobInfo.setReceviedResumeNumber(jobInfo.getReceviedResumeNumber() - 1);
                jobInfoService.saveJobInfo(jobInfo);
                //删除B端 接收到的简历
                receviedInfoService.deleteReceviedInfoById(receviedInfo.getId());
                //删除流程表内容
                processInfoService.deleteProcessById(id);
                return new AjaxResponse(ResponseCode.APP_SUCCESS);
            }
        }
    }

    /* *
     * B端 面试反馈
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    @RequestMapping(value = "/interviedFeedback",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "processInfoId",value = "流程id",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "feedbackStatus",value = "反馈状态 0 面试通过 1未到场 2未通过 3更改面试时间",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "enteyTime",value = "入职时间",paramType = "query",required = true,dataType = "date"),
            @ApiImplicitParam(name = "interviewTime",value = "面试时间",paramType = "query",required = true,dataType = "date")
    })
    @ApiOperation(value = "面试反馈",notes = "根据相应状态做出相应操作")
    public AjaxResponse interviedFeedback(String processInfoId, Integer feedbackStatus, Date entryTime,Date interviewTime){
        if(StringUtils.isBlank(processInfoId) || feedbackStatus == null || feedbackStatus.equals("undefined")){
            return new AjaxResponse(ResponseCode.APP_FAIL,"数据不能为空！");
        }
        //面试通过的情况
        if(feedbackStatus.equals(0)){
            //面试通过 但入职时间为空的情况
             if(entryTime == null){
                 return new AjaxResponse(ResponseCode.APP_FAIL,"入职时间不能为空");
             }
             //通过面试 确定入职时间  改变流程状态 获取流程表信息
            ProcessInfo processInfo = processInfoService.getProcessInfoById(processInfoId);
            String receviedId = processInfo.getReceviedId();
            processInfo.setIsFeedback(1);

            //添加新的流程进入下一步
            ProcessInfo newProcessInfo = new ProcessInfo();
            newProcessInfo.setReceviedId(receviedId);
            newProcessInfo.setOwner(1);
            newProcessInfo.setbUserId(getBuser().getId());
            newProcessInfo.setaUserId(processInfo.getaUserId());
            newProcessInfo.setStatus(2);
            newProcessInfo.setIsFeedback(0);
            newProcessInfo.setDelFlag(0);
            newProcessInfo.setProcessContent("面试通过，" + "将于" + DateUtil.dateToString(entryTime,DateUtil.ymdFormat) + "入职");
            newProcessInfo.setOwner(1);
            newProcessInfo.setIsInterview(1);
            newProcessInfo.setIsEntry(0);
            newProcessInfo.setIsQuit(0);
            newProcessInfo.setIsEnd(0);
            newProcessInfo.setSortNumber(processInfo.getSortNumber() + 1);
            //保存流程信息
            processInfoService.saveProcessInfo(newProcessInfo);
            processInfo.setFeedbackId(newProcessInfo.getId());
            processInfoService.saveProcessInfo(processInfo);
            //设置recevied的最新反馈
            ReceviedInfo receviedInfo = receviedInfoService.getReceviedInfoById(receviedId);
            receviedInfo.setLatestFeedback("面试通过，" + "将于" + DateUtil.dateToString(entryTime,DateUtil.ymdFormat).trim() + "入职");
            receviedInfoService.saveReceviedInfo(receviedInfo);
            return new AjaxResponse(ResponseCode.APP_SUCCESS);
        }else if (feedbackStatus.equals(1) || feedbackStatus.equals(2)){ //面试未到场 或者未通过的情况
            ProcessInfo processInfo = processInfoService.getProcessInfoById(processInfoId);
            String receviedId = processInfo.getReceviedId();
            ReceviedInfo receviedInfo = receviedInfoService.getReceviedInfoById(receviedId);
            processInfo.setIsFeedback(1);

            //设置新流程
            ProcessInfo newProcessInfo = new ProcessInfo();
            newProcessInfo.setReceviedId(receviedId);
            newProcessInfo.setOwner(1);
            newProcessInfo.setbUserId(getBuser().getId());
            newProcessInfo.setaUserId(processInfo.getaUserId());
            newProcessInfo.setStatus(1);
            newProcessInfo.setDelFlag(0);
            newProcessInfo.setOwner(1);
            newProcessInfo.setIsEntry(0);
            newProcessInfo.setIsQuit(0);
            newProcessInfo.setIsEnd(1);
            newProcessInfo.setSortNumber(processInfo.getSortNumber() + 1);
            if(feedbackStatus.equals(1)){ //表示面试未到场的情况
                newProcessInfo.setProcessContent("面试未到场");
                newProcessInfo.setIsInterview(0);
                receviedInfo.setLatestFeedback("面试未到场");
                receviedInfo.setStatus(2);
            }else {
                newProcessInfo.setProcessContent("面试未通过");
                newProcessInfo.setIsInterview(1);
                receviedInfo.setLatestFeedback("面试未通过");
                receviedInfo.setStatus(2);
            }
            //首先保存流程表 原流程 新流程反馈
            processInfoService.saveProcessInfo(processInfo);
            processInfo.setFeedbackId(newProcessInfo.getId());
            processInfoService.saveProcessInfo(newProcessInfo);
            //保存接收简历状态
            receviedInfoService.saveReceviedInfo(receviedInfo);
            return new AjaxResponse(ResponseCode.APP_SUCCESS);
        }else if(feedbackStatus.equals(3)){ //修改面试时间
            ProcessInfo processInfo = processInfoService.getProcessInfoById(processInfoId);
            String receviedId = processInfo.getReceviedId();
            ReceviedInfo receviedInfo = receviedInfoService.getReceviedInfoById(receviedId);
            processInfo.setIsFeedback(1);

            // 新增反馈流程
            ProcessInfo newProcessInfo = new ProcessInfo();
            newProcessInfo.setReceviedId(receviedId);
            newProcessInfo.setOwner(1);
            newProcessInfo.setbUserId(getBuser().getId());
            newProcessInfo.setaUserId(processInfo.getaUserId());
            newProcessInfo.setStatus(processInfo.getStatus());
            newProcessInfo.setDelFlag(0);
            newProcessInfo.setOwner(1);
            newProcessInfo.setIsEntry(0);
            newProcessInfo.setIsQuit(0);
            newProcessInfo.setIsEnd(0);
            newProcessInfo.setIsInterview(0);
            newProcessInfo.setInterviewTime(interviewTime);
            newProcessInfo.setProcessContent("修改面试时间为" + DateUtil.dateToString(interviewTime,DateUtil.ymdFormat));
            newProcessInfo.setSortNumber(processInfo.getSortNumber() + 1);
            //保存流程表
            processInfoService.saveProcessInfo(newProcessInfo);
            processInfo.setFeedbackId(newProcessInfo.getId());
            processInfoService.saveProcessInfo(processInfo);
            //保存接收简历信息
            receviedInfo.setLatestFeedback("修改面试时间为" + DateUtil.dateToString(interviewTime,DateUtil.ymdFormat));
            receviedInfoService.saveReceviedInfo(receviedInfo);
            return new AjaxResponse(ResponseCode.APP_SUCCESS);
        }
        return null;
    }

    /* *
     * B端 入职反馈
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    @RequestMapping(value = "/entryFeedback",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "processInfoId",value = "流程id",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "feedbackStatus",value = "反馈状态 0 已入职 1未入职 2修改入职时间",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "enteyTime",value = "入职时间",paramType = "query",required = true,dataType = "date")})
    @ApiOperation(value = "入职反馈",notes = "根据相应状态做出相应操作")
    public AjaxResponse entryFeedback(String processInfoId, Integer feedbackStatus,Date entryTime){
        if(StringUtils.isBlank(processInfoId) || feedbackStatus == null || feedbackStatus.equals("undefined")){
            return new AjaxResponse(ResponseCode.APP_FAIL,"数据有误！");
        }
        // 成功入职 写入入职表
        if(feedbackStatus.equals(0)){
            //首先 流程反馈 然后改变接收简历表信息 然后写入入职表
            ProcessInfo processInfo = processInfoService.getProcessInfoById(processInfoId);
            processInfo.setIsFeedback(1);
            String receviedId = processInfo.getReceviedId();
            ReceviedInfo receviedInfo = receviedInfoService.getReceviedInfoById(receviedId);
            String resumeId = receviedInfo.getResumeId();
            ResumeInfo resumeInfo = resumeInfoService.getResumeById(resumeId);
            String jobId = receviedInfo.getJobId();
            JobInfo jobInfo = jobInfoService.getJobInfoById(jobId);
            Integer num = jobInfo.getOvertime();
            // 新流程表
            ProcessInfo newProcessInfo = new ProcessInfo();
            newProcessInfo.setReceviedId(receviedId);
            newProcessInfo.setOwner(1);
            newProcessInfo.setbUserId(getBuser().getId());
            newProcessInfo.setaUserId(processInfo.getaUserId());
            newProcessInfo.setStatus(3);
            newProcessInfo.setIsFeedback(0);
            newProcessInfo.setDelFlag(0);
            newProcessInfo.setEntryTime(entryTime);
            //计算过保时间
            Date overTime = DateUtil.getDateAfter(entryTime,num);
            newProcessInfo.setProcessContent("成功入职，将于" + DateUtil.dateToString(overTime,DateUtil.ymdFormat).trim() + "日过保" );
            newProcessInfo.setOwner(1);
            newProcessInfo.setIsInterview(1);
            newProcessInfo.setIsEntry(1);
            newProcessInfo.setIsQuit(0);
            newProcessInfo.setIsEnd(0);
            newProcessInfo.setSortNumber(processInfo.getSortNumber() + 1);
            //保存流程表
            processInfoService.saveProcessInfo(newProcessInfo);
            processInfo.setFeedbackId(newProcessInfo.getId());
            processInfoService.saveProcessInfo(processInfo);
            //更新 接收简历表信息
            receviedInfo.setLatestFeedback("成功入职，将于" + DateUtil.dateToString(overTime,DateUtil.ymdFormat).trim() + "日过保");
            receviedInfo.setStatus(1);
            receviedInfoService.saveReceviedInfo(receviedInfo);
            //写入 入职表 方便计算收益
            EntryInfo entryInfo = new EntryInfo();
            entryInfo.setaUserId(resumeInfo.getaUserId());
            entryInfo.setbUserId(receviedInfo.getbUserId());
            entryInfo.setJobId(jobId);
            entryInfo.setResumeId(resumeInfo.getId());
            entryInfo.setReceviedId(receviedId);
            entryInfo.setEntryTime(entryTime);
            entryInfo.setOvertime(num);
            entryInfo.setOverDatetime(overTime);
            entryInfo.setDelFlag(0);
            entryInfo.setCreateTime(new Date());
            entryInfoService.saveEntryInfo(entryInfo);
            return new AjaxResponse(ResponseCode.APP_SUCCESS);
        }else if(feedbackStatus.equals(1)){
            //未入职的情况 只需改变 流程信息和更新接收简历信息
            ProcessInfo processInfo = processInfoService.getProcessInfoById(processInfoId);
            processInfo.setIsFeedback(1);
            String receviedId = processInfo.getReceviedId();
            ReceviedInfo receviedInfo = receviedInfoService.getReceviedInfoById(receviedId);
            //新流程表
            ProcessInfo newProcessInfo = new ProcessInfo();
            newProcessInfo.setReceviedId(receviedId);
            newProcessInfo.setOwner(1);
            newProcessInfo.setbUserId(getBuser().getId());
            newProcessInfo.setaUserId(processInfo.getaUserId());
            newProcessInfo.setStatus(processInfo.getStatus());
            newProcessInfo.setIsFeedback(0);
            newProcessInfo.setDelFlag(0);
            newProcessInfo.setProcessContent("未入职" );
            newProcessInfo.setOwner(1);
            newProcessInfo.setIsInterview(processInfo.getIsInterview());
            newProcessInfo.setIsEntry(0);
            newProcessInfo.setIsQuit(0);
            newProcessInfo.setIsEnd(1);
            newProcessInfo.setSortNumber(processInfo.getSortNumber() + 1);
            // 保存流程表
            processInfoService.saveProcessInfo(newProcessInfo);
            processInfo.setFeedbackId(newProcessInfo.getId());
            processInfoService.saveProcessInfo(processInfo);
            // 保存接收简历表
            receviedInfo.setLatestFeedback("未入职");
            receviedInfo.setStatus(2);
            receviedInfoService.saveReceviedInfo(receviedInfo);
            return new AjaxResponse(ResponseCode.APP_SUCCESS);
        }else if (feedbackStatus.equals(2)){
            // 修改入职时间 改变流程 接收简历信息
            ProcessInfo processInfo = processInfoService.getProcessInfoById(processInfoId);
            processInfo.setIsFeedback(1);
            String receviedId = processInfo.getReceviedId();
            ReceviedInfo receviedInfo = receviedInfoService.getReceviedInfoById(receviedId);
            // 新流程表
            ProcessInfo newProcessInfo = new ProcessInfo();
            newProcessInfo.setReceviedId(receviedId);
            newProcessInfo.setOwner(1);
            newProcessInfo.setbUserId(processInfo.getbUserId());
            newProcessInfo.setaUserId(processInfo.getaUserId());
            newProcessInfo.setStatus(processInfo.getStatus());
            newProcessInfo.setIsFeedback(0);
            newProcessInfo.setDelFlag(0);
            newProcessInfo.setProcessContent("修改入职时间为：" + DateUtil.dateToString(entryTime,DateUtil.ymdFormat) + "日");
            newProcessInfo.setOwner(1);
            newProcessInfo.setIsInterview(processInfo.getIsInterview());
            newProcessInfo.setIsEntry(0);
            newProcessInfo.setIsQuit(0);
            newProcessInfo.setIsEnd(0);
            newProcessInfo.setSortNumber(processInfo.getSortNumber() + 1);
            processInfoService.saveProcessInfo(newProcessInfo);
            processInfo.setFeedbackId(newProcessInfo.getId());
            processInfoService.saveProcessInfo(processInfo);
            // 更新接收简历信息
            receviedInfo.setLatestFeedback("修改入职时间为：" + DateUtil.dateToString(entryTime,DateUtil.ymdFormat) + "日");
            receviedInfo.setStatus(0);
            return new AjaxResponse(ResponseCode.APP_SUCCESS);
        }
        return null;
    }

    /* *
     * B端 离职反馈
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    @RequestMapping(value = "/quitFeedback",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "processInfoId",value = "流程id",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "quitTime",value = "离职时间",paramType = "query",required = true,dataType = "date")})
    @ApiOperation(value = "离职反馈",notes = "根据相应状态做出相应操作")
    public AjaxResponse quitFeedback(String processInfoId,Date quitTime){
        if(StringUtils.isBlank(processInfoId)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"id不能为空！");
        }
        ProcessInfo processInfo = processInfoService.getProcessInfoById(processInfoId);
        processInfo.setIsFeedback(1);
        String receviedId = processInfo.getReceviedId();
        ReceviedInfo receviedInfo = receviedInfoService.getReceviedInfoById(receviedId);
        // 新流程 更新
        ProcessInfo newProcessInfo = new ProcessInfo();
        newProcessInfo.setReceviedId(receviedId);
        newProcessInfo.setOwner(1);
        newProcessInfo.setbUserId(processInfo.getbUserId());
        newProcessInfo.setaUserId(processInfo.getaUserId());
        newProcessInfo.setStatus(processInfo.getStatus() + 1);
        newProcessInfo.setIsFeedback(0);
        newProcessInfo.setDelFlag(0);
        newProcessInfo.setProcessContent("已于：" + DateUtil.dateToString(quitTime,DateUtil.ymdFormat) + "日离职");
        newProcessInfo.setOwner(1);
        newProcessInfo.setIsInterview(processInfo.getIsInterview());
        newProcessInfo.setIsEntry(processInfo.getIsEntry());
        newProcessInfo.setIsQuit(1);
        newProcessInfo.setIsEnd(1);
        newProcessInfo.setSortNumber(processInfo.getSortNumber() + 1);
        // 保存流程表
        processInfoService.saveProcessInfo(newProcessInfo);
        processInfo.setFeedbackId(newProcessInfo.getId());
        processInfoService.saveProcessInfo(processInfo);
        //保存接收简历表
        receviedInfo.setLatestFeedback("已于：" + DateUtil.dateToString(quitTime,DateUtil.ymdFormat).trim() + "日离职");
        return new AjaxResponse(ResponseCode.APP_SUCCESS);
    }


}
