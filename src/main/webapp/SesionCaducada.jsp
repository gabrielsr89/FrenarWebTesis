
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sesión caducada</title>
    </head>
    <body id="fondomain">
        
        <div class="container text-center" style="width: 100%; color:ivory">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="margin-top: 60px!important;">
                    <h1 class="titulares">Sesión caducada</h1><small>Debe logear para acceder a este sitio</small>
                </div>
            
            <div class="row">
                <div class="col" style="width: 300px; height: 400px;">
                    <img src="img/Error.png" alt=""/>
                </div>                
            </div>
            <div class="row">
                <div class="col">
                    <input type="button" class="btn btn-dark" value="Ir a página principal" onclick="location.href='./headerServlet'">
                </div>                
            </div>
            
            
        </div>
        
        <c:import url="bootstrap.jsp"></c:import>
    </body>
</html>
