<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="container border border-primary rounded mt-2 p-0">
    <nav class="navbar navbar-expand-lg navbar-light bg-primary">
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item"><a class="nav-link text-light" href="<c:url value="/home"/>"><spring:message code="web.code.menu.home"/></a></li>
                <li class="nav-item"><a class="nav-link text-light" href="<c:url value="/events"/>"><spring:message code="web.code.menu.events"/></a></li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-light" data-toggle="dropdown" href="#">
                        <spring:message code="web.code.menu.language"/>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="?lang=en"><spring:message code="web.code.english"/></a>
                        <a class="dropdown-item" href="?lang=ru"><spring:message code="web.code.russian"/></a>
                    </div>
                </li>
                <li class="nav-item"><a class="nav-link text-light" href="/perform_logout"><spring:message code="web.code.menu.logout"/></a></li>
            </ul>
        </div>
    </nav>
</div>