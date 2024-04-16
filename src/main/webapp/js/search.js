$(document).ready(function () {
    $("#search").click(function () {
        var searchQuery = $("#title").val();

        $.ajax({
            type: "GET",
            url: "/searching/" + searchQuery,
            success: function (response) {

                $("#post-card").empty();

                if (response.length > 0) {
                    response.forEach(function (article) {
                        $("#post-card").append("<h4><a href='/post/detail/" + article.uuid + "'>" + article.title +
                            "</a></h4><br>");
                    });
                } else {
                    $("#post-card").append("Нет результатов по вашему запросу.");
                }
            },
            error: function (xhr, status, error) {
                console.log("Error: " + error);
            }
        });
    });
});
