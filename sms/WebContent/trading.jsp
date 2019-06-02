<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="jquery-3.4.1.min.js" ></script>
<script src="bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
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
function info(){
	var html="";
		html+="<tr>";
		html+="<td>卖五</td>";
		html+="<td>${sp4}</td>";
		html+="<td>${sn4}</td>";
		html+="</tr>";
		html+="<tr>";
		html+="<td>卖四</td>";
		html+="<td>${sp3}</td>";
		html+="<td>${sn3}</td>";
		html+="</tr>";
		html+="<tr>";
		html+="<td>卖三</td>";
		html+="<td>${sp2}</td>";
		html+="<td>${sn2}</td>";
		html+="</tr>";
		html+="<tr>";
		html+="<td>卖二</td>";
		html+="<td>${sp1}</td>";
		html+="<td>${sn1}</td>";
		html+="</tr>";
		html+="<tr>";
		html+="<td>卖一</td>";
		html+="<td>${sp0}</td>";
		html+="<td>${sn0}</td>";
		html+="</tr>";
		html+="<tr>";
		html+="<td>买一</td>";
		html+="<td>${bp0}</td>";
		html+="<td>${bn0}</td>";
		html+="</tr>";
		html+="<tr>";
		html+="<td>买二</td>";
		html+="<td>${bp1}</td>";
		html+="<td>${bn1}</td>";
		html+="</tr>";
		html+="<tr>";
		html+="<td>买三</td>";
		html+="<td>${bp2}</td>";
		html+="<td>${bn2}</td>";
		html+="</tr>";
		html+="<tr>";
		html+="<td>买四</td>";
		html+="<td>${bp3}</td>";
		html+="<td>${bn3}</td>";
		html+="</tr>";
		html+="<tr>";
		html+="<td>买五</td>";
		html+="<td>${bp4}</td>";
		html+="<td>${bn4}</td>";
		html+="</tr>";
	$("#tradeinfo").html(html);
};
$(function(){
	info();
});
</script>
<table class="table table-hover">
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
<td>${upsanddowns}%</td>
</tr>
</tbody>
</table>
<table class="table table-hover">
<tbody id="tradeinfo">
</tbody>
</table>
<div class="container">
<div class="row row-centered">
<div class="well col-md-6 col-centered">
<form action="deal" method="post">
<label class="radio-inline col-md-4">
<input type="radio" name="trade_type" value="purchase" checked>购买
</label>
<label class="radio-inline col-md-4">
<input type="radio" name="trade_type" value="sell">出售
</label>
<div class="form-group form-inline">
<label class="control-label col-md-2">数量：</label>
<input class="form-control col-md-6" type="text" name="number" value="${number}" autocomplete="off">00股
</div>
<div class="form-group form-inline">
<label class="control-label col-md-2">价格：</label>
<input class="form-control col-md-6" type="text" name="price" value="${price}" autocomplete="off">
</div>
<input type="hidden" name="stockID" value="${stockID}">
<div class="form-group form-inline">
<input class="col-md-2" type="submit" value="提交">
<div class="col-md-6"><a href="main">放弃交易，返回</a></div>
</div>
</form>
</div>
</div>
</div>
</body>
</html>