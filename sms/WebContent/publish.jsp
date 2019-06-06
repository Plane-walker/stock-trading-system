<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
<title>股票交易系统</title>
<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="jquery-3.4.1.min.js" ></script>
<script src="bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
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
<div class="container">
<div class="row row-centered">
<div class="well col-md-6 col-centered">
<h2>发布股票</h2>
<form action="publishing" method="post">
        <div class="form-group form-inline">
            <label class="control-label col-md-4">股票名称：</label>
            <input type="text" class="form-control col-md-6" name="name" value="${name}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4">发行量：</label>
            <input type="text" class="form-control col-md-6" name="issue_circulation" value="${issue_ciculation}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4" >发行价格：</label>
            <input type="text" class="form-control col-md-6" ID="issue_price" name="issue_price" value="${issue_price}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4" >股票类型：</label>
            <select class="form-control col-md-6" name="type" >
            <option value="USA">USA</option>
            <option value="HongKong">HK</option>
            <option value="ShangHai">SH</option>
            <option value="ShenZhen">SZ</option>
            </select>
        </div>
        <div class="form-group form-inline">
            <div class="col-md-2"><input type="submit"  class="btn btn-primary" value="发布"></div>
            <div class="col-md-6"><a href="main">取消发布，返回</a></div>
            <div class="col-md-4"><font color="red">${error}</font></div>
        </div>

</form>
</div>
</div>
</div>
</body>

</html>