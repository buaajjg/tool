<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>中农普惠</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<!-- Tell the browser to be responsive to screen width -->
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<link type="image/x-icon" rel="shortcut icon" href="/static/favicon.ico" />
	
	<link rel="stylesheet" href="/static/src/plugins/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="/static/src/dist/css/font-awesome.min.css">

	
	<!-- Theme style -->
	<link rel="stylesheet" href="/static/src/dist/css/AdminLTE.min.css">
	<link rel="stylesheet" href="/static/src/dist/css/skins/skin-blue.min.css">

	<link rel="stylesheet" href="/static/src/plugins/element/css/index.css">
	<style type="text/css">
	.el-upload__input {
	    display: none !important;
	}
	.content-wrapper {
		background: white;
	}
	.body_re{
		font-family: Helvetica Neue,Helvetica,PingFang SC,Hiragino Sans GB,Microsoft YaHei,SimSun,sans-serif;
	    overflow: auto;
	    font-weight: 400;
	    -webkit-font-smoothing: antialiased;
	}
	.demo-block {
	    margin:5%;
	    padding: 80px 0 120px;
	    box-sizing: border-box;
	    border: 1px solid #eaeefb;
		border-radius: 4px;
		transition: .2s;	
	}
	.demo-block .hover{
		box-shadow:0 0 8px 0 rgba(232,237,250,.6),0 2px 4px 0 rgba(232,237,250,.5)
	}
	.zn-content{
		height: 50px;
	    line-height: 50px;
	    text-align: center;
	    border:0.05rem solid #d2e7f7;
	    margin:-0.025rem;
	}
	.zn-content-big{
		min-height: 73px;
	    line-height: 70px;
	    text-align: center;
	    border:0.05rem solid #d2e7f7;
	    margin:-0.025rem;
	    
	    
	}
	.zn-content-noneBorder{
		height: 50px;
	    line-height: 50px;
	    text-align: center;
	}
	.zn-title{
		border-radius: 4px;
	    min-height: 35px;
	    background: #9fd6ff;
	    font-size: 18px;
	    line-height: 35px;
	}
	.zn-info-img{
		width: 5rem;
		height: 5rem;
	}
	sup{
		color:red;
	}
	</style>
</head>
<body class="hold-transition skin-blue body_re">
	<div class="wrapper">
		<%@include file="/WEB-INF/views/snd/common/common_head.jsp"%>
		<%@include file="/WEB-INF/views/snd/common/common_menu.jsp"%>