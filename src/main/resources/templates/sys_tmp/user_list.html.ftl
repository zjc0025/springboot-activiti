<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns="http://www.w3.org/1999/html"
      xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">

<!--
    @Describe 代码生成器生成
    @Author   ${author}
    @Date     ${date}
    @Email    ${cfg.email}
-->
<head>
    <meta charset="UTF-8">
    <title>${table.name ? cap_first}列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/static/lib/layui/css/layui.css">
    <link rel="stylesheet" href="/static/css/scroll-bar.css">
    <link rel="stylesheet" href="/static/css/sub-page.css">
    <link rel="stylesheet" href="/static/lib/nprogress/nprogress.css">
</head>
<body>
<div class="ok-body">
    <!--面包屑导航区域-->
    <div class="ok-body-breadcrumb">
        <span class="layui-breadcrumb">
            <a><cite>首页</cite></a>
            <a><cite>常用页面</cite></a>
            <a><cite>${table.name ? cap_first}列表</cite></a>
        </span>
        <a class="layui-btn layui-btn-sm" href="javascript:location.replace(location.href);" title="刷新">
            <i class="layui-icon layui-icon-refresh"></i>
        </a>
    </div>
    <!--模糊搜索区域-->
    <div class="layui-row">
        <form class="layui-form layui-col-md12 ok-search" id="searchForm">
            <#list table.fields as field>
                <#if !field.keyFlag><#--生成普通字段 -->
            <div class="layui-form-item layui-inline" >
                <label class="layui-form-label" style="width: 68px;padding: 9px 2px">${field.comment}</label>
                <div class="layui-input-block" style="margin-left: 80px">
                    <input style="height: 32px" type="text" id="${field.propertyName}" name="${field.propertyName}" placeholder="请输入" autocomplete="off" class="layui-input">
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
    </div>
    <!--工具栏-->
    <script  type="text/html" id="textBar">
            <button class="layui-btn layui-btn-normal" lay-event="batchEnable" >
                <i class="iconfont icon-shangsheng"></i>批量启用
            </button>
            <button class="layui-btn layui-btn-warm" lay-event="batchDisEnable" >
                <i class="iconfont icon-web-icon-"></i>批量停用
            </button>
        <shiro:hasAnyPermissions name="${entity ? uncap_first}:add,${entity ? uncap_first}:update">
            <button class="layui-btn"  lay-event="add" >
                <i class="layui-icon">&#xe61f;</i>添加用户
            </button>
            <button class="layui-btn"  lay-event="update" >
                <i class="layui-icon">&#xe642;</i>修改用户
            </button>
        </shiro:hasAnyPermissions>
        <shiro:hasPermission name='${entity ? uncap_first}:delete'>
            <button class="layui-btn layui-btn-danger"  lay-event="delete" >
                <i class="layui-icon layui-icon-delete"></i>删除用户
            </button>
        </shiro:hasPermission>

        <!--<span>共有数据：<i id="countNum"></i> 条</span>-->
    </script>
    <!--数据表格-->
    <table class="layui-hide" id="tableId" lay-filter="tableFilter"></table>
    <script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>

    <#--修改 隐藏域-->
    <input id="hidArea" hidden>
</div>
<!--js逻辑-->
<script src="/static/lib/layui/layui.js"></script>
<script src="/static/lib/nprogress/nprogress.js"></script>
<script th:inline="none">
    NProgress.start();
    window.onload = function () {
        NProgress.done();
    }
    layui.use(['element', 'table', 'form', 'jquery', 'laydate'], function () {
        var element = layui.element;
        var table = layui.table;
        var form = layui.form;
        var $ = layui.jquery;
        var laydate = layui.laydate;

        table.render({
            id:'reloadId',
            elem: '#tableId',
            url: '/${package.ModuleName}/${entity ? lower_case}/findByParams',
            toolbar: '#textBar',
            limit: 10,
            page: true,
            cols: [[
                {type: 'checkbox'},
                // {field: 'status', title: '状态', width: 85, templet: '#statusTpl'},
                // {field: 'role', title: '角色', width: 100, templet: '#roleTpl'},
                <#list table.fields as field>
                <#if field.keyFlag><#--主键 -->
                {field:'${field.propertyName}', title:'${field.comment}', width:80,  unresize: true, sort: true},
                </#if>
                <#if !field.keyFlag><#--生成普通字段 -->
                {field:'${field.propertyName}', title:'${field.comment}', width:120},
                </#if>
                </#list>
                {fixed: 'right', title:'操作', toolbar: '#barDemo', width:150},
            ]]
            // ,done: function (res, curr, count) {
            //     $("#countNum").text(count);
            // }
        });

        //查询表单监听
        form.on('submit(subFilter)', function(data){
            // console.log(JSON.stringify(data.field));
            reloadTable(data.field);
            return false;
        });


        var operate;
        table.on('toolbar(tableFilter)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                // 写在这里可以避免表格重新加载带来的按钮绑定失效
                case 'add':
                    operate = layer.open({
                        type: 2,
                        maxmin: true,
                        shade: 0.5,
                        anim: 4,
                        area: ['90%', '90%'], //宽高
                        skin: 'layui-layer-rim', //加上边框
                        content:'/${package.ModuleName}/${entity ? lower_case}/addOrUpdateIndex'
                    });
                    break;

                case 'update':
                    var data = checkStatus.data;
                    // console.log(data);
                    if(data.length != 1){
                        layer.alert("请确认选中一条记录！")
                        return false;
                    }
                    $("#hidArea").val(JSON.stringify(data));  //隐藏域赋值
                    operate = layer.open({
                        type: 2,
                        maxmin: true,
                        shade: 0.5,
                        anim: 4,
                        area: ['90%', '90%'], //宽高
                        skin: 'layui-layer-rim', //加上边框
                        content:'/${package.ModuleName}/${entity ? lower_case}/addOrUpdateIndex',
                        end: function () {
                            console.log("end");
                            $("#hidArea").val(""); //清空回填数据
                        }
                    });
                    break;

                case 'delete':
                    var data = checkStatus.data;
                    if(data.length <= 0){
                        layer.alert("请至少选中一条记录！")
                        return false;
                    }
                    var array = new Array();
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
                    layer.confirm('确定要删除选中数据吗？', {
                        skin: 'layui-layer-lan',
                        icon: 2, title: '提示', anim: 6,
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        $.ajax({
                            url:'/${package.ModuleName}/${entity ? lower_case}/delByIds',
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
                    }, function(){
                        //取消回调
                    });

                    break;
            };
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
                  ${field.propertyName}: formData.${field.propertyName},
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

    })
</script>
<!--模板-->
<script type="text/html" id="statusTpl">
    <input type="checkbox" name="status" value="{{d.id}}" lay-skin="switch" lay-text="启用|停用" {{ d.status==0 ? 'checked' : ''}}>
</script>

</body>
</html>
