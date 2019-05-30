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
<td>${upsanddowns}</td>
</tr>
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
<input class="form-control col-md-6" type="text" name="number" value="${number}" autocomplete="off">
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