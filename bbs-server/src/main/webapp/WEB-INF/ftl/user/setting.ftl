<#include "../common/layout.ftl"/>
<@html page_title="修改个人资料" page_tab="setting">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-heading">
                <a href="/">主页</a> / 个人设置
            </div>
            <div class="panel-body">
                <#if errors??>
                    <div class="alert alert-danger">${errors!}</div>
                </#if>
                <#if msg??>
                    <div class="alert alert-success" role="alert">${msg!}</div>
                </#if>
                <form action="/user/setting" method="post" id="userProfileForm">
                    <div class="form-group">
                        <label for="nickname">昵称</label>
                        <input type="text" disabled class="form-control" id="nickname" value="${userInfo.nickname!}"/>
                    </div>
                    <div class="form-group">
                        <label for="email">邮箱</label>
                        <input type="text" disabled class="form-control" id="email" value="${userInfo.email!}"/>
                    </div>
                    <div class="form-group">
                        <label for="url">主页</label>
                        <input type="text" class="form-control" id="url" name="url" value="${userInfo.url!}"/>
                    </div>
                    <div class="form-group">
                        <label for="signature">个性签名</label>
                        <textarea class="form-control" name="signature" id="signature">${userInfo.signature!}</textarea>
                    </div>
                    <div class="form-group">
                        <input type="checkbox" id="receiveMsg" name="receiveMsg" value="1" <#if userInfo.receiveMsg>checked</#if>/>
                        <label for="receiveMsg">是否接收系统邮件</label>
                    </div>
                    <button type="button" id="userProfileUpdateBtn" onclick="updateUserProfile()" class="btn btn-default">保存设置</button>
                    <span id="error_message"></span>
                </form>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs">
    </div>
</div>
</@html>