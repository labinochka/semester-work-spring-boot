<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<t:mainLayout title="${post.title()}">
    <script src="<c:url value="/js/comments.js"/>"></script>
    <br>
    <div class="text-center">
        <br>
        <br>
        <p class="h1">${post.title()}</p>
        <br>
        <p class="h4"><a
                href="${pageContext.request.contextPath}/account/someone?username=${post.author().username()}">
                ${post.author().username()}</a>
        </p>
        <br>
        <p class="h6">Дата
            публикации: ${post.dateOfPublication().getDate()}.${post.dateOfPublication().getMonth() + 1}.${post.dateOfPublication().getYear() + 1900}</p>
        <img src="${post.image()}" class="rounded img-thumbnail"/>
        <br>
        <br>
        <p align="left" style="white-space: pre-wrap;">
            <font size="5">${post.content()}</font>
        </p>
        <br>

        <sec:authorize access="isAuthenticated()">
            <textarea type="text" id="content" name="content" class="form-control" minlength="1" rows="5"
                      cols="10" required></textarea>
            <br>
            <button id="submit" type="submit" value="create" class="btn btn-secondary mb-4">Оставить комментарий
            </button>
        </sec:authorize>
    </div>

    <div id="comment-card" class="comment-card">
        <c:forEach items="${comment}" var="comment">
            <div id="${comment.uuid()}">
                <h5 class="comment-author">
                    <a
                            href="${pageContext.request.contextPath}/account/someone?username=${comment.author().username()}">
                            ${comment.author().username()}
                    </a>
                </h5>
                <h6 class="comment-content">${comment.dateOfPublication().toLocalDateTime().toLocalDate()}</h6>
                <h4 class="comment-content">${comment.content()}</h4>
                <c:if test="${comment.author().username() == account.username() ||
                account != null && account.role().name() == 'ROLE_ADMIN'}">
                    <button value="${comment.uuid()}" class="btn btn-outline-secondary btn-sm btn-block delete">
                        Удалить
                    </button>
                </c:if>
                <br>
                <br>
            </div>
        </c:forEach>
    </div>
</t:mainLayout>

