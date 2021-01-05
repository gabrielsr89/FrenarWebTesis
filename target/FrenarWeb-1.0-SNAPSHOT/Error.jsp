<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">       
        <link rel="stylesheet" href="./css/Header.css"></link>
        <link rel="stylesheet" href="./css/FrenarFont.css"></link>
        <link rel="stylesheet" href="./css/BlackieFont.css"></link>     
    </head>
    <body id="fondomain">
       
        <div class="bg-secondary position-absolute text-center" style="width: 40%; height: 100%; left: 50%; transform: translateX(-50%); align-items: flex; text-align: center; color:ivory; ">
              
            <img src="./img/Error.png">
            <h1 style="color:ivory; font-size: 35px; font-family: blackie; margin-top: 40px;">No se han procesado los cambios</h1>
            <c:choose>
                <c:when test='${msj!= null}'>                   
                </c:when>
            </c:choose>
            
             
            <input type="button" class="btn btn-dark" style="width: 40%; margin-top: 20px;" value="Volver" onclick="history.go(-1);">
        </div>
           
        <c:import url="bootstrap.jsp"></c:import>  
    </body>
</html>
