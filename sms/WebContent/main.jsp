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
var page=1;
var last=false;
function psw(){
	html="";
	if(page>1)
	html+="<button class='control-label col-md-2 btn btn-info' onclick='switchp()'>上一页</button>";
	   html+="<label class='control-label col-md-1'>第</label>";
    html+="<input type='text' class='form-control col-md-1' id='paget' name='paget' value='${paget}' autocomplete='off'>";
    html+="<button class='control-label col-md-2 btn btn-info' onclick='switchl()'>下一页</button>";
    html+="<button class='control-label col-md-2 btn btn-info' onclick='jump()'>跳页</button>";
    $("#pageswitch").html(html);
    $("#paget").val(page);
}
function switchl(){
	if(!last){
	page++;
	refresh();
	psw();
	}
}
function switchp(){
	page--;
	refresh();
	psw();
}
function jump(){
	page=$("#paget").val();
	refresh();
	psw();
}
function refresh(){
	  var url = "refresh";
	  var data = {"s_name":s_ID,"size":"10","page":page};
	  $.ajax({
	   type :"post",
	   dataType: "json",
	   url : url,
	   data : data,
	   timeout:1000,
	   success:function(dates){
		   var html="";
		   if(dates.length<10)
			   last=true;
		   else
			   last=false;
		   for(var i=0;i<dates.length;i++){
		   html+="<tr>";
		   if(dates[i].upsanddowns<0){
		   html+="<td><font color='green'>"+dates[i].ID+"<font></td>";
		   html+="<td><font color='green'>"+dates[i].name+"<font></td>";
		   html+="<td><font color='green'>"+dates[i].now_price.toFixed(2)+"<font></td>";
		   html+="<td><font color='green'>"+dates[i].upsanddowns.toFixed(2)+"% ↓<font></td>";
		   }
		   else{
			   html+="<td><font color='red'>"+dates[i].ID+"<font></td>";
			   html+="<td><font color='red'>"+dates[i].name+"<font></td>";
			   html+="<td><font color='red'>"+(dates[i].now_price).toFixed(2)+"<font></td>";
			   html+="<td><font color='red'>"+(dates[i].upsanddowns).toFixed(2)+"% ↑<font></td>";
		   }
		   if("<%out.print((String)session.getAttribute("acc_type"));%>"=="individual")
		   html+="<td><form action='trade' methon='get'><input type='hidden' name='stockID' value='"+dates[i].ID+"'><input class='btn btn-info' type='submit' value='交易'></input></form></td>";
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

function exit(){
	var url = "exit";
	var data = {};
	 $.ajax({
		   type :"post",
		   dataType: "json",
		   url : url,
		   data : data,
		   timeout:1000,
		   success:function(dates){
		   },
		   error:function() {
		       }
		  });
	 window.location.reload();
};
 $(function(){
	 refresh();
	 psw();
 	 setInterval(refresh,20*1000);
})
</script>
<label class="col-md-12 text-right"><a href=<%="home?ID="+(String)session.getAttribute("ID")%>><%out.print((String)session.getAttribute("name"));%></a> 你好 <a id="exit" href="javascript:void(0);" onclick="return exit()">退出登录</a></label>
<div class="container">
<input class="col-md-4" type="text" ID="stockID" value="${stockID}" autocomplete="off" placeholder="输入股票名称">
<input class="col-md-1 btn btn-info" type="submit" value="搜索" onclick="return searchID()">
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
<div class="container">
<div class="row row-centered">
<div class="well col-md-6 col-centered">
<div id="pageswitch" class="form-group form-inline">
            <label class="control-label col-md-2">第</label>
            <input type="text" class="form-control col-md-2" id="page" name="page" value="${page}" autocomplete="off">
        </div>
</div>
</div>
</div>
</body>
</html>