<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalles de entrega</title>
    </head>
    <body id="fondomain">
        <c:import url="header.jsp"></c:import>
            <div class="container bg-dark" style="height: max-content; padding-right: 0px;
                 padding-left: 0px;">

                <div class="row" style="height: 250px;">
                    <div class="col text-center " style="color:ivory; padding-top: 130px;">
                        <strong style="font-size: 35px;">
                            Detalles de pedido
                        </strong>               
                    </div>
                    <div class="col" style="display: inline-grid; font-size: 18px; color:ivory;">
                        <small>Cliente:</small> <strong>${carrito.ObtenerNombreCliente()}</strong>
                        <small>Número de factura:</small><strong>${carrito.id_factura}</strong> 
                        <small>Importe total:</small><strong>$${carrito.calcularTotal()}</strong>
                        <small>Vehículo:</small><strong>${carrito.marcaModeloDelAuto()}</strong>
                    </div>
                </div>

            <div class="container bg-dark mr-auto" style="width: 100%;">
                <div class="row text-center" >
                    <div class="col text-center bg-dark mt-3 mb-5">
                        <input type="button" class="btn btn-secondary" id="cancelarButton" onClick="history.go(-1);" style="color:ivory; width: 30%;" value="Volver"> 
                    </div>
                </div>
            </div>

            <div class="row mx-auto">                    
                <div class="col bg-secondary">
                    <div class="row mb-5 bg-secondary">
                        <div class="col bg-secondary">
                            <h1 style="font-size: 40px; margin-top: 80px; height: 40px; text-align: center; color:ivory;">Repuestos</h1>

                        </div>
                    </div>
                    <div class="row text-center bg-light" style="height: 100%;">
                        <div class="col bg-light" style="overflow: auto;min-width: 400px; min-height: 200px; max-height: 300px;">
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
                </div>
                <div class="col bg-light">
                    <div class="row mb-5">
                        <div class="col">
                            <h1 style="font-size: 40px;  margin-top: 80px; height: 40px;text-align: center;">Servicios presupuestados</h1>

                        </div>
                    </div>
                    <div class="row bg-secondary" style="height: 100%">
                        <div class="col bg-secondary" style="overflow: auto;  margin: 0px; height: 100%;">
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
                </div>

            </div>


        </div>
        <c:import url="bootstrap.jsp"></c:import>
    </body>
</html>
