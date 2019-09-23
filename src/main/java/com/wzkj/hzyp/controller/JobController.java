package com.wzkj.hzyp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wzkj.hzyp.common.AjaxResponse;
import com.wzkj.hzyp.common.ResponseCode;
import com.wzkj.hzyp.entity.*;
import com.wzkj.hzyp.service.CommonService;
import com.wzkj.hzyp.service.JobInfoService;
import com.wzkj.hzyp.service.StoreInfoService;
import com.wzkj.hzyp.utils.StringUtils;
import com.wzkj.hzyp.vo.JobInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* *
 *
 * @author zhaoMaoJie
 * @date 2019/7/22 0022
 */
@RequestMapping("/job")
@Controller
@Component
@Api(value = "岗位信息相关的接口",tags = "岗位信息相关的controller")
public class JobController extends BaseController {

    @Autowired
    private StoreInfoService storeInfoService;

    @Autowired
    private JobInfoService jobInfoService;

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/jobList",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum",value = "分页数量",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize",value = "分页页数",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "keyWord",value = "关键字 用于搜索",paramType = "query",required = false,dataType = "string"),
            @ApiImplicitParam(name = "label",value = "岗位标签 分别为0 1 2",paramType = "query",required = false,dataType = "Integer")})
    @ApiOperation(value = "默认页面的岗位列表",notes = "A端获取发布的岗位信息")
    public AjaxResponse getJobList(Integer pageNum,Integer pageSize,String keyWord,Integer label){
        PageHelper.startPage(pageNum,pageSize);
        //这里laben暂时不放进判断
        label = null;
        List<JobInfoVO> list = jobInfoService.getJobList(keyWord, label);
        PageInfo page = new PageInfo(list);
        Map map = commonService.getMapByList(page,list);
        return new AjaxResponse(ResponseCode.APP_SUCCESS,map);
    }

    @RequestMapping(value = "/jobDetail",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "id",value = "岗位id",paramType = "query",required = true,dataType = "integer")})
    @ApiOperation(value = "根据岗位获取岗位详情",notes = "从岗位列表中点击接单时调用")
    public AjaxResponse jobDetail(String id){
        if(StringUtils.isBlank(id)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"id不能为空!");
        }else {
            System.out.println(id);
            JobInfoVO jobInfo = jobInfoService.jobDetail(id);
            return new AjaxResponse(ResponseCode.APP_SUCCESS,jobInfo);
        }
    }

    /* *
     * 收藏岗位
     * id 表示岗位id
     * @author zhaoMaoJie
     * @date 2019/8/4 0004
     */
    @RequestMapping(value = "/collectionJob",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "id",value = "岗位id",paramType = "query",required = true,dataType = "string")})
    @ApiOperation(value = "A端用户收藏岗位",notes = "岗位详情页中点击【接单】时调用")
    public AjaxResponse collectionJob(String id){
        if(StringUtils.isBlank(id)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"id不能为空!");
        }else {
            //这里首先需要判断 是否已经收藏过该岗位 不能重复收藏
            AuserInfo auserInfo = getLoginUser();
            String userId = auserInfo.getId();
            boolean isCollection = jobInfoService.isCollection(userId,id);
            //如果已经收藏过该岗位 则不能收复收藏
            //这里暂时用来测试 取消校验是否收藏
            if(isCollection){
                return new AjaxResponse(ResponseCode.APP_FAIL,"该岗位已被您收藏,请勿重复收藏!");
            }else {
                JobInfo jobInfo = jobInfoService.getJobInfoById(id);
                String bUserId = jobInfo.getbUserId();
                CollectionJob collectionJob = new CollectionJob();
                collectionJob.setaUserId(userId);
                collectionJob.setJobId(id);
                collectionJob.setbUserId(bUserId);
                collectionJob.setCreateTime(new Date());
                collectionJob.setUpdateTime(new Date());
                collectionJob.setStatus(0);
                collectionJob.setDelFlag(0);
                jobInfoService.collectionJob(collectionJob);
                return new AjaxResponse(ResponseCode.APP_SUCCESS);
            }

        }
    }

    /* *
     * A端查看收藏岗位
     * @author zhaoMaoJie
     * @date 2019/8/10 0010
     */
    @RequestMapping(value = "/collectionJobList",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum",value = "分页数量",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize",value = "分页页数",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "collectionJobStatus",value = "岗位状态 0未开始 1 已开始 ",paramType = "query",required = false,dataType = "Integer")})
    @ApiOperation(value = "A端 查看收藏的岗位",notes = "即功能中【我的项目】按钮，默认显示已开始的项目")
    public AjaxResponse collectionJobList(Integer pageNum,Integer pageSize,Integer collectionJobStatus){
        AuserInfo auserInfo = getLoginUser();
        String userId = auserInfo.getId();
        PageHelper.startPage(pageNum,pageSize);
        List<JobInfoVO> list = jobInfoService.collectionJobList(userId,collectionJobStatus);
        PageInfo page = new PageInfo(list);
        Map map = commonService.getMapByList(page,list);
        return new AjaxResponse(ResponseCode.APP_SUCCESS,map);
    }

    /* *
     * b端查看发布的岗位信息
     * @author zhaoMaoJie
     * @date 2019/8/10 0010
     */
    @RequestMapping(value = "/publishJobList",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum",value = "分页数量",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize",value = "分页页数",paramType = "query",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "jobName",value = "岗位名称",paramType = "query",required = false,dataType = "string"),
            @ApiImplicitParam(name = "status",value = "岗位状态 审核中 1 已发布 2已下架 ",paramType = "query",required = false,dataType = "Integer")})
    @ApiOperation(value = "B端 查看发布的岗位",notes = "即功能中【发布的项目】按钮，默认显示已发布的项目")
    public AjaxResponse publishJobList(Integer pageNum,Integer pageSize,String jobName,Integer status){
        String userId = getBuser().getId();
        PageHelper.startPage(pageNum,pageSize);
        List<JobInfoVO> list = jobInfoService.publishJobList(userId,jobName,status);
        PageInfo page = new PageInfo(list);
        Map map = commonService.getMapByList(page,list);
        return new AjaxResponse(ResponseCode.APP_SUCCESS,map);
    }

    /* *
     * 保存岗位信息
     * @author zhaoMaoJie
     * @date 2019/8/10 0010
     */
    @RequestMapping(value = "/saveJob",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "id",value = "岗位id",paramType = "query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "industryCode",value = "行业代码",paramType = "query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "jobName",value = "岗位名称",paramType = "query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "description",value = "岗位描述 ",paramType = "query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "num",value = "招聘人数 ",paramType = "query",required = true,dataType = "integer"),
            @ApiImplicitParam(name = "educationDegree",value = "学历 0 1 2 3 分别对应 不限，高中，大专，本科及以上 ",paramType = "query",required = true,dataType = "integer"),
            @ApiImplicitParam(name = "workExperience",value = "工作经验 0 1 2 3分别对应 不限，一年以上，三年以上，五年以上 ",paramType = "query",required = true,dataType = "integer"),
            @ApiImplicitParam(name = "probation",value = "是否有试用期 0没有 1 有 ",paramType = "query",required = true,dataType = "integer"),
            @ApiImplicitParam(name = "trialTime",value = "可面试时间 ",paramType = "query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "trialSalary",value = "试用薪资 ",paramType = "query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "officialSalary",value = "正式薪资 ",paramType = "query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "avgSalary",value = "平均薪资 ",paramType = "query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "reward",value = "岗位赏金 ",paramType = "query",required = true,dataType = "integer"),
            @ApiImplicitParam(name = "overtime",value = "过保时间 ",paramType = "query",required = true,dataType = "integer"),
    })
    @ApiOperation(value = "B端 保存/修改岗位信息",notes = "赏金等 不可以修改")
    public AjaxResponse saveJob(String id,String industryCode,String jobName,String description,Integer num,Integer educationDegree,
                                Integer workExperience,Integer minAge,Integer maxAge,Integer probation,String trialTime,String interviewTime,
                                String trialSalary,String officialSalary,String avgSalary,Integer reward,Integer overtime){
        //reward overtime total_amount 应该为Integer
        BuserInfo buserInfo = getBuser();
        String bUserId = buserInfo.getId();
        StoreInfo storeInfo = storeInfoService.getStoreByUserId(bUserId);
        String storeId = storeInfo.getId();
        //新增 岗位
        if(StringUtils.isBlank(id)){
            JobInfo jobInfo = new JobInfo();
            jobInfo.setbStoreId(storeId);
            jobInfo.setbUserId(bUserId);
            jobInfo.setIndustryCode(industryCode);
            jobInfo.setJobName(jobName);
            jobInfo.setDescription(description);
            jobInfo.setNum(num);
            jobInfo.setEducationDegree(educationDegree);
            jobInfo.setWorkExperience(workExperience);
            jobInfo.setAgeMin(minAge);
            jobInfo.setAgeMax(maxAge);
            jobInfo.setProbation(probation);
            jobInfo.setTrialTime(trialTime);
            jobInfo.setTrialSalary(trialSalary);
            jobInfo.setOfficialSalary(officialSalary);
            jobInfo.setAvgSalary(avgSalary);
            jobInfo.setReward(reward);
            //这里根据前端传过来的数据进行判断过保时间赋值
            if(overtime != null && overtime == 0){
                jobInfo.setOvertime(7);
            }else if(overtime != null && overtime == 1){
                jobInfo.setOvertime(15);
            }else if(overtime != null && overtime == 2){
                jobInfo.setOvertime(30);
            }
            jobInfo.setTotalAmount(num * reward);
            jobInfo.setStatus(0);
            jobInfo.setDelFlag(0);
            Integer total = (int)Math.ceil(num * 1.3);
            jobInfo.setReceviedResumeNumber(0);
            jobInfo.setTotalResumeNumber(total);
            jobInfo.setCreateTime(new Date());
            jobInfo.setUpdateTime(new Date());
            jobInfo.setIsPay(0);
            jobInfo.setInterviewTime(interviewTime);
            jobInfoService.saveJobInfo(jobInfo);
            return new AjaxResponse(ResponseCode.APP_SUCCESS);
        }else { //修改
            JobInfo jobInfo = jobInfoService.getJobInfoById(id);
            jobInfo.setbStoreId(storeId);
            jobInfo.setbUserId(bUserId);
            jobInfo.setIndustryCode(industryCode);
            jobInfo.setJobName(jobName);
            jobInfo.setDescription(description);
            jobInfo.setNum(num);
            jobInfo.setEducationDegree(educationDegree);
            jobInfo.setWorkExperience(workExperience);
            jobInfo.setAgeMin(minAge);
            jobInfo.setAgeMax(maxAge);
            jobInfo.setProbation(probation);
            jobInfo.setTrialTime(trialTime);
            jobInfo.setTrialSalary(trialSalary);
            jobInfo.setOfficialSalary(officialSalary);
            jobInfo.setAvgSalary(avgSalary);
            jobInfo.setReward(reward);
            //这里根据前端传过来的数据进行判断过保时间赋值
            if(overtime != null && overtime == 0){
                jobInfo.setOvertime(7);
            }else if(overtime != null && overtime == 1){
                jobInfo.setOvertime(15);
            }else if(overtime != null && overtime == 2){
                jobInfo.setOvertime(30);
            }
            jobInfo.setTotalAmount(num * reward);
            jobInfo.setStatus(0);
            jobInfo.setDelFlag(0);
            Integer total = (int)Math.ceil(num * 1.3);
            jobInfo.setReceviedResumeNumber(0);
            jobInfo.setTotalResumeNumber(total);
            jobInfo.setCreateTime(new Date());
            jobInfo.setUpdateTime(new Date());
            jobInfo.setIsPay(0);
            jobInfo.setInterviewTime(interviewTime);
            jobInfoService.saveJobInfo(jobInfo);
            return new AjaxResponse(ResponseCode.APP_SUCCESS);
        }
    }

    /* *
     * 更改对应id的岗位状态 上架/下架
     * @author zhaoMaoJie
     * @date 2019/8/10 0010
     */
    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "id",value = "岗位id",paramType = "query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "status",value = "岗位状态",paramType = "query",required = true,dataType = "Integer")})
    @ApiOperation(value = "B端 更改岗位（上架下架）状态",notes = "审核后更改状态")
    public AjaxResponse updateStatus(String id,Integer status){
        if(StringUtils.isBlank(id) || status == null){
            return new AjaxResponse(ResponseCode.APP_FAIL,"数据错误!");
        }
        jobInfoService.updateStatus(id, status);
        return new AjaxResponse(ResponseCode.APP_SUCCESS);
    }

    /* *
     * 获取地址信息
     * @author zhaoMaoJie
     * @date 2019/8/10 0010
     */
    @RequestMapping(value = "/getArea",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "parentCode",value = "初始parentCode为root",paramType = "query",required = true,dataType = "string")})
    @ApiOperation(value = "B端 获取地址信息",notes = "用于获取地址信息")
    public AjaxResponse getAreaByParentCode(String parentCode){
        List<JpCityInfo> list = jobInfoService.getAreaByParentCode(parentCode);
        return new AjaxResponse(ResponseCode.APP_SUCCESS,"获取成功",list);
    }

    /* *
     * 获取行业信息
     * @author zhaoMaoJie
     * @date 2019/8/10 0010
     */
    @RequestMapping(value = "/getIndustry",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "B端 获取行业信息",notes = "用于获取行业信息")
    public AjaxResponse getIndustry(){
        List<JpIndustryInfo> list = jobInfoService.getIndustryInfo();
        return new AjaxResponse(ResponseCode.APP_SUCCESS,list);
    }







}
