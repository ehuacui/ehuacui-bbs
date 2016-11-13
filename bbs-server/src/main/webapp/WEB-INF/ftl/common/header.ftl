<#macro header page_tab="">
<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" style="color:#fff;" href="/">${siteTitle!}</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse header-navbar">
            <#if solrStatus == "true">
                <form class="navbar-form navbar-left" role="search" action="/search" method="get">
                    <div class="form-group">
                        <input type="text" class="form-control" name="q" value="${q!}" placeholder="回车搜索">
                    </div>
                </form>
            </#if>
            <ul class="nav navbar-nav navbar-right">
            <#--
             <li <#if page_tab == 'about'> class="active" </#if>>
                 <a href="/about">关于</a>
             </li>
             -->
                <#if userInfo??>
                    <li class="hidden-md hidden-lg">
                        <a href="/topic/create">发布话题</a>
                    </li>
                    <li <#if page_tab == 'notification'> class="active" </#if>>
                        <a href="/notification/index">通知 <span class="badge" id="badge">${notifications!}</span></a>
                    </li>
                    <li <#if page_tab == 'user'> class="active" </#if>>
                        <a href="/user/${userInfo.nickname!}">
                        ${userInfo.nickname!}
                            <span class="badge" id="badge"></span>
                        </a>
                    </li>
                    <li <#if page_tab == 'setting'> class="active" </#if>>
                        <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"
                           data-hover="dropdown">
                            设置
                            <span class="caret"></span>
                        </a>
                        <span class="dropdown-arrow"></span>
                        <ul class="dropdown-menu">
                            <li><a href="/user/setting">个人资料</a></li>
                            <li><a href="/user/password/update">修改密码</a></li>
                            <li><a href="/logout">退出</a></li>
                            <@py.hasPermission name="section:list" id="${userInfo.id!}">
                                <li role="separator" class="divider"></li>
                                <li><a href="/section/list">板块管理</a></li>
                            </@py.hasPermission>
                            <@py.hasPermission name="reply:list" id="${userInfo.id!}">
                                <li><a href="/reply/list">回复管理</a></li>
                            </@py.hasPermission>
                            <@py.hasPermission name="system:users" id="${userInfo.id!}">
                                <li role="separator" class="divider"></li>
                                <li><a href="/manage/users">用户管理</a></li>
                            </@py.hasPermission>
                            <@py.hasPermission name="system:roles" id="${userInfo.id!}">
                                <li><a href="/manage/roles">角色管理</a></li>
                            </@py.hasPermission>
                            <@py.hasPermission name="system:permissions" id="${userInfo.id!}">
                                <li><a href="/manage/permissions">权限管理</a></li>
                            </@py.hasPermission>
                            <@py.hasPermission name="system:solr" id="${userInfo.id!}">
                                <li role="separator" class="divider"></li>
                                <li><a href="/solr">索引所有话题(慎用)</a></li>
                            </@py.hasPermission>
                            <@py.hasPermission name="system:delete:all:index" id="${userInfo.id!}">
                                <li><a href="/delete-all-index">删除所有索引</a></li>
                            </@py.hasPermission>
                            <@py.hasPermission name="system:clear:cache" id="${userInfo.id!}">
                                <li><a href="/clear">删除所有缓存</a></li>
                            </@py.hasPermission>
                        </ul>
                    </li>
                <#else>
                    <li>
                        <a href="/login">登录</a>
                    </li>
                    <li>
                        <a href="/register">加入我们</a>
                    </li>
                </#if>
            </ul>
        </div>
    </div>
</nav>
</#macro>