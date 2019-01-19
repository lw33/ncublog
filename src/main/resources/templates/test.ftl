
<#if user??>
    ${user.name!}
<#else>
    <h2>Thinking in Java</h2>
</#if>
<#if list?? && (list?size > 0)>
    <#list list as j>
        ${j.name!}
    </#list>
</#if>

<#--${id!?c}-->
${time?datetime_format)}
