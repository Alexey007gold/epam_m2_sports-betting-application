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
    <script type="text/javascript" src="<spring:url value="/resources/js/jquery.bootstrap-growl.min.js"/>"></script>
    <title>SportsBet - Events</title>
</head>
<body>

<jsp:include page="navbar.jsp" />

<div class="container border border-primary rounded mt-2">
    <div class="row bg-primary">
        <h6 class="text-center text-light font-weight-normal p-2 pl-4"><spring:message code="web.code.bets"/></h6>
    </div>
    <table class="w-100">
        <tr>
            <th><spring:message code="web.code.event.title"/></th>
            <th><spring:message code="web.code.event.type"/></th>
            <th><spring:message code="web.code.event.start"/></th>
            <th><spring:message code="web.code.event.end"/></th>
            <th><spring:message code="web.code.bet.type"/></th>
            <th><spring:message code="web.code.bet.description"/></th>
            <th></th>
        </tr>
        <c:forEach items="${bets}" var="item" varStatus="loop">
            <tr bet_id="${item.id}">
                <td>${item.event.title}</td>
                <td>${item.event['class'].simpleName}</td>
                <td>${item.event.startDate}</td>
                <td>${item.event.endDate}</td>
                <td>${item.betType}</td>
                <td>${item.description}</td>
                <td><input class="border border-primary rounded bg-primary text-light" type="button" data-toggle="modal" data-target="#newBetModal" data-bet_id="${item.id}" value="<spring:message code="web.code.bet.make"/>"></td>


                <select id="bet_${item.id}_outcomes" class="d-none">
                    <c:forEach items="${item.outcomes}" var="outcome" varStatus="loop">
                        <option value="${outcome.id}">${outcome.value}</option>
                    </c:forEach>
                </select>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="modal fade" id="newBetModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle"><spring:message code="web.code.bet.make.title"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <h2 id="modal_event_title"><spring:message code="web.code.bet.make.title"/></h2>
                <span id="modal_event_type"><spring:message code="web.code.bet.make.title"/></span><br>
                <span id="modal_event_date"><spring:message code="web.code.bet.make.title"/></span><br>
                <span id="modal_bet_type"><spring:message code="web.code.bet.make.title"/></span><br>
                <span id="modal_bet_description"><spring:message code="web.code.bet.make.title"/></span><br>
                <select id="modal_outcomes">

                </select>
                <input type="number" min="0.01" step="0.01" required id="wage_amount"> ${currency}<br>
                <span id="modal-error" class="text-danger d-none">Some error has happened</span>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="web.code.cancel"/></button>
                <button type="button" class="btn btn-primary" onclick="submitWage()"><spring:message code="web.code.submit"/></button>
            </div>
        </div>
    </div>
</div>

<script>
    $('#newBetModal').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget); // Button that triggered the modal
        let betRow = button[0].parentNode.parentNode;
        let betId = button.data('bet_id'); // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        let modal = $(this);
        modal.find('#modal_event_title').text(betRow.children[0].textContent);
        modal.find('#modal_event_type').text(betRow.children[1].textContent);
        modal.find('#modal_event_date').text(betRow.children[2].textContent + ' - ' + betRow.children[3].textContent);
        modal.find('#modal_bet_type').text(betRow.children[4].textContent);
        modal.find('#modal_bet_description').text(betRow.children[5].textContent);

        modal.find('#modal_outcomes').html($("#bet_" + betId + "_outcomes").html());
    });

    function submitWage() {
        let modal = $('#newBetModal');
        let amountInput = modal.find("#wage_amount")[0];
        if (amountInput.checkValidity()) {
            let outcomeId = modal.find("#modal_outcomes")[0].selectedOptions[0].value;
            $.ajax({
                url: '<c:url value="/newWager"/>',
                data: {'outcome_id' : outcomeId, 'amount': amountInput.value},
                type: 'POST',
                success: function(result) {
                    if (result === true) {
                        amountInput.classList.remove('alert-danger');
                        $('#modal-error').addClass('d-none');
                        modal.modal('hide');
                        $.bootstrapGrowl('<spring:message code="web.code.wager.success"/>',{
                            type: 'success',
                            delay: 2000,
                        });
                    }
                },
                error: function () {
                    $('#modal-error').removeClass('d-none');
                }
            });
        } else {
            amountInput.classList.add('alert-danger');
            $.bootstrapGrowl('<spring:message code="web.code.error.fill"/>',{
                type: 'danger',
                delay: 2000,
            });
        }
    }
</script>

</body>
</html>
