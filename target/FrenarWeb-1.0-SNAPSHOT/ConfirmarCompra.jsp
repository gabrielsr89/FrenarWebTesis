<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./css/FrenarFont.css">
        <link rel="stylesheet" href="./css/Header.css">
        <link rel="stylesheet" href="./css/BlackieFont.css">
        <link rel="stylesheet" href="./css/CatalogoRepuestos.css">
        <title>Confirmación</title>
    </head>
        <body id="fondomain">
    <c:import url="header.jsp"></c:import>  
    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4" style="height: 100%">
            <div class="container" style="height: 100%;">

                <div class="row" style="height: 250px;">
                    <div class="col bg-dark text-center " style="color:ivory; padding-top: 130px;">
                        <strong style="font-size: 35px;">
                            Cliente: ${nombre} 
                    </strong>
                    <br/>
                    <c:choose>
                        <c:when test ="${auto != ''}">
                            <small>
                                Vehículo: <strong>
                                    ${auto}
                                </strong>
                            </small>
                        </c:when>
                    </c:choose>

                </div>                
            </div>
            <div class="row">                    
                <%-- COLUMNA REPUESTOS --%>
                <div class="col bg-secondary">
                    <%-- titulo--%>
                    <div class="row mb-5">
                        <div class="col">
                            <h1 style="font-size: 40px; margin-top: 80px; height: 40px; text-align: center; color:ivory;">Repuestos a comprar</h1>

                        </div>
                    </div>
                    <%-- listado--%>
                    <div class="row text-center">
                        <div class="col bg-light" style="overflow: auto; border-radius: 2px; border: ivory 2px solid; min-width: 400px; min-height: 200px; max-height: 300px;">
                            <c:choose>
                                <c:when test='${carrito.lstRepuesto != null}'>
                                    <ul class="list-group bg-light mt-3">
                                        <c:forEach  items="${carrito.lstRepuesto}" var="r">
                                            <li class="list-group-item bg-light">
                                                <div class="row">
                                                    <div class="col">
                                                        <h5>
                                                            ${r.r.repuesto} 
                                                        </h5>
                                                    </div>                                                   
                                                </div>
                                                <div class="row">
                                                    <div class="col" style="text-align: right;">
                                                        <small >
                                                            Cantidad: ${r.cantidad}
                                                        </small>
                                                    </div>                                                   
                                                    <div class="col" style="text-align: right;">
                                                        <strong>
                                                            Precio unitario: $${r.r.precio}
                                                        </strong>
                                                    </div>                                                   
                                                </div>                                                
                                            </li>
                                        </c:forEach>
                                        <li class="list-group-item bg-light">
                                            <div class="row">
                                                <div class="col" style="text-align: right; font-size: 25px;">
                                                    <strong>
                                                        Total: ${carrito.calcularTotalRepuestos()}
                                                    </strong>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </c:when>
                                <c:otherwise>
                                    <h3 style="text-align: center; margin-top: 40px;">
                                        No hay repuestos cargados
                                    </h3>
                                </c:otherwise>                                
                            </c:choose>
                        </div>
                    </div>
                    <div class="row mt-5 mb-5 text-center">
                        <div class="col">
                            <c:choose>
                                <c:when test="${carrito.lstRepuesto == null}">                                    
                                    <input type="button" class="btn btn-dark" value="Incluír repuestos a la factura" onclick="location.href = 'CatRepuestosServlet?idCt=${carrito.id_cliente}'">

                                </c:when>
                                <c:otherwise>
                                    <input type="button" class="btn btn-dark" value="Cancelar compra" onclick="CancelarCompra()">
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <script type="text/javascript">


                </script>

                <%-- COLUMNA SERVICIOS --%>
                <c:choose>
                    <c:when test="${cliente == false}">

                        <div class="col bg-light">
                            <div class="row mb-5">
                                <div class="col">
                                    <h1 style="font-size: 40px;  margin-top: 80px; height: 40px;text-align: center;">Servicios presupuestados</h1>

                                </div>
                            </div>
                            <%-- listado--%>
                            <div class="row">
                                <div class="col bg-secondary" style="overflow: auto; border-radius: 2px; border: ivory 2px solid; margin: 0px; min-height: 200px; max-height: 300px;">
                                    <c:choose>
                                        <c:when test='${carrito.lstServicio != null}'>
                                            <ul class="list-group bg-secondary mt-3" style="color: ivory">
                                                <c:forEach  items="${carrito.lstServicio}" var="s">
                                                    <li class="list-group-item bg-secondary">
                                                        <div class="row">
                                                            <div class="col">
                                                                <h5>
                                                                    ${s.s.servicio} 
                                                                </h5>
                                                            </div>                                                   
                                                        </div>
                                                        <div class="row">
                                                            <div class="col" style="text-align: right;">
                                                                <small >
                                                                    Duración aproximada (min): ${s.s.tiempo}
                                                                </small>
                                                            </div>                                                   
                                                            <div class="col" style="text-align: right;">
                                                                <strong >
                                                                    Costo: $${s.precioServicio}
                                                                </strong>
                                                            </div>                                                   
                                                        </div>                                                
                                                    </li>
                                                </c:forEach>
                                                <li class="list-group-item bg-secondary">
                                                    <div class="row">
                                                        <div class="col" style="text-align: right; display: inline-grid">
                                                            <small>
                                                                Importe:<strong style="margin-left: 10px;">${carrito.calcularTotalServicios()}</strong>
                                                            </small>
                                                            <small>
                                                                Descuento por categoría: <strong style="margin-left: 10px;">${carrito.calcularBonificacionServiciosPorCategoriaEmpleado()}</strong>
                                                            </small>
                                                            <strong style="font-size: 25px;">
                                                                Total: $${carrito.calcularMontoFinalServicios()}
                                                            </strong>
                                                        </div>
                                                    </div>
                                                </li>
                                            </ul>
                                        </c:when>
                                        <c:otherwise>
                                            <h3 style="text-align: center; margin-top: 40px; color: ivory;">
                                                No hay servicios cargados
                                            </h3>
                                        </c:otherwise>                                
                                    </c:choose>
                                </div>
                            </div>
                            <div class="row mt-5 mb-5 text-center">
                                <div class="col">
                                    <c:choose>
                                        <c:when test="${carrito.lstServicio == null}">                                    
                                            <input type="button" class="btn btn-dark" value="Incluír presupuesto" onclick="Presupuestar()">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="button" class="btn btn-dark" value="Cancelar presupuesto" onclick="CancelarPresupuesto()">
                                        </c:otherwise>
                                    </c:choose>                                
                                </div>
                            </div>

                        </div>
                    </c:when>
                </c:choose>
            </div>
            <%-- fila botones --%>
            <form method="POST"  action="./ConfirmarCompraServlet" name="frmPago">
                <div class="container bg-dark mr-auto">
                    <div class="row text-center ">
                        <c:choose>
                            <c:when test="${carrito.lstServicio.size() > 0 && carrito.lstServicio != null}">
                                <div class="col mt-5 mb-5" style="text-align: center;">
                                    <input type="hidden" name="idFormaPago" value="3">
                                    <input type="button" class="btn btn-secondary" onclick="frmPago.submit()" value="Generar presupuesto" style="width: 300px;">
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="col" style="margin-top: 20px; text-align: center;">
                                    <label class="listadoHeader" for="cboTipoPago" style="margin-bottom: 0px;">Seleccione forma de pago</label>                       
                                    <div class="input-group mb-3" style="width: 60%;margin-left: 50%; transform: translateX(-50%); display: flex !important; width: 430px;">
                                        <select class="custom-select" id="cboTipoPago" name="idFormaPago">
                                            <option value="0">Despliegue para seleccionar método de pago</option>
                                            <c:forEach items="${lstTipoPago}" var="tp">  
                                                <option value="${tp.id_tipo}" ${tp.tipo eq tp.id_tipo?'selected':''}>${tp.tipo}</option>
                                            </c:forEach>
                                        </select>
                                        <div class="input-group-append">
                                            <input type="button" class="btn btn-secondary" onclick="Enviar()" for="inputGroupSelect02" value="Elegir">
                                        </div>
                                    </div> 
                                    
                                    <%-- acá --%>
                                </div>
                                <div class="col mt-5">
                                    <input type="button" class="btn btn-secondary" value="Cancelar operación" style="margin-left: 15px; width: 200px; display: table-cell; " onclick="location.href = 'CancelarCompraServlet?todo=1'">

                                </div>                            
                            </c:otherwise>                            
                        </c:choose>
                    </div>
                </div>
            </form>
            
                                    
                                     <script src="https://www.mercadopago.com.ar/integrations/v1/web-payment-checkout.js" 
                                    data-preference-id="${preference.id}" data-button-label="Mercado Pago"></script>
                                 
        </div>
      
                                    

    <script type="text/javascript">

        function Enviar() {
            var cboTipoPago = document.getElementById('cboTipoPago');            
            
            if (cboTipoPago.value == 2) {
                var btnMercado = document.getElementsByClassName('mercadopago-button');
                //btnMercado[0].setAttribute("type","button");
                //console.log(btnMercado);
                btnMercado[0].click();
            }
            if (cboTipoPago.value == 1) {
                document.frmPago.submit();
            }

        }
        function CancelarCompra() {
        <c:choose>
            <c:when test="${cliente == true}">
            location.href = "CancelarCompraServlet?repuestos=1";
            </c:when>
            <c:otherwise>
            location.href = "CancelarCompraServlet?repuestos=2"
            </c:otherwise>
        </c:choose>
        }
        function Presupuestar() {
            var idCliente = 0;
        <c:choose>
            <c:when test="${carrito.id_cliente != 0}">
            idCliente = ${carrito.id_cliente}
            </c:when>
        </c:choose>
            if (idCliente == 0) {
                location.href = 'NuevoPresupuestoServlet';
            } else {
                location.href = 'NuevoPresupuestoServlet?idCliente=' + idCliente;
            }
        }
        function CancelarPresupuesto() {
        <c:choose>
            <c:when test="${carrito.lstRepuesto != null || carrito.lstRepuesto.size()> 0}">
            location.href = "CancelarCompraServlet?servicio=1";
            </c:when>
            <c:otherwise>
            location.href = "CancelarCompraServlet?servicio=2";
            </c:otherwise>
        </c:choose>

        }
    </script>
    <c:import url="bootstrap.jsp"></c:import>  
    </main>
</body>
</html>
