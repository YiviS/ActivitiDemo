<%--
  Created by IntelliJ IDEA.
  User: XuGuang
  Date: 2016/9/28
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<jsp:include page="/WEB-INF/plugin/head.jsp"></jsp:include>
<div class="wrap">
    <div class="container">
        <form class="form-signin" role="form" action="${path}/checkLogin.do" method="post">
            <h2 class="form-signin-heading text-center">用户登录</h2>
            <input type="text" class="form-control input-lg" id="userName" name="userName" placeholder="用户名" >
            <input type="password" class="form-control input-lg" id="passWord" name="passWord" placeholder="密   码" value="000000">
            <div align="center">
                <button type="submit" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-ok-sign"></span> 登 录</button>
                <button type="reset" class="btn btn-default btn-lg"><span class="glyphicon glyphicon-remove-circle"></span> 取消</button>
            </div>
        </form>
    </div>
</div>
<jsp:include page="/WEB-INF/plugin/foot.jsp"></jsp:include>
