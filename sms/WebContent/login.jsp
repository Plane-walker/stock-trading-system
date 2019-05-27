<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>股票交易系统</title>
</head>
<body>
<form action="log" method="post">
    <table>
        <tr>
            <th colspan="2">登录</th>
        </tr>
        <tr>
        <td><input type="radio" name="acc_type" value="individual" checked>个人账户</td><td><input type="radio" name="acc_type" value="company">公司账户</td>
        </tr>
        <tr>
            <td>ID</td>
            <td><input type="text" name="userID" value="${userID}" autocomplete="off"></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input type="password" name="password" value="${password}" autocomplete="off"></td>
        </tr>
        <tr>
            <td><input type="submit" value="登录"></td>
            <td><a href="register">注册</a></td>
            <td><font color="red">${error}</font></td>
        </tr>
    </table>
</form>
</body>