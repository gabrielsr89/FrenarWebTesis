<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listado de clientes por servicio</title>
    </head>
    <body>
        <div class="container-fluid" >
            <div class="row justify-content-center" style="height: 100%;">
                <div class="col-9">
                    <div class="container-fluid bg-secondary" >
                        <div class="row" style="height: 100%;">
                            <nav class="col-md-2 d-none d-md-block bg-light sidebar pt-5">
                                <c:import url="menu.jsp" >
                                </c:import>
                            </nav>
                            <div class="col-md-10 bg-secondary"  style="overflow: hidden; margin-top: 100px; color: ivory;">
                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="margin-top: 60px!important;">
                                    <h1 class="titulares">Listado de clientes por servicio</h1>
                                </div>                                
                                <small style="color: ivory; font-size: 18px;">El listado muestra los servicios realizados a entregar, que se van a realizar y los que se están realizando.</small><br/>                    
                                <center>
                                    <div class="row mt-5 mb-5">
                                        <div class="col">
                                            <c:choose>
                                                <c:when test="${lstEspera != null && lstEspera.size() > 0}">
                                                    <h1 class="mb-4" style="border-bottom: 1px solid ivory;">
                                                        Listado de clientes por servicios a realizar en espera
                                                    </h1>
                                                    <table class="table table-striped table-dark mb-3" style="width: 60%;">
                                                        <thead>
                                                            <tr>
                                                                <th scope="col">Cliente</th>
                                                                <th scope="col">Vehículo</th>
                                                                <th scope="col">Servicio</th>
                                                                <th scope="col">Estado</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach items="${lstEspera}" var="l" >
                                                                <tr>
                                                                    <th scope="row">${l.nombreCliente}</th>
                                                                    <td style="color: ivory">${l.nombreVehiculo}</td>                                                    
                                                                    <td style="color: ivory">${l.servicio}</td>                                                    
                                                                    <td style="color: ivory">${l.estado}</td>                                                    
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>

                                                </c:when>
                                                <c:otherwise>
                                                    <h1>
                                                        -No hay servicios a realizar en espera-
                                                    </h1>
                                                </c:otherwise>
                                            </c:choose>                                            
                                        </div>                                        
                                    </div>
                                    
                                    
                                    <div class="row mt-5 mb-5">
                                        <div class="col">
                                            <c:choose>
                                                <c:when test="${lstReparacion != null && lstReparacion.size() > 0}">
                                                    <h1 class="mb-4" style="border-bottom: 1px solid ivory;">
                                                        Listado de clientes por servicios a realizar en reparación
                                                    </h1>
                                                    <table class="table table-striped table-dark mb-3" style="width: 60%;">
                                                        <thead>
                                                            <tr>
                                                                <th scope="col">Cliente</th>
                                                                <th scope="col">Vehículo</th>
                                                                <th scope="col">Servicio</th>
                                                                <th scope="col">Estado</th>
                                                                <th scope="col">Fecha inicio</th>
                                                                <th scope="col">Fecha fin estimado</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach items="${lstReparacion}" var="l">
                                                                <tr>
                                                                    <th scope="row">${l.nombreCliente}</th>
                                                                    <td style="color: ivory">${l.nombreVehiculo}</td>                                                    
                                                                    <td style="color: ivory">${l.servicio}</td>                                                    
                                                                    <td style="color: ivory">${l.estado}</td>                                                    
                                                                    <td style="color: ivory">${l.FechaInicio()}</td>                                                    
                                                                    <td style="color: ivory">${l.FechaFin()}</td>                                                    
                                                                </tr>
                                                           </c:forEach>                                                                                                               
                                                    </table>

                                                </c:when>
                                                <c:otherwise>
                                                    <h1>
                                                        -No hay servicios a realizar en reparación-
                                                    </h1>
                                                </c:otherwise>
                                            </c:choose>                                            
                                        </div>                                        
                                    </div>
                                    
                                    
                                    <div class="row mt-5 mb-5">
                                        <div class="col">
                                            <c:choose>
                                                <c:when test="${lstFinalizado != null && lstFinalizado.size() > 0}">
                                                    <h1 class="mb-4" style="border-bottom: 1px solid ivory;">
                                                        Listado de servicios realizados a entregar
                                                    </h1>
                                                    <table class="table table-striped table-dark mb-3" style="width: 60%;">
                                                        <thead>
                                                            <tr>
                                                                <th scope="col">Cliente</th>
                                                                <th scope="col">Vehículo</th>
                                                                <th scope="col">Servicio</th>
                                                                <th scope="col">Estado</th>
                                                                <th scope="col">Fecha inicio</th>
                                                                <th scope="col">Fecha fin</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach items="${lstFinalizado}" var="l">
                                                                <tr>
                                                                    <th scope="row">${l.nombreCliente}</th>
                                                                    <td style="color: ivory">${l.nombreVehiculo}</td>                                                    
                                                                    <td style="color: ivory">${l.servicio}</td>                                                    
                                                                    <td style="color: ivory">${l.estado}</td>                                                    
                                                                    <td style="color: ivory">${l.FechaInicio()}</td>                                                    
                                                                    <td style="color: ivory">${l.FechaFin()}</td>                                                    
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </c:when>
                                                <c:otherwise>
                                                    <h1>
                                                        -No hay servicios realizados a entregar-
                                                    </h1>
                                                </c:otherwise>
                                            </c:choose>                                            
                                        </div>                                        
                                    </div>
                                    
                                </center>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>