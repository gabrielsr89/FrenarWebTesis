
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listado de reservas</title>
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
                                    <h1 class="titulares">Listado de reservas</h1>
                                </div>

                                <div>
                                    <form method="POST" action="./ConfirmarReservaServlet" name="frmReserva">
                                        <ul class="list-group">
                                            <c:choose>
                                                <c:when test='${listado.size()>0}'>
                                                    <c:forEach items="${listado}" var="listado">
                                                        <li class="list-group-item">
                                                            <div class="row">
                                                                <div class="col" style="display: inline-grid">
                                                                    <strong>Cliente: ${listado.ObtenerNombreCliente()}</strong>
                                                                    <small>Número de factura: ${listado.id_factura}</small>
                                                                    <strong>Importe: <label  class="text-muted">$${listado.calcularTotal()}</label></strong>

                                                                </div>
                                                                <div class="col text-center" style="display: flex; align-items: center;">
                                                                    <a href="./DetalleEntregaServlet?id_factura=${listado.id_factura}" style="margin-left: 50%; transform: translateX(-50%)">Ver detalles</a>
                                                                </div>
                                                                <div class="col" style="display: flex; align-items: center;">
                                                                    <c:choose>
                                                                        <c:when test="${tipoDeUsuario == 'Empleado'}">
                                                                            <div class="row">
                                                                                <a href="#" onclick="Entregar(${listado.id_factura})" style="margin-left: 50%; transform: translateX(-50%)">Confirmar reserva</a>

                                                                            </div>
                                                                        </c:when>
                                                                        <c:otherwise>                                                            
                                                                            <div class="row">
                                                                                <a href="#" onclick="Cancelar(${listado.id_factura})" style="margin-left: 50%; transform: translateX(-50%)">Cancelar reserva</a>

                                                                            </div>
                                                                            <div class="row">
                                                                                <a href="#" onclick="Modificar('${listado.id_factura}')" style="margin-left: 50%; transform: translateX(-50%)">Modificar reserva</a>

                                                                            </div>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </div>
                                                            </div>                            
                                                        </li>

                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <h4 style="color: ivory; font-size: 35px;">
                                                        No hay reservas
                                                    </h4>
                                                </c:otherwise>
                                            </c:choose>
                                        </ul>
                                        <div id="divEntrega"></div>
                                    </form>
                                </div>
                                <script type="text/javascript">
                                    function Entregar(id) {
                                        $.ajax({
                                            type: "POST",
                                            url: "ConfirmarReservaServlet",
                                            data: {id_f: id},
                                            success: function (data, textStatus, jqXHR) {
                                                if (data != 'true') {
                                                    alert('De momento no se puede confirmar la reserva debido a la falta de stock de uno o más de los repuestos pertinentes.')
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

                                    function Modificar(id) {
                                        if (tipoUsuario === 'Cliente') {
                                            var conf = confirm('¿Esta seguro de modificar la reserva?');
                                            if (conf) {
                                                location.href = "./CatRepuestosServlet?id_f=" + id;
                                            }

                                        }
                                    }
                                    var tipoUsuario = '${tipoDeUsuario}';
                                    function Cancelar(id_f) {
                                        if (tipoUsuario === 'Cliente') {
                                            var conf = confirm('¿Estás seguro de cancelar la reserva?');
                                            if (conf) {
                                                location.href = "./CancelarReserva?id_f=" + id_f;
                                            }
                                        } 
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