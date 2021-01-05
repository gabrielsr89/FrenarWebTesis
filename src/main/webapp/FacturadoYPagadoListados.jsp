
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Repuestos a entregar</title>
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
                                    <h1 class="titulares">Listado a entregar</h1>
                                </div>

                                <div>
                                    <form method="POST" action="./FacturadoYPagadoListados" name="frmEntrega">
                                        <ul class="list-group">
                                            <c:choose>
                                                <c:when test='${listado.size()!=0}'>
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
                                                                    <a href="#" onclick="Entregar(${listado.id_factura})" style="margin-left: 50%; transform: translateX(-50%)">Entregar</a>
                                                                </div>
                                                            </div>                            
                                                        </li>

                                                    </c:forEach>
                                                </c:when>   
                                                <c:otherwise>
                                                    <h4 style="color: ivory; font-size: 35px;">
                                                        No hay entregas disponibles.
                                                    </h4>
                                                </c:otherwise>
                                            </c:choose>
                                        </ul>
                                        <div id="divEntrega"></div>
                                    </form>
                                </div>
                                <script type="text/javascript">
                                    function Entregar(id_f) {
                                        var divEntrega = document.getElementById('divEntrega');
                                        divEntrega.innerHTML = "<input type=\"hidden\" value=\"" + id_f + "\" name=\"id_factura\">";
                                        document.frmEntrega.submit();
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