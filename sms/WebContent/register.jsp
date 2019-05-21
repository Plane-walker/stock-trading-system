<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<title>股票交易系统</title>
</head>
<body>
<form action="register" method="post">
    <table>
        <tr>
            <th colspan="2">注册</th>
        </tr>
        <tr>
            <td>ID</td>
            <td><input type="text" name="userID" value="${userID}"></td>
        </tr>
        <tr>
            <td>名称</td>
            <td><input type="text" name="username" value="${username}"></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input type="text" name="password" value="${password}"></td>
        </tr>
        <tr>
            <td>重复密码：</td>
            <td><input type="text" name="repassword" value="${repassword}"></td>
        </tr>
        <tr>
            <td>性别</td>
            <td><input type="text" name="gender" value="${gender}"></td>
        </tr>
        <tr>
            <td>国家或地区</td>
            <td><input type="text" name="country" value="${country}"></td>
        </tr>
        <tr>
            <td><input type="submit" value="提交"></td>
            <td><a href="login.jsp">登录</a></td>
            <td><font color="red">${error}</font></td>
        </tr>
    </table>
</form>
</body>