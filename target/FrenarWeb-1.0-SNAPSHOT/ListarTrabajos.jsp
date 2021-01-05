<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tomar trabajo pendiente</title>
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
                                    <h1 class="titulares">Tomar trabajo pendiente</h1>
                                </div>

                                <div>
                                    <form method="POST" action="./ListarTrabajos" name="frmListarTrabajos">
                                        <ul class="list-group">
                                            <c:choose>
                                                <c:when test='${lista.size()>0}'>
                                                    <c:forEach  items="${lista}" var="l">                                   
                                                        <li class="list-group-item">
                                                            <div class="row">
                                                                <div class="col" style="display: inline-grid">

                                                                    <small class="text-muted">Vehículo: </small><strong >${l.MostrarAuto()}</strong>
                                                                    <small class="text-muted">Servicio: </small><strong >${l.toString()}</strong>
                                                                    <small class="text-muted">Número de factura: </small><strong >${l.id_factura}</strong>
                                                                    <small class="text-muted">Detalle de factura: </small><strong >${l.id_detalleServicio}</strong>

                                                                </div>


                                                                <div class="col text-center" style="display: inline-grid; align-items: center;">
                                                                    <small class="text-muted">Estado: </small>
                                                                    <c:choose>
                                                                        <c:when test="${l.o.id_estado == 1}">
                                                                            <strong style="color: darkgreen; font-size: 20px;">
                                                                                En espera
                                                                            </strong>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <strong style="color: #106BA0; font-size: 20px;">
                                                                                En reparación
                                                                            </strong>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </div>


                                                                <c:choose>
                                                                    <c:when test="${l.o.id_estado == 2}">
                                                                        <div class="col" style="display: inline-grid; align-items: center;">
                                                                            <small class="text-muted">Inicio: </small>
                                                                            <strong>${l.o.FechaInicio()}</strong>
                                                                            <strong>${l.o.HoraInicio()}</strong>
                                                                            <small class="text-muted">Fin estimado: </small>
                                                                            <strong>${l.o.FechaFinEstimado()}</strong>
                                                                            <strong>${l.o.HoraFinEstimado()}</strong>
                                                                        </div>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <div class="col" style="display: flex; align-items: center;">

                                                                        </div>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <c:choose>
                                                                    <c:when test="${l.o.id_estado == 2}">
                                                                        <div class="col" style="display: flex; align-items: center;">
                                                                            <a href="#" onclick="Finalizar(${l.id_detalleServicio}, ${l.id_factura})" style="margin-left: 50%; transform: translateX(-50%)">Reparación finalizada</a>
                                                                        </div>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <div class="col" style="display: flex; align-items: center;">
                                                                            <a href="#" onclick="Tomar(${l.id_detalleServicio})" style="margin-left: 50%; transform: translateX(-50%)">Realizar servicio</a>
                                                                        </div>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>                            
                                                        </li>

                                                    </c:forEach>

                                                </c:when>
                                                <c:otherwise>
                                                    <h4 style="color: ivory; font-size: 35px;">
                                                        No hay presupuestos disponibles.
                                                    </h4>
                                                </c:otherwise>
                                            </c:choose>
                                        </ul>
                                        <div id="divPresupuesto"></div>
                                    </form>
                                </div>
                                <script type="text/javascript">
                                    var divPresupuesto = document.getElementById('divPresupuesto');
                                    function Tomar(id_d) {
                                        divPresupuesto.innerHTML = "<input type=\"hidden\" value=\"" + id_d + "\" name=\"id_detalle\"> <input type=\"hidden\" value=\"tomar\" name=\"accion\"> ";
                                        document.frmListarTrabajos.submit();
                                    }
                                    function Finalizar(id_d, id_f) {
                                        divPresupuesto.innerHTML = "<input type=\"hidden\" value=\"" + id_d + "\" name=\"id_detalle\"> \n\
                                                                    <input type=\"hidden\" value=\"" + id_f + "\" name=\"id_fts\"> \n\
                                                                    <input type=\"hidden\" value=\"finalizar\" name=\"accion\"> ";
                                        document.frmListarTrabajos.submit();
                                    }
                                </script>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body> 
</html>
