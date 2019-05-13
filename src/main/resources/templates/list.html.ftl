<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns="http://www.w3.org/1999/html">

<!--
    @Describe 代码生成器生成
    @Author   ${author}
    @Date     ${date}
    @Email    ${cfg.email}
-->
<head>
    <meta charset="utf-8">
    <title>${table.name ? cap_first} 列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/static/layui/css/layui.css"  media="all">
</head>
<body>

<!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
<form class="layui-form" id="searchForm">

    <#list table.fields as field>
        <#if !field.keyFlag><#--生成普通字段 -->
            <div class="layui-form-item layui-inline" >
                <label class="layui-form-label" style="width: 68px;padding: 9px 2px">${field.comment}</label>
                <div class="layui-input-block" style="margin-left: 80px">
                    <input style="height: 32px" type="text" name="${field.name}" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>
            </div>
        </#if>
    </#list>

    <div class="layui-form-item layui-inline">
        <div class="layui-input-block" style="margin-left: 40px">
            <button class="layui-btn" style="height: 32px; line-height: 32px" lay-submit lay-filter="subFilter" id="subBtn">检索</button>
            <button type="reset" style="height: 32px; line-height: 32px" class="layui-btn">重置</button>
        </div>
    </div>
</form>

<table class="layui-hide" id="test" lay-filter="test"></table>

<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm"  lay-event="add" id="addBtn"> <i class="layui-icon"> 新增</i></button>
        <button class="layui-btn layui-btn-sm" lay-event="update" id="updateBtn"><i class="layui-icon"> 修改</i></button>
        <button class="layui-btn layui-btn-sm" lay-event="delete" id="deleteBtn"><i class="layui-icon"> 删除</i></button>
    </div>
</script>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>


<script src="/static/layui/layui.js" charset="utf-8"></script>
<#--解决 cols 换行报错-->
<script th:inline="none">
    layui.use(['table','jquery','form'], function(){
        var table = layui.table,
            $ = layui.jquery,
            form = layui.form;

        table.render({
            id:'reloadId'
            ,elem: '#test'
            ,url:'<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/findByParam'
            ,toolbar: '#toolbarDemo'
            ,title: '用户数据表'
            ,cols: [[
                {type: 'checkbox', fixed: 'left'}

                <#list table.fields as field>
                <#if field.keyFlag><#--主键 -->
                ,{field:'${field.name}', title:'${field.comment}', width:80, fixed: 'left', unresize: true, sort: true}
                </#if>
                <#if !field.keyFlag><#--生成普通字段 -->
                ,{field:'${field.name}', title:'${field.comment}', width:120}
                 </#if>
                </#list>
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
            ]]
            ,page: true
        });

        //头工具栏事件
        var operate;
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'getCheckData':
                    var data = checkStatus.data;
                    layer.alert(JSON.stringify(data));
                    break;
                case 'getCheckLength':
                    var data = checkStatus.data;
                    layer.msg('选中了：'+ data.length + ' 个');
                    break;
                case 'isAll':
                    layer.msg(checkStatus.isAll ? '全选': '未全选');
                    break;
                // 写在这里可以避免表格重新加载带来的按钮绑定失效
                case 'add':
                    //清空表单
                    $("#operate")[0].reset();
                    operate = layer.open({
                        type: 1,
                        skin: 'layui-layer-rim', //加上边框
                        area: ['540px', '350px'], //宽高
                        content:$("#operate")
                    });
                    break;

                case 'update':
                    //清空表单
                    $("#operate")[0].reset();

                    var data = checkStatus.data;
                    console.log(data);
                    if(data.length != 1){
                        layer.alert("请确认选中一条记录！")
                        return false;
                    }
                    //表单回填
                    var formData = checkStatus.data;
                    form.val("operateFormFilter", formData[0]);

                    operate = layer.open({
                        type: 1,
                        skin: 'layui-layer-rim', //加上边框
                        area: ['540px', '350px'], //宽高
                        content:$("#operate"),
                    });
                    break;

                case 'delete':
                    var data = checkStatus.data;
                    if(data.length <= 0){
                        layer.alert("请至少选中一条记录！")
                        return false;
                    }
                    var array = new Array();
                    <#list table.fields as field>
                    <#if field.keyFlag><#--主键 -->
                     ${field.name};
                    </#if>
                    </#list>
                    $.each(data,function (key,val) {
                        <#list table.fields as field>
                        <#if field.keyFlag><#--主键 -->
                        array.push(val.${field.name})
                        </#if>
                        </#list>
                    })
                    if(array.length == 0){
                        return false;
                    }
                    $.ajax({
                        url:'<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/delByIds',
                        type:'post',
                        dataType:'json',
                        data:{"ids":array},
                        success: function (res) {
                            if(res.code== 1){
                                layer.alert("操作异常，请联系管理员！")
                            }else{
                                var objs=  $("#searchForm").serializeObject();
                                reloadTable(objs);          //重载table
                                layer.alert("操作成功！")    //弹出完成提示
                            }
                        }
                    })

                    break;
            };
        });

        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;
            //console.log(obj)
            if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    obj.del();
                    layer.close(index);
                });
            } else if(obj.event === 'edit'){
                layer.prompt({
                    formType: 2
                    ,value: data.email
                }, function(value, index){
                    obj.update({
                        email: value
                    });
                    layer.close(index);
                });
            }
        });

        //查询表单监听
        form.on('submit(subFilter)', function(data){
            console.log(JSON.stringify(data.field));
            reloadTable(data.field);
            return false;
        });

        //表格表单监听
        form.on('submit(operateFilter)', function(data){
            console.log(JSON.stringify(data.field))
            $.ajax({
                url:'<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/addOrUpdate',
                type:'post',
                dataType:'json',
                data:data.field,
                success: function (res) {
                    if(res.code== 1){
                        layer.alert("操作异常，请联系管理员！")
                    }else{
                        var objs=  $("#searchForm").serializeObject();
                        reloadTable(objs);          //重载table
                        layer.close(operate);       //关闭 表单弹窗
                        layer.alert("操作成功！")    //弹出完成提示
                    }
                }
            })
            return false;
        });

        //表格重载
        function reloadTable(formData){
            //执行重载
            table.reload('reloadId', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                <#list table.fields as field>
                    <#if !field.keyFlag> <#--生成普通字段 -->
                    ${field.name}: formData.${field.name},
                    </#if>
                </#list>
                }
            });
        }

        //定义serializeObject方法，序列化表单
        $.fn.serializeObject = function() {
            var o = {};
            var a = this.serializeArray();
            $.each(a, function() {
                if (o[this.name]) {
                    if (!o[this.name].push) {
                        o[this.name] = [ o[this.name] ];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        };

    });
</script>

</body>

<!-- 新增 修改 表单 -->
<form id="operate" class="layui-form" style="display: none" lay-filter="operateFormFilter">

    <#list table.fields as field>
        <#if field.keyFlag>  <#--主键  设置 隐藏-->
            <div class="layui-form-item" hidden>
                <label class="layui-form-label" style="width: 50px;padding: 9px 2px">ID</label>
                <div class="layui-input-block" style="margin-left: 65px">
                    <input style="height: 32px" type="text"  id="${field.name}" name="${field.name}" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>
            </div>
        </#if>
        <#if !field.keyFlag><#--生成普通字段 -->
            <div class="layui-form-item layui-inline" >
            <label class="layui-form-label" style="width: 50px;padding: 9px 2px">${field.comment}</label>
                <div class="layui-input-block" style="margin-left: 65px">
                    <input style="height: 32px" type="text" id="${field.name}" name="${field.name}" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>
            </div>
        </#if>
    </#list>

    <div class="layui-form-item" style="text-align: right">
        <div class="layui-input-block" style="margin-right: 40px">
            <button class="layui-btn" style="height: 32px; line-height: 32px" lay-submit lay-filter="operateFilter" id="operateSubBtn">提交</button>
            <button type="reset" style="height: 32px; line-height: 32px" class="layui-btn">重置</button>
        </div>
    </div>

</form>
</html>