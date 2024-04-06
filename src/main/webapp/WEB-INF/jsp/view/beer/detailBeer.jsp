<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:mainLayout title="${beer.type()}">
    <br>
    <div class="text-center">
        <br>
        <br>
        <p class="h1">${beer.type()}</p>
        <br>
        <img src="${beer.image()}" class="rounded img-thumbnail">
        <br>
        <br>
        <p align="left" style="white-space: pre-wrap;">
            <font size="5">${beer.content()}</font>
        </p>
        <br>
    </div>
</t:mainLayout>
