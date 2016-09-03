package org.ehuacui.model;

import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: ehuacui_topic
@mbggenerated do_not_delete_during_merge 2016-08-16 13:07:45
 */
public class Topic {
    /**
     * Column: ehuacui_topic.id
    @mbggenerated 2016-08-16 13:07:45
     */
    private Integer id;

    /**
     *   版块标识
     * Column: ehuacui_topic.tab
    @mbggenerated 2016-08-16 13:07:45
     */
    private String tab;

    /**
     *   话题标题
     * Column: ehuacui_topic.title
    @mbggenerated 2016-08-16 13:07:45
     */
    private String title;

    /**
     *   话题内容标签
     * Column: ehuacui_topic.tag
    @mbggenerated 2016-08-16 13:07:45
     */
    private String tag;

    /**
     *   录入时间
     * Column: ehuacui_topic.in_time
    @mbggenerated 2016-08-16 13:07:45
     */
    private Date inTime;

    /**
     *   修改时间
     * Column: ehuacui_topic.modify_time
    @mbggenerated 2016-08-16 13:07:45
     */
    private Date modifyTime;

    /**
     *   最后回复话题时间，用于排序
     * Column: ehuacui_topic.last_reply_time
    @mbggenerated 2016-08-16 13:07:45
     */
    private Date lastReplyTime;

    /**
     *   最后回复话题的用户id
     * Column: ehuacui_topic.last_reply_author
    @mbggenerated 2016-08-16 13:07:45
     */
    private String lastReplyAuthor;

    /**
     *   浏览量
     * Column: ehuacui_topic.view
    @mbggenerated 2016-08-16 13:07:45
     */
    private Integer view;

    /**
     *   话题作者id
     * Column: ehuacui_topic.author
    @mbggenerated 2016-08-16 13:07:45
     */
    private String author;

    /**
     *   1置顶 0默认
     * Column: ehuacui_topic.top
    @mbggenerated 2016-08-16 13:07:45
     */
    private Boolean top;

    /**
     *   1精华 0默认
     * Column: ehuacui_topic.good
    @mbggenerated 2016-08-16 13:07:45
     */
    private Boolean good;

    /**
     *   1显示0不显示
     * Column: ehuacui_topic.show_status
    @mbggenerated 2016-08-16 13:07:45
     */
    private Boolean showStatus;

    /**
     *   回复数量
     * Column: ehuacui_topic.reply_count
    @mbggenerated 2016-08-16 13:07:45
     */
    private Integer replyCount;

    /**
     *   1删除0默认
     * Column: ehuacui_topic.is_delete
    @mbggenerated 2016-08-16 13:07:45
     */
    private Boolean isDelete;

    /**
     *   话题内容标签是否被统计过1是0否默认
     * Column: ehuacui_topic.tag_is_count
    @mbggenerated 2016-08-16 13:07:45
     */
    private Boolean tagIsCount;

    /**
     *   话题内容
     * Column: ehuacui_topic.content
    @mbggenerated 2016-08-16 13:07:45
     */
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab == null ? null : tab.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getLastReplyTime() {
        return lastReplyTime;
    }

    public void setLastReplyTime(Date lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
    }

    public String getLastReplyAuthor() {
        return lastReplyAuthor;
    }

    public void setLastReplyAuthor(String lastReplyAuthor) {
        this.lastReplyAuthor = lastReplyAuthor == null ? null : lastReplyAuthor.trim();
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Boolean getGood() {
        return good;
    }

    public void setGood(Boolean good) {
        this.good = good;
    }

    public Boolean getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Boolean showStatus) {
        this.showStatus = showStatus;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Boolean getTagIsCount() {
        return tagIsCount;
    }

    public void setTagIsCount(Boolean tagIsCount) {
        this.tagIsCount = tagIsCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 转换置顶状态
     *
     * @param topic
     * @return
     */
    public String isTop(Topic topic) {
        if (top) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * 转换精华状态
     *
     * @param topic
     * @return
     */
    public String isGood(Topic topic) {
        if (good) {
            return "true";
        } else {
            return "false";
        }
    }
}