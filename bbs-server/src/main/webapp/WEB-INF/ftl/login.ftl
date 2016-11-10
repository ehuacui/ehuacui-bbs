<#include "./common/layout.ftl">
<@html page_title="登录">
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <a href="/">主页</a> / 登录
            </div>
            <div class="panel-body">
                <div class="col-md-4" style="padding-left: 10px;padding-right: 10px;">
                    <#if errors??>
                        <div class="alert alert-danger">${errors!}</div>
                    </#if>
                    <form class="form-horizontal" role="form" action="/login" method="post" id="form">
                        <div class="form-group">
                            <label for="username" class="col-sm-3 control-label">用户名</label>

                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="username" name="username"
                                       placeholder="昵称/邮箱">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="col-sm-3 control-label">密码</label>

                            <div class="col-sm-9">
                                <input type="password" class="form-control" id="password" name="password"
                                       placeholder="密码">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" id="rememberMe" name="rememberMe">请记住我
                                    </label>
                                </div>
                            </div>
                            <div class="col-sm-offset-9">
                                <div class="checkbox">
                                    <a href="/forget/password">忘记密码?</a>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-9">
                                <button type="submit" class="btn btn-default">登录</button>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md-8" style="border-left:1px dashed #C0C0C0;height: 200px;padding-left: 10px;">
                    <div class="alert alert-info">信息！请注意这个信息。</div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $("#form").submit(function () {
            var username = $("#username");
            var password = $("#password");
            if (username.val().length == 0) {
                layer.alert('用户名不能为空', {icon: 2, skin: 'layer-ext-moon', closeBtn: 0});
                username.focus();
                return false;
            }
            if (password.val().length == 0) {
                layer.alert('密码不能为空', {icon: 2, skin: 'layer-ext-moon', closeBtn: 0});
                password.focus();
                return false;
            }
        })
    })
</script>
</@html>