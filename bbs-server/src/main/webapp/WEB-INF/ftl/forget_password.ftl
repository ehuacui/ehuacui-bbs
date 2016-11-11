<#include "./common/layout.ftl">
<@html page_title="忘记密码">
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <a href="/">主页</a> / 忘记密码
            </div>
            <div class="panel-body">
                <div class="col-md-4" style="padding-left: 10px;padding-right: 10px;">
                    <#if errors??>
                        <div class="alert alert-danger">${errors!}</div>
                    </#if>
                    <#if msg??>
                        <div class="alert alert-success" role="alert">${msg!}</div>
                    </#if>
                    <form class="form-horizontal" role="form" action="/forget/password" method="post" id="form">
                        <div class="form-group">
                            <label for="email" class="col-sm-3 control-label">邮箱</label>

                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="email" name="email"
                                       placeholder="邮箱">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-9">
                                <button type="submit" class="btn btn-default">提交</button>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md-8" style="border-left:1px dashed #C0C0C0;height: 200px;padding-left: 10px;">
                    <div class="alert alert-info" style="height: 200px;">
                        <h5>属于Java语言的bbs</h5>
                        <p>在这里，您可以提问，回答，分享，诉说，这是个属于Java程序员的社区，欢迎您的加入！</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $("#form").submit(function () {
            var email = $("#email");
            var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            if (email.val().length == 0) {
                layer.alert('邮箱不能为空', {icon: 2, skin: 'layer-ext-moon', closeBtn: 0});
                email.focus();
                return false;
            }
            if (!emailReg.test(email.val())) {
                layer.alert('邮件格式不正确', {icon: 2, skin: 'layer-ext-moon', closeBtn: 0});
                email.focus();
                return false;
            }
        })
    })
</script>
</@html>