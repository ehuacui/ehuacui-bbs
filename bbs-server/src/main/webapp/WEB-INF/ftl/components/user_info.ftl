<#macro info>
<div class="panel panel-default">
    <div class="panel-heading">
        个人信息
    </div>
    <div class="panel-body">
        <div class="media">
            <div class="media-left">
                <a href="/user/${userInfo.nickname!}">
                    <img src="${userInfo.avatar!}" title="${userInfo.nickname!}" class="avatar"/>
                </a>
            </div>
            <div class="media-body">
                <div class="media-heading">
                    <a href="/user/${userInfo.nickname!}">${userInfo.nickname!}</a>
                </div>
                <p>积分: ${userInfo.score!}</p>
            </div>
            <#if userInfo.signature?? && userInfo.signature != "">
                <div style="color: #7A7A7A; font-size: 12px; margin-top:5px;">
                    <i>“ ${userInfo.signature!} ” </i>
                </div>
            </#if>
        </div>
    </div>
</div>
</#macro>