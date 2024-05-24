<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:mainLayout title="Статьи">
    <br>
    <br>
    <div class="post-info">
        <br>
        <p class="h1">Статьи</p>
        <br>
        <c:forEach items="${post}" var="post">
            <div class="post-card">
                <h4 class="post-name">
                    <a href="${pageContext.request.contextPath}/post/detail?id=${post.uuid()}">
                            ${post.title()}</a>
                </h4>
                <c:if test="${account != null && account.role().name() == 'ROLE_ADMIN'}">
                    <a href="${pageContext.request.contextPath}/post/deleteAdmin?id=${post.uuid()}">
                        <button class="btn btn-outline-secondary btn-sm btn-block">Удалить
                        </button>
                    </a>
                    <br>
                </c:if>
                <br>
            </div>
        </c:forEach>
    </div>
</t:mainLayout>