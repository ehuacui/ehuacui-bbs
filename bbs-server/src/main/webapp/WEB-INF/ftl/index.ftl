<#include "./common/layout.ftl">
<@html page_title="首页 - ${siteTitle!}">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-heading">
                <ul class="nav nav-pills">
                    <li <#if tab == 'all'>class="active"</#if>><a href="/?tab=all">全部</a></li>
                    <li <#if tab == 'good'>class="active"</#if>><a href="/?tab=good">精华</a></li>
                    <li <#if tab == 'noreply'>class="active"</#if>><a href="/?tab=noreply">等待回复</a></li>
                    <#list sections as section>
                        <li <#if tab == section.tab>class="active"</#if>>
                            <a href="/?tab=${section.tab!}">${section.name!}</a>
                        </li>
                    </#list>
                </ul>
            </div>
            <div class="panel-body paginate-bot">
                <#include "./components/topics.ftl"/>
                <@topics/>
                <#include "./components/paginate.ftl"/>
                <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="/" urlParas="&tab=${tab!}"/>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs">
        <#if userInfo??>
            <#include "components/user_info.ftl">
            <@info/>
            <#include "components/create_topic.ftl">
            <@createtopic/>
        <#else>
            <#include "./components/welcome.ftl">
            <@welcome/>
        </#if>
        <#--<#include "./components/scores.ftl"/>-->
        <#--<@userscores limit=10 />-->
    </div>
</div>
</@html>