<%--
  Created by IntelliJ IDEA.
  User: Oleksii_Kovetskyi
  Date: 12/14/2018
  Time: 6:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<html>
<head>
    <!-- Required meta tags -->
    <meta http-equiv='Content-Type' charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<spring:url value="/resources/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<spring:url value="/resources/css/style.css"/>">

    <script src="<spring:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
    <script src="<spring:url value="/resources/js/bootstrap.min.js"/>"></script>
    <title>SportsBet - Events</title>
</head>
<body>

<jsp:include page="navbar.jsp" />

<div class="container border border-primary rounded mt-2">
    <div class="row bg-primary">
        <h6 class="text-center text-light font-weight-normal p-2 pl-4"><spring:message code="web.code.events"/></h6>
    </div>
    <table class="w-100">
        <tr>
            <th><spring:message code="web.code.event.title"/></th>
            <th><spring:message code="web.code.event.type"/></th>
            <th><spring:message code="web.code.event.start"/></th>
            <th><spring:message code="web.code.event.end"/></th>
            <th></th>
        </tr>
        <c:forEach items="${events}" var="item" varStatus="loop">
            <tr event_id="${item.id}">
                <td>${item.title}</td>
                <td>${item['class'].simpleName}</td>
                <td>${item.startDate}</td>
                <td>${item.endDate}</td>
                <td><input class="border border-primary rounded bg-primary text-light <c:out escapeXml="false" value="${item.result == null ? '' : 'd-none'}"/>" type="button" value="<spring:message code="web.code.bet.list"/>" onclick="window.location = '/bets?event_id=${item.id}'"></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
