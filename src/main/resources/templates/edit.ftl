<div id="div11"></div>
<input type="hidden" name="content" id="txt11">
<script type="text/javascript">
    window.onload = function () {

        var myEdit = window.wangEditor;
        var myEditor = new myEdit('#div11');
        myEditor.customConfig.uploadImgShowBase64 = true;   // 使用 base64 保存图片

        myEditor.customConfig.onchange = function (html) {
            // html 即变化之后的内容

            var html = myEditor.txt.html();
            // var text=editor.txt.text();
            $("#txt11").val(html);
        }
        myEditor.create();
    }
</script>
