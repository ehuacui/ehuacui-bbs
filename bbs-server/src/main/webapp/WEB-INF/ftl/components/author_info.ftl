<#macro info>
<div class="panel panel-default">
    <div class="panel-heading">
        作者
    </div>
    <div class="panel-body">
        <div class="media">
            <div class="media-left">
                <a href="/user/${authorInfo.nickname!}">
                    <img src="${authorInfo.avatar!}" title="${authorInfo.nickname!}" class="avatar"/>
                </a>
            </div>
            <div class="media-body">
                <div class="media-heading">
                    <a href="/user/${authorInfo.nickname!}">${authorInfo.nickname!}</a>
                </div>
                <p>积分: ${authorInfo.score!}</p>
            </div>
            <#if authorInfo.signature?? && authorInfo.signature != "">
                <div style="color: #7A7A7A; font-size: 12px; margin-top:5px;">
                    <i>“ ${authorInfo.signature!} ” </i>
                </div>
            </#if>
        </div>
    </div>
</div>
</#macro>