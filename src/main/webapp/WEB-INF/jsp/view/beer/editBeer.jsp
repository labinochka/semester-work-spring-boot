<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:mainLayout title="Редактировать тип">
    <br>
    <br>
    <br>
    <br>
    <div class="col-md-12 d-flex justify-content-center">
        <p style="color:red">${error}</p>
    </div>
    <div class="tab-content">
        <div class="tab-pane fade show active" id="editPost" role="tabpanel" aria-labelledby="tab-login">
            <form id="formCreatePost" action="${pageContext.request.contextPath}/beer/edit?id=${beer.uuid()}"
                  enctype="multipart/form-data"
                  method="post" modelAttribute="beer">

                <div class="col-md-12 d-flex justify-content-center">
                    <div class="form-outline m-lg-4">
                        <textarea type="text" id="sort" name="sort" class="form-control" minlength="2" cols="50"
                                  required>${beer.sort()}</textarea>
                        <label class="form-label" for="sort">Сорт</label>
                    </div>
                </div>

                <div class="col-md-12 d-flex justify-content-center">
                    <div class="form-outline m-lg-4">
                        <textarea type="text" id="type" name="type" class="form-control" minlength="6" cols="100"
                                  required>${beer.type()}</textarea>
                        <label class="form-label" for="type">Название</label>
                    </div>
                </div>

                <div class="col-md-12 d-flex justify-content-center">
                    <div class="form-outline m-lg-2">

                        <textarea type="text" id="content" name="content" class="form-control" minlength="2" rows="10"
                                  cols="100" required>${beer.content()}</textarea>
                        <label class="form-label" for="content">Текст</label>
                    </div>
                </div>

                <div class="d-flex justify-content-center">
                    <input id="image" name="image" type="file" accept=".jpg, .png, .jpeg"
                           class="btn btn-secondary mb-4">Изменить изображение</input>
                </div>
                <div class="d-flex justify-content-center">
                    <button id="submit" type="submit" class="btn btn-primary mb-4">Сохранить изменения</button>
                </div>
            </form>
            <br>
            <br>
        </div>
    </div>
</t:mainLayout>