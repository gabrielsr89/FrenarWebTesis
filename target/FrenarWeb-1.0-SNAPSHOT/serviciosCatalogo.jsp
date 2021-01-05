
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nuestros servicios</title>
        <style type="text/css"> html,body{ margin:0px; height:100%; } </style>
    </head>
    <body id="fondomain">
        <c:import url="header.jsp"></c:import>
            <div class="container bg-secondary" style=" overflow: auto; height: 100vh; text-align: center;">

                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="padding-top: 130px!important">
                    <h1 class="titulares">${categoria}</h1>
            </div>
            <div class="row">
                <div class="col" style="margin-top: 60px; margin-bottom: 30px; color: ivory; text-align: center; display: inline-grid; margin-left: 50%; transform: translateX(-50%)">
                    <span>Listado aproximado de costos de mano de obra y tiempo que demandan los distintos servicios ofrecidos.</span>
                    <span>Para una información más detallada debe acercarse al taller para presupuestar según el caso.</span>
                </div>
            </div>
            

            <div class="row">
                <div class="col">

                    <c:choose>
                        <c:when test = "${lstServicio.size() == 0}">

                            <h2 style="color: ivory; text-align: center;">No tenemos descripción para este servicio en particular</h2>     

                        </c:when>
                        <c:otherwise>
                            <table class="table-striped table-sm mt-5 bg-dark" style="position: static !important; margin-left: 50%; transform: translateX(-50%); width: 90%; border-radius: 30px;">
                                <thead class="listadoHeader">
                                    <tr>
                                        <th>Servicio</th>
                                        <th>Descripción</th>
                                        <th>Demora aproximada en minutos</th>
                                        <th>Precio aproximado de mano de obra</th>                                                                   
                                    </tr>
                                </thead>
                                <tbody class="listadoResto">
                                    <c:forEach items="${lstServicio}" var="lst" >
                                        <tr style="text-align: center">
                                            <td>${lst.servicio}</td>
                                            <td>${lst.descripcion}</td>
                                            <td>${lst.tiempo}</td>
                                            <td>$${lst.precio}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <button type="button" class="btn btn-dark mt-5" id="cancelarButton" onClick="history.go(-1);" style="width: 40%; margin-bottom: 30px;">Volver a la página principal</button>
                </div>
            </div>
            <span class="text-white" style="position:static; padding-bottom:30px; left: 50%; transform: translateX(-50%);">© 2020</span>
        </div>
        <c:import url="bootstrap.jsp"></c:import>
    </body>
</html>
