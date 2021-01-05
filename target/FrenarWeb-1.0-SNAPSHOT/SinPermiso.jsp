<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body id="fondomain">
        <div class="container bg-secondary" style="height: 100%; display: flex; justify-content: center; align-items: center; ">
            <div style="width: 400px; height: 400px;text-align: center; margin-bottom: 30%; color:ivory;">
                <img src="./img/Error.png" style="width: 350px; height: 350px; ">
                <h3 style="font-family: blackie; width: 100%;">
                    No cuenta con los permisos para acceder a este sitio 
                </h3>
                <small>
                    Tenga en cuenta que si permanece inactivo por 30 minutos o más el sistema cerrará su sesión por motivos de seguridad.
                </small>
                <div style=" margin-top: 20px;">
                    <a href="./headerServlet"style="color:ivory; font-size: 25px;">Volver a la página principal</a>
                </div>
            </div>
        </div>

        <c:import url="bootstrap.jsp"></c:import>
    </body>
</html>
