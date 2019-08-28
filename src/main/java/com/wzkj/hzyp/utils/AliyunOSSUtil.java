package com.wzkj.hzyp.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.wzkj.hzyp.entity.FileEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/* *
 * 阿里鱼上传文件工具类
 * @author zhaoMaoJie
 * @date 2019/8/9 0009
 */
@Component
public class AliyunOSSUtil {

    private static final Logger logger = LoggerFactory.getLogger(AliyunOSSUtil.class);

    @Autowired
    private AliyunOSSconfig aliyunOSSconfig;


    /* *
     * 上传文件
     * @author zhaoMaoJie
     * @date 2019/8/9 0009
     */
    public FileEntity uploadFile(File file,String userInfo) {
        //获取配置文件属性
        String endpoint = aliyunOSSconfig.getEndpoint();
        String accessKeyId = aliyunOSSconfig.getAccessKeyId();
        String accessKeySecret = aliyunOSSconfig.getAccessKeySecret();
        String bucketName = aliyunOSSconfig.getBucketName();
        String folder = null;
        //选择不同的存储空间
        if(userInfo != null && userInfo.equals("user")){
            folder = aliyunOSSconfig.getUserFolder();
        }else {
            folder = aliyunOSSconfig.getStoreFolder();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(new Date());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        //
        if (file == null) {
            return null;
        }
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            String fileUrl = folder + "/" + (dateStr + "/" + uuid) + "-" + file.getName();
            // 设置权限(公开读)
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file));
            if (result != null) {
                System.out.println(result);
                logger.info("------OSS文件上传成功------" + fileUrl);
                return new FileEntity(
                        file.length(),//文件大小
                        fileUrl,//文件的绝对路径
                        aliyunOSSconfig.getWebUrl() + "/" + fileUrl,//文件的web访问地址
                        suffix,//文件后缀
                        "",//存储的bucket
                        bucketName,//原文件名
                        folder//存储的文件夹
                );
            }
        }catch (OSSException oe){
            logger.error(oe.getMessage());
        }catch (ClientException  ce){
            logger.error(ce.getErrorMessage());
        }finally {
            //关闭连接
            ossClient.shutdown();
        }
        return null;
    }


}
