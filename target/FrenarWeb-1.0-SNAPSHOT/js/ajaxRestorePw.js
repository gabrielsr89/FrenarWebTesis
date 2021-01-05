$(document).ready(function () {
    $('#enviarRecu').click(function (event) {
        
        var email = $('#mail').val();
        $body = $("body");

        $(document).on({
            ajaxStart: function () {
                $body.addClass("loading");
            },
            ajaxStop: function () {
                $body.removeClass("loading");
            }
        });
        //envio a servlet recupass
        $.ajax({
            type: "POST",
            url: "RestorePwServlet",
            data: {correoAlServlet: email}, // Desde acà se manda el mail al servlet a verificar
            success: function (data, textStatus, jqXHR) {
                $('#result').html(data)
            },
            error: function (jqXHR) {
                console.log("error")
            }
        });
    });
});

