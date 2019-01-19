<#include "header.ftl">
<link rel="stylesheet" href="styles/index.css">
<link rel="stylesheet" href="styles/detail.css">
<style>
    .paging {
        padding: 30px 10px 30px 70%;
        border-bottom: 1px solid #ebebeb;
        border-top: 1px solid #ebebeb;
        font-size: 15px;
        color: #1a1a1a;
        font-family: -apple-system, BlinkMacSystemFont, Helvetica Neue, PingFang SC, Microsoft YaHei, Source Han Sans SC, Noto Sans CJK SC, WenQuanYi Micro Hei, sans-serif;

    }
    .paging ul {

        list-style:none; /* 去掉ul前面的符号 */
        margin: 0px; /* 与外界元素的距离为0 */
        padding: 0px; /* 与内部元素的距离为0 */
        padding-bottom: 10px;
        width: auto; /* 宽度根据元素内容调整 */

    }

    .paging ul li {

        padding: 10px; /* 与内部元素的距离为0 */
        float:left;

    }

    .expandable.entry-body {
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical;
        overflow: hidden;
        display: -webkit-inline-box;
        /*word-break: break-all; !* 追加这一行代码 *!*/
    }

</style>
    <div class="zg-wrap zu-main clearfix " role="main">
        <div class="zu-main-content">
            <div class="zu-main-content-inner">
                <div class="zg-section" id="zh-home-list-title">
                    <i class="zg-icon zg-icon-feedlist"></i>首页
                    <input type="hidden" id="is-topstory">
                    <span class="zg-right zm-noti-cleaner-setting" style="list-style:none">
                        <#--<a href="https://nowcoder.com/settings/filter" class="zg-link-gray-normal">-->
                            <#--<i class="zg-icon zg-icon-settings"></i>设置</a></span>-->
                </div>
                <div class="zu-main-feed-con navigable" data-feedtype="topstory" id="zh-question-list" data-widget="navigable" data-navigable-options="{&quot;items&quot;:&quot;&gt; .zh-general-list .feed-content&quot;,&quot;offsetTop&quot;:-82}">
                    <a href="javascript:;" class="zu-main-feed-fresh-button" id="zh-main-feed-fresh-button" style="display:none"></a>
                    <div id="js-home-feed-list" class="zh-general-list topstory clearfix" data-init="{&quot;params&quot;: {}, &quot;nodename&quot;: &quot;TopStory2FeedList&quot;}" data-delayed="true" data-za-module="TopStoryFeedList">

                        <#--#foreach($vo in $vos)-->

                    <#if vos?? && (vos?size > 0)>
                        <#list vos as vo>

                        <div class="feed-item folding feed-item-hook feed-item-2
                        " feed-item-a="" data-type="a" id="feed-2" data-za-module="FeedItem" data-za-index="">
                            <meta itemprop="ZReactor" data-id="389034" data-meta="{&quot;source_type&quot;: &quot;promotion_answer&quot;, &quot;voteups&quot;: 4168, &quot;comments&quot;: 69, &quot;source&quot;: []}">
                            <div class="feed-item-inner">
                                <div class="avatar">
                                    <a title="${vo.user.name!}" data-tip="p$t$amuro1230" class="zm-item-link-avatar" target="_blank" href="/user/${vo.user.id?c}">
                                        <img src="${vo.user.headUrl!"/img/logo.jpg"}" class="zm-item-img-avatar"></a>
                                </div>
                                <div class="feed-main">
                                    <div class="feed-content" data-za-module="AnswerItem">
                                        <meta itemprop="answer-id" content="389034">
                                        <meta itemprop="answer-url-token" content="13174385">
                                        <h2 class="feed-title">
                                            <a class="question_link" target="_blank" href="/blog/${vo.blog.id!0?c}">${vo.blog.title!}</a></h2>
                                        <div class="feed-question-detail-item">
                                            <div class="question-description-plain zm-editable-content"></div>
                                        </div>
                                        <div class="expandable entry-body">
                                            <div class="zm-item-vote">
                                                <a class="zm-item-vote-count js-expand js-vote-count" href="javascript:;" data-bind-votecount="">${vo.followCount?c}</a></div>
                                            <div class="zm-item-answer-author-info">
                                                <a class="author-link" data-tip="p$b$amuro1230" target="_blank" href="/user/${vo.user.id!0?c}">${vo.user.name!}</a>
                                                ，${vo.blog.createdDate?datetime!}</div>
                                            <div class="zm-item-vote-info" data-votecount="4168" data-za-module="VoteInfo">
                                                <span class="voters text">
                                                    <a href="#" class="more text">
                                                        <span class="js-voteCount">4168</span>&nbsp;人赞同</a></span>
                                            </div>
                                            <div class="zm-item-rich-text expandable js-collapse-body" data-resourceid="123114" data-action="/answer/content" data-author-name="李淼" data-entry-url="/question/19857995/answer/13174385">
                                                <div class="zh-summary summary clearfix">${vo.blog.content!}</div>
                                            </div>
                                        </div>

                                        <div class="feed-meta">
                                            <div class="zm-item-meta answer-actions clearfix js-contentActions">
                                                <div class="zm-meta-panel">
                                                    <a data-follow="q:link" class="follow-link zg-follow meta-item" href="/blog/${vo.blog.id?c}" id="sfb-123114">
                                                        <i class="z-icon-follow"></i>关注博客</a>
                                                    <a  href="/blog/${vo.blog.id?c}" name="addcomment" class="meta-item toggle-comment js-toggleCommentBox" >
                                                        <i class="z-icon-comment"></i>${vo.blog.commentCount?c} 条评论</a>


                                                    <button class="meta-item item-collapse js-collapse">
                                                        <i class="z-icon-fold"></i>收起</button>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <#--#end-->
                        </#list>
                    </#if>
                    </div>

                    <#--<a href="javascript:;" id="zh-load-more" data-method="next" class="zg-btn-white zg-r3px zu-button-more" style="">更多</a></div>-->
                    <#if pageInfo.pages != 0>

                    <div class="paging">
                        <ul class="">

                            <li>
                                第${pageInfo.pageNum?c}页
                            </li>
                            <li class="gray">
                                共${pageInfo.pages?c}页
                            </li>
                            <li>
                                <#--<a href="/${pageInfo.pageNum==1?1:pageInfo.pageNum-1 }">-->
                                <a href="/index/${(pageInfo.pageNum==1)?string('1', '${pageInfo.pageNum - 1}')}">
                                    <i class="glyphicon glyphicon-menu-left">
                                        上一页
                                    </i>
                                </a>
                            </li>
                            <li>
                                <#--<a href="${pageContext.request.contextPath}/article/getArticles/${pageInfo.pageNum==pageInfo.pages?pageInfo.pages:pageInfo.pageNum+1 }">-->
                                <a href="/index/${(pageInfo.pageNum==pageInfo.pages)?string('${pageInfo.pages}','${pageInfo.pageNum+1}')}">
                                    <i class="glyphicon glyphicon-menu-right">
                                        下一页
                                    </i>
                                </a>
                            </li>
                        </ul>
                    </div>
                    </#if>
            </div>
        </div>
    </div>
<#--<#include "js.ftl">-->
<script type="text/javascript" src="/scripts/main/site/detail.js"></script>
<#include "footer.ftl">