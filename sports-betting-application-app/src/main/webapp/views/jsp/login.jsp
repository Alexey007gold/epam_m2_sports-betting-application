<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!doctype html>
<html lang="${cookie['lang'].value}">
<head>
    <!-- Required meta tags -->
    <meta http-equiv='Content-Type' charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<spring:url value="/resources/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<spring:url value="/resources/css/style.css"/>">

    <script src="<spring:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
    <script src="<spring:url value="/resources/js/bootstrap.min.js"/>"></script>
    <title>SportsBet - Main</title>
</head>
<body>

<div class="container mt-2 p-0">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-light" data-toggle="dropdown" href="#">
                        <spring:message code="web.code.menu.language"/>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="?lang=en"><spring:message code="web.code.english"/></a>
                        <a class="dropdown-item" href="?lang=ru"><spring:message code="web.code.russian"/></a>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
</div>

<div class="container">
    <div class="row bg-dark">
        <div class="col-md-12">
            <p></p>
            <h2 class="text-center text-light"><spring:message code="web.code.welcome"/></h2>
            <p class="text-center text-light"><spring:message code="web.code.about"/></p>
        </div>
    </div>

    <div class="row mt-4">
        <p class="col-2"></p>
        <div class="col">
            <div class="row">
                <h4><a href="#"><spring:message code="web.code.login"/></a> <spring:message code="web.code.or"/> <a href="#"><spring:message code="web.code.register"/></a> <spring:message code="web.code.tostart"/></h4>
            </div>
            <div class="row border border-primary rounded d-inline-block mt-2">
                <div>
                    <p class="bg-primary p-2 text-light font-weight-bold"><spring:message code="web.code.dologin"/></p>
                    <div class="m-2">
                        <form action="<c:url value="/perform_login"/>" method="post">
                            <c:if test="${param.logout != null}">
                                <span class="text-success"><spring:message code="web.code.success.loggedout"/></span><br>
                            </c:if>
                            <label>
                                <input class="border rounded p-1" type="text" name="username" placeholder="<spring:message code="web.code.email"/>">
                            </label>
                            <br>
                            <label>
                                <input class="border rounded p-1" type="password" name="password" placeholder="<spring:message code="web.code.password"/>">
                            </label>
                            <br>

                            <c:if test="${param.error}">
                                <span class="text-danger"><spring:message code="web.code.error.invalid"/></span><br>
                            </c:if>
                            <input class="bg-primary border rounded text-light" type="submit" value="<spring:message code="web.code.dologin"/>">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>