<#include "../common/layout.ftl"/>
<@html page_title="通知" page_tab="notification">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-heading">
                通知
                <span class="pull-right">总共收到通知 ${page.totalRow!}</span>
            </div>
            <div class="panel-body">
                <#list page.list as notification>
                    <div class="media">
                        <div class="media-left">
                            <img src="${getAvatarByNickname(notification.author)!}" class="avatar-sm">
                        </div>
                        <div class="media-body">
                            <div class="gray" <#if notification.isRead>style="font-weight:700;"</#if>>
                                <a href="/user/${notification.author!}">${notification.author!}</a>
                                <#if notification.action == "COLLECT">
                                    收藏了你发布的话题
                                <#elseif notification.action == "REPLY">
                                    在
                                <#elseif notification.action == "AT">
                                    在回复
                                </#if>
                                <a href="/topic/${notification.tid!}">${notification.title!}</a>
                                <#if notification.action == "REPLY">
                                    里回复了你
                                <#elseif notification.action == "AT">
                                    时提到了你
                                </#if>
                                <span>${formatDate(notification.inTime)!}</span>
                            </div>
                            <#if notification.content?? && notification.content != "">
                                <div class="payload">
                                ${marked(notification.content)!}
                                </div>
                            </#if>
                        </div>
                    </div>
                    <#if notification_has_next>
                        <div class="divide mar-top-5"></div>
                    </#if>
                </#list>
                <#include "../components/paginate.ftl"/>
                <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="/notifications"/>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>