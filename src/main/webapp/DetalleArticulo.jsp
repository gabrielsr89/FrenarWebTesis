
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <link href="form-validation.css" rel="stylesheet">
        <link rel="stylesheet" href="./css/FrenarFont.css">
        <link rel="stylesheet" href="./css/Header.css">
        <link rel="stylesheet" href="./css/BlackieFont.css">
        <link rel="stylesheet" href="./css/CatalogoRepuestos.css">
        <style>
            #Contenedor{
                width: 700px;
                margin-top: 150px;
                height: 100%;
                left: 50%;
                transform: translateX(-50%);
                align-items: flex;
                text-align: center;
                border: ivory 4px solid;
                border-radius: 20px;
            }
        </style>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalle del repuesto</title>
    </head>
    <body id="fondomain">        
        <c:import url="header.jsp"></c:import>

            <div class="position-absolute bg-secondary" id="Contenedor" style="color: ivory; font-size: 20px;">
                <form method="POST" action="./DetalleArticuloServlet">
                    <input type="hidden" value="${idCliente}" name="idCliente">
                <%--EL ID DEL REPUESTO--%>   
                <input type="hidden" value="${r.id_repuesto}" id="rocoTest" name="id_repuesto">
                <input type="hidden" value="${bandera}" id="rocoTest" name="bandera">
                <input type="hidden" value="${posicion}" id="rocoTest" name="posicion">
                <%--IMAGEN--%>
                <div class="row">
                    <div class="col" style="align-items: center;">
                        <img src="${r.foto}" id="fotos">
                    </div>                
                </div>
                <%--NOMBRE--%>
                <div class="row mt-5">
                    <div class="col"  style="font-family: blackie; font-size: 28px; margin-left: 30px;margin-right: 30px;;word-wrap: break-word;">
                        ${r.repuesto}
                    </div>                
                </div>
                <%--DETALLES--%>
                <div class="row mt-4">
                    <div class="col" id="descripcion">
                        ${r.descripcion}
                    </div>                
                </div>
                <%--PRECIO Y CANTIDAD--%>
                <div class="row mt-5">
                    <div class="col" style="text-align: center;">
                        <label for="precio" style="font-family: blackie;">Precio</label>
                        <span id="precio">$ ${r.precio}</span>
                    </div>                
                    <div class="col">
                        <c:choose>
                            <c:when test='${r.stock > 0}'>
                                <label for="numCantidad" style="font-family: blackie;">Cantidad: </label>                            
                                <input type="number" min="1" max="${r.stock}" id="numCantidad" name="cantidad" onblur="ControlarCantidad()" value="${cantidad}">
                            </c:when>
                            <c:otherwise>
                                <span style="margin-right: 30px;">Actualmente no hay stock</span>
                            </c:otherwise>
                        </c:choose>
                    </div>                
                </div>
                <%--BOTONES--%>
                <div class="row mt-5">
                    <div class="col">
                        <c:choose>
                            <c:when test='${r.stock > 0}'>
                                <input type="submit" class="btn btn-dark " value="Añadir al carrito">
                                <input type="button" class="btn btn-dark " value="Volver al catalogo" onclick="history.go(-1);">

                            </c:when>
                            <c:otherwise>
                                <input type="button" class="btn btn-dark " value="Volver al catalogo" onclick="history.go(-1);">
                            </c:otherwise>
                        </c:choose>

                    </div>                
                </div>
                <p class="mt-5 mb-3 text-white">© 2020</p>
            </form>
        </div>
        <c:import url="bootstrap.jsp"></c:import>  

            <script type="text/javascript">
                var cantidad = document.getElementById("numCantidad");
                function ControlarCantidad() {
                    if (cantidad.value > ${r.stock}) {
                        alert('Disponemos de ${r.stock} unidades por el momento');
                        cantidad.value = ${r.stock};
                    }
                    if (cantidad.value < 1) {
                        alert('La cantidad no puede ser menor a 1, si no desea adquirir el producto presione \"Volver al catalogo\", luego presione \"Quitar\" en listado del carrito');
                    }

                }
        </script>
    </body>
</html>
