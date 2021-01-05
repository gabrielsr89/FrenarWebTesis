<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Presupuestos cancelados</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row justify-content-center">
                <div class="col-9">
                    <div class="container-fluid bg-secondary" >
                        <div class="row" style="height: 100%;">
                            <nav class="col-md-2 d-none d-md-block bg-light sidebar pt-5">
                                <c:import url="menu.jsp" >
                                </c:import>
                            </nav>
                            <div class="col-md-10 bg-secondary"  style="overflow: hidden; margin-top: 100px; color:ivory;">


                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="padding-top: 100px!important">
                                    <h1 class="titulares">Presupuestos cancelados</h1>
                                </div>                    

                                <div>
                                    <form method="POST" action="./ConfirmarPresupuestoServlet" name="frmConfirmarPresupuesto">
                                        <ul class="list-group">
                                            <c:choose>
                                                <c:when test='${lista.size()>0}'>
                                                    <c:forEach items="${lista}" var="l">
                                                        <li class="list-group-item">
                                                            <div class="row">
                                                                <div class="col" style="display: inline-grid">
                                                                    <strong>Vehículo: ${l.marcaModeloDelAuto()}</strong>
                                                                    <small>Número de factura: ${l.id_factura}</small>
                                                                    <strong>Importe: <label  class="text-muted">$${l.calcularTotal()}</label></strong>
                                                                    <strong>Fecha: <label  class="text-muted">${l.Fecha()}</label></strong>

                                                                </div>
                                                                <div class="col text-center" style="display: flex; align-items: center;">
                                                                    <a href="./DetalleEntregaServlet?id_presupuesto=${l.id_factura}" style="margin-left: 50%; transform: translateX(-50%)">Ver detalles</a>
                                                                </div>                                                
                                                            </div>                            
                                                        </li>

                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <h4 style="color: ivory; font-size: 35px;">
                                                        No hay presupuestos cancelados registrados.
                                                    </h4>
                                                </c:otherwise>
                                            </c:choose>
                                        </ul>
                                        <div id="divPresupuesto"></div>
                                    </form>
                                </div>
                            </div>            
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
