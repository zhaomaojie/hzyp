package com.wzkj.hzyp.controller;

import com.wzkj.hzyp.common.AjaxResponse;
import com.wzkj.hzyp.common.ResponseCode;
import com.wzkj.hzyp.entity.FileEntity;
import com.wzkj.hzyp.entity.StoreInfo;
import com.wzkj.hzyp.service.StoreInfoService;
import com.wzkj.hzyp.utils.AliyunOSSUtil;
import com.wzkj.hzyp.utils.FileUtil;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
@RequestMapping("/store")
@Controller
@Api(value = "商户相关的接口",tags = "商户相关相关的controller")
public class StoreInfoController extends BaseController {

    @Autowired
    private StoreInfoService storeInfoService;

    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;

    @RequestMapping(value = "/saveStore",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "id",value = "商户id",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "name",value = "商户名称",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "cityCode",value = "商户位置code",paramType = "query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "address",value = "商户地址",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "industryCode",value = "行业代码",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "contactName",value = "联系人",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "businessImg",value = "商业执照",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "companyImg",value = "工作场景图",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "logo",value = "公司logo",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "phone",value = "联系人电话",paramType = "query",required = true,dataType = "String")
            })
    @ApiOperation(value = "商户注册/修改",notes = "注册或修改商户时使用")
    public AjaxResponse saveStore(String id,String name,String cityCode,String address,String industryCode,
                                  String contactName,String businessImg,String companyImg,String logo,String phone){
        //id为空 表示 新增商户
        if(StringUtils.isBlank(id)){
            StoreInfo storeInfo = new StoreInfo();
            storeInfo.setName(name);
            storeInfo.setCityCode(cityCode);
            storeInfo.setAddress(address);
            storeInfo.setIndustryCode(industryCode);
            storeInfo.setContactName(contactName);
            //保存 用户图片
            storeInfo.setBusinessImg(businessImg);
            storeInfo.setCompanyImg(companyImg);
            storeInfo.setLogo(logo);
            storeInfo.setCreateTime(new Date());
            storeInfo.setUpdateTime(new Date());
            storeInfo.setDelFlag(0);
            String userId = getBuser().getId();
            storeInfo.setbUserId(userId);
            storeInfoService.saveStoreInfo(storeInfo);
            return new AjaxResponse(ResponseCode.APP_SUCCESS);
        }else {
            StoreInfo storeInfo = storeInfoService.getStoreInfoById(id);
            storeInfo.setName(name);
            storeInfo.setCityCode(cityCode);
            storeInfo.setAddress(address);
            storeInfo.setIndustryCode(industryCode);
            storeInfo.setContactName(contactName);
            //保存图片信息
            storeInfo.setBusinessImg(businessImg);
            storeInfo.setCompanyImg(companyImg);
            storeInfo.setLogo(logo);
            storeInfo.setCreateTime(new Date());
            storeInfo.setUpdateTime(new Date());
            storeInfo.setDelFlag(0);
            String userId = getBuser().getId();
            storeInfo.setbUserId(userId);
            storeInfoService.saveStoreInfo(storeInfo);
            return new AjaxResponse(ResponseCode.APP_SUCCESS);
        }
    }

    /* *
     * 上传图片到oss 并返回web访问地址
     * @author zhaoMaoJie
     * @date 2019/8/9 0009
     */
    @RequestMapping(value = "/uploadImage",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "file",value = "上传文件",paramType = "query",required = true,dataType = "string")})
    @ApiOperation(value = "B端注册时 上传图片接口",notes = "返回网页访问地址字符串")
    public AjaxResponse uploadImage(MultipartFile file){
        if(file.isEmpty()){
            return new AjaxResponse(ResponseCode.APP_FAIL,"请先选择图片！");
        }
//        logger.info("文件上传");
        String filename = file.getOriginalFilename();
        System.out.println(filename);

        try {
            // 判断文件
            if (file!=null) {
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    // 上传到OSS
                    FileEntity fileEntity =  aliyunOSSUtil.uploadFile(newFile);
                    if(fileEntity == null){
                        return new AjaxResponse(ResponseCode.APP_FAIL,"上传失败！");
                    }
                    //删除本地图片
                    File directory = new File("");//参数为空
                    String courseFile = directory.getCanonicalPath() ;
                    String path = courseFile + "\\" + filename;
                    FileUtil.deleteFile(path);
                    String webUrl = fileEntity.getWebUrl();
                    return new AjaxResponse(ResponseCode.APP_SUCCESS,webUrl);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /* *
     * 删除商户
     * @author zhaoMaoJie
     * @date 2019/8/9 0009
     */
    @RequestMapping(value = "/deleteStore",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "id",value = "商户id",paramType = "query",required = true,dataType = "string")})
    @ApiOperation(value = "删除商户 ",notes = "用于内管系统 暂不用测试")
    public AjaxResponse deleteStore(String id){
        if(StringUtils.isBlank(id)){
            return new AjaxResponse(ResponseCode.APP_FAIL,"id不能够为空！");
        }
        storeInfoService.deleteStoreById(id);
        return new AjaxResponse(ResponseCode.APP_SUCCESS);
    }

}
