<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!doctype html>
<fmt:setLocale value="${cookie['lang'].value}" />
<fmt:requestEncoding value="utf-8" />
<fmt:setBundle basename="messages/messages" />
<html lang="${cookie['lang'].value}">
<head>
    <!-- Required meta tags -->
    <meta http-equiv='Content-Type' charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<spring:url value="/resources/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<spring:url value="/resources/css/style.css"/>">
    <title>SportsBet - Home</title>
</head>
<body>

<div class="container border border-primary rounded mt-2">
    <div class="row bg-primary">
        <h6 class="text-center text-light font-weight-normal p-2"><spring:message code="web.code.details"/></h6>
    </div>
    <form id="player" class="mt-3" action="<c:url value="/updateAccount"/>" method="post">
        <div class="input-group">
            <div class="input-group-prepend">
                <span class="input-group-text"><spring:message code="web.code.name"/></span>
            </div>
            <input class="form-control" type="text" minlength="3" required="required" name="name" value="${user.name}">
        </div>
        <div class="input-group mt-2">
            <div class="input-group-prepend">
                <span class="input-group-text"><spring:message code="web.code.birth"/></span>
            </div>
            <input class="form-control" type="date" required="required" name="birthDate" value="${user.birthDate.toString()}">
        </div>
        <div class="input-group mt-2">
            <div class="input-group-prepend">
                <span class="input-group-text"><spring:message code="web.code.account"/></span>
            </div>
            <input class="form-control" type="text" minlength="3" required="required" name="accountNumber" value="${user.accountNumber}">
        </div>
        <div class="input-group mt-2">
            <div class="input-group-prepend">
                <span class="input-group-text"><spring:message code="web.code.currency"/></span>
            </div>
            <select class="form-control" name="currency">
                <c:forEach items="${currencyOptions}" varStatus="loop">
                    <option value="${currencyOptions[loop.index]}" <c:out value="${currencyOptions[loop.index] eq user.currency ? 'selected' : ''}" />>${currencyOptions[loop.index]}</option>
                </c:forEach>
            </select>
        </div>
        <div class="input-group mt-2">
            <div class="input-group-prepend">
                <span class="input-group-text"><spring:message code="web.code.balance"/></span>
            </div>
            <input class="form-control" type="number" step="0.01" min="0" required="required" name="balance" value="${user.balance}">
        </div>
        <input class="form-control d-none" type="number" name="id" value="${user.id}">
        <input class="bg-primary border border-primary rounded mt-2 mb-2 text-light" type="submit" value="<spring:message code="web.code.save"/>">
    </form>
</div>

<div class="container border border-primary rounded mt-2">
    <div class="row bg-primary">
        <h6 class="text-center text-light font-weight-normal p-2"><spring:message code="web.code.wagers"/></h6>
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
        <c:forEach items="${wagers}" var="item" varStatus="loop">
            <tr wager_id="${item.id}">
                <td><input class="border border-primary rounded bg-primary text-light <c:out escapeXml="false" value="${item.processed ? '' : 'd-none'}"/>" type="button" value="<spring:message code="web.code.remove"/>" onclick="removeWager(this.parentNode.parentNode)"></td>
                <td>${loop.index + 1}</td>
                <td>${item.event.title}</td>
                <td>${item.event['class'].simpleName}</td>
                <td>${item.outcomeOdd.outcome.bet.betType}</td>
                <td>${item.outcomeOdd.outcome.value}</td>
                <td class="odd" val="${item.outcomeOdd.value}"></td>
                <td>${item.amount} ${item.currency}</td>
                <c:if test="${item.processed}">
                    <c:if test="${item.winner}">
                        <td><spring:message code="web.code.yes"/></td>
                    </c:if>
                    <c:if test="${!item.winner}">
                        <td><spring:message code="web.code.no"/></td>
                    </c:if>
                    <td><spring:message code="web.code.yes"/></td>
                </c:if>
                <c:if test="${!item.processed}">
                    <td>-</td>
                    <td><spring:message code="web.code.no"/></td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</div>

<script src="<spring:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="<spring:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script>
    function removeWager(row) {
        $.ajax({
            url: '/removeWager?wager_id=' + row.getAttribute("wager_id") + '&player_id=' + document.getElementById("player").querySelectorAll("input[name=id]")[0].value,
            type: 'DELETE',
            success: function(result) {
                if (result === true) {
                    row.remove();
                }
            }
        });
    }
    function gcd(a, b) {
        if (a === b) {
            return a;
        }
        if (a > b) {
            return gcd(a - b, b);
        }
        return gcd(a, b - a);
    }

    function doubleToProportion(d) {
        if(d === 1) return '1/1';
        if (d > 1) {
            let strVal = d + '';
            let pointIdx = strVal.indexOf('.');
            if (pointIdx !== -1) {
                let b = Math.pow(10, strVal.length - pointIdx - 1);
                let a = d * b;
                let gcdN = gcd(a, b);
                a /= gcdN;
                b /= gcdN;
                return a + '/' + b;
            }
            return d + '/' + 1;
        } else {
            return '1/' + +(1 / d).toFixed(2);
        }
    }

    $(document).ready(function() {
        let oddElems = document.querySelectorAll(".odd");
        for (let i = 0; i < oddElems.length; i++) {
            oddElems[i].innerText = doubleToProportion(oddElems[i].getAttribute("val"));
        }
    })
</script>
</body>
</html>