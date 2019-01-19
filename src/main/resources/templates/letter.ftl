<#include "header.ftl">
    <div id="main">
        <div class="zg-wrap zu-main clearfix ">
            <ul class="letter-list">
                <#--#foreach($conversation in $conversations)-->
            <#if conversations?? && (conversations?size > 0)>
                <#list conversations as conversation>
                <li id="conversation-item-10005_622873">
                    <a class="letter-link" href="/msg/detail?conversationId=${conversation.message.conversationId}"></a>
                    <div class="letter-info">
                        <span class="l-time">${conversation.message.createdDate?datetime}</span>
                        <div class="l-operate-bar">
                            <a href="javascript:void(0);" class="sns-action-del" data-id="10005_622873">
                            删除
                            </a>
                            <a href="/msg/detail?${conversation.message.conversationId}">
                                共${conversation.message.id?c}条会话
                            </a>
                        </div>
                    </div>
                    <div class="chat-headbox">
                        <#if conversation.unread gt 0>
                        <span class="msg-num">
                            ${conversation.unread?c}
                        </span>
                        </#if>
                        <a class="list-head">
                            <img alt="头像" src="${conversation.user.headUrl!'/img/logo.png'}">
                        </a>
                    </div>
                    <div class="letter-detail">
                        <a title="通知" class="letter-name level-color-1">
                            ${conversation.user.name!}
                        </a>
                        <p class="letter-brief">
                            ${conversation.message.content}
                        </p>
                    </div>
                </li>
                <#--#end-->
                </#list>
            </#if>
                </ul>

        </div>
    </div>
<script type="text/javascript" src="/scripts/main/site/detail.js"></script>
<#include "footer.ftl">