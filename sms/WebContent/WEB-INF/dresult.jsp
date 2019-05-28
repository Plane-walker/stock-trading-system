<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="jquery-3.4.1.min.js" ></script>
<meta charset="UTF-8">
<title>股票交易系统</title>
</head>
<body>
<script>
$(function(){
	var x = 3;
	function jump() {
		x--;
		if (x > 0) {
			document.getElementById("time").innerHTML = x;
		} else {
			location.href = "main";
		}
	}
	 setInterval(jump,1000);
})
</script>
<%= request.getAttribute("result") %>
<label ID="time">3</label><label>s后跳转</label>
</body>
</html>