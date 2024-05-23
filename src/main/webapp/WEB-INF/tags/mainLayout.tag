<%@ tag description="Default Layout Tag" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ attribute name="title" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/style/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/style/main.css"/>">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light fixed-top" style="background-color: #ffec8b">
    <div class="container-fluid">
        <a class="navbar-brand">BeerOK</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown"
                aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="/main/about">О
                        сайте</a>
                </li>
                <sec:authorize access="isAnonymous()">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/sign-in">Войти</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/account/profile">Профиль</a>
                    </li>
                </sec:authorize>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/beer/list">Виды</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/post/list">Статьи</a>
                </li>
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/post/create">Написать статью</a>
                    </li>
                </sec:authorize>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/search">Поиск</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <jsp:doBody/>
</div>
</body>
</html>