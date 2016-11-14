package org.ehuacui.bbs.controller;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.ehuacui.bbs.dto.Constants;
import org.ehuacui.bbs.dto.ResponseDataBody;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.service.UserService;
import org.ehuacui.bbs.utils.DateUtil;
import org.ehuacui.bbs.utils.JsonUtil;
import org.ehuacui.bbs.utils.StringUtil;
import org.ehuacui.bbs.utils.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Controller
public class BaseController {

    private final static Logger logger = LoggerFactory.getLogger(BaseController.class);

    //设置好账号的ACCESS_KEY和SECRET_KEY
    @Value("${qiniu.access_key}")
    private String QINIU_ACCESS_KEY;
    @Value("${qiniu.secret_key}")
    private String QINIU_SECRET_KEY;
    //要上传的空间
    @Value("${qiniu.bucket_name}")
    private String bucketName;

    @Autowired
    private UserService userService;

    // 接口返回状态码
    private static final String CODE_SUCCESS = "200";
    private static final String CODE_FAILURE = "201";
    private static final String DESC_SUCCESS = "success";

    private HttpSession getHttpSession(HttpServletRequest request) {
        return request.getSession();
    }

    public ResponseDataBody success(Object object) {
        return new ResponseDataBody(CODE_SUCCESS, DESC_SUCCESS, object);
    }

    public ResponseDataBody error(String message) {
        return new ResponseDataBody(CODE_FAILURE, message, null);
    }

    public User getUser(HttpServletRequest request) {
        User user = null;
        Object sessionObject = getHttpSession(request).getAttribute("user");
        if (sessionObject != null) {
            user = (User) sessionObject;
        }
        if (user == null) {
            String user_cookie = WebUtil.getCookie(request, Constants.USER_ACCESS_TOKEN);
            if (StringUtil.notBlank(user_cookie)) {
                user = userService.findByAccessToken(StringUtil.getDecryptToken(user_cookie));
            }
        }
        return user;
    }

    public void setUser(HttpServletRequest request, User user) {
        if (user != null) {
            getHttpSession(request).setAttribute("user", user);
        }
    }

    public void removeUser(HttpServletRequest request) {
        getHttpSession(request).removeAttribute("user");
    }

    protected String redirect(String url) {
        return "redirect:" + url;
    }

    public Map upload(String filePath) throws IOException {
        //上传到七牛后保存的文件名
        String key = DateUtil.getNowDateInfo() + "_" + StringUtil.randomString(6);
        try {
            //密钥配置
            Auth auth = Auth.create(QINIU_ACCESS_KEY, QINIU_SECRET_KEY);
            //简单上传，使用默认策略，只需要设置上传的空间名就可以了
            String uploadToken = auth.uploadToken(bucketName);
            //创建上传对象
            UploadManager uploadManager = new UploadManager();
            //调用put方法上传
            Response res = uploadManager.put(filePath, key, uploadToken);
            //打印返回的信息
            return JsonUtil.nonDefaultMapper().fromJson2Map(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息响应的文本信息
            logger.error(r.bodyString(), e);
            return null;
        }
    }

}
