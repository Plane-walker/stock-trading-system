<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<title>股票交易系统</title>
<script type="text/javascript" src="jquery-3.4.1.min.js" ></script>
</head>
<body>
<script>
function comp(){
	$("#gender").attr("type","hidden");
	$("#gendertext").attr("style","display:none");
	$("#phone").attr("type","hidden");
	$("#phonetext").attr("style","display:none");
	$("#email").attr("type","hidden");
	$("#emailtext").attr("style","display:none");
}
function indi(){
	$("#gender").attr("type","text");
	$("#gendertext").attr("style","display:block");
	$("#phone").attr("type","text");
	$("#phonetext").attr("style","display:block");
	$("#email").attr("type","text");
	$("#emailtext").attr("style","display:block");
}
</script>
<form action="reg" method="post">
    <table>
        <tr>
            <th colspan="2">注册</th>
        </tr>
        <tr>
        <td><input type="radio" name="acc_type" value="individul" checked onclick="indi()">个人账户</td><td><input type="radio" name="acc_type" value="company" onclick="comp()">公司账户</td>
        </tr>
        <tr>
            <td>ID</td>
            <td><input type="text" name="userID" value="${userID}" autocomplete="off"></td>
        </tr>
        <tr>
            <td>名称</td>
            <td><input type="text" name="username" value="${username}" autocomplete="off"></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input type="password" name="password" value="${password}" autocomplete="off"></td>
        </tr>
        <tr>
            <td>重复密码：</td>
            <td><input type="password" name="repassword" value="${repassword}" autocomplete="off"></td>
        </tr>
        <tr>
            <td><label ID="gendertext">性别</label></td>
            <td><input type="text" ID="gender" name="gender" value="${gender}" autocomplete="off"></td>
        </tr>
        <tr>
            <td>国家或地区</td>
            <td><input type="text" name="country" value="${country}" autocomplete="off"></td>
        </tr>
        <tr>
            <td><label ID="phonetext">电话</label></td>
            <td><input type="text" ID="phone" name="phone" value="${phone}" autocomplete="off"></td>
        </tr>
        <tr>
            <td><label ID="emailtext">邮箱</label></td>
            <td><input type="text" ID="email" name="email" value="${email}" autocomplete="off"></td>
        </tr>
        <tr>
            <td><input type="submit" value="注册"></td>
            <td><a href="login">登录</a></td>
            <td><font color="red">${error}</font></td>
        </tr>
    </table>
</form>
</body>