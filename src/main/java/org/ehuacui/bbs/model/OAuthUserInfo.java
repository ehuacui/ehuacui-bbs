package org.ehuacui.bbs.model;

/**
 * OAuthUserInfo
 * Created by jianwei.zhou on 2016/9/30.
 */
public class OAuthUserInfo {
    /**
     * OAuth认证系统用户标识
     */
    private String oauthUserId;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 用户Email
     */
    private String userEmail;
    /**
     * 图像URL
     */
    private String avatarUrl;
    /**
     * 主页URL
     */
    private String homeUrl;

    public String getOauthUserId() {
        return oauthUserId;
    }

    public void setOauthUserId(String oauthUserId) {
        this.oauthUserId = oauthUserId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }
}
