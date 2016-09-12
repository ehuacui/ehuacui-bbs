package org.ehuacui.bbs.model;

import java.util.Date;

/**
 * Table: ehuacui_user
 */
public class User {
    /**
     * Column: ehuacui_user.id
     */
    private Integer id;

    /**
     * 昵称
     * Column: ehuacui_user.nickname
     */
    private String nickname;

    /**
     * 积分
     * Column: ehuacui_user.score
     */
    private Integer score;

    /**
     * 头像
     * Column: ehuacui_user.avatar
     */
    private String avatar;

    /**
     * 邮箱
     * Column: ehuacui_user.email
     */
    private String email;

    /**
     * 个人主页
     * Column: ehuacui_user.url
     */
    private String url;

    /**
     * 个性签名
     * Column: ehuacui_user.signature
     */
    private String signature;

    /**
     * 第三方账户id
     * Column: ehuacui_user.third_id
     */
    private String thirdId;

    /**
     * Column: ehuacui_user.access_token
     */
    private String accessToken;

    /**
     * 邮箱是否接收社区消息
     * Column: ehuacui_user.receive_msg
     */
    private Boolean receiveMsg;

    /**
     * 录入时间
     * Column: ehuacui_user.in_time
     */
    private Date inTime;

    /**
     * Column: ehuacui_user.expire_time
     */
    private Date expireTime;

    /**
     * Column: ehuacui_user.channel
     */
    private String channel;

    /**
     * 禁用0默认 1禁用
     * Column: ehuacui_user.is_block
     */
    private Boolean isBlock;

    /**
     * 第三方登录获取的access_token
     * Column: ehuacui_user.third_access_token
     */
    private String thirdAccessToken;

    private Long collectCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId == null ? null : thirdId.trim();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    public Boolean getReceiveMsg() {
        return receiveMsg;
    }

    public void setReceiveMsg(Boolean receiveMsg) {
        this.receiveMsg = receiveMsg;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public Boolean getIsBlock() {
        return isBlock;
    }

    public void setIsBlock(Boolean isBlock) {
        this.isBlock = isBlock;
    }

    public String getThirdAccessToken() {
        return thirdAccessToken;
    }

    public void setThirdAccessToken(String thirdAccessToken) {
        this.thirdAccessToken = thirdAccessToken == null ? null : thirdAccessToken.trim();
    }

    public Long getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Long collectCount) {
        this.collectCount = collectCount;
    }
}