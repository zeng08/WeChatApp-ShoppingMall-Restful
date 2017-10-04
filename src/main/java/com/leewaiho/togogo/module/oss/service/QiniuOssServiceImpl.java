package com.leewaiho.togogo.module.oss.service;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Author leewaiho
 * Email 791783391@qq.com
 * Date 2017/10/4
 * Project togogo-shixun
 */
@Service
public class QiniuOssServiceImpl implements OssService {
    
    private static final Logger log = LoggerFactory.getLogger(OssService.class);
    @Value("${oss.qiniu.access-key:}")
    private String accessKey;
    @Value("${oss.qiniu.secret-key:}")
    private String secretKey;
    @Value("${oss.qiniu.bucket:}")
    private String bucket;
    @Value("${oss.qiniu.callbackUrl}")
    private String callbackUrl;
    @Value("${oss.expireSeconds:3600}")
    private long expireSeconds;
    @Value("${oss.callbackBodyType:application/json}")
    private String callbackBodyType;
    
    public String getToken() {
        log.info("accessKey: {}", accessKey);
        log.info("secretKey: {}", secretKey);
        log.info("bucket: {}", bucket);
        log.info("callbackUrl: {}", callbackUrl);
        log.info("expireSeconds: {}", expireSeconds);
        log.info("callbackBodyType: {}", callbackBodyType);
        
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
        putPolicy.put("callbackUrl", callbackUrl);
        putPolicy.put("callbackBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        putPolicy.put("callbackBodyType", callbackBodyType);
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        log.info("upToken: {}", upToken);
        return upToken;
    }
    
}
