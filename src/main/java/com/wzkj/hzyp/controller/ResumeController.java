package com.wzkj.hzyp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wzkj.hzyp.common.AjaxResponse;
import com.wzkj.hzyp.common.ResponseCode;
import com.wzkj.hzyp.entity.*;
import com.wzkj.hzyp.service.*;
import com.wzkj.hzyp.utils.DateUtil;
import com.wzkj.hzyp.utils.ImageConfig;
import com.wzkj.hzyp.utils.StringUtils;
import com.wzkj.hzyp.vo.ResumeRecordVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private CommonService commonService;

    @Autowired
    private ImageConfig imageConfig;

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
            resumeInfo.setName(name.toString());
            resumeInfo.setPhone(phone.toString());
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
            if(gender == 0){
                resumeInfo.setAvatar(imageConfig.getResumeMan());
            }else {
                resumeInfo.setAvatar(imageConfig.getResumeWoman());
            }
            resumeInfoService.saveResume(resumeInfo);
            String newId = resumeInfo.getId();
            Map<String,Object> map = new HashMap();
            map.put("id",newId);
            return new AjaxResponse(ResponseCode.APP_SUCCESS,"新增成功",map);
        }else {
            ResumeInfo resume = resumeInfoService.getResumeById(id);
            resume.setName(name.toString());
            resume.setPhone(phone.toString());
            resume.setGender(gender);
            resume.setAge(age);
            resume.setUpdateTime(new Date());
            resume.setEducationDegree(educationDegree);
            resume.setWorkExperience(workExperience);
            resume.setUniversity(university);
            resume.setNativePlace(nativePlace);
            resume.setPastWork(pastWork);
            resume.setIntentionalWork(intentionalWork);
            if(gender == 0){
                resume.setAvatar(imageConfig.getResumeMan());
            }else {
                resume.setAvatar(imageConfig.getResumeWoman());
            }
            resumeInfoService.saveResume(resume);
            String newId = resume.getId();
            Map<String,Object> map = new HashMap();
            map.put("id",newId);
            return new AjaxResponse(ResponseCode.APP_SUCCESS,"修改成功",map);
        }
    }

    /* *
     * 绑定头像
     * @author zhaoMaoJie
     * @date 2019/8/7 0007
     */
    @RequestMapping(value = "/bindingAvatar",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse bindingAvatar(String id,MultipartFile file ){
        if(StringUtils.isBlank(id) || file.isEmpty()){
            return new AjaxResponse(ResponseCode.APP_FAIL,"参数有误！");
        }
        //绑定头像 首先判断是否有上传头像 如果有就执行上传然后绑定到简历对象
        //如果没有上传 那么根据性别自动绑定默认图片
        boolean isBind = resumeInfoService.bindingAvatar(id, file);
        if(isBind){
            return new AjaxResponse(ResponseCode.APP_SUCCESS,"绑定成功！");
        }else {
            return new AjaxResponse(ResponseCode.APP_FAIL,"绑定失败");
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
    public AjaxResponse pushResume(String jobId,String resumeId,String interviewDate,String  formId){
        if(StringUtils.isBlank(jobId) || StringUtils.isBlank(resumeId)
                || StringUtils.isBlank(interviewDate) ){
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
            //简历推送成功后 在流程表中加入该数据 加入两条数据 一条短信模板 一条流程内容
            ResumeInfo resumeInfo = resumeInfoService.getResumeById(resumeId);
            String receviedId = receviedInfo.getId();
            //短信模板
            String storeId = jobInfo.getbStoreId();
            StoreInfo storeInfo = storeInfoService.getStoreInfoById(storeId);
            String address = storeInfo.getAddress();
            ProcessInfo newProcessInfo = new ProcessInfo();
            newProcessInfo.setReceviedId(receviedId);
            newProcessInfo.setaUserId(aUserId);
            newProcessInfo.setbUserId(bUserId);
            newProcessInfo.setSortNumber(0);
            newProcessInfo.setOwner(0);
            newProcessInfo.setDelFlag(0);
            StringBuilder sms = new StringBuilder();
            sms.append("尊敬的求职者").append(":").append(resumeInfo.getName()).append("你好").append(",")
                    .append("现邀请您参加").append(storeInfo.getName())
                    .append(jobInfo.getJobName()).append("岗位的面试").append("。")
                    .append("面试时间为").append(":").append(interviewDate.substring(0,interviewDate.lastIndexOf(":"))).append(",")
                    .append("面试地址为").append(":").append(address).append("。")
                    .append("请准时参加").append(",").append("预祝您面试通过");
            newProcessInfo.setProcessContent(sms.toString());
            newProcessInfo.setCreateTime(new Date());
            List<JSONObject> json = commonService.getJsonList("A","sms",false);
            String jsonStr = json.toString().replace("\\","");
            newProcessInfo.setButtonA(jsonStr);
            newProcessInfo.setIsFeedback(1);
            newProcessInfo.setStatus(1);
            processInfoService.saveProcessInfo(newProcessInfo);

            //流程反馈
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setReceviedId(receviedId);
            processInfo.setaUserId(aUserId);
            processInfo.setbUserId(bUserId);
            processInfo.setStatus(1);
            processInfo.setIsFeedback(0);
            processInfo.setDelFlag(0);
            String processContent = resumeInfo.getName() + "将于" + interviewDate + "参加面试";
            String begin = processContent.substring(0,processContent.indexOf("日") + 6);
            String end = processContent.substring(processContent.indexOf("日") + 6);
            String contentFormat = begin + "-" + end;
            processInfo.setProcessContent(contentFormat);
            processInfo.setOwner(0);
//            Integer sortNumber =  processInfoService.getNewSortNumber(receviedId);
            processInfo.setSortNumber(1);

            List<JSONObject> jsonObjects = commonService.getJsonList("A","interviewFeedback",false);
            List<JSONObject> jsonB = commonService.getJsonList("B","interviewFeedback",false);
            String str = jsonObjects.toString().replace("\\","");
            String str1 = jsonB.toString().replace("\\","");
            processInfo.setButtonA(str);
            processInfo.setButtonB(str1);
            processInfo.setCreateTime(new Date());
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
//            Map<String,Object> map = new HashMap();
//            String storeId = jobInfo.getbStoreId();
//            StoreInfo storeInfo = storeInfoService.getStoreInfoById(storeId);
//            String address = storeInfo.getAddress().trim();
//            map.put("name",resumeInfo.getName());
//            map.put("date",interviewDate);
////            map.put("time",interviewTime);
//            map.put("address",address);
            //给B端发送模板消息 start
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
            WxMaTemplateData data5 = new WxMaTemplateData("keyword5", DateUtil.dateToString(receviedInfo.getCreateTime()));
            templateDataList.add(data1);
            templateDataList.add(data2);
            templateDataList.add(data3);
            templateDataList.add(data4);
            templateDataList.add(data5);
            //3，设置推送消息
            BuserInfo buserInfo = buserService.getBuserById(bUserId);
            String openId = buserInfo.getOpenId();
            wxTemplateService.pushResumeTemplate(openId,formId,templateDataList,wxMaService);
//            if(isSuccess){
//                map.put("isTemplate",true);
//            }else {
//                map.put("isTemplate",false);
//            }
//            StringBuilder message = new StringBuilder();
//            message.append("尊敬的求职者您好，现邀请你参加").append(":").append(storeInfo.getName()).append("公司的面试").append(",")
//                    .append("面试时间为:").append(DateUtil.dateToString(receviedInfo.getCreateTime())).append(",").append("预祝您面试通过!");
//            map.put("message",message);
            //给B端发送模板消息 end
            return new AjaxResponse(ResponseCode.APP_SUCCESS);
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
    public AjaxResponse pushResumeList(Integer pageNum,Integer pageSize,Integer status,String jobId,String name,String phone,String keyWord){
        if(StringUtils.isBlank(jobId)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"id不能为空！");
        }else {
            String userId = getLoginUser().getId();
            PageHelper.startPage(pageNum,pageSize);
            List<Map<String,Object>> list = resumeInfoService.pushResumeList(userId,jobId,status,keyWord);
            PageInfo page = new PageInfo(list);
            Map map = commonService.getMapByList(page,list);
            return new AjaxResponse(ResponseCode.APP_SUCCESS,map);
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
    public AjaxResponse resumeList(Integer pageNum,Integer pageSize,Integer status,String keyWord){
        AuserInfo auserInfo = getLoginUser();
        String userId = auserInfo.getId();
        PageHelper.startPage(pageNum,pageSize);
        List<ResumeInfo> list = resumeInfoService.resumeList(userId,status,keyWord);
        PageInfo page = new PageInfo(list);
        Map map = commonService.getMapByList(page,list);
        return new AjaxResponse(ResponseCode.APP_SUCCESS,map);
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
            List<ResumeRecordVO> list = resumeInfoService.resumeRecord(id);
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
    public AjaxResponse myCandidate(Integer pageNum,Integer pageSize,Integer status,String keyWord){
        String bUserId = getBuser().getId();
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> list = resumeInfoService.myCandidate(bUserId,status,keyWord);
        PageInfo page = new PageInfo(list);
        Map map = commonService.getMapByList(page,list);
        return new AjaxResponse(ResponseCode.APP_SUCCESS,map);
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
