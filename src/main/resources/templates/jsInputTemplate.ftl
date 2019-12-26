layui.use(['form','layer'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    form.on("submit(submitBtn)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        // 实际使用时的提交信息
        $.post(ctx+'/${entityPackage}/${lowerName}/save.htm',{
            id : data.field.id,
            #foreach($bean in $!{columnData})
            ${bean.columnName} : $(".${bean.columnName}").val(),  //${bean.columnComment}
            #end
        },function(res){
            var resJson = JSON.parse(res);
            if(resJson.resCode==0){
                top.layer.close(index);
                top.layer.msg(resJson.resMsg);
                layer.closeAll("iframe");
                //刷新父页面
                parent.location.reload();
            }else {
                top.layer.msg(resJson.resMsg);
            }
        })
        return false;
    })
})