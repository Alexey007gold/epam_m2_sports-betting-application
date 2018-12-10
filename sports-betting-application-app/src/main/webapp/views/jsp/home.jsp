<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<spring:url value="/resources/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<spring:url value="/resources/css/style.css"/>">
    <title>SportsBet - Home</title>
</head>
<body>

<div class="container border border-primary rounded mt-2">
    <div class="row bg-primary">
        <h6 class="text-center text-light font-weight-normal p-2">Account details</h6>
    </div>
    <form class="mt-3">
        <div class="input-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Name</span>
            </div>
            <input class="form-control" type="text">
        </div>
        <div class="input-group mt-2">
            <div class="input-group-prepend">
                <span class="input-group-text">Date of Birth</span>
            </div>
            <input class="form-control" type="date">
        </div>
        <div class="input-group mt-2">
            <div class="input-group-prepend">
                <span class="input-group-text">Account number</span>
            </div>
            <input class="form-control" type="text">
        </div>
        <div class="input-group mt-2">
            <div class="input-group-prepend">
                <span class="input-group-text">Currency</span>
            </div>
            <input class="form-control" type="text" value="USD" disabled>
        </div>
        <div class="input-group mt-2">
            <div class="input-group-prepend">
                <span class="input-group-text">Balance</span>
            </div>
            <input class="form-control" type="text">
        </div>
        <input class="bg-primary border border-primary rounded mt-2 mb-2 text-light" type="submit" value="Save">
    </form>
</div>

<div class="container border border-primary rounded mt-2">
    <div class="row bg-primary">
        <h6 class="text-center text-light font-weight-normal p-2">Wagers</h6>
    </div>
    <table class="w-100">
        <tr>
            <th></th>
            <th>#</th>
            <th>Event title</th>
            <th>Event type</th>
            <th>Bet type</th>
            <th>Outcome value</th>
            <th>Outcome odd</th>
            <th>Wager amount</th>
            <th>Winner</th>
            <th>Processed</th>
        </tr>
        <tr>
            <td><input class="border border-primary rounded bg-primary text-light" type="button" value="Remove"></td>
            <td>1</td>
            <td>MTK-FTC - 2018.01.02</td>
            <td>Football match</td>
            <td>Winner</td>
            <td>MTK</td>
            <td>1:2</td>
            <td>10 000 USD</td>
            <td>-</td>
            <td>-</td>
        </tr>
        <tr>
            <td><input class="d-none border border-primary rounded bg-primary text-light" type="button" value="Remove"></td>
            <td>2</td>
            <td>MTK-FTC - 2018.01.02</td>
            <td>Football match</td>
            <td>Goals</td>
            <td>5</td>
            <td>1:3</td>
            <td>10 000 USD</td>
            <td>Yes</td>
            <td>Yes</td>
        </tr>
        <tr>
            <td><input class="d-none border border-primary rounded bg-primary text-light" type="button" value="Remove"></td>
            <td>3</td>
            <td>MTK-FTC - 2018.01.02</td>
            <td>Football match</td>
            <td>Winner</td>
            <td>FTC</td>
            <td>1:5</td>
            <td>10 000 USD</td>
            <td>No</td>
            <td>Yes</td>
        </tr>
    </table>
</div>

<script src="<spring:url value="/resources/js/bootstrap.min.js"/>"></script>
</body>
</html>