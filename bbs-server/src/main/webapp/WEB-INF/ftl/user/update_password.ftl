<#include "../common/layout.ftl">
<@html page_title="重置密码">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-heading">
                <a href="/">主页</a> / 重置密码
            </div>
            <div class="panel-body">
                <#if errors??>
                    <div class="alert alert-danger">${errors!}</div>
                </#if>
                <#if msg??>
                    <div class="alert alert-success" role="alert">${msg!}</div>
                </#if>
                <form action="/user/password/update" method="post" id="form">
                    <div class="form-group">
                        <label for="password">当前密码</label>
                        <input type="password" class="form-control" id="oldPassword" name="oldPassword"
                               placeholder="当前密码">
                    </div>
                    <div class="form-group">
                        <label for="password">新密码</label>
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="新密码">
                    </div>
                    <div class="form-group">
                        <label for="password">确认新密码</label>
                        <input type="password" class="form-control" id="newPassword" name="newPassword"
                               placeholder="确认新密码">
                    </div>
                    <div class="form-group">
                        <div>
                            <button type="submit" class="btn btn-default">提交</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs">
    </div>
</div>
<script>
    $(function () {
        $("#form").submit(function () {
            var oldPassword = $("#oldPassword");
            var password = $("#password");
            var newPassword = $("#newPassword");
            if (oldPassword.val().length == 0) {
                layer.alert('当前密码不能为空', {icon: 2, skin: 'layer-ext-moon', closeBtn: 0});
                oldPassword.focus();
                return false;
            }
            if (password.val().length == 0) {
                layer.alert('新密码不能为空', {icon: 2, skin: 'layer-ext-moon', closeBtn: 0});
                password.focus();
                return false;
            }
            if (newPassword.val().length == 0) {
                layer.alert('确认新密码不能为空', {icon: 2, skin: 'layer-ext-moon', closeBtn: 0});
                newPassword.focus();
                return false;
            }
            if (oldPassword.val().length < 6 || oldPassword.val().length > 15) {
                layer.alert('密码长度介于6-15位之间', {icon: 2, skin: 'layer-ext-moon', closeBtn: 0});
                oldPassword.focus();
                return false;
            }
            if (password.val().length < 6 || password.val().length > 15) {
                layer.alert('密码长度介于6-15位之间', {icon: 2, skin: 'layer-ext-moon', closeBtn: 0});
                password.focus();
                return false;
            }
            if (newPassword.val() != password.val()) {
                layer.alert('新密码两次输入不一致', {icon: 2, skin: 'layer-ext-moon', closeBtn: 0});
                newPassword.focus();
                return false;
            }
        })
    })
</script>
</@html>