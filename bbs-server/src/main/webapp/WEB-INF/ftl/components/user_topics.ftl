<#macro usertopics topics>
    <#list topics as topic>
    <div class="media">
        <div class="media-body">
            <div class="title">
                <a href="/topic/${topic.id!}">${topic.title!}</a>
            </div>
            <p>
                <a href="/?tab=${topic.tab!}">${getNameByTab(topic.tab)!}</a>
                <span>•</span>
                <span><a href="/user/${topic.author!}">${topic.author!}</a></span>
                <span class="hidden-sm hidden-xs">•</span>
                <span class="hidden-sm hidden-xs">${topic.replyCount!0}个回复</span>
                <span class="hidden-sm hidden-xs">•</span>
                <span class="hidden-sm hidden-xs">${topic.viewCount!0}次浏览</span>
                <span>•</span>
                <span>${formatDate(topic.inTime)!}</span>
                <#if topic.lastReplyAuthor?? && topic.lastReplyAuthor != "">
                    <span>•</span>
                    <span>最后回复来自 <a href="/user/${topic.lastReplyAuthor!}">${topic.lastReplyAuthor!}</a></span>
                </#if>
            </p>
        </div>
    </div>
        <#if topic_has_next>
        <div class="divide mar-top-5"></div>
        </#if>
    </#list>
</#macro>