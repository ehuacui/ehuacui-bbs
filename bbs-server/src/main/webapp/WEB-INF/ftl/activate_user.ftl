<#include "./common/layout.ftl">
<@html page_title="邮件激活用户">
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <a href="/">主页</a> / 邮件激活用户
            </div>
            <div class="panel-body">
                <div class="col-md-4">
                    <#if errors??>
                        <div class="alert alert-danger">${errors!}</div>
                    </#if>
                    <#if msg??>
                        <div class="alert alert-success" role="alert">${msg!}</div>
                    </#if>
                    <div>
                        <a href="/login">现在登录</a>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="alert alert-info" style="height: 200px;">
                        <h5>属于Java语言的bbs</h5>
                        <p>在这里，您可以提问，回答，分享，诉说，这是个属于Java程序员的社区，欢迎您的加入！</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</@html>