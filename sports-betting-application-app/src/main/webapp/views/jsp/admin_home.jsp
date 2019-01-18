<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!doctype html>
<fmt:setLocale value="${cookie['localeInfo'].value}" />
<fmt:requestEncoding value="utf-8" />
<fmt:setBundle basename="messages/messages" />
<html lang="${cookie['localeInfo'].value}">
<head>
    <!-- Required meta tags -->
    <meta http-equiv='Content-Type' charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>">

    <script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <title>SportsBet - Home</title>
</head>
<body>

<div class="container border border-primary rounded mt-2 p-0">
    <nav class="navbar navbar-expand-lg navbar-light bg-primary">
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item"><a class="nav-link text-light" href="<c:url value="/admin_home"/>"><fmt:message key="web.code.menu.home"/></a></li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-light" data-toggle="dropdown" href="#">
                        <fmt:message key="web.code.menu.language"/>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="<c:url value="?lang=en"/>"><fmt:message key="web.code.english"/></a>
                        <a class="dropdown-item" href="<c:url value="?lang=ru"/>"><fmt:message key="web.code.russian"/></a>
                    </div>
                </li>
                <li class="nav-item"><a class="nav-link text-light" href="<c:url value="/perform_logout"/>"><fmt:message key="web.code.menu.logout"/></a></li>
            </ul>
        </div>
    </nav>
</div>
<div class="container border border-primary rounded mt-2">
    <h2>This is a stub page for now</h2>
</div>

</body>
</html>