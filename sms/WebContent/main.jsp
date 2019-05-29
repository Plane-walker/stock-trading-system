<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="jquery-3.4.1.min.js" ></script>
<script src="bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title>股票交易系统</title>
<style>
            /*web background*/
            .container{
                display:table;
                height:100%;
            }

            .row{
                display: table-cell;
                vertical-align: middle;
            }
            /* centered columns styles */
            .row-centered {
                text-align:center;
            }
            .col-centered {
                display:inline-block;
                float:none;
                text-align:left;
                margin-right:-4px;
            }
        </style>
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
		   if(dates.upsanddowns<0){
		   html+="<td><font color='green'>"+dates[i].ID+"<font></td>";
		   html+="<td><font color='green'>"+dates[i].name+"<font></td>";
		   html+="<td><font color='green'>"+dates[i].now_price+"<font></td>";
		   html+="<td><font color='green'>"+dates[i].upsanddowns*100+"% ↓<font></td>";
		   }
		   else{
			   html+="<td><font color='red'>"+dates[i].ID+"<font></td>";
			   html+="<td><font color='red'>"+dates[i].name+"<font></td>";
			   html+="<td><font color='red'>"+dates[i].now_price+"<font></td>";
			   html+="<td><font color='red'>"+dates[i].upsanddowns*100+"% ↑<font></td>";
		   }
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
<label class="col-md-12 text-right"><% out.print((String)session.getAttribute("name")+" 你好");%></label>
<div class="container">
<input class="col-md-4" type="text" ID="stockID" value="${stockID}" autocomplete="off" placeholder="输入股票名称">
<input class="col-md-1" type="submit" value="搜索" onclick="return searchID()">
</div>
<table class="table table-hover">
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