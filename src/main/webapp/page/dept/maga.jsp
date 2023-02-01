<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>欢迎页面-X-admin2.0</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${ctx}/public/css/font.css">
    <link rel="stylesheet" href="${ctx}/public/css/xadmin.css">
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/public/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/public/js/xadmin.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
      <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
      <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
      .row {
        display: flex;
      }
      .column {
        flex: 50%;
      }
    </style>

  </head>
  
  <body>
    <div class="x-body">
        <form class="layui-form" method="POST" id="deptForm"  action="${ctx}/dept/add">
          
          <input type="hidden" name="id" id="id" value="${dept.id }" >

          <div class="row">
            <div class="column">
                <div class="layui-form-item">
                    <label for="username" class="layui-form-label">
                        <span class="x-red">*</span>グループ名
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="name" name="name" required="" lay-verify="required"
                        autocomplete="off" class="layui-input" value="${dept.name }">
                    </div>
                </div>

                <div class="layui-form-item">
                  <label for="username" class="layui-form-label" style="white-space: nowrap; width: 95px;">
                      <span class="x-red">*</span>グループ内目標
                  </label>
                  <div class="layui-input-inline">
                      <input type="text" id="name" name="name" required="" lay-verify="required"
                      autocomplete="off" class="layui-input" value="${dept.name }">
                  </div>
                </div>

                <div class="layui-form-item">
                  <label for="username" class="layui-form-label" style="white-space: nowrap;">
                      <span class="x-red">*</span>議事録・資料
                  </label>
                  <div class="layui-input-inline">
                      <input type="text" id="name" name="name" required="" lay-verify="required"
                      autocomplete="off" class="layui-input" value="${dept.name }">
                  </div>
                </div>
            </div>
            <div class="column">
              グループ内構成要員の目標管理
              <table class="layui-table">
                <thead>
                  <tr>
                    
                    <th style="text-align: center;">役割</th>
                    <th style="text-align: center;">構成要員（名前）</th>
                    
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${requestScope.list}" var="dept" varStatus="stat">
                    <tr>
                      
                      <td>${dept.job}</td>
                      <td>${dept.name}<a href="#"style=" float:right" onclick="x_admin_show('OKRグループ内ユーザー管理（役割設定）','${ctx}/dept/maga1?id=${dept.id}',650)">;</a></td>
                    
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
          
          <!-- <div class="layui-form-item">
              <label for="phone" class="layui-form-label">
                  <span class="x-red">*</span>详细信息
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="remark" name="remark" required="" lay-verify="required"
                  autocomplete="off" class="layui-input" value="${dept.remark }">
              </div>
            
          </div> -->
          <div class="layui-form-item">
              <label for="L_repass" class="layui-form-label">
              </label>
              <input type="submit" value=" 変更" class="layui-btn" lay-filter="add" lay-submit=""/>
                 
          </div>
      　</form>
    </div>
    <script>
        layui.use(['form','layer'], function(){
            $ = layui.jquery;
          var form = layui.form
          ,layer = layui.layer;
        
          //自定义验证规则
          form.verify({
            nikename: function(value){
              if(value.length < 5){
                return '昵称至少得5个字符啊';
              }
            }
            ,pass: [/(.+){6,12}$/, '密码必须6到12位']
            ,repass: function(value){
                if($('#L_pass').val()!=$('#L_repass').val()){
                    return '两次密码不一致';
                }
            }
          });

          //监听提交
          form.on('submit(add)', function(data){
        	  
            console.log(data);
            var id = document.getElementById("id").value;
            console.log(id);
            if (id === null || id === '') {
                layer.alert("增加成功", {icon: 6},function () {
            	document.getElementById('deptForm').submit();
                // 获得frame索引
                var index = parent.layer.getFrameIndex(window.name);
                //关闭当前frame
                parent.layer.close(index);
               
            });
            } else{
                layer.alert("修改成功", {icon: 6},function () {
            	document.getElementById('deptForm').submit();
                // 获得frame索引
                var index = parent.layer.getFrameIndex(window.name);
                //关闭当前frame
                parent.layer.close(index);
               
            });
            }

            //发异步，把数据提交给php
            
            return false;
          });
          
          
        });
    </script>
    
  </body>

</html>