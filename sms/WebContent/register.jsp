<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page contentType="text/html; charset=UTF-8"  %>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
<title>股票交易系统</title>
<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="jquery-3.4.1.min.js" ></script>
<script src="bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
<style>
            /*web background*/
            .container{
                display:table;
                height:100%;
            }

            .row{
                display: table-cell;
                vertical-align: middle;
            }
            /* centered columns styles */
            .row-centered {
                text-align:center;
            }
            .col-centered {
                display:inline-block;
                float:none;
                text-align:left;
                margin-right:-4px;
            }
        </style>
</head>
<body>
<% response.setContentType("text/html;charset=utf-8"); 
request.setCharacterEncoding("utf-8"); %>
<script>
function comp(){
	$("#gender").prop('disabled', true);
	$("#phone").prop('disabled', true);
	$("#email").prop('disabled', true);
}
function indi(){
	$("#gender").removeAttr('disabled');
	$("#phone").removeAttr('disabled');
	$("#email").removeAttr('disabled');
}
</script>
<div class="container">
<div class="row row-centered">
<div class="well col-md-6 col-centered">
<h2 class="text-primary">注册</h2>
<form action="reg" method="post">
        <label class="radio-inline col-md-5">
        <input type="radio" name="acc_type" value="individual" checked onclick="indi()">个人账户
        </label>
        <label class="radio-inline col-md-5">
        <input type="radio" name="acc_type" value="company" onclick="comp()">公司账户
        </label>
        <div class="form-group form-inline">
            <label class="control-label col-md-4">*用户名：</label>
            <input class="form-control col-md-6" type="text" name="userID" value="${userID}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4">*昵称：</label>
            <input type="text" class="form-control col-md-6" name="username" value="${username}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4">*密码：</label>
            <input type="password" class="form-control col-md-6" name="password" value="${password}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4">*重复密码：</label>
            <input type="password" class="form-control col-md-6" name="repassword" value="${repassword}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4" ID="gendertext">*性别：</label>
            <select class="form-control col-md-6" ID="gender" name="gender" >
            <option value="male" selected>男</option>
            <option value="female">女</option>
            </select>
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4">*国家或地区：</label>
            <select class="form-control col-md-6" name="country" >
            <option value="China">中国</option>
            <option value="USA">美国</option>
            <option value="UK">英国</option>
            <option value="Russia">俄罗斯</option>
            <option value="France">法国</option>
            </select>
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4" ID="phonetext">电话：</label>
            <input type="text" class="form-control col-md-6" ID="phone" name="phone" value="${phone}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4" ID="emailtext">邮箱：</label>
            <input type="text" class="form-control col-md-6" ID="email" name="email" value="${email}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <div class="col-md-4"><input class="btn btn-primary" type="submit" value="注册"></div>
            <div class="col-md-4"><a href="login">登录</a></div>
            <div class="col-md-4"><font color="red">${error}</font></div>
        </div>
</form>
</div>
</div>
</div>
</body>