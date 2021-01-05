<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administración de ubicaciones</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row justify-content-center" style="height: 100%;">
                <div class="col-9">
                    <div class="container-fluid bg-secondary" >
                        <div class="row" style="height: 100%;">
                            <nav class="col-md-2 d-none d-md-block bg-light sidebar pt-5">
                                <c:import url="menu.jsp" >
                                </c:import>
                            </nav>
                            <div class="col-md-10 bg-secondary"  style="overflow: hidden; margin-top: 100px; color: ivory;">

                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="margin-top: 60px!important">
                                    <h1 class="titulares">Reportes generales</h1>
                                </div>
                                <center>
                                    <div class="mt5 mb-5" id="divConsulta1">
                                        <h2 class="mt-5" style="border-bottom: 1px solid ivory; width: 60%;">
                                            Listado de clientes mostrados desde los más atendidos a los menos atendidos mensualmente 
                                        </h2>
                                        <c:choose>
                                            <c:when test = "${lst1a.size() == 0}">
                                                <div  class="mt-5">
                                                    <h5>No existen registros para esta consulta</h5>   
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <table class="mt-5 table-striped table-sm">
                                                    <thead style="color: ivory;">
                                                        <tr>
                                                            <th>Año</th>
                                                            <th>Mes</th>
                                                            <th>Cliente</th>
                                                            <th>Cantidad de veces que fue atendido</th>                                                       
                                                        </tr>
                                                    </thead>
                                                    <tbody class="listadoResto">
                                                        <c:forEach items="${lst1a}" var="r1" >
                                                            <tr style="text-align: center">
                                                                <td>${r1.año}</td>
                                                                <td>${r1.mes()}</td>
                                                                <td>${r1.nombreCliente}</td>
                                                                <td>${r1.cantidad}</td>
                                                            </tr>
                                                        </c:forEach>

                                                    </tbody>
                                                </table>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    
                                    <div class="mt5 mb-5" id="divConsulta2">
                                        <h2 class="mt-5" style="border-bottom: 1px solid ivory; width: 60%;">
                                            Listado de las 5 marcas de repuestos más vendidas en lo que va del año.
                                        </h2>
                                        <c:choose>
                                            <c:when test = "${lst2a.size() == 0}">
                                                <div  class="mt-5">
                                                    <h5>No existen registros para esta consulta</h5>   
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <table class="mt-5 mb-3 table-striped table-sm">
                                                    <thead style="color: ivory;">
                                                        <tr>
                                                            <th>ID</th>
                                                            <th>Marca</th>                                                       
                                                            <th>Cantidad</th>                                                       
                                                        </tr>
                                                    </thead>
                                                    <tbody class="listadoResto">
                                                        <c:forEach items="${lst2a}" var="r2" >
                                                            <tr style="text-align: center">
                                                                <td>${r2.id_marca}</td>
                                                                <td>${r2.marca}</td>
                                                                <td>${r2.cantidad}</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </c:otherwise>
                                        </c:choose>
                                        <a class="nav-link" href="#" style="color: ivory; font-size: 25">Ir arriba</a>
                                    </div>
                                    
                                    <div class="mt5 mb-5" id="divConsulta3">
                                        <h2 class="mt-5" style="border-bottom: 1px solid ivory; width: 60%;">
                                            Rubro de repuesto más vendido por mes en lo que va del año.
                                        </h2>
                                        <c:choose>
                                            <c:when test = "${lst3a.size() == 0}">
                                                <div  class="mt-5">
                                                    <h5>No existen registros para esta consulta</h5>   
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <table class="mt-5 mb-3 table-striped table-sm">
                                                    <thead style="color: ivory;">
                                                        <tr>
                                                            <th>Mes</th>
                                                            <th>Rubro</th>                                                       
                                                            <th>Cantidad</th>                                                       
                                                        </tr>
                                                    </thead>
                                                    <tbody class="listadoResto">
                                                        <c:forEach items="${lst2b}" var="r3" >
                                                            <tr style="text-align: center">
                                                                <td>${r3.mes()}</td>
                                                                <td>${r3.rubro}</td>
                                                                <td>${r3.cantidad}</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </c:otherwise>
                                        </c:choose>
                                                <a class="nav-link" href="#" style="color: ivory; font-size: 25">Ir arriba</a>
                                    </div>
                                    
                                    <div class="mt5 mb-5" id="divConsulta4">
                                        <h2 class="mt-5" style="border-bottom: 1px solid ivory; width: 60%;">
                                            Listado de los 5 repuestos más vendidos el mes pasado (${lst2c.get(0).mes()})
                                        </h2>
                                        <c:choose>
                                            <c:when test = "${lst2c.size() == 0}">
                                                <div  class="mt-5">
                                                    <h5>No existen registros para esta consulta</h5>   
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <table class="mt-5 mb-3 table-striped table-sm">
                                                    <thead style="color: ivory;">
                                                        <tr>
                                                            <th>Repuesto</th>
                                                            <th>Cantidad</th>                                                       
                                                        </tr>
                                                    </thead>
                                                    <tbody class="listadoResto">
                                                        <c:forEach items="${lst2c}" var="r2" >
                                                            <tr style="text-align: center">
                                                                <td>${r2.repuesto}</td>
                                                                <td>${r2.cantidad}</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </c:otherwise>
                                        </c:choose>
                                                <a class="nav-link" href="#" style="color: ivory; font-size: 25">Ir arriba</a>
                                    </div>
                                        
                                    <div class="mt5 mb-5" id="divConsulta5">
                                        <h2 class="mt-5" style="border-bottom: 1px solid ivory; width: 60%;">
                                            Listado de marcas y modelos de los autos atendidos ordenados de los más atendidos a los menos atendidos en lo que va del año.
                                        </h2>
                                        <c:choose>
                                            <c:when test = "${lst3a.size() == 0}">
                                                <div  class="mt-5">
                                                    <h5>No existen registros para esta consulta</h5>   
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <table class="mt-5 mb-3 table-striped table-sm">
                                                    <thead style="color: ivory;">
                                                        <tr>
                                                            <th>Vehículo</th>
                                                            <th>Cantidad</th>                                                       
                                                        </tr>
                                                    </thead>
                                                    <tbody class="listadoResto">
                                                        <c:forEach items="${lst3a}" var="r3" >
                                                            <tr style="text-align: center">
                                                                <td>${r3.nombreVehiculo}</td>
                                                                <td>${r3.cantidad}</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </c:otherwise>
                                        </c:choose>
                                                <a class="nav-link" href="#" style="color: ivory; font-size: 25">Ir arriba</a>
                                    </div>
                                        
                                    <div class="mt5 mb-5" id="divConsulta6">
                                        <h2 class="mt-5" style="border-bottom: 1px solid ivory; width: 60%;">
                                            Reporte mensual de los 5 servicios más recurrentes por mes en lo que va del año.
                                        </h2>
                                        <c:choose>
                                            <c:when test = "${lst3b.size() == 0}">
                                                <div  class="mt-5">
                                                    <h5>No existen registros para esta consulta</h5>   
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <table class="mt-5 mb-3 table-striped table-sm">
                                                    <thead style="color: ivory;">
                                                        <tr>
                                                            <th>Mes</th>
                                                            <th>Servicio</th>                                                       
                                                            <th>Descripción</th>                                                       
                                                            <th>Cantidad</th>                                                       
                                                        </tr>
                                                    </thead>
                                                    <tbody class="listadoResto">
                                                        <c:forEach items="${lst3b}" var="r3" >
                                                            <tr style="text-align: center">
                                                                <td>${r3.mes()}</td>
                                                                <td>${r3.servicio}</td>
                                                                <td>${r3.descripcion}</td>
                                                                <td>${r3.cantidad}</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </c:otherwise>
                                        </c:choose>
                                                <a class="nav-link" href="#" style="color: ivory; font-size: 25">Ir arriba</a>
                                    </div>  
                                        
                                    <div class="mt5 mb-5" id="divConsulta7">
                                        <h2 class="mt-5" style="border-bottom: 1px solid ivory; width: 60%;">
                                            Reportes de las categorías de servicio más frecuentes por año.
                                        </h2>
                                        <c:choose>
                                            <c:when test = "${lst3c.size() == 0}">
                                                <div  class="mt-5">
                                                    <h5>No existen registros para esta consulta</h5>   
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <table class="mt-5 mb-3 table-striped table-sm">
                                                    <thead style="color: ivory;">
                                                        <tr>
                                                            <th>Año</th>
                                                            <th>Categoría</th>                                                       
                                                            <th>Cantidad</th>                                                       
                                                        </tr>
                                                    </thead>
                                                    <tbody class="listadoResto">
                                                        <c:forEach items="${lst3c}" var="r3" >
                                                            <tr style="text-align: center">
                                                                <td>${r3.año}</td>
                                                                <td>${r3.categoria}</td>
                                                                <td>${r3.cantidad}</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </c:otherwise>
                                        </c:choose>
                                                <a class="nav-link" href="#" style="color: ivory; font-size: 25">Ir arriba</a>
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
