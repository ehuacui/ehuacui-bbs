package org.ehuacui.bbs.controller;

import org.ehuacui.bbs.dto.Constants;
import org.ehuacui.bbs.dto.PageDataBody;
import org.ehuacui.bbs.dto.ResponseDataBody;
import org.ehuacui.bbs.interceptor.BeforeAdviceController;
import org.ehuacui.bbs.interceptor.PermissionInterceptor;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.model.Topic;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.service.ISearchService;
import org.ehuacui.bbs.service.ISectionService;
import org.ehuacui.bbs.service.ITopicService;
import org.ehuacui.bbs.service.IUserService;
import org.ehuacui.bbs.template.FormatDate;
import org.ehuacui.bbs.template.GetAvatarByNickname;
import org.ehuacui.bbs.template.GetNameByTab;
import org.ehuacui.bbs.utils.DateUtil;
import org.ehuacui.bbs.utils.StringUtil;
import org.ehuacui.bbs.utils.WebUtil;
import org.ehuacui.bbs.utils.identicon.Identicon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Controller
public class IndexController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Value("${solr.status}")
    private String solrStatus;
    @Value("${pageSize}")
    private Integer pageSize;
    @Value("${cookie.domain}")
    private String cookieDomain;
    @Value("${static.path}")
    private String staticPath;
    @Value("${upload.type}")
    private String uploadType;
    @Value("${file.domain}")
    private String fileDomain;
    @Value("${qiniu.url}")
    private String qiniuURL;

    @Autowired
    private ISectionService sectionService;
    @Autowired
    private ITopicService topicService;
    @Autowired
    private ISearchService searchService;
    @Autowired
    private IUserService userService;

    /**
     * 首页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, @RequestParam(value = "tab", defaultValue = "all") String tab,
                        @RequestParam(value = "p", defaultValue = "1") Integer p) {
        /* 隐藏版块
        if (!tab.equals("all") && !tab.equals("good") && !tab.equals("noreply")) {
            Section section = sectionService.findByTab(tab);
            request.setAttribute("sectionName", section.getName());
        } else {
            request.setAttribute("sectionName", "版块");
        }
        */
        PageDataBody<Topic> page = topicService.page(p, pageSize, tab);
        request.setAttribute("tab", tab);
        request.setAttribute("sections", sectionService.findByShowStatus(true));
        request.setAttribute("page", page);
        request.setAttribute("getAvatarByNickname", new GetAvatarByNickname());
        request.setAttribute("getNameByTab", new GetNameByTab());
        request.setAttribute("formatDate", new FormatDate());
        return "index";
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        if (getUser(request) == null) {
            return "login";
        } else {
            return redirect("/");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
                        @RequestParam(value = "rememberMe", required = false, defaultValue = "false") Boolean rememberMe,
                        @RequestParam(value = "verifyCode", required = false) String verifyCode,
                        @RequestParam(value = "callback", required = false) String callback,
                        HttpServletRequest request, HttpServletResponse response) {
        User user = null;
        if (StringUtil.notBlank(username)) {
            if (StringUtil.isEmail(username)) {
                user = userService.findByEmailAndPassword(username, password);
            } else {
                user = userService.findByNickNameAndPassword(username, password);
            }
        }
        if (user != null) {
            if (user.getIsBlock()) {
                return redirect("/403.html");
            }
            if (rememberMe) {
                WebUtil.setCookie(response, Constants.USER_ACCESS_TOKEN,
                        StringUtil.getEncryptionToken(user.getAccessToken()),
                        30 * 24 * 60 * 60, "/", cookieDomain, true);
            }
            setUser(request, user);
            return redirect("/");
        } else {
            request.setAttribute("errors", "登录失败...");
            return redirect("login");
        }
    }

    /**
     * 忘记密码
     */
    @RequestMapping(value = "/forget/password", method = RequestMethod.GET)
    public String forgetPassword(HttpServletRequest request) {
        if (getUser(request) == null) {
            return "forget_password";
        } else {
            return redirect("/");
        }
    }

    @RequestMapping(value = "/forget/password", method = RequestMethod.POST)
    public String forgetPassword(@RequestParam("email") String email, HttpServletRequest request) {
        return redirect("/forget/password");
    }

    /**
     * 用户重置密码
     */
    @RequestMapping(value = "/reset/password", method = RequestMethod.GET)
    public String updatePassword() {
        return "reset_password";
    }

    @RequestMapping(value = "/reset/password", method = RequestMethod.POST)
    public String updatePassword(HttpServletRequest request,
                                 @RequestParam("password") String password,
                                 @RequestParam("newPassword") String newPassword) {
        request.setAttribute("msg", "密码重置成功。");
        return redirect("/password/reset");
    }

    /**
     * 注册
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(HttpServletRequest request) {
        if (getUser(request) == null) {
            return "register";
        } else {
            return redirect("/");
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("username") String username, @RequestParam("password") String password,
                           @RequestParam("email") String email, HttpServletRequest request) {
        if (StringUtil.isBlank(username)) {
            request.setAttribute("errors", "用户名不能为空");
        } else if (StringUtil.isBlank(email)) {
            request.setAttribute("errors", "电子邮件不能为空");
        } else if (StringUtil.isBlank(password)) {
            request.setAttribute("errors", "密码不能为空");
        } else {
            if (userService.findByNickname(username) != null) {
                request.setAttribute("errors", "用户名已经被注册");
            } else if (userService.findByEmail(username) != null) {
                request.setAttribute("errors", "Email已经被注册");
            } else {
                String avatarName = StringUtil.getUUID();
                Identicon identicon = new Identicon();
                identicon.generator(staticPath, avatarName);
                User user = new User();
                user.setNickname(username);
                user.setPassword(password);
                user.setEmail(email);
                user.setScore(0);
                user.setIsBlock(false);
                user.setReceiveMsg(true);
                user.setAccessToken(StringUtil.getUUID());
                Date now = new Date();
                user.setExpireTime(DateUtil.getDateAfter(now, 30));//30天后过期,要重新认证
                user.setInTime(new Date());
                user.setAvatar(fileDomain + "/imgs/" + avatarName + ".png");
                userService.save(user);
                return redirect("/login");
            }
        }
        return "register";
    }

    /**
     * 登出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        removeUser(request);
        WebUtil.removeCookie(response, Constants.USER_ACCESS_TOKEN, "/", cookieDomain);
        return redirect("/");
    }

    /**
     * 关于
     */
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return "about";
    }

    /**
     * 上传
     */
    @ResponseBody
    @BeforeAdviceController(UserInterceptor.class)
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseDataBody upload(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = DateUtil.getNowDateInfo() + "_" + StringUtil.randomString(6) + "_" + file.getOriginalFilename();
            File targetFile = new File(staticPath, fileName);
            file.transferTo(targetFile);
            String url = "";
            if (uploadType.equals("local")) {
                url = fileDomain + "/imgs/" + fileName;
            } else if (uploadType.equals("qiniu")) {
                // 将本地文件上传到七牛,并删除本地文件
                String filePath = staticPath + fileName;
                Map map = upload(filePath);
                targetFile.delete();
                url = qiniuURL + "/" + map.get("key");
            }
            return success(url);
        } catch (Exception e) {
            logger.error("上传失败", e);
            return error("图片上传失败,再试一次吧");
        }
    }

    /**
     * 索引所有话题
     */
    @BeforeAdviceController({UserInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/solr", method = RequestMethod.GET)
    public String solr() {
        if (solrStatus.equalsIgnoreCase("true")) {
            searchService.indexAll();
        }
        return redirect("/");
    }

    /**
     * 搜索
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(HttpServletRequest request, @RequestParam(value = "p", defaultValue = "1") Integer p, @RequestParam("q") String q) {
        if (solrStatus.equalsIgnoreCase("true")) {
            PageDataBody page = searchService.indexQuery(p, q);
            request.setAttribute("q", q);
            request.setAttribute("page", page);
        }
        return "search";
    }

    @BeforeAdviceController({UserInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/delete-all-index", method = RequestMethod.GET)
    public String deleteAllIndex() {
        if (solrStatus.equalsIgnoreCase("true")) {
            searchService.deleteAll();
        }
        return redirect("/");
    }

    /**
     * 积分前100名用户
     */
    @RequestMapping(value = "/top/100", method = RequestMethod.GET)
    public String top100() {
        return "top100";
    }

    /**
     * 清理缓存
     */
    @BeforeAdviceController({UserInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public String clear() {
        return redirect("/");
    }

}