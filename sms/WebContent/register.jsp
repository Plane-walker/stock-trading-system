<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<script>
function comp(){
	$("#gender").attr("type","hidden");
	$("#phone").attr("type","hidden");
	$("#email").attr("type","hidden");
}
function indi(){
	$("#gender").attr("type","text");
	$("#phone").attr("type","text");
	$("#email").attr("type","text");
}
</script>
<div class="container">
<div class="row row-centered">
<div class="well col-md-6 col-centered">
<h2>注册</h2>
<form action="reg" method="post">
        <label class="radio-inline col-md-5">
        <input type="radio" name="acc_type" value="individul" checked onclick="indi()">个人账户
        </label>
        <label class="radio-inline col-md-5">
        <input type="radio" name="acc_type" value="company" onclick="comp()">公司账户
        </label>
        <div class="form-group form-inline">
            <label class="control-label col-md-4">ID：</label>
            <input class="form-control col-md-6" type="text" name="userID" value="${userID}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4">名称：</label>
            <input type="text" class="form-control col-md-6" name="username" value="${username}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4">密码：</label>
            <input type="password" class="form-control col-md-6" name="password" value="${password}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4">重复密码：</label>
            <input type="password" class="form-control col-md-6" name="repassword" value="${repassword}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4" ID="gendertext">性别：</label>
            <input type="text" class="form-control col-md-6" ID="gender" name="gender" value="${gender}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4">国家或地区：</label>
            <input type="text" class="form-control col-md-6" name="country" value="${country}" autocomplete="off">
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
            <div class="col-md-4"><input type="submit" value="注册"></div>
            <div class="col-md-4"><a href="login">登录</a></div>
            <div class="col-md-4"><font color="red">${error}</font></div>
        </div>
</form>
</div>
</div>
</div>
</body>