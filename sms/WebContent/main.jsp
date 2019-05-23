<%@ page language="java" contentType="text/html; UTF-8"
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
 setInterval(refresh,1000);
 function refresh(){
  var url = "refresh";
  var data = {};
  $.ajax({
   type :"post",
   url : url,
   data : data,
   timeout:1000,
   success:function(dates){
	   var arr=dates.split(" ");
	   $("#stockID").html(arr[0]);
	   $("#stockname").html(arr[1]);
   },
   error:function() {
       }
  });
  };
})
</script>
<label><% out.print((String)session.getAttribute("name")+" 你好");%></label>
<form action="search" method="post">
<input type="text" name="searchstock" value="${searchstock}" autocomplete="off" placeholder="输入股票名称">
<input type="submit" value="搜索">
<table>
<tr>
<td><label>热门股票</label></td>
</tr>
<tr><td><label ID="stockID">${stockID}</label></td><td><label ID="stockname">${stockname}</label></td></tr>
</table>
</form>
</body>
</html>