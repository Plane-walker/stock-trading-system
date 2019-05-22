<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>股票交易系统</title>
</head>
<body>
<label><% out.print((String)session.getAttribute("name")+" 你好");%></label>
<form action="search" method="post">
<input type="text" name="searchstock" value="${searchstock}" autocomplete="off" placeholder="输入股票名称">
<input type="submit" value="搜索">
<table>
<tr>
<td><label>热门股票</label></td>
</tr>
</table>
</form>
</body>
</html>