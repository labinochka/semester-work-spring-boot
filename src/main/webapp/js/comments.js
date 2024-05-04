$(document).ready(function () {
    $("#submit").click(function () {
        var content = $("#content").val();

        var url = window.location.href;
        var urlParams = new URLSearchParams(url.split('?')[1]);
        var postId = urlParams.get('id');


        $.ajax({
            type: "POST",
            url: "/comment/create?postId=" + postId,
            data: {content: content, postId: postId},
            success: function (response) {
                $("#comment-card").prepend("<h5><a href='/account/someone?username=" +
                    response.author.username + "'>" + response.author.username + "</a></h5><h6 class='comment-date'>" +
                    response.dateOfPublication.substring(0, 10) + "</h6><h4 class='comment-content'>" +
                    response.content + "</h4><br><br>");
                $("#content").val('');
            },
            error: function (xhr, status, error) {
                console.log("Error: " + error);
            }
        });
    });
});
