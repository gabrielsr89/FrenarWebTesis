$(document).ready(function () {
    $('#email').blur(function (event) {        
        
        var email = $('#email').val();
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
            url: "./VerificarMailAltaPersonaServlet",
            data: {VerificarCorreo: email}, // Desde acà se manda el mail al servlet a verificar
            success: function (data, textStatus, jqXHR) {
                $('#result').html(data)
            },
            error: function (jqXHR) {
                console.log("error");
            }
        });
    });
});