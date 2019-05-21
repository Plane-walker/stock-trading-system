<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="manageplat.cards.*" %>
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
            <td>ID</td>
            <td><input type="text" name="userID" value="${username}"></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input type="text" name="password" value="${password}"></td>
        </tr>
        <tr>
            <td><input type="submit" value="提交"></td>
            <td><a href="register.jsp">注册</a></td>
            <td><font color="red">${error}</font></td>
        </tr>
    </table>
</form>
</body>