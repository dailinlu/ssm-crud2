<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/12/7
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
<%--引入jquery--%>
<script src="${APP_PATH}/static/js/jquery.js"></script>
<%--引入bootstrap的样式--%>
<link href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<script src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<html>
<head>
    <title>员工列表</title>
</head>
<body>
<%--搭建页面--%>
    <div class="container">
        <%--标题--%>
       <div class="row">
            <div class="col-md-12">
                <h1>SSM-CRUD   <small>  by dll</small></h1>
            </div>
       </div>
       <%--按钮--%>
       <div class="row">
           <div class="col-md-4 col-md-offset-8">
               <button class="btn btn-primary">新增</button>
               <button class="btn btn-danger">删除</button>
           </div>
       </div>
       <div class="row">
           <div class="col-md-12">
               <table class="table table-hover">
                   <tr>
                       <th>#</th>
                       <th>empName</th>
                       <th>gender</th>
                       <th>email</th>
                       <th>deptName</th>
                       <th>操作</th>
                   </tr>
                   <c:forEach items="${pageInfo.list}" var="emp">
                       <tr>
                           <th>${emp.empId}</th>
                           <th>${emp.empName}</th>
                           <th>${emp.gender=="m"?"男":"女"}</th>
                           <th>${emp.email}</th>
                           <th>${emp.department.deptName}</th>
                           <th>
                               <button class="btn btn-primary btn-sm">
                                   <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                   编辑
                               </button>
                               <button class="btn btn-danger btn-sm">
                                   <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                   删除
                               </button>
                               <button class="btn btn-info btn-sm">
                                   <span class="glyphicon glyphicon-send" aria-hidden="true"></span>
                                   发信息
                               </button>
                           </th>
                       </tr>
                   </c:forEach>

               </table>
           </div>
       </div>
       <%--分页--%>
       <div class="row">
           <%--分页文字信息--%>
           <div class="col-md-6">
               当前${pageInfo.pageNum}页,总共${pageInfo.pages}页,总共${pageInfo.total}条数据.
           </div>
           <%--分页条信息--%>
           <div class="col-md-6">
               <nav aria-label="Page navigation">
                   <ul class="pagination">
                       <%--首页是 1--%>
                       <li><a href="${APP_PATH}/emps?pn=1">首页</a></li>
                       <%-- pageInfo.hasPreviousPage 是上一页--%>
                       <c:if test="${pageInfo.hasPreviousPage}">
                           <li>
                               <%--pageInfo.pageNum 是当前页--%>
                               <a href="${APP_PATH}/emps?pn=${pageInfo.pageNum-1}" aria-label="Previous">
                                       <%--上一页--%>
                                   <span aria-hidden="true">&laquo;</span>
                               </a>
                           </li>
                       </c:if>
                            <%--pageInfo.navigatepageNums 是所有页数--%>
                       <c:forEach items="${pageInfo.navigatepageNums}" var="page_Num">
                           <c:if test="${pageInfo.pageNum == page_Num}">
                               <li class="active"><a href="">${page_Num}</a></li>
                           </c:if>
                           <c:if test="${pageInfo.pageNum != page_Num}">
                               <li><a href="${APP_PATH}/emps?pn=${page_Num}">${page_Num}</a></li>
                           </c:if>
                       </c:forEach>
                            <%--pageInfo.hasNextPage 下一页是否还有--%>
                       <c:if test="${pageInfo.hasNextPage}">
                           <li>
                               <a href="${APP_PATH}/emps?pn=${pageInfo.pageNum+1}" aria-label="Next">
                                   <span aria-hidden="true">&raquo</span>
                               </a>
                           </li>
                       </c:if>
                            <%--末页就是总页数--%>
                       <li><a href="${APP_PATH}/emps?pn=${pageInfo.pages}">末页</a></li>
                   </ul>
               </nav>
           </div>
       </div>
    </div>
</body>
</html>
