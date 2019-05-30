<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
<script src="jquery-3.4.1.min.js" ></script>
<script src="bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title>股票交易系统</title>
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
<div class="container">
<div class="row row-centered">
<div class="well col-md-6 col-centered">
<h2>登录</h2>
<form role="form" action="log" method="post">
        <label class="radio-inline col-md-4">
        <input type="radio" name="acc_type" value="individual" checked>个人账户
        </label>
        <label class="radio-inline col-md-4">
        <input type="radio" name="acc_type" value="company">公司账户
        </label>
        <div class="form-group form-inline">
            <label class="control-label col-md-3">用户名：</label>
            <input type="text" class="form-control col-md-6" name="userID" value="${userID}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-3">密码：</label>
            <input type="password" class="form-control col-md-6" name="password" value="${password}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <div class="col-md-2"><input type="submit" value="登录"></div>
            <div class="col-md-2"><a href="register">注册</a></div>
            <div class="col-md-6"><font color="red">${error}</font></div>
            </div>
</form>
</div>
</div>
</div>
</body>