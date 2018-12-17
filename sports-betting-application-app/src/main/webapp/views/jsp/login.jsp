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
    <title>SportsBet - Main</title>
</head>
<body>

<div class="container">
    <div class="row bg-dark">
        <div class="col-md-12">
            <p></p>
            <h2 class="text-center text-light">Welcome to SportsBet!</h2>
            <p class="text-center text-light">Sports betting is the activity of predicting sports results and placing a wager on the outcome.</p>
        </div>
    </div>

    <div class="row mt-4">
        <p class="col-2"></p>
        <div class="col">
            <div class="row">
                <h4><a href="#">Login</a> or <a href="#">Register</a> to start!</h4>
            </div>
            <div class="row border border-primary rounded d-inline-block mt-2">
                <div>
                    <p class="bg-primary p-2 text-light font-weight-bold">Login</p>
                    <div class="m-2">
                        <form action="<c:url value="/perform_login"/>" method="post">
                            <label>
                                <input class="border rounded p-1" type="text" name="username" placeholder="Email">
                            </label>
                            <br>
                            <label>
                                <input class="border rounded p-1" type="password" name="password" placeholder="Password">
                            </label>
                            <br>
                            <input class="bg-primary border rounded text-light" type="submit" value="Login">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="<spring:url value="resources/js/bootstrap.min.js"/>"></script>
</body>
</html>