package org.ehuacui.bbs.controller;

import org.ehuacui.bbs.config.BusinessException;
import org.ehuacui.bbs.dto.Constants;
import org.ehuacui.bbs.dto.PageDataBody;
import org.ehuacui.bbs.dto.ResponseDataBody;
import org.ehuacui.bbs.interceptor.BeforeAdviceController;
import org.ehuacui.bbs.interceptor.PermissionInterceptor;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.model.Role;
import org.ehuacui.bbs.model.Topic;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.model.UserRole;
import org.ehuacui.bbs.service.*;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
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
    @Value("${site.domain}")
    private String siteDomain;
    @Value("${qiniu.url}")
    private String qiniuURL;


    @Autowired
    private SectionService sectionService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private HtmlGeneratorService htmlGeneratorService;
    @Autowired
    private EmailSendService emailSendService;

    /**
     * 首页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, @RequestParam(value = "tab", defaultValue = "all") String tab,
                        @RequestParam(value = "p", defaultValue = "1") Integer p) {
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
            if (StringUtil.notBlank(callback)) {
                return redirect(callback);
            }
            return redirect("/");
        } else {
            request.setAttribute("errors", "登录失败...");
            return "login";
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
        if (getUser(request) == null) {
            Map<String, Object> data = new HashMap<>();
            try {
                data.put("resetPwdUrl", siteDomain + "/reset/password?email=" + email);
                String htmlEmail = htmlGeneratorService.generateHtmlBody("email_forget_password.ftl", data);
                emailSendService.sendHtmlMail(null, email, null, "重置密码", htmlEmail);
                request.setAttribute("msg", "已发至您的Email...");
            } catch (BusinessException e) {
                request.setAttribute("errors", "Email发送失败...");
                logger.error(e.getMessage(), e);
            }
            return "forget_password";
        } else {
            return redirect("/");
        }
    }

    /**
     * 用户重置密码
     */
    @RequestMapping(value = "/reset/password", method = RequestMethod.GET)
    public String updatePassword(HttpServletRequest request, @RequestParam("email") String email) {
        request.setAttribute("email", email);
        return "reset_password";
    }

    @RequestMapping(value = "/reset/password", method = RequestMethod.POST)
    public String updatePassword(HttpServletRequest request, @RequestParam("email") String email,
                                 @RequestParam("password") String password) {
        if (getUser(request) == null) {
            User user = userService.findByEmail(email);
            if (user != null) {
                user.setPassword(password);
                userService.update(user);
                return redirect("/login");
            } else {
                request.setAttribute("errors", "重置密码失败");
                return "reset_password";
            }
        } else {
            return redirect("/");
        }
    }

    /**
     * 激活账号
     */
    @RequestMapping(value = "/activate/{accessToken}", method = RequestMethod.GET)
    public String activate(HttpServletRequest request, @PathVariable String accessToken) {
        if (getUser(request) == null) {
            User user = userService.findByAccessToken(accessToken);
            if (user != null) {
                user.setIsBlock(!user.getIsBlock());
                userService.update(user);
                request.setAttribute("msg", "邮件激活成功");
            } else {
                request.setAttribute("errors", "邮件激活失败");
            }
            return "activate_user";
        } else {
            return redirect("/");
        }
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
        request.setAttribute("username", username);
        request.setAttribute("password", password);
        request.setAttribute("email", email);
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
                user.setScore(100);
                user.setIsBlock(false);
                user.setReceiveMsg(true);
                user.setAccessToken(StringUtil.getUUID());
                Date now = new Date();
                user.setExpireTime(DateUtil.getDateAfter(now, 30 * 12 * 10));//用户有效期
                user.setInTime(new Date());
                user.setAvatar(fileDomain + "/imgs/" + avatarName + ".png");
                userService.save(user);
                //新注册的用户角色都是普通用户
                Role role = roleService.findByName("user");
                if (role != null) {
                    UserRole userRole = new UserRole();
                    userRole.setUid(user.getId());
                    userRole.setRid(role.getId());
                    userRoleService.save(userRole);
                }
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

    /**
     * 国际化测试
     */
    @RequestMapping(value = "/i18n", method = RequestMethod.GET)
    public String lang() {
        return "lang";
    }

}