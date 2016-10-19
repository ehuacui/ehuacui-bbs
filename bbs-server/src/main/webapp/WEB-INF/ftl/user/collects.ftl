<#include "../common/layout.ftl"/>
<@html page_title="${currentUser.nickname!}收藏的话题" page_tab="user">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-heading">
            ${currentUser.nickname!}收藏的话题
            </div>
            <div class="panel-body">
                <#include "../components/user_topics.ftl"/>
                    <@usertopics topics=page.list/>
                    <#include "../components/paginate.ftl"/>
                    <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="/user/collects/${currentUser.nickname!}" urlParas=""/>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>