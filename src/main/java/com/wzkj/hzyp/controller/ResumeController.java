package com.wzkj.hzyp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.github.pagehelper.PageHelper;
import com.wzkj.hzyp.common.AjaxResponse;
import com.wzkj.hzyp.common.ResponseCode;
import com.wzkj.hzyp.entity.*;
import com.wzkj.hzyp.service.*;
import com.wzkj.hzyp.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@RequestMapping("/resume")
@ResponseBody
@Component
@Api(value = "简历相关的接口",tags = "简历相关的controller")
public class ResumeController extends BaseController {


    @Autowired
    private StoreInfoService storeInfoService;

    @Autowired
    private ProcessInfoService processInfoService;

    @Autowired
    private JobInfoService jobInfoService;

    @Autowired
    private ResumeInfoService resumeInfoService;

    @Autowired
    private WxTemplateService wxTemplateService;

    /* *
     * 保存/修改简历
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    @RequestMapping(value = "/saveResume",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "id",value = "简历id",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "name",value = "名字",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "phone",value = "手机号",paramType = "query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "gender",value = "性别",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "age",value = "年龄",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "educationDegree",value = "学历 0 1 2 3 分别对应 不限，高中，大专，本科及以上",
                    paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "workExperience",value = "工作经验 0 1 2 3分别对应 不限，一年以上，三年以上，五年以上",
                    paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "university",value = "毕业院校",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "nativePlace",value = "籍贯",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "pastWork",value = "过往工作",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "intentionalWork",value = "意向工作",paramType = "query",required = true,dataType = "String")
            })
    @ApiOperation(value = "A端 保存/修改简历",notes = "简历页面使用")
    public AjaxResponse saveResume(String id,String name,String phone,Integer gender,Integer age,Integer educationDegree ,Integer workExperience,
                                   String university,String nativePlace,String pastWork,String intentionalWork){
        if(StringUtils.isBlank(id)){
            AuserInfo auserInfo = getLoginUser();
            String userId = auserInfo.getId();
            ResumeInfo resumeInfo = new ResumeInfo();
            resumeInfo.setName(name);
            resumeInfo.setPhone(phone);
            resumeInfo.setGender(gender);
            resumeInfo.setAge(age);
            resumeInfo.setCreateTime(new Date());
            resumeInfo.setUpdateTime(new Date());
            resumeInfo.setDelFlag(0);
            resumeInfo.setaUserId(userId);
            resumeInfo.setEducationDegree(educationDegree);
            resumeInfo.setWorkExperience(workExperience);
            resumeInfo.setUniversity(university);
            resumeInfo.setNativePlace(nativePlace);
            resumeInfo.setStatus(0);
            resumeInfo.setPastWork(pastWork);
            resumeInfo.setIntentionalWork(intentionalWork);
            resumeInfoService.saveResume(resumeInfo);
            return new AjaxResponse(ResponseCode.APP_SUCCESS);
        }else {
            ResumeInfo resume = resumeInfoService.getResumeById(id);
            resume.setName(name);
            resume.setPhone(phone);
            resume.setGender(gender);
            resume.setAge(age);
            resume.setUpdateTime(new Date());
            resume.setEducationDegree(educationDegree);
            resume.setWorkExperience(workExperience);
            resume.setUniversity(university);
            resume.setNativePlace(nativePlace);
            resume.setPastWork(pastWork);
            resume.setIntentionalWork(intentionalWork);
            resumeInfoService.saveResume(resume);
            return new AjaxResponse(ResponseCode.APP_SUCCESS);
        }
    }

    /* *
     * 删除简历
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    @RequestMapping(value = "/deleteResume",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "id",value = "简历id",paramType = "query",required = true,dataType = "string")})
    @ApiOperation(value = "A端 删除简历",notes = "用于A端简历管理")
    public AjaxResponse deleteResume(String id){
        if(StringUtils.isBlank(id)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"id不能为空！");
        }else {
            resumeInfoService.deleteResumeById(id);
            return new AjaxResponse(ResponseCode.APP_SUCCESS);
        }
    }

    /* *
     * 推送简历
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    @RequestMapping(value = "/pushResume",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "jobId",value = "岗位id",paramType = "query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "resumeId",value = "简历id",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "interviewDate",value = "面试日期",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "interviewTime",value = "面试时间",paramType = "query",required = true,dataType = "String")
            })
    @ApiOperation(value = "A端 推送简历",notes = "前端需判断相关硬属性")
    public AjaxResponse pushResume(String jobId,String resumeId,String interviewDate,String interviewTime,String  formId){
        if(StringUtils.isBlank(jobId) || StringUtils.isBlank(resumeId)
                || StringUtils.isBlank(interviewDate) || StringUtils.isBlank(interviewTime)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"数据有误！");
        }else {
            // 推送简历 首先要判断岗位的剩余情况 是否允许推送
            JobInfo jobInfo = jobInfoService.getJobInfoById(jobId);
            Integer totalNum = jobInfo.getTotalResumeNumber();
            Integer num = jobInfo.getReceviedResumeNumber();
            if((totalNum - num) <= 0){
                return new AjaxResponse(ResponseCode.APP_FAIL,"该岗位简历已达上限！");
            }
            //然后判断该份简历 是否已经被推送过这个岗位
            boolean isPush = resumeInfoService.isPush(jobId, resumeId);
            if(isPush){
                return new AjaxResponse(ResponseCode.APP_FAIL,"该简历已被推送过该岗位，请勿重复推送！");
            }
            //允许推送 且没有重复的情况下进行推送 B端接收简历表加入数据
            String aUserId = getLoginUser().getId();
            String bUserId = jobInfo.getbUserId();
            ReceviedInfo receviedInfo = new ReceviedInfo();
            receviedInfo.setJobId(jobId);
            receviedInfo.setResumeId(resumeId);
            receviedInfo.setCreateTime(new Date());
            receviedInfo.setDelFlag(0);
            receviedInfo.setaUserId(aUserId);
            receviedInfo.setbUserId(bUserId);
            receviedInfo.setLatestFeedback("将于" + interviewDate + "日" + "面试");
            receviedInfo.setStatus(0);
            resumeInfoService.pushResume(receviedInfo);
            //简历推送成功后 在流程表中加入该数据
            ResumeInfo resumeInfo = resumeInfoService.getResumeById(resumeId);
            String receviedId = receviedInfo.getId();
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setReceviedId(receviedId);
            processInfo.setaUserId(aUserId);
            processInfo.setbUserId(bUserId);
            processInfo.setStatus(1);
            processInfo.setIsFeedback(0);
            processInfo.setDelFlag(0);
            String processContent = resumeInfo.getName() + "将于" + interviewDate + interviewTime + "参加面试";
            processInfo.setProcessContent(processContent);
            processInfo.setOwner(0);
            processInfoService.saveProcessInfo(processInfo);
            //推送成功后改变原始岗位的简历数 收藏岗位的状态 B端接收简历表加入数据 流程表加入内容
            //改变岗位收到的简历数
            jobInfo.setReceviedResumeNumber(jobInfo.getReceviedResumeNumber() + 1);
            jobInfoService.saveJobInfo(jobInfo);
            //改变我收藏岗位的岗位状态
            jobInfoService.changeCollectionStatus(aUserId,jobId,1);
            //改变简历状态 变为已推荐过
            resumeInfo.setStatus(1);
            resumeInfoService.saveResume(resumeInfo);
            //推送简历后 应该给求职者发送面试短信 取消短信平台 暂时不发短信
            //给前端返回面试信息
            Map<String,Object> map = new HashMap();
            String storeId = jobInfo.getbStoreId();
            StoreInfo storeInfo = storeInfoService.getStoreInfoById(storeId);
            String address = storeInfo.getAddress().trim();
            map.put("name",resumeInfo.getName());
            map.put("date",interviewDate);
            map.put("time",interviewTime);
            map.put("address",address);
            //给B端发送模板消息
            //1,配置小程序信息
            WxMaInMemoryConfig wxConfig = wxTemplateService.getWxConfig();
            WxMaService wxMaService = new WxMaServiceImpl();
            wxMaService.setWxMaConfig(wxConfig);
            //2,设置模版信息（keyword1：类型，keyword2：内容）
            List<WxMaTemplateData> templateDataList = new ArrayList<>(2);
            WxMaTemplateData data1 = new WxMaTemplateData("keyword1", resumeInfo.getName());
            WxMaTemplateData data2 = new WxMaTemplateData("keyword2", resumeInfo.getGender() == 0 ? "男" : "女");
            WxMaTemplateData data3 = new WxMaTemplateData("keyword3", resumeInfo.getAge().toString());
            WxMaTemplateData data4 = new WxMaTemplateData("keyword4", jobInfo.getJobName());
            WxMaTemplateData data5 = new WxMaTemplateData("keyword5", receviedInfo.getCreateTime().toString());
            templateDataList.add(data1);
            templateDataList.add(data2);
            templateDataList.add(data3);
            templateDataList.add(data4);
            templateDataList.add(data5);
            //3，设置推送消息
            BuserInfo buserInfo = buserService.getBuserById(bUserId);
            String openId = buserInfo.getOpenId();
            boolean isSuccess = wxTemplateService.pushResumeTemplate(openId,formId,templateDataList,wxMaService);
            if(isSuccess){
                map.put("isTemplate",true);
            }else {
                map.put("isTemplate",false);
            }
            return new AjaxResponse(ResponseCode.APP_SUCCESS,map);
        }
    }



    /* *
     * A端 查看岗位推送的简历
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    @RequestMapping(value = "/pushResumeList",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum",value = "分页数量",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize",value = "分页页数",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "status",value = "简历状态 0 未推送 1已推送",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "jobId",value = "岗位id",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "name",value = "名字，用于搜索",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "phone",value = "电话，用于搜索",paramType = "query",required = true,dataType = "String")
             })
    @ApiOperation(value = "查看对应岗位推送的简历",notes = "我的项目中管理使用")
    public AjaxResponse pushResumeList(Integer pageNum,Integer pageSize,Integer status,String jobId,String name,String phone){
        if(StringUtils.isBlank(jobId)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"id不能为空！");
        }else {
            String userId = getLoginUser().getId();
            PageHelper.startPage(pageNum,pageSize);
            List<Map<String,Object>> list = resumeInfoService.pushResumeList(userId,jobId,status,name,phone);
            return new AjaxResponse(ResponseCode.APP_SUCCESS,list);
        }
    }

    /* *
     * A端查看创建的简历列表
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    @RequestMapping(value = "/resumeList",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum",value = "分页数量",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize",value = "分页页数",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "status",value = "简历状态 0 未推送 1已推送",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "name",value = "名字，用于搜索",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "phone",value = "电话，用于搜索",paramType = "query",required = true,dataType = "String")
    })
    @ApiOperation(value = "查看自己创建的简历",notes = "简历管理中使用")
    public AjaxResponse resumeList(Integer pageNum,Integer pageSize,Integer status,String name,String phone){
        AuserInfo auserInfo = getLoginUser();
        String userId = auserInfo.getId();
        PageHelper.startPage(pageNum,pageSize);
        List<ResumeInfo> list = resumeInfoService.resumeList(userId,status,name,phone);
        return new AjaxResponse(ResponseCode.APP_SUCCESS,list);
    }

    /* *
     * 根据id获取简历详情
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    @RequestMapping(value = "/resumeDetail",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "id",value = "简历id",paramType = "query",required = true,dataType = "string")})
    @ApiOperation(value = "A端根据id查看简历详情",notes = "用于A端简历管理")
    public AjaxResponse resumeDetail(String id){
        if(StringUtils.isBlank(id)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"id不能为空！");
        }else {
            ResumeInfo resumeInfo = resumeInfoService.getResumeById(id);
            return new AjaxResponse(ResponseCode.APP_SUCCESS,resumeInfo);
        }
    }

    /* *
     * 获取简历推荐记录
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    @RequestMapping(value = "/resumeRecord",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum",value = "分页数量",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize",value = "分页页数",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "id",value = "简历id",paramType = "query",required = true,dataType = "string")})
    @ApiOperation(value = "A端根据id查看简历推荐记录",notes = "用于A端简历管理，点击【推荐记录】时调用")
    public AjaxResponse resumeRecord(Integer pageNum,Integer pageSize,String id){
        if(StringUtils.isBlank(id)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"id不能为空!");
        }else {
            PageHelper.startPage(pageNum,pageSize);
            List<Map<String,Object>> list = resumeInfoService.resumeRecord(id);
            return new AjaxResponse(ResponseCode.APP_SUCCESS,list);
        }
    }

    /* *
     * B端 获取我的候选人列表
     * @author zhaoMaoJie
     * @date 2019/8/11 0011
     */
    @RequestMapping(value = "/myCandidate",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum",value = "分页数量",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize",value = "分页页数",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "status",value = "接收简历状态 0交付中 1已完成（指已入职） 2已结束",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "name",value = "名字，用于搜索",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "phone",value = "电话，用于搜索",paramType = "query",required = true,dataType = "String")
    })
    @ApiOperation(value = "查看自己创建的简历",notes = "简历管理中使用")
    public AjaxResponse myCandidate(Integer pageNum,Integer pageSize,Integer status,String name,String phone){
        String bUserId = getBuser().getId();
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> list = resumeInfoService.myCandidate(bUserId,status,name,phone);
        return new AjaxResponse(ResponseCode.APP_SUCCESS,list);
    }


//    /* *
//     * 不使用云短信 该方法暂时废弃
//     * @author zhaoMaoJie
//     * @date 2019/8/5 0005
//     */
//    @RequestMapping("/sendMessage")
//    @ResponseBody
//    public AjaxResponse sendMessage(String jobId,String resumeId,String date,String time){
//        if(StringUtils.isBlank(resumeId) || StringUtils.isBlank(date) || StringUtils.isBlank(time)){
//            return new AjaxResponse(ResponseCode.APP_FAIL,"信息有误！");
//        }else {
//            ResumeInfo resumeInfo = resumeInfoService.getResumeById(resumeId);
//            String phone = resumeInfo.getPhone();
//            JobInfo jobInfo = jobInfoService.getJobInfoById(jobId);
//            String jobName = jobInfo.getJobName();
//            String bStoreId = jobInfo.getbStoreId();
//            StoreInfo storeInfo = storeInfoService.getStoreInfoById(bStoreId);
//            String address = storeInfo.getAddress();
//
//            return new AjaxResponse(ResponseCode.APP_SUCCESS);
//        }
//    }


}
