<#macro userreplies replies>
<table class="table table-striped">
    <#list replies as reply>
        <tr>
            <td>
                <a href="/user/${reply.replyAuthor!}">${reply.replyAuthor!}</a>
            ${formatDate(reply.inTime)!}
                回复了
                <a href="/user/${reply.topicAuthor!}">${reply.topicAuthor!}</a>
                创建的话题 › <a href="/topic/${reply.tid!}">${reply.title!}</a>
            </td>
        </tr>
        <tr>
            <td>${marked(reply.content)!}</td>
        </tr>
    </#list>
</table>
</#macro>