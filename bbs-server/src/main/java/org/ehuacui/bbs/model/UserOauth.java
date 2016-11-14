package org.ehuacui.bbs.model;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_user_oauth
@mbggenerated do_not_delete_during_merge 2016-11-14 18:06:22
 */
public class UserOauth {
    /**
     * Column: tb_user_oauth.id
    @mbggenerated 2016-11-14 18:06:22
     */
    private Integer id;

    /**
     * Column: tb_user_oauth.channel
    @mbggenerated 2016-11-14 18:06:22
     */
    private String channel;

    /**
     * Column: tb_user_oauth.third_id
    @mbggenerated 2016-11-14 18:06:22
     */
    private Integer thirdId;

    /**
     * Column: tb_user_oauth.third_access_token
    @mbggenerated 2016-11-14 18:06:22
     */
    private String thirdAccessToken;

    /**
     * Column: tb_user_oauth.uid
    @mbggenerated 2016-11-14 18:06:22
     */
    private Integer uid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public Integer getThirdId() {
        return thirdId;
    }

    public void setThirdId(Integer thirdId) {
        this.thirdId = thirdId;
    }

    public String getThirdAccessToken() {
        return thirdAccessToken;
    }

    public void setThirdAccessToken(String thirdAccessToken) {
        this.thirdAccessToken = thirdAccessToken == null ? null : thirdAccessToken.trim();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}