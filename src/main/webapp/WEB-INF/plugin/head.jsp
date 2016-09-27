<%--
  Created by IntelliJ IDEA.
  User: XuGuang
  Date: 2016/9/27
  Time: 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>请假流程</title>

    <script src="${path}/plugin/jquery/jquery-3.1.1.js"></script>

    <%-- Bootstrap core JS --%>
    <script src="${path}/plugin/bootstrap-3.3.0/js/bootstrap.js"></script>
    <script src="${path}/plugin/bootstrap-select-1.11.2/js/bootstrap-select.js"></script>
    <script src="${path}/plugin/google-code-prettify/prettify.js"></script>

    <!-- Bootstrap core CSS -->
    <link  type="text/css" href="${path}/plugin/bootstrap-3.3.0/css/bootstrap.min.css" rel="stylesheet">
    <link  type="text/css" href="${path}/plugin/bootstrap-3.3.0/css/bootstrap-theme.min.css" rel="stylesheet">
    <link  type="text/css" href="${path}/plugin/bootstrap-select-1.11.2/css/bootstrap-select.min.css" rel="stylesheet">
    <link  type="text/css" href="${path}/plugin/google-code-prettify/prettify.css" rel="stylesheet">

    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

    <style type="text/css">

        html,body{
            height: 100%;
        }

        body{
            padding-top: 90px;
        }

        .wrap{
            min-height: 100%;
            height: auto;
            /* Negative indent footer by its height */
            margin: 0 auto -60px;
            /* Pad bottom by footer height */
            padding: 0 0 60px;
        }

        .form-signin {
            max-width: 330px;
            padding: 15px;
            margin: 45px auto;
        }

        .leaveform{
            max-width: 460px;
            padding: 15px;
            margin: 45px auto;
        }

        .form-signin .form-signin-heading,
        .form-signin .checkbox {
            margin-bottom: 10px;
        }
        .form-signin .checkbox {
            font-weight: normal;
        }
        .form-signin .form-control {
            position: relative;
            font-size: 16px;
            height: auto;
            padding: 10px;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
        }
        .form-signin .form-control:focus {
            z-index: 2;
        }
        .form-signin input[type="text"] {
            margin-bottom: -1px;
            border-bottom-left-radius: 0;
            border-bottom-right-radius: 0;
        }
        .form-signin input[type="password"] {
            margin-bottom: 10px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
        }

        #footer {
            height: 60px;
            background-color: #f5f5f5;
        }

        .container .text-muted {
            margin: 20px 0;
        }

        table tr,th{
            text-align: center;
        }

    </style>

</head>
<body>
<div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${path }/index.do">基于Activiti5.21.0和Bootstrap3.3.0的请假流程</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="${path }/index.do">主页</a></li>
                <li><a href="#about">关于</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">联系<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="https://github.com/YiviS">GitHub:YiviS</a></li>
                        <li class="divider"></li>
                        <li><a href="#">QQ:522022461</a></li>
                        <li class="divider"></li>
                        <li><a href="#">微信:YiviS</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${empty user.id}">
                    <li><a href="${path}/simple/login.do">登录 <span class="glyphicon glyphicon-log-in"></span></a></li>
                </c:if>
                <c:if test="${!empty user.id}">
                    <li><a href="#">${user.id} <span class="glyphicon glyphicon-user"></span></a></li>
                    <li><a href="${path}/simple/login.do">注销 <span class="glyphicon glyphicon glyphicon-off"></span></a></li>
                    <li><a href="#">退出 <span class="glyphicon glyphicon-log-out"></span></a></li>
                </c:if>
            </ul>
        </div><!-- /.nav-collapse -->
    </div><!-- /.container -->
</div><!-- /.navbar -->
