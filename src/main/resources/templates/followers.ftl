<#include "header.ftl">
<link rel="stylesheet" href="../../styles/result.css">
<link rel="stylesheet" href="../../styles/index.css">
<link rel="stylesheet" href="../../styles/detail.css">
   <div id="main">
        <div class="zg-wrap zu-main clearfix ">
            <div class="zm-profile-section-wrap zm-profile-followee-page">
                <div class="zm-profile-section-head">
                    <span class="zm-profile-section-name">
                        <a href="#">${curUser.name}</a> 粉丝 ${followerCount} 人
                    </span>
                </div>
                <div class="zm-profile-section-list">
                    <div id="zh-profile-follows-list">
                        <div class="zh-general-list clearfix">
                        <#if followers?? && (followers?size > 0)>
                            <#list followers as vo>
                            <#--#foreach($vo in $followers)-->
                            <div class="zm-profile-card zm-profile-section-item zg-clear no-hovercard">
                                <#--#if($vo.followed)-->
                                <#if vo.user.id != user.id>
                                <#if vo.followed??>
                                <div class="zg-right">
                                    <button class="zg-btn zg-btn-unfollow zm-rich-follow-btn small nth-0
                                    js-follow-user" data-status="1" data-id="${vo.user.id?c}">取消关注</button>
                                </div>
                                <#--#else</#if>   -->
                                <#else>
                                <div class="zg-right">
                                    <button class="zg-btn zg-btn-follow zm-rich-follow-btn small nth-0
                                    js-follow-user"  data-id="${vo.user.id?c}">关注</button>
                                </div>
                                <#--#end-->
                                </#if>
                                </#if>
                                <a title="Barty" class="zm-item-link-avatar" href="/user/${vo.user.id?c}">
                                    <img src="${vo.user.headUrl}" class="zm-item-img-avatar">
                                </a>
                                <div class="zm-list-content-medium">
                                    <h2 class="zm-list-content-title"><a data-tip="p$t$buaabarty" href="/user/${vo.user.id?c}" class="zg-link" title="Barty">${vo.user.name}</a></h2>
<#--
                                    <div class="zg-big-gray">计蒜客教研首席打杂</div> &ndash;&gt;-->
                                    <div class="details zg-gray">
                                        <a target="_blank" href="/user/${vo.user.id?c}/followers" class="zg-link-gray-normal">${vo.followerCount?c}粉丝</a>
                                        /
                                        <a target="_blank" href="/user/${vo.user.id?c}/followees" class="zg-link-gray-normal">${vo.followeeCount?c}关注</a>
                                        /
                                        <a target="_blank" href="#" class="zg-link-gray-normal">${vo.commentCount?c} 回答</a>
                                        /
                                        <a target="_blank" href="#" class="zg-link-gray-normal">548 赞同</a>
                                    </div>
                                </div>
                            </div>
                            <#--#end-->
                            </#list>
                        </#if>
                        </div>
                        <a aria-role="button" class="zg-btn-white zu-button-more">更多</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
<script type="text/javascript" src="/scripts/main/site/follow.js"></script>
<#include "footer.ftl">