


$(document).ready(function () {

    $('#verificarMail').click(function (event) {

        var email = $('#mail').val();
        var pass = $('#password').val();
        
        $body = $("body");
        
        $(document).on({
            ajaxStart: function () {
                $body.addClass("loading");
            },
            ajaxStop: function () {
                $body.removeClass("loading");
            }
        });

        $.ajax({
            
            type: "POST",
            url: "LoginServlet",

            data: {email: email, pass: pass},

            success: function (data, textStatus, jqXHR) { //traer de data
                if (!data.includes("html")) {
                    $('#result').html(data);                   
                }
                else{        
                    location.href="./headerServlet";
                }
            },
            error: function (jqXHR) {
                console.log("error")
            }
        });


        return false;
    });
});
