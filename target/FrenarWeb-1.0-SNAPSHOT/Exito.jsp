
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">       
        <link rel="stylesheet" href="./css/Header.css"></link>
        <link rel="stylesheet" href="./css/FrenarFont.css"></link>
        <link rel="stylesheet" href="./css/BlackieFont.css"></link>  
        <style>
            #Contenedor{
                width: 40%;
                margin-top: 150px;
                height: 60%;
                left: 50%;
                transform: translateX(-50%);
                align-items: flex;
                text-align: center;
                border: ivory 4px solid;
                border-radius: 20px;
            }
        </style>
    </head>
    <body id="fondomain">

        <div class="bg-secondary position-absolute " id="Contenedor">

            <img src="./img/CambioCorrecto.png" style="margin-top: 40px;">
            <h1 style="color:ivory; font-size: 20px; font-family: blackie; margin-top: 40px; margin-left: 40px; margin-right: 40px;">Proceso realizado con exito</h1>

            <input type="button" class="btn btn-dark" style="width: 40%; margin-top: 30px; " value="Volver" onclick="location.href = './${url}'">
        </div>



        <c:import url="bootstrap.jsp"></c:import>  
    </body>
</html>
