package com.wzkj.hzyp.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wzkj.hzyp.common.AjaxResponse;
import com.wzkj.hzyp.common.ResponseCode;
import com.wzkj.hzyp.entity.*;
import com.wzkj.hzyp.service.*;
import com.wzkj.hzyp.utils.DateUtil;
import com.wzkj.hzyp.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.*;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@RequestMapping("/process")
@Controller
//@RestController
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
    private CommonService commonService;


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
            List<ProcessInfo> list = processInfoService.getProcessInfoByReceviedId(receviedId);
            PageInfo page = new PageInfo(list);
            Map map = commonService.getMapByList(page,list);
            return new AjaxResponse(ResponseCode.APP_SUCCESS,map);
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
    @RequestMapping(value = "/interviewFeedback",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "processInfoId",value = "流程id",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "feedbackStatus",value = "反馈状态 0 面试通过 1未到场 2未通过 3更改面试时间",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "enteyTime",value = "入职时间",paramType = "query",required = true,dataType = "date"),
            @ApiImplicitParam(name = "interviewTime",value = "面试时间",paramType = "query",required = true,dataType = "date")
    })
    @ApiOperation(value = "面试反馈",notes = "根据相应状态做出相应操作")
    public AjaxResponse interviewFeedback(String processInfoId, Integer feedbackStatus, String time){
        if(StringUtils.isBlank(processInfoId) || feedbackStatus == null || feedbackStatus.equals("undefined")){
            return new AjaxResponse(ResponseCode.APP_FAIL,"数据不能为空！");
        }
        Date timeFormat = null;
        if(time != null && time != "undefined"){
            timeFormat = DateUtil.stringToDate(time);
        }
        //面试通过的情况
        if(feedbackStatus.equals(0)){
            //面试通过 但入职时间为空的情况
             if(timeFormat == null){
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
            newProcessInfo.setProcessContent("面试通过，" + "将于" + DateUtil.dateToString(timeFormat,DateUtil.ymdFormat) + "入职");
            newProcessInfo.setOwner(1);
            newProcessInfo.setIsInterview(1);
            newProcessInfo.setIsEntry(0);
            newProcessInfo.setIsQuit(0);
            newProcessInfo.setIsEnd(0);
            newProcessInfo.setSortNumber(processInfo.getSortNumber() + 1);
            newProcessInfo.setCreateTime(new Date());
            //设置按钮
            List<JSONObject> jsonA = commonService.getJsonList("A","entryFeedback",false);
            List<JSONObject> jsonB = commonService.getJsonList("B","entryFeedback",false);
            String strA = jsonA.toString().replace("\\","");
            String strB = jsonB.toString().replace("\\","");
            newProcessInfo.setButtonA(strA);
            newProcessInfo.setButtonB(strB);
            //保存流程信息
            processInfoService.saveProcessInfo(newProcessInfo);
            processInfo.setFeedbackId(newProcessInfo.getId());
            processInfoService.saveProcessInfo(processInfo);
            //设置recevied的最新反馈
            ReceviedInfo receviedInfo = receviedInfoService.getReceviedInfoById(receviedId);
            receviedInfo.setLatestFeedback("面试通过，" + "将于" + DateUtil.dateToString(timeFormat,DateUtil.ymdFormat).trim() + "入职");
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
            //设置A端按钮
            List<JSONObject> jsonA = commonService.getJsonList("A","entryFeedback",false);
            String strA = jsonA.toString().replace("\\","");
            newProcessInfo.setButtonA(strA);
            newProcessInfo.setCreateTime(new Date());
            newProcessInfo.setIsFeedback(0);
            newProcessInfo.setCreateTime(new Date());
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
            newProcessInfo.setInterviewTime(timeFormat);
            newProcessInfo.setProcessContent("修改面试时间为" + DateUtil.dateToString(timeFormat,DateUtil.ymdFormat));
            newProcessInfo.setSortNumber(processInfo.getSortNumber() + 1);
            newProcessInfo.setIsFeedback(0);
            //设置按钮
            List<JSONObject> jsonA = commonService.getJsonList("A","entryFeedback",true);
            List<JSONObject> jsonB = commonService.getJsonList("B","interviewFeedback",true);
            String strA = jsonA.toString().replace("\\","");
            String strB = jsonB.toString().replace("\\","");
            newProcessInfo.setButtonA(strA);
            newProcessInfo.setButtonB(strB);
            newProcessInfo.setCreateTime(new Date());
            //保存流程表
            processInfoService.saveProcessInfo(newProcessInfo);
            processInfo.setFeedbackId(newProcessInfo.getId());
            processInfoService.saveProcessInfo(processInfo);
            //保存接收简历信息
            receviedInfo.setLatestFeedback("修改面试时间为" + DateUtil.dateToString(timeFormat,DateUtil.ymdFormat));
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
    public AjaxResponse entryFeedback(String processInfoId, Integer feedbackStatus,String entryTime){
        if(StringUtils.isBlank(processInfoId) || feedbackStatus == null || feedbackStatus.equals("undefined")){
            return new AjaxResponse(ResponseCode.APP_FAIL,"数据有误！");
        }
        Date timeFormat = null;
        if(entryTime != null && entryTime != "undefined"){
            timeFormat = DateUtil.stringToDate(entryTime);
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
            newProcessInfo.setEntryTime(timeFormat);
            //计算过保时间
            Date overTime = DateUtil.getDateAfter(timeFormat,num);
            newProcessInfo.setProcessContent("成功入职，将于" + DateUtil.dateToString(overTime,DateUtil.ymdFormat).trim() + "日过保" );
            newProcessInfo.setOwner(1);
            newProcessInfo.setIsInterview(1);
            newProcessInfo.setIsEntry(1);
            newProcessInfo.setIsQuit(0);
            newProcessInfo.setIsEnd(0);
            newProcessInfo.setSortNumber(processInfo.getSortNumber() + 1);
            //设置按钮
            List<JSONObject> jsonA = commonService.getJsonList("A","entryFeedback",false);
            List<JSONObject> jsonB = commonService.getJsonList("B","quitFeedback",false);
            String strA = jsonA.toString().replace("\\","");
            String strB = jsonB.toString().replace("\\","");
            newProcessInfo.setButtonA(strA);
            newProcessInfo.setButtonB(strB);
            newProcessInfo.setCreateTime(new Date());
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
            entryInfo.setEntryTime(timeFormat);
            entryInfo.setOvertime(num);
            entryInfo.setOverDatetime(overTime);
            entryInfo.setDelFlag(0);
            entryInfo.setCreateTime(new Date());
            entryInfo.setIsCash(0);
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
            //设置A端按钮
            List<JSONObject> jsonA = commonService.getJsonList("A","entryFeedback",false);
            String strA = jsonA.toString().replace("\\","");
            newProcessInfo.setButtonA(strA);
            newProcessInfo.setCreateTime(new Date());
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
            newProcessInfo.setProcessContent("修改入职时间为：" + DateUtil.dateToString(timeFormat,DateUtil.ymdFormat) + "日");
            newProcessInfo.setOwner(1);
            newProcessInfo.setIsInterview(processInfo.getIsInterview());
            newProcessInfo.setIsEntry(0);
            newProcessInfo.setIsQuit(0);
            newProcessInfo.setIsEnd(0);
            newProcessInfo.setSortNumber(processInfo.getSortNumber() + 1);
            //设置按钮
            List<JSONObject> jsonA = commonService.getJsonList("A","entryFeedback",true);
            List<JSONObject> jsonB = commonService.getJsonList("B","entryFeedback",true);
            String strA = jsonA.toString().replace("\\","");
            String strB = jsonB.toString().replace("\\","");
            newProcessInfo.setButtonA(strA);
            newProcessInfo.setButtonB(strB);
            newProcessInfo.setCreateTime(new Date());
            processInfoService.saveProcessInfo(newProcessInfo);
            processInfo.setFeedbackId(newProcessInfo.getId());
            processInfoService.saveProcessInfo(processInfo);
            // 更新接收简历信息
            receviedInfo.setLatestFeedback("修改入职时间为：" + DateUtil.dateToString(timeFormat,DateUtil.ymdFormat) + "日");
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
    public AjaxResponse quitFeedback(String processInfoId,String quitTime){
        if(StringUtils.isBlank(processInfoId)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"id不能为空！");
        }
        Date timeFormat = null;
        if(quitTime != null && quitTime != "undefined"){
            timeFormat = DateUtil.stringToDate(quitTime);
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
        newProcessInfo.setProcessContent("已于：" + DateUtil.dateToString(timeFormat,DateUtil.ymdFormat) + "日离职");
        newProcessInfo.setOwner(1);
        newProcessInfo.setIsInterview(processInfo.getIsInterview());
        newProcessInfo.setIsEntry(processInfo.getIsEntry());
        newProcessInfo.setIsQuit(1);
        newProcessInfo.setIsEnd(1);
        newProcessInfo.setSortNumber(processInfo.getSortNumber() + 1);
        //设置按钮
        List<JSONObject> jsonA = commonService.getJsonList("A","quitFeedback",false);
        String strA = jsonA.toString().replace("\\","");
        newProcessInfo.setButtonA(strA);
        newProcessInfo.setCreateTime(new Date());
        // 保存流程表
        processInfoService.saveProcessInfo(newProcessInfo);
        processInfo.setFeedbackId(newProcessInfo.getId());
        processInfoService.saveProcessInfo(processInfo);
        //保存接收简历表
        receviedInfo.setLatestFeedback("已于：" + DateUtil.dateToString(timeFormat,DateUtil.ymdFormat).trim() + "日离职");
        return new AjaxResponse(ResponseCode.APP_SUCCESS);
    }

    /* *
     * A端 已知悉
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    @RequestMapping(value = "/isKnow",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse isKnow(String processInfoId){
        ProcessInfo processInfo = processInfoService.getProcessInfoById(processInfoId);
        ProcessInfo newProcessInfo = new ProcessInfo();
        newProcessInfo.setReceviedId(processInfo.getReceviedId());
        newProcessInfo.setbUserId(processInfo.getbUserId());
        newProcessInfo.setaUserId(processInfo.getaUserId());
        newProcessInfo.setStatus(processInfo.getStatus());
        newProcessInfo.setDelFlag(0);
        newProcessInfo.setProcessContent("已知晓，谢谢");
        newProcessInfo.setOwner(0);
        newProcessInfo.setIsInterview(processInfo.getIsInterview());
        newProcessInfo.setIsEntry(processInfo.getIsEntry());
        newProcessInfo.setInterviewTime(processInfo.getInterviewTime());
        newProcessInfo.setEntryTime(processInfo.getEntryTime());
        newProcessInfo.setIsQuit(processInfo.getIsQuit());
        newProcessInfo.setQuitTime(processInfo.getQuitTime());
        newProcessInfo.setIsEnd(processInfo.getIsEnd());
        newProcessInfo.setSortNumber(processInfo.getSortNumber() + 1);
        newProcessInfo.setCreateTime(new Date());
        newProcessInfo.setIsFeedback(0);
//        newProcessInfo.setButtonA(processInfo.getButtonA());
        newProcessInfo.setButtonB(processInfo.getButtonB());
        processInfoService.saveProcessInfo(newProcessInfo);
        processInfo.setIsFeedback(1);
        processInfo.setFeedbackId(newProcessInfo.getId());
        processInfoService.saveProcessInfo(processInfo);
        return new AjaxResponse(ResponseCode.APP_SUCCESS);
    }

    /* *
     * A端 申诉
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    @RequestMapping(value = "/appeal",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse appeal(String processInfoId,String content){
        ProcessInfo processInfo = processInfoService.getProcessInfoById(processInfoId);
        ProcessInfo newProcessInfo = new ProcessInfo();
        processInfo.setIsFeedback(1);
        newProcessInfo.setReceviedId(processInfo.getReceviedId());
        newProcessInfo.setbUserId(processInfo.getbUserId());
        newProcessInfo.setaUserId(processInfo.getaUserId());
        newProcessInfo.setStatus(processInfo.getStatus());
        newProcessInfo.setDelFlag(0);
        newProcessInfo.setProcessContent(content);
        newProcessInfo.setOwner(0);
        newProcessInfo.setIsInterview(processInfo.getIsInterview());
        newProcessInfo.setIsEntry(processInfo.getIsEntry());
        newProcessInfo.setInterviewTime(processInfo.getInterviewTime());
        newProcessInfo.setEntryTime(processInfo.getEntryTime());
        newProcessInfo.setIsQuit(processInfo.getIsQuit());
        newProcessInfo.setQuitTime(processInfo.getQuitTime());
        newProcessInfo.setIsEnd(processInfo.getIsEnd());
        newProcessInfo.setCreateTime(new Date());
        newProcessInfo.setIsFeedback(0);
        //设置按钮
        List<JSONObject> jsonB = commonService.getJsonList("B","appeal",false);
        String strB = jsonB.toString().replace("\\","");
        newProcessInfo.setButtonB(strB);
        newProcessInfo.setSortNumber(processInfo.getSortNumber() + 1);
        newProcessInfo.setCreateTime(new Date());
        processInfoService.saveProcessInfo(newProcessInfo);
        processInfo.setFeedbackId(newProcessInfo.getId());
        processInfoService.saveProcessInfo(processInfo);
        return new AjaxResponse(ResponseCode.APP_SUCCESS);
    }

    /* *
     * A端 认可
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    @RequestMapping(value = "/approval",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse approval(String processInfoId){
        ProcessInfo processInfo = processInfoService.getProcessInfoById(processInfoId);
        ProcessInfo newProcessInfo = new ProcessInfo();
        newProcessInfo.setReceviedId(processInfo.getReceviedId());
        newProcessInfo.setbUserId(processInfo.getbUserId());
        newProcessInfo.setaUserId(processInfo.getaUserId());
        newProcessInfo.setStatus(processInfo.getStatus());
        newProcessInfo.setDelFlag(0);
        newProcessInfo.setProcessContent("认可");
        newProcessInfo.setOwner(0);
        newProcessInfo.setIsInterview(processInfo.getIsInterview());
        newProcessInfo.setIsEntry(processInfo.getIsEntry());
        newProcessInfo.setInterviewTime(processInfo.getInterviewTime());
        newProcessInfo.setEntryTime(processInfo.getEntryTime());
        newProcessInfo.setIsQuit(processInfo.getIsQuit());
        newProcessInfo.setQuitTime(processInfo.getQuitTime());
        newProcessInfo.setIsEnd(processInfo.getIsEnd());
        //设置按钮
        List<JSONObject> jsonB = new ArrayList();
        Integer status = processInfo.getStatus();
        if(status != null && status == 1){
            jsonB = commonService.getJsonList("B","interviewFeedback",false);
        }else if(status != null && status == 2){
            jsonB = commonService.getJsonList("B","entryFeedback",false);
        }else if(status != null && status == 3){
            jsonB = commonService.getJsonList("B","quitFeedback",false);
        }
        String strB = jsonB.toString().replace("\\","");
        newProcessInfo.setButtonB(strB);
        newProcessInfo.setCreateTime(new Date());
        newProcessInfo.setSortNumber(processInfo.getSortNumber() + 1);
        newProcessInfo.setIsFeedback(0);
        newProcessInfo.setCreateTime(new Date());
        processInfoService.saveProcessInfo(newProcessInfo);
        processInfo.setIsFeedback(1);
        processInfo.setFeedbackId(newProcessInfo.getId());
        processInfoService.saveProcessInfo(processInfo);
        return new AjaxResponse(ResponseCode.APP_SUCCESS);
    }

    //向平台申诉









}
