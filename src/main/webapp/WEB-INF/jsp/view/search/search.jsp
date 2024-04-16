<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:mainLayout title="Поиск">
    <script src="<c:url value="/js/search.js"/>"></script>
    <br>
    <br>
    <br>
    <br>
    <div class="input-group">
        <input type="search" id="title" name="title" class="form-control rounded" placeholder="Поиск статей"
               aria-label="Search"
               aria-describedby="search-addon" required/>
        <button type="submit" id="search" class="btn btn-outline-primary">Найти</button>
    </div>
    <br>
    <div id="post-card" class="post-card">
    </div>
</t:mainLayout>
