package org.ehuacui.bbs.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class QiniuUploadUtil {

    //设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = ResourceUtil.getWebConfigValueByKey("qiniu.access_key");
    String SECRET_KEY = ResourceUtil.getWebConfigValueByKey("qiniu.secret_key");
    //要上传的空间
    String bucketname = ResourceUtil.getWebConfigValueByKey("qiniu.bucketname");
    //上传到七牛后保存的文件名
    String key = DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss") + StringUtil.randomString(6);

    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    public Map upload(String filePath) throws IOException {
        try {
            //创建上传对象
            UploadManager uploadManager = new UploadManager();
            //调用put方法上传
            Response res = uploadManager.put(filePath, key, getUpToken());
            //打印返回的信息
            return JsonUtil.nonDefaultMapper().fromJson2Map(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            //LogKit.error(r.toString());
            //响应的文本信息
            //LogKit.info(r.bodyString());
            return null;
        }
    }
}
