package com.cn.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.hf.tools.util.CommonCustomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanghf/f5psyche@163.com
 * @version 1.0
 * @date 13:56 2022/5/29
 */
@RestController
@RequestMapping("/oss")
public class OssUtilsController {

    private static final Logger log = LoggerFactory.getLogger(OssUtilsController.class);

    @Value("${ali-yun.oss.endpoint}")
    private String endpoint;

    @Value("${ali-yun.oss.access-key-id}")
    private String accessKeyId;

    @Value("${ali-yun.oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${ali-yun.oss.bucket-name}")
    private String bucketName;

    @GetMapping(value = "/bucketInfo")
    public Map<String, Object> ossBucketInfo() {
        long sizeTotal = 0L;
        long currentTimeMillis = System.currentTimeMillis();
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            ObjectListing objectListing = null;
            do {
                ListObjectsRequest request = new ListObjectsRequest(bucketName).withDelimiter("/").withMaxKeys(1000);
                if (objectListing != null) {
                    request.setMarker(objectListing.getNextMarker());
                }
                objectListing = ossClient.listObjects(request);
                //获取当前文件夹下所有子目录大小
                List<String> folders = objectListing.getCommonPrefixes();
                for (String folder : folders) {
                    sizeTotal = calculateFolderLength(ossClient, bucketName, folder) + sizeTotal;
                }
                //获取当前文件夹下所有文件大小
                List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
                if (CollectionUtils.isEmpty(sums)) {
                    continue;
                }
                for (OSSObjectSummary s : sums) {
                    sizeTotal = sizeTotal + s.getSize();
                }
            } while (objectListing.isTruncated());
            long endCurrentTimeMillis1 = System.currentTimeMillis();
            log.info("ossBucketInfo：sizeTotal={}, cusTime={}", sizeTotal, endCurrentTimeMillis1 - currentTimeMillis);
            sizeTotal = sizeTotal / 1024 / 1024 / 1024 / 1024;
            Map<String, Object> map = new HashMap<>(16);
            map.put("sizeTotal", sizeTotal);
            map.put("cusTime", endCurrentTimeMillis1 - currentTimeMillis);
            return map;
        } catch (OSSException | ClientException e) {
            log.error("errMsg={}", CommonCustomUtils.getStackTraceString(e));
        } finally {
            ossClient.shutdown();
        }
        return Collections.emptyMap();
    }


    private static long calculateFolderLength(OSS ossClient, String bucketName, String folder) {
        long size = 0L;
        ObjectListing objectListing = null;
        do {
            // MaxKey默认值为100，最大值为1000。
            ListObjectsRequest request = new ListObjectsRequest(bucketName).withPrefix(folder).withMaxKeys(1000);
            if (objectListing != null) {
                request.setMarker(objectListing.getNextMarker());
            }
            objectListing = ossClient.listObjects(request);
            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                size += s.getSize();
            }
            log.info("calculateFolderLength：size={}", size);
        } while (objectListing.isTruncated());
        return size;
    }
}
