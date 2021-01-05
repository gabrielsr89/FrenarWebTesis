<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestión de cobranza</title>
    </head>
    <body id="fondomain">
        <div class="container-fluid">
            <div class="row justify-content-center" style="height: 100%;">
                <div class="col-9">
                    <div class="container-fluid bg-secondary" >
                        <div class="row" style="height: 100%;">
                            <nav class="col-md-2 d-none d-md-block bg-light sidebar pt-5">
                                <c:import url="menu.jsp" >
                                </c:import>
                            </nav>
                            <div class="col-md-10 bg-secondary text-center"  style="overflow: hidden; margin-top: 100px; color: ivory;">


                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="margin-top: 60px!important;">
                                    <h1 class="titulares">Registrar cobranza</h1>
                                </div>

                                <form method="POST" action="./NuevoPagoServlet" name="frmCobranza">
                                    <c:choose>
                                        <c:when test="${idCt != 0}">
                                            <input type="hidden" value="${idCt}" name="idCliente">
                                        </c:when>
                                    </c:choose>
                                    <div class="row text-center mt-5">
                                        <div class="col">
                                            <label for="cboCliente" style="color:ivory; font-size: 25px;">Seleccione el cliente</label>
                                            <select class="form-control" id="cboCliente" name="idCliente"  style="width: 60%; margin-left: 50%; transform: translateX(-50%);">                        
                                                <option value = "0">Despliegue para seleccionar</option>
                                                <c:forEach items="${lstCliente}" var="ct">  
                                                    <option value="${ct.id}" ${ct.p.nombre eq ct.id?'selected':''}>${ct.p.toString()}</option>
                                                </c:forEach>
                                            </select>                                           
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col">
                                            <label for="cboFactura" style="color:ivory; font-size: 25px;">Elija la factura</label>
                                            <select class="form-control" id="cboFactura" name="idFactura"  style="width: 60%; margin-left: 50%; transform: translateX(-50%);">                        
                                                <option value = "0">Despliegue para seleccionar</option>                        
                                            </select>
                                        </div>              
                                    </div>
                                    <div class="row">
                                        <div class="col">
                                            <c:choose>
                                                <c:when test="${idFormaPago > 0}">
                                                    <input type="hidden" value="${idFormaPago}" name="idFormaPago">                            
                                                </c:when>                     
                                            </c:choose>
                                            <label for="cboPago" style="color:ivory; font-size: 25px;">Elija forma de pago</label>
                                            <select class="custom-select" id="cboTipoPago" name="idFormaPago" style="width: 60%; margin-left: 50%; transform: translateX(-50%);">
                                                <option value="0">Despliegue para seleccionar método de pago</option>
                                                <option value="1">Pago en efectivo</option>                                                
                                            </select>
                                        </div>              
                                    </div>


                                    <div class="row">
                                        <div class="col text-center mt-5">
                                            <input type="submit" class="btn btn-dark" onclick="Enviar()" value="Registrar cobranza" style="width: 300px;">                    
                                        </div>                
                                    </div>
                                </form>

                                <script src="js/CombosUbicacion.js" type="text/javascript"></script>
                                <script type="text/javascript">
                                                var cboFactura = document.getElementById('cboFactura');
                                                var cboPago = document.getElementById('cboPago');
                                                var cboCliente = document.getElementById('cboCliente');
                                                var clientePorParametro = 0;
                                                var idFormaPago = 0;

                                    <c:choose>
                                        <c:when test="${idCt != 0}">
                                                clientePorParametro = ${idCt}
                                        </c:when>
                                    </c:choose>

                                    <c:choose>
                                        <c:when test="${idFormaPago != 0}">
                                                idFormaPago = ${idFormaPago}
                                        </c:when>
                                    </c:choose>
                                                window.onload = function cargarClienteParametro() {
                                                    if (idFormaPago > 0) {
                                                        cboPago.value = idFormaPago;
                                                        cboPago.disabled = true;
                                                    }

                                                    if (clientePorParametro > 0) {
                                                        cboCliente.value = clientePorParametro;
                                                        cboCliente.disabled = true;

                                                        var facturas = new Array();
                                    <c:forEach items="${lstFacturas}" var="f">
                                                        var factura = new Ubicacion(${f.id_factura}, '${f.toString()}', ${f.id_cliente});
                                                        facturas.push(factura);
                                    </c:forEach>
                                                        var facturasFiltradas = new Array();

                                                        for (f of facturas) {
                                                            if (f.id_padre == clientePorParametro) {
                                                                facturasFiltradas.push(f);
                                                            }
                                                        }
                                                        //CARGAR EL CBO MODELOS
                                                        facturasFiltradas.sort(facturasFiltradas.descripcion);
                                                        addOptions(cboFactura, facturasFiltradas);
                                                    }
                                                }
                                                function Enviar() {
                                                    if (cboCliente.value < 1) {
                                                        alert('Debe elegir al menos un cliente');
                                                    } else if (cboFactura.value < 1) {
                                                        alert('Debe elegir la factura');
                                                    } else {
                                                        document.frmCobranza.submit();
                                                    }
                                                }

                                                cboCliente.addEventListener('change', (event) => {
                                                    var facturas = new Array();
                                    <c:forEach items="${lstFacturas}" var="f">
                                                    var factura = new Ubicacion(${f.id_factura}, '${f.toString()}', ${f.id_cliente});
                                                    facturas.push(factura);
                                    </c:forEach>
                                                    var facturasFiltradas = new Array();

                                                    for (f of facturas) {
                                                        if (f.id_padre == cboCliente.value) {
                                                            facturasFiltradas.push(f);
                                                        }
                                                    }
                                                    //CARGAR EL CBO MODELOS
                                                    facturasFiltradas.sort(facturasFiltradas.descripcion);
                                                    addOptions(cboFactura, facturasFiltradas);
                                                });

                                </script>


                                <c:import url="/bootstrap.jsp"></c:import>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
