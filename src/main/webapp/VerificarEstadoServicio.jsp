
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Servicios en curso</title>
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
                            <div class="col-md-10 bg-secondary"  style="overflow: hidden; margin-top: 100px; color:black;">


                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="padding-top: 100px!important">
                                    <h1 class="titulares">Servicios en curso</h1>
                                </div>
                                <div class="text-center mb-5" style="color: ivory; font-size: 18px;">
                                    <span>
                                        Si se encuentra en <span style="color: green; background-color: ivory;">espera</span> significa que el presupuesto ha sido confirmado y que el vehículo está en espera a ser reparado.
                                    </span><br/>
                                    <span>
                                        Si se encuentra en <span style="color: #007bff; background-color: ivory;">reparación</span> significa que el vehículo está en manos del equipo mecánico del taller pronto a ser reparado.
                                    </span><br/>
                                    <span>
                                        Si se encuentra <span style="color: orange; background-color: ivory;">finalizado</span> significa que el vehículo ya ha sido reparado por nuestros mecánicos en espera a ser retirado.
                                    </span>
                                </div>

                                <c:choose>
                                    <c:when test="${!cliente}">    
                                        <div class="row text-center">
                                            <div class="col">
                                                <div class="row">
                                                    <div class="col">
                                                        <label class="listadoHeader" for="cboCliente">Seleccione el cliente y realize la busqueda</label>
                                                        <select class="form-control" id="cboCliente" name="idCliente"  style="width: 60%; margin-left: 50%; transform: translateX(-50%);">                        
                                                            <option value = "0">Despliegue para seleccionar</option>
                                                            <c:forEach items="${lstCliente}" var="ct">  
                                                                <option value="${ct.id}" ${ct.p.nombre eq ct.id?'selected':''}>${ct.p.toString()}
                                                                </option></c:forEach>
                                                            </select>                                                           
                                                        </div>                                                    
                                                    </div>
                                                    <div class="row mt-3 mb-5">
                                                        <div class="col">
                                                            <button class="btn btn-dark" onclick="Buscar()">Buscar</button>
                                                        </div>
                                                    </div>
                                                    <script type="text/javascript">
                                                        var cboCliente = document.getElementById('cboCliente');
                                                        function Buscar() {
                                                            if (cboCliente.value == 0) {
                                                                alert('Debe seleccionar al menos un cliente');
                                                                cboCliente.focus();
                                                            } else {
                                                                location.href = 'VerificarEstadoServicioServlet?id_c=' + cboCliente.value;
                                                            }
                                                        }
                                                    </script>
                                                </div>
                                            </div>                                        
                                    </c:when>
                                </c:choose>

                                <div class="row">
                                    <div class="col">
                                        <ul class="list-group mb-5">
                                            <c:choose>
                                                <c:when test='${lstEspera.size() == 0 && lstReparacion.size() == 0 && lstFinalizado.size() == 0}'>
                                                    <div class="text-center">
                                                        <h4 style="color: ivory;">
                                                            No hay servicios en curso
                                                        </h4>
                                                        
                                                    </div>
                                                </c:when>   
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${lstEspera.size() > 0 }">

                                                            <c:forEach items="${lstEspera}" var="f">
                                                                <li class="list-group-item">
                                                                    <div class="row">
                                                                        <div class="col" style="display: inline-grid">
                                                                            <strong>Cliente: ${f.ObtenerNombreCliente()}</strong>
                                                                            <small>Número de factura: ${f.id_factura}</small>
                                                                            <strong>Vehículo: <label  class="text-muted">${f.ObtenerStrngDeAuto()}</label></strong>

                                                                        </div>
                                                                        <div class="col text-center" style="display: flex; align-items: center;">
                                                                            <a href="./DetalleEntregaServlet?id_factura=${f.id_factura}" style="margin-left: 50%; transform: translateX(-50%)">Ver detalles</a>
                                                                        </div>
                                                                        <div class="col" style="display: flex; align-items: center;">
                                                                            <ul class="list-group text-center w-100"><li class="list-group-item" style="border: none !important"><strong style="color: green"> En espera</strong></li></ul>
                                                                        </div>
                                                                    </div>                            
                                                                </li>

                                                            </c:forEach>
                                                        </c:when>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${lstReparacion.size()> 0}">
                                                            <c:forEach items="${lstReparacion}" var="f">
                                                                <li class="list-group-item">
                                                                    <div class="row">
                                                                        <div class="col" style="display: inline-grid">
                                                                            <strong>Cliente: ${f.ObtenerNombreCliente()}</strong>
                                                                            <small>Número de factura: ${f.id_factura}</small>
                                                                            <strong>Vehículo: <label  class="text-muted">${f.ObtenerStrngDeAuto()}</label></strong>

                                                                        </div>
                                                                        <div class="col text-center" style="display: flex; align-items: center;">
                                                                            <a href="./DetalleEntregaServlet?id_factura=${f.id_factura}" style="margin-left: 50%; transform: translateX(-50%)">Ver detalles</a>
                                                                        </div>
                                                                        <div class="col" style="display: flex; align-items: center;">
                                                                            <ul class="list-group text-center" style="border: none !important;">
                                                                                <li class="list-group-item" style="border: none !important;"><strong style="color: #007bff"> En reparación</strong><br/></li> 
                                                                                <li class="list-group-item"><strong style="color: #007bff"> Inicio: ${f.FechaInicio()}</strong></li>

                                                                            </ul>
                                                                        </div>
                                                                    </div>                            
                                                                </li>
                                                            </c:forEach>
                                                        </c:when>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${lstFinalizado.size() > 0}">
                                                            <c:forEach items="${lstFinalizado}" var="f">
                                                                <li class="list-group-item">
                                                                    <div class="row">
                                                                        <div class="col" style="display: inline-grid">
                                                                            <strong>Cliente: ${f.ObtenerNombreCliente()}</strong>
                                                                            <small>Número de factura: ${f.id_factura}</small>
                                                                            <strong>Vehículo: <label  class="text-muted">${f.ObtenerStrngDeAuto()}</label></strong>

                                                                        </div>
                                                                        <div class="col text-center" style="display: flex; align-items: center;">
                                                                            <a href="./DetalleEntregaServlet?id_factura=${f.id_factura}" style="margin-left: 50%; transform: translateX(-50%)">Ver detalles</a>
                                                                        </div>
                                                                        <div class="col" style="display: flex; align-items: center;">
                                                                            <ul class="list-group text-center">
                                                                                <li class="list-group-item" style="border: none !important;"><strong style="color: darkorange">Finalizado</strong><br/></li>
                                                                                <li class="list-group-item"><strong style="color: darkorange"> Inicio: ${f.FechaInicio()}</strong><br/></li>
                                                                                <li class="list-group-item"><strong style="color: darkorange"> Fin: ${f.FechaFin()}</strong></li>
                                                                            </ul>
                                                                        </div>
                                                                    </div>                            
                                                                </li>
                                                            </c:forEach>   
                                                        </c:when>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose> 
                                        </ul>                                        
                                    </div>                                
                                </div>                                
                            </div>                                
                        </div>
                    </div>
                </div>
            </div>
        </div>    
    </body>
</html>