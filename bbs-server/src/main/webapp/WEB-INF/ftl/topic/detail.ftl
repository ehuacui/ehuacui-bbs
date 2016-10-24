<#include "../common/layout.ftl">
<@html page_title="${topic.title}">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-body topic-detail-header">
                <div class="media">
                    <div class="media-body">
                        <h2 class="topic-detail-title">${topic.title!}</h2>

                        <p class="gray">
                            <#if topic.isTop>
                                <span class="label label-primary">置顶</span>
                                <span>•</span>
                            <#elseif topic.isGood>
                                <span class="label label-success">精华</span>
                                <span>•</span>
                            </#if>
                            <span><a href="/user/${topic.author!}">${topic.author!}</a></span>
                            <span>•</span>
                            <span>${formatDate(topic.inTime)!}</span>
                            <span>•</span>
                            <span>${topic.viewCount!1}次点击</span>
                            <span>•</span>
                            <span>来自 <a href="/?tab=${section.tab!}">${section.name!}</a></span>
                            <#if userInfo??>
                                <#if userInfo.id == authorInfo.id>
                                    <span>•</span>
                                    <span><a href="/topic/append/${topic.id}">内容追加</a></span>
                                </#if>
                                <@py.hasPermission name="topic:edit" id="${userInfo.id!}">
                                    <span>•</span>
                                    <span><a href="/topic/edit?id=${topic.id}">编辑</a></span>
                                </@py.hasPermission>
                                <@py.hasPermission name="topic:delete" id="${userInfo.id!}">
                                    <span>•</span>
                                    <span><a
                                            href="javascript:if(confirm('确定要删除吗？'))location.href='/topic/delete?id=${topic.id}'">删除</a></span>
                                </@py.hasPermission>
                                <@py.hasPermission name="topic:top" id="${userInfo.id!}">
                                    <span>•</span>
                                    <span><a
                                            href="javascript:if(confirm('确定要${topic.top!}吗？'))location.href='/topic/top?id=${topic.id}'">${topic.top!}</a></span>
                                </@py.hasPermission>
                                <@py.hasPermission name="topic:good" id="${userInfo.id!}">
                                    <span>•</span>
                                    <span><a
                                            href="javascript:if(confirm('确定要${topic.good!}吗？'))location.href='/topic/good?id=${topic.id}'">${topic.good!}</a></span>
                                </@py.hasPermission>
                            </#if>
                        </p>
                    </div>
                    <div class="media-right">
                        <img src="${getAvatarByNickname(topic.author)!}" alt="" class="avatar-lg"/>
                    </div>
                </div>
            </div>
            <#if topic.content?? && topic.content != "">
                <div class="divide"></div>
                <div class="panel-body topic-detail-content">
                ${markedNotAt(topic.content)!}
                </div>
            </#if>
            <#list topicAppends as topicAppend>
                <div class="divide"></div>
                <div class="panel-body topic-append-content">
                    <p class="gray">
                        <span>第 ${topicAppend_index + 1} 条追加</span>
                        <span>•</span>
                        <span>${formatDate(topicAppend.inTime)!}</span>
                        <#if userInfo??>
                            <@py.hasPermission name="topic:append:edit" id="${userInfo.id!}">
                                <span>•</span>
                                <a href="/topic/append-edit?id=${topicAppend.id!}">编辑</a>
                            </@py.hasPermission>
                        </#if>
                    </p>
                ${markedNotAt(topicAppend.content)!}
                </div>
            </#list>
            <#if userInfo??>
                <div class="panel-footer">
                    <#if collect??>
                        <a href="/collect/delete?tid=${topic.id!}">取消收藏</a>
                    <#else>
                        <a href="/collect/add?tid=${topic.id!}">加入收藏</a>
                    </#if>
                    <span class="pull-right">${collectCount!0}个收藏</span>
                </div>
            </#if>
        </div>
        <#if topic.replyCount == 0>
            <div class="panel panel-default">
                <div class="panel-body text-center">目前暂无回复</div>
            </div>
        <#else>
            <div class="panel panel-default">
                <div class="panel-heading">${topic.replyCount!0} 条回复</div>
                <div class="panel-body paginate-bot">
                    <#include "../components/replies.ftl"/>
                    <@replies replies=page.list/>
                    <#include "../components/paginate.ftl"/>
                    <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="/topic/${topic.id}"/>
                </div>
            </div>
        </#if>
        <#if userInfo??>
            <div class="panel panel-default">
                <div class="panel-heading">
                    添加一条新回复
                    <a href="javascript:;" id="goTop" class="pull-right">回到顶部</a>
                </div>
                <div class="panel-body">
                    <form action="/reply/save" method="post" id="replyForm">
                        <input type="hidden" value="${topic.id}" name="tid"/>

                        <div class="form-group">
                            <textarea name="content" id="content" rows="5"
                                      class="form-control"></textarea>
                        </div>
                        <button type="button" onclick="replySubmit()" class="btn btn-default">回复</button>
                        <span id="error_message"></span>
                    </form>
                </div>
            </div>
        </#if>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs">
        <#include "../components/author_info.ftl"/>
        <@info/>
        <#include "../components/other_topics.ftl"/>
        <@othertopics/>
    </div>
</div>
<link rel="stylesheet" href="${staticDomain!}/static/css/jquery.atwho.min.css"></script>
<script type="text/javascript" src="${staticDomain!}/static/js/jquery.atwho.min.js"></script>
<script type="text/javascript" src="${staticDomain!}/static/js/lodash.min.js"></script>
<link rel="stylesheet" href="${staticDomain!}/static/libs/editor/editor.css"/>
<style>
    .CodeMirror {
        height: 150px;
    }
</style>
<script type="text/javascript" src="${staticDomain!}/static/js/highlight.min.js"></script>
<script type="text/javascript" src="${staticDomain!}/static/libs/webuploader/webuploader.withoutimage.js"></script>
<script type="text/javascript" src="${staticDomain!}/static/libs/markdownit.js"></script>
<script type="text/javascript" src="${staticDomain!}/static/libs/editor/editor.js"></script>
<script type="text/javascript" src="${staticDomain!}/static/libs/editor/ext.js"></script>
<script type="text/javascript">

    $('pre code').each(function (i, block) {
        hljs.highlightBlock(block);
    });

    var editor = new Editor({element: $("#content")[0], status: []});
    editor.render();

    var $input = $(editor.codemirror.display.input);
    $input.keydown(function (event) {
        if (event.keyCode === 13 && (event.ctrlKey || event.metaKey)) {
            event.preventDefault();
            if (editor.codemirror.getValue().length == 0) {
                $("#error_message").html("回复内容不能为空");
            } else {
                $("#replyForm").submit();
            }
        }
    });

    // at.js 配置
    var codeMirrorGoLineUp = CodeMirror.commands.goLineUp;
    var codeMirrorGoLineDown = CodeMirror.commands.goLineDown;
    var codeMirrorNewlineAndIndent = CodeMirror.commands.newlineAndIndent;
    var data = [];
        <#list page.list as reply>
        data.push('${reply.author}');
        </#list>
    data = _.unique(data);
    $input.atwho({
        at: "@",
        data: data
    }).on('shown.atwho', function () {
        CodeMirror.commands.goLineUp = _.noop;
        CodeMirror.commands.goLineDown = _.noop;
        CodeMirror.commands.newlineAndIndent = _.noop;
    })
            .on('hidden.atwho', function () {
                CodeMirror.commands.goLineUp = codeMirrorGoLineUp;
                CodeMirror.commands.goLineDown = codeMirrorGoLineDown;
                CodeMirror.commands.newlineAndIndent = codeMirrorNewlineAndIndent;
            });
    // END at.js 配置
</script>
</@html>