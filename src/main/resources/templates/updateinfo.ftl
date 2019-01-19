<#include "header.ftl">
<style>
    .ui-item {
        padding: 30px 10px 30px 125px;
        border-bottom: 1px solid #ebebeb;
        font-size: 15px;
        color: #1a1a1a;
        font-family: -apple-system, BlinkMacSystemFont, Helvetica Neue, PingFang SC, Microsoft YaHei, Source Han Sans SC, Noto Sans CJK SC, WenQuanYi Micro Hei, sans-serif;
    }

    .Field-label {
        font-size: 15px;
        font-weight: 600;
        color: #444;
    }

    .inputinfo-div {

        position: relative;
    }

    .inputinfo {
        position: absolute;
        left: 45%;
        border: 1px solid #dbdbdb;
        bottom: -4px;
    }

    .gender-div {
        position: absolute;
        left: 45%;
        bottom: 0px;
    }

    .change-head {
        width: 100px;
        height: 10px;
        background-color: #0084ff;
    }
</style>

<#--  private String name;
  private String password;
  private String salt;
  private String headUrl;
  private LocalDate birth;
  private int gender;
  private String vocation;
  private String introduction;
  private LocalDateTime registerDate;-->

<div class="zg-wrap zu-main clearfix ">
    <form method="post" action="/user/updateInfo" enctype="multipart/form-data">
        <div class="ui-item">
            <label class=""><h3 class="Field-label">头 像</h3></label>
            <div class="inputinfo-div">
                <label for="file"
                       style="z-index: 5;position: absolute;left: 45%;bottom: -3px;">
                     <img src="${user.headUrl}" width="76px" style="z-index: 5;position: absolute;bottom: -22px;">

                </label>


                <input id="file" type="file" name="inputfile"
                       style="position: absolute;left: 45%;color: #666666;bottom: 6px;">
            </div>
        </div>

    <#--name-->
        <div class="ui-item">
            <label for="name" class=""><h3 class="Field-label">用户名</h3></label>
            <div class="inputinfo-div">
                <input type="text" class="input-sm inputinfo" id="name"
                       name="name" placeholder="" value="${user.name}">
            </div>
        </div>

    <#--password-->
        <div class="ui-item">
            <label for="password" class=""><h3 class="Field-label">密码</h3></label>
            <div class="inputinfo-div">
                <input type="password" class="inputinfo input-sm " id="password" name="password" placeholder=""
                       value="">
            </div>
        </div>

    <#--birth-->
        <div class="ui-item">
            <label for="birth" class=""><h3 class="Field-label">生日</h3></label>
            <div class="inputinfo-div">
                <input type="date" class="inputinfo input-sm " id="birth" name="birth" placeholder=""
                       value="${user.birth?date}">
            </div>
        </div>

    <#--gender-->
        <div class="ui-item">
            <label  class=""><h3 class="Field-label">性别</h3></label>
            <div class="inputinfo-div">
                    <div class="gender-div" >
                        <input type="radio" name="gender" id="normal" value="0" <#if user.gender = 0>checked</#if>
                        >男 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" name="gender" id="forbid" value="1" <#if user.gender = 1>checked</#if>
                        >女
                    </div>

            </div>
        </div>

    <#--vocation-->
        <div class="ui-item">
            <label for="vocation" class=""><h3 class="Field-label">职业</h3></label>
            <div class="inputinfo-div">
                <input type="text" class="input-sm inputinfo" id="vocation"
                       name="vocation" placeholder="" value="${user.vocation!}">
            </div>
        </div>

    <#--introduction-->
        <div class="ui-item">
            <label for="introduction" class="">简介</label>
            <div class="inputinfo-div">
                <textarea name="introduction" id="introduction" cols="50" rows="2" class="inputinfo" style="bottom: -19px;">${user.introduction!}</textarea>
            </div>
        </div>

        <div class="ui-item">
            <input type="submit" value="提交" class="btn btn-xs;" style="border-radius:4px;margin-left: 80%;width: 69px;height: 30px;line-height: 13px; background-color:#0084FF; color: #fff;">
        </div>

    </form>
</div>

<#include "footer.ftl">

