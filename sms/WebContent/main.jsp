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
var s_ID;
function refresh(){
	  var url = "refresh";
	  var data = {"s_ID":s_ID,"size":"10"};
	  $.ajax({
	   type :"post",
	   dataType: "json",
	   url : url,
	   data : data,
	   timeout:1000,
	   success:function(dates){
		   var html="";
		   for(var i=0;i<dates.length;i++){
		   html+="<tr>";
		   html+="<td>"+dates[i].ID+"</td>";
		   html+="<td>"+dates[i].name+"</td>";
		   html+="<td>"+dates[i].now_price+"</td>";
		   html+="<td>"+dates[i].upsanddowns+"</td>";
		   html+="<td><form action='trade' methon='get'><input type='hidden' name='stockID' value='"+dates[i].ID+"'><input type='submit' value='交易'></input></form></td>";
		   html+="</tr>";
	   }
		   $("#stocktable").html(html);
	   },
	   error:function() {
	       }
	  });
	  };
function searchID(){
	s_ID=$("#stockID").val();
	refresh();
};
 $(function(){
	 refresh();
 	 setInterval(refresh,1000);
})
</script>
<label><% out.print((String)session.getAttribute("name")+" 你好");%></label>
<input type="text" ID="stockID" value="${stockID}" autocomplete="off" placeholder="输入股票名称">
<input type="submit" value="搜索" onclick="return searchID()">
<table>
<thead>
<tr>
<th>股票ID</th>
<th>股票名称</th>
<th>当前价格</th>
<th>涨跌幅</th>
</tr>
</thead>
<tbody id="stocktable"></tbody>
</table>
</body>
</html>