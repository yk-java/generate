layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;
    //${codeName}
    var tableIns = table.render({
        id : "listTableId",
        elem: '#tableData',
        url : ctx+'/${entityPackage}/${lowerName}/list.htm',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 20,
        cols : [[
            {type: "checkbox", fixed:"left", width:50}
            #foreach($bean in $!{columnData})
            ,{field: '${bean.columnName}', title: '${bean.columnComment}', minWidth:100, align:"center"}
			#end
            ,{title: '操作', minWidth:120, templet:'#listBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索
    $(".search_btn").on("click",function(){
        if($(".searchVal").val() != ''){
            table.reload("listTableId",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    searchKey: $(".searchVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的内容");
        }
    });

    //添加${codeName}
    function addFun(edit){
        var index = layui.layer.open({
            title : "添加${codeName}",
            type : 2,
            content : ctx+'/${entityPackage}/${lowerName}/input.htm',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find("#id").val(edit.id);
                    #foreach($bean in $!{columnData})
                    body.find(".${bean.columnName}").val(edit.${bean.columnName});  //${bean.columnComment}
                    #end
                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        window.sessionStorage.setItem("index",index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(window.sessionStorage.getItem("index"));
        })
    }
    $(".add_btn").click(function(){
        addFun();
    })

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('listTableId'),
            data = checkStatus.data,
            idArr = [];
        if(data.length > 0) {
            for (var i in data) {
                idArr.push(data[i].id);
            }
            layer.confirm('确定批量删除选中的记录？', {icon: 3, title: '提示信息'}, function (index) {
                //向服务端发送删除指令
                $.ajax({
                    type : "post",
                    url : ctx+"/${entityPackage}/${lowerName}/delete.htm",
                    data: {'ids':idArr.toString()},
                    dataType:"json",
                    success:function(res){
                        if(res.resCode == 0){
                            tableIns.reload();
                            layer.close(index);
                        }else{
                            layer.msg(res.resMsg);
                        }
                    }
                });
            })
        }else{
            layer.msg("请选择需要删除的记录");
        }
    })

    //列表操作
    table.on('tool(tableData)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            addFun(data);
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此记录？',{icon:3, title:'提示信息'},function(index){
                //向服务端发送删除指令
                $.ajax({
                    type : "post",
                    url : ctx+"/${entityPackage}/${lowerName}/delete.htm",
                    data: {'ids':data.id},
                    dataType:"json",
                    success:function(res){
                        if(res.resCode == 0){
                            tableIns.reload();
                            layer.close(index);
                        }else{
                            layer.msg(res.resMsg);
                        }
                    }
                });
            });
        }
    });

})
