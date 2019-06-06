<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="jquery-3.4.1.min.js" ></script>
<script src="bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title>股票交易系统</title>
</head>
<body>
<script>
function info(){
	var html="";
	html+="<tr>";
	html+="<th>用户名</th>";
	html+="<th>昵称</th>";
	if("<%out.print((String)session.getAttribute("acc_type"));%>"=="individual")
	html+="<th>性别</th>";
	html+="<th>国家或地区</th>";
	if("<%out.print((String)session.getAttribute("acc_type"));%>"=="individual")
	html+="<th>可用资产</th>";
	html+="</tr>";
	$("#infohead").html(html);
	html="<tr>";
	html+="<td>${ID}</td>";
	html+="<td>${name}</td>";
	if("<%out.print((String)session.getAttribute("acc_type"));%>"=="individual")
	html+="<td>${gender}</td>";
	html+="<td>${country}</td>";
	if("<%out.print((String)session.getAttribute("acc_type"));%>"=="individual")
	html+="<td>${asset}</td>";
	if("<%out.print((String)session.getAttribute("acc_type"));%>"!="individual")
		html+="<td><a href='publish'>发布股票</a></td>";
	html+="</tr>";
	$("#infobody").html(html);
};
function own(){
	  var url = "own";
	  var data = {"ID":"${ID}"};
	  $.ajax({
	   type :"post",
	   dataType: "json",
	   url : url,
	   data : data,
	   timeout:1000,
	   success:function(dates){
		   if("<%out.print((String)session.getAttribute("acc_type"));%>"=="individual"){
		   var html="";
		   html+="<tr>";
			html+="<th>股票ID</th>";
			html+="<th>股票名称</th>";
			html+="<th>数量</th>";
			html+="</tr>";
			$("#ownhead").html(html);
			html="";
		   for(var i=0;i<dates.length;i++){
			   html+="<tr>";
			   html+="<td>"+dates[i].sto_ID+"</td>";
			   html+="<td>"+dates[i].name+"</td>";
			   html+="<td>"+dates[i].number+"</td>";
			   html+="</tr>";
	   }
		   $("#ownbody").html(html);
		   }
	   },
	   error:function() {
	       }
	  });
	  };
$(function(){
	info();
	own();
});
</script>
<a href="main">返回</a>
<table class="table table-hover">
<thead id="infohead"></tbody>
<tbody id="infobody"></tbody>
</table>
<table class="table table-hover">
<thead id="ownhead"></tbody>
<tbody id="ownbody"></tbody>
</table>
</body>
</html>