<%--
  Created by IntelliJ IDEA.
  User: XuGuang
  Date: 2016/9/27
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/plugin/head.jsp" %>
<div class="container">

    <div class="row">

        <div class="col-lg-2">
            <div class="panel panel-primary">
                <div class="panel-heading">流程定义列表</div>
                <div class="panel-body">
                    <a href="#pilist">流程定义列表</a>
                </div>
            </div>
            <div class="panel panel-primary">
                <div class="panel-heading">认领任务</div>
                <div class="panel-body">
                    <a href="#claimtasklist">认领任务</a>
                </div>
            </div>
            <div class="panel panel-primary">
                <div class="panel-heading">待办任务</div>
                <div class="panel-body">
                    <a href="#tasklist">待办任务</a>
                </div>
            </div>
            <div class="panel panel-primary">
                <div class="panel-heading">任务流转状况</div>
                <div class="panel-body">
                    <a href="#taskhistory">任务流转状况</a>
                </div>
            </div>
            <div class="panel panel-primary">
                <div class="panel-heading">历史流程</div>
                <div class="panel-body">
                    <a href="#taskhistory">历史流程</a>
                </div>
            </div>
        </div>

        <div class="col-lg-10">
            <!-- 流程列表-->
            <div id="pilist">
                <table class="table table-bordered table-hover">
                    <caption><h3><strong>流程定义</strong></h3></caption>
                    <thead>
                    <tr>
                        <th>流程ID</th>
                        <th>流程名称</th>
                        <th>流程版本</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="pd" items="${pdList}"  >
                        <tr>
                            <td>${pd.id}</td>
                            <td>${pd.name}</td>
                            <td>${pd.version}</td>
                            <td>
                                <a href="${path}/startProcessDefinition.do?processDefId=${pd.id}">启动流程</a>
                                &nbsp;|&nbsp;
                                <a href="${path}/viewprocessDef.do?processDefId=${pd.id}" target="_blank">查看流程定义</a>
                                &nbsp;|&nbsp;
                                <a href="javascript:showModle('请假流程图','${path}/viewprocessDefImage.do?processDefId=${pd.id}');">查看流程图</a>
                                &nbsp;|&nbsp;
                                <a href="${path}/remove.do?processDefId=${pd.id}">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="pdfModal" tabindex="-1" role="dialog" aria-labelledby="pdfModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="pdfModalLabel">请假流程图</h4>
                    </div>
                    <div class="modal-body">

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

    </div>
</div>
<%@ include file="/WEB-INF/plugin/foot.jsp" %>
