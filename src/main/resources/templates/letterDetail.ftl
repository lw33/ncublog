<#include "header.ftl">
<div id="main">
    <div class="zg-wrap zu-main clearfix ">
        <#if targetUser.id = 0>
            <h2>系统通知</h2>
        <#else >
        <h2>与
            <a href="/user/${targetUser.id?c}"> ${targetUser.name}</a>
            的对话</h2>
        </#if>
        <ul class="letter-chatlist">
             <#--#foreach($message in $messages)-->
        <#if messages?? && (messages?size > 0)>
            <#list messages as message>
            <li id="msg-item-4009580" style="position: relative;">
                <#if message.message.fromId = user.id>
                    <a class="list-head" style="position: absolute; right: 20px;">
                        <img alt="头像" src="${user.headUrl!"/img/logo.png"}">
                    </a>
                    <div class="tooltip fade right in" style="float: right; right: 110px;">
                        <div class="tooltip-arrow" style="right: -7px; left: initial;border-right: initial;border-left: 8px solid #ECF1F1;"></div>
                        <div class="tooltip-inner letter-chat clearfix">
                            <div class="letter-info">
                                <p class="letter-time">${message.message.createdDate?datetime}</p>
                                <!-- <a href="javascript:void(0);" id="del-link" name="4009580">删除</a> -->
                            </div>

                            <p class="chat-content" style="word-break: break-all;width: initial;overflow:auto;">
                                ${message.message.content!}
                            </p>
                        </div>
                    </div>

                <#else >
                <a class="list-head">
                    <img alt="头像" src="${targetUser.headUrl!"/img/logo.png"}">
                </a>
                <div class="tooltip fade right in">
                    <div class="tooltip-arrow"></div>
                    <div class="tooltip-inner letter-chat clearfix">
                        <div class="letter-info">
                            <p class="letter-time">${message.message.createdDate?datetime}</p>
                            <!-- <a href="javascript:void(0);" id="del-link" name="4009580">删除</a> -->
                        </div>
                        <p class="chat-content">
                            ${message.message.content!}
                        </p>
                    </div>
                </div>
                </#if>
            </li>
            <#--#end-->
            </#list>
        </#if>
        </ul>

    </div>
</div>
<script type="text/javascript" src="/scripts/main/site/detail.js"></script>
<#include "footer.ftl">