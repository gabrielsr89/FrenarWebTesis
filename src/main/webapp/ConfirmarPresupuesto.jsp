<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirmar presupuesto</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row justify-content-center"  style="height: 100%;">
                <div class="col-9" >
                    <div class="container-fluid bg-secondary" >
                        <div class="row"  style="height: 100%;">
                            <nav class="col-md-2 d-none d-md-block bg-light sidebar pt-5" >
                                <c:import url="menu.jsp" >
                                </c:import>
                            </nav>
                            <div class="col-md-10 bg-secondary"  style="overflow: hidden; margin-top: 100px; color: black;">
                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="padding-top: 100px!important">
                                    <h1 class="titulares">Confirmar presupuesto</h1>
                                </div>
                                <div class="mb-5">
                                    <center>
                                        <c:choose>
                                            <c:when test="${usuario=='empleado'}">
                                                <a href="./PresupuestosCancelados" class="text-white" ><span style="color:ivory; font-size: 20px;">Ver listado de presupuestos cancelados</span></a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="./PresupuestosCancelados?id_c=${id_c}" class="text-white" ><span style="color:ivory; font-size: 20px;">Ver listado de presupuestos cancelados</span></a>                                
                                            </c:otherwise>
                                        </c:choose>
                                    </center>                        
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
                                                                <div class="col" style="display: flex; align-items: center;">
                                                                    <a href="#" onclick="Confirmar(${l.id_factura})" style="margin-left: 50%; transform: translateX(-50%)">Confirmar presupuesto</a>
                                                                </div>
                                                                <div class="col" style="display: flex; align-items: center;">
                                                                    <a href="#" onclick="Cancelar(${l.id_factura})" style="margin-left: 50%; transform: translateX(-50%)">Cancelar presupuesto</a>
                                                                </div>
                                                                <c:choose>
                                                                    <c:when test="${!cliente}">                                                                            
                                                                        <div class="col" style="display: flex; align-items: center;">
                                                                            <a href="#" onclick="Modificar(${l.id_factura})" style="margin-left: 50%; transform: translateX(-50%)">Modificar presupuesto</a>
                                                                        </div>
                                                                    </c:when>
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
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <script type="text/javascript">
            var divPresupuesto = document.getElementById('divPresupuesto');
            function Confirmar(id_f) {


                var conf = confirm('Al aceptar el presupuesto se registrarán los distintos servicios a realizar. Acepta el presupuesto?');
                if (conf) {
                    $.ajax({
                        type: "POST",
                        url: "ConfirmarPresupuestoServlet",
                        data: {id_f: id_f},
                        success: function (data, textStatus, jqXHR) {
                            if (data != 'true') {
                                alert('De momento no se puede confirmar el presupuesto debido a la falta de stock de uno o más de los repuestos pertinentes.')
                            } else {
                                alert('Confirmación exitosa');
                                location.reload();
                            }
                        },
                        error: function (jqXHR) {
                            console.log("error")
                        }
                    });
                }
            }
            function Cancelar(id_f) {
                var conf = confirm('Está seguro de cancelar el presupuesto?');
                if (conf) {
                    divPresupuesto.innerHTML = "<input type=\"hidden\" value=\"" + id_f + "\" name=\"id_factura\"> <input type=\"hidden\" value=\"cancelar\" name=\"accion\"> ";
                    document.frmConfirmarPresupuesto.submit();
                }
            }
            function Modificar(id_f) {
                var conf = confirm('Está seguro de modificar el presupuesto?');
                if (conf) {
                    location.href = "./ModificarFacturaServlet?idPresupuesto=" + id_f;
                }
            }
        </script>
    </main>
</body>
</html>
