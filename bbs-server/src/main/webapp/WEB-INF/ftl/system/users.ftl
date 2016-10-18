<#include "../common/layout.ftl"/>
<@html page_title="用户管理" page_tab="setting">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-heading">
                用户管理
                <span class="pull-right">${page.totalRow!}个用户</span>
            </div>
            <div class="table-responsive">
                <table class="table table-striped">
                    <tbody>
                        <#list page.list as user>
                        <tr>
                            <td>${user.id!}</td>
                            <td><a href="/user/${user.nickname!}" target="_blank">${user.nickname!}</a></td>
                            <td><a href="mailto:${user.email!}" target="_blank">${user.email!}</a></td>
                            <td><a href="${user.url!}" target="_blank">${user.url!}</a></td>
                            <td>
                                <a href="/manage/user-role?id=${user.id!}" class="btn btn-xs btn-warning">配置角色</a>
                                <a href="javascript:if(confirm('确认此操作吗?')) location.href='/manage/user-block?id=${user.id!}'"
                                   class="btn btn-xs btn-danger">
                                    <#if user.isBlock == true>
                                        取消禁用
                                    <#elseif user.isBlock == false>
                                        禁用账户
                                    </#if>
                                </a>
                                <a href="javascript:if(confirm('确认删除吗?')) location.href='/manage/delete-user?id=${user.id!}'"
                                   class="btn btn-xs btn-danger">删除</a>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
            <div class="panel-body" style="padding: 0 15px;">
                <#include "../components/paginate.ftl"/>
                    <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="/manage/users" urlParas="" showdivide="no"/>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>