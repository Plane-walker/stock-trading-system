<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
<title>股票交易系统</title>
</head>
<body>
<table>
<thead>
<tr>
<th>股票ID</th>
<th>股票名称</th>
<th>当前价格</th>
<th>涨跌幅</th>
</tr>
</thead>
<tbody>
<tr>
<td>${stockID}</td>
<td>${stockname}</td>
<td>${now_price}</td>
<td>${upsanddowns}</td>
</tr>
</tbody>
</table>
<form action="deal" method="post">
<table>
<tr>
<td><input type="radio" name="trade_type" value="purchase" checked>购买</td><td><input type="radio" name="trade_type" value="sell">出售</td>
</tr>
<tr>
<td>数量</td>
<td><input type="text" name="number" value="${number}"></td>
</tr>
<tr>
<td>价格</td>
<td><input type="text" name="price" value="${price}"></td>
</tr>
<tr>
<td><input type="hidden" name="stockID" value="${stockID}"></td>
</tr>
<tr>
<td><input type="submit" value="提交"></td>
</tr>
</table>
</form>
</body>
</html>