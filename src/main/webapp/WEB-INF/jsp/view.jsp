<%--
  Created by IntelliJ IDEA.
  User: XuGuang
  Date: 2016/9/29
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>view</title>
</head>
<body>
    <div>
        <img src="${path}/viewPic.do?executionId=${executionId}" style="position:absolute;left:0px;top:0px;">
    </div>
</body>
</html>
