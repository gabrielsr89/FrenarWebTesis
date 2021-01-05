
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html lang="en">
    <head>
        <meta charset="utf-8">   
        <title>Catálogo de repuestos</title> 
        <link rel="stylesheet" href="./css/FrenarFont.css">
        <link rel="stylesheet" href="./css/Header.css">
        <link rel="stylesheet" href="./css/BlackieFont.css">
        <link rel="stylesheet" href="./css/CatalogoRepuestos.css">
    </head>
    <body class="bg-light" id="fondomain">
        <form method="POST" action="./CatRepuestosServlet">
            <c:choose>
                <c:when test='${carrito != null}'>
                    <input type="hidden" value="${carrito}" name="carrito">                    
                </c:when>
            </c:choose>
            <c:import url="header.jsp"></c:import>  

                <div class="container bg-light rounded-lg " style="position: relative; top: 65px">
                    <div class="py-5 text-center">
                    <c:choose>
                        <c:when test = '${cliente==true}'>
                            <h2 class="my-5" style="font-family: blackie">Catálogo de repuestos</h2>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${idCt == 0}">
                                    <h2 class="my-5" style="font-family: blackie">Venta de repuestos</h2>
                                    <label for="cboCliente">Seleccione el cliente</label>
                                    <select class="form-control" id="cboCliente" name="idCliente"  style="width: 60%; margin-left: 50%; transform: translateX(-50%);">                        
                                        <option value = "0">Despliegue para seleccionar</option>
                                        <c:forEach items="${lstCliente}" var="ct">  
                                            <option value="${ct.id}" ${ct.p.nombre eq ct.id?'selected':''}>${ct.p.toString()}</option>
                                        </c:forEach>
                                    </select> 
                                </c:when>
                                <c:otherwise>
                                    <h4> Cliente: ${perkin}</h4>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                    <p class="lead" style="margin-top: 30px;">Este catá   logo ofrece un precio aproximado de los repuestos, el precio final se consulta en nuestro local.</p>
                </div>

                <div class="row">
                    <div class="col" style="color: ivory; display: flex; align-items: center; justify-content: center;">
                        <center>
                            <h2 style="color: black; font-size: 60px;">
                                Parametrizar<br/> búsqueda
                            </h2> 
                        </center>                        
                    </div>
                    <div class="col">

                        <div class="row">
                            <div class="col" >
                                <label for="cboRubros">Rubros</label>
                                <select class="form-control mb-2" id="cboRubros" name="cboRubros" style="width: 70%;">      
                                    <option value="0"> Todos los rubros</option>
                                    <c:forEach items="${lstRubro}" var="r">  
                                        <option value="${r.id}" ${rubro eq r.id?'selected':''}>${r}</option>                            
                                    </c:forEach>   
                                </select>

                                <label for="cboMarcas">Marcas</label>
                                <select class="form-control mb-2" id="cboMarcas" name="cboMarcas" style="width: 70%;">      
                                    <option value="0"> Todas las marcas </option>
                                    <c:forEach items="${lstMarcaRepuesto}" var="mr">  
                                        <option value="${mr.id_marcaRepuesto}" ${marcaRepuesto eq mr.id_marcaRepuesto?'selected':''}>${mr}</option>                            
                                    </c:forEach>     

                                </select>
                                <input type="radio" id="rbtMayorMenor" name="rbt" style="margin-right: 20px; margin-top: 15px;"><label for="rbtMayorMenor" style="font-size: 20px;">  Del mayor precio al menor</label><br/>
                                <input type="radio" id="rbtMenorMayor" name="rbt" style="margin-right: 20px;"><label for="rbtMenorMayor" style="font-size: 20px;">  Del menor precio al mayor</label>

                            </div>
                        </div>
                        <div class="row">   
                            <div class="col mt-3">
                                <button type="button" class="btn btn-dark" onclick="Envido()" id="btnEnviar" style="width: 70%;" >Buscar</button>
                            </div> 
                        </div>

                        <script type="text/javascript">
                            var cboRubros = document.getElementById('cboRubros');
                            var cboMarcas = document.getElementById('cboMarcas');
                            var rbtMayorMenor = document.getElementById('rbtMayorMenor');
                            var rbtMenorMayor = document.getElementById('rbtMenorMayor');
                            function Envido() {
                                var id_rubro = cboRubros.value;
                                var id_marca = cboMarcas.value;
                                var rbt = '';
                                if (rbtMayorMenor.checked) {
                                    rbt = 'mayor';
                                } else {
                                    rbt = 'menor';
                                }
                                location.href = './CatRepuestosServlet?rubro=' + id_rubro + '&marca=' + id_marca + '&precio=' + rbt;
                            }
                        </script>
                    </div>  
                </div>  

                <div class="row mt-5">
                    <div class="col-md-4 order-md-2 mb-4">
                        <h4 class="d-flex justify-content-between align-items-center mb-3">
                            <span class="text-muted ">Carrito</span>
                            <c:choose>
                                <c:when test='${carrito != null}'>
                                    <span class="badge badge-secondary badge-pill">${carrito.carritoCantidadElementos()}</span>                                        
                                </c:when>
                            </c:choose>
                        </h4>

                        <%--ACÁ SE CARGA LA CARTERA--%>
                        <c:choose>
                            <c:when test='${carrito.lstRepuesto.size()> 0}'>
                                <c:forEach items="${carrito.lstRepuesto}" var="c" >
                                    <ul class="list-group mb-3">

                                        <li class="list-group-item">

                                            <div class="row mt-2 mb-2">
                                                <div class="col">
                                                    <h6 class="my-0">${c.r.repuesto}</h6>
                                                </div>

                                            </div>
                                            <div class="row text-center">  
                                                <div class="col mt-3 mb-2" >
                                                    <div class="row">
                                                        <div class="col">
                                                            <small class="text-muted">Precio unitario: $${c.r.precio}</small>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col">
                                                            <small class="text-muted">Cantidad: ${c.cantidad}</small>
                                                        </div>                                                        
                                                    </div>
                                                </div>

                                                <div class="col" style="text-align: center; align-items: center; display: flex; width: 50px;">
                                                    <span>Subtotal: ${c.cantidad * c.r.precio}</span>                                                   
                                                </div>
                                            </div>  
                                            <div class="row text-center">
                                                <div class="col">
                                                    <a href="./DetalleArticuloServlet?modificar=${c.r.id_repuesto}">Cambiar cantidad</a>                                                   
                                                </div>
                                                <div class="col">                                                    
                                                    <a href="./CatRepuestosServlet?sacar=${c.r.id_repuesto}">Quitar</a>
                                                </div>
                                            </div>
                                        </li>

                                    </c:forEach>
                                    <li class="list-group-item" style="text-align: center; margin-top: 10px;">
                                        <div class="row">
                                            <div class="col">
                                                <span>Total acumulado:</span>
                                            </div>
                                            <div class="col">
                                                <strong>$${carrito.calcularTotalRepuestos()}</strong>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px;">
                                            <div class="col">
                                                <a href="./ConfirmarCompraServlet" class="btn btn-secondary" style="width: 90%">Confirmar compra</a>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px;">
                                            <div class="col">
                                                <button type="button" class="btn btn-secondary" onclick="location.href = './CancelarCompraServlet?repuestos=2'" style="width: 90%">Cancelar compra</button>
                                            </div>
                                        </div>
                                    </li>
                                </ul>  
                            </c:when>
                            <c:otherwise>                               
                                <span style="font-size: 22px;">El carrito está vacío</span>                               
                            </c:otherwise>
                        </c:choose>
                    </div>                    


                    <%--listado--%>
                    <div class="col-md-8 order-md-1">
                        <h4 >Repuestos</h4>

                        <c:choose>
                            <c:when test = "${lstr == null}">
                                <h2>No hay repuestos disponibles</h2>                                        
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${lstr}" var="r" >
                                    <ul class="list-group text-black" style="color: black">
                                        <li class="list-group-item" style="height: 200px; margin-top: 10px;">
                                            <div class="row">

                                                <div class="col" id="divImagen">
                                                    <img class="imagenes mr-auto" src="${r.foto}">
                                                </div>

                                                <div class="col" style="align-items: center; display: flex; text-align: center;">
                                                    <h6 class="my-0" >${r.repuesto}</h6>
                                                </div>

                                                <div class="col" style="align-items: center; display: flex; text-align: center;">
                                                    <span class="text-muted" >Precio unitario:<span style="color: #106BA0; font-size: 18px;"> $${r.precio}</span></span> 
                                                </div>

                                                <div class="col" style="align-items: center; display: flex; text-align: center;">     
                                                    <a class="text-dark" href="#" onclick="ValidarCarrito(${r.id_repuesto})"><img src="./img/carrito.png"><br/>Agregar al carrito</a>
                                                </div>

                                            </div>
                                        </li>
                                    </ul>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>  
                </div>

                <footer class="my-5 pt-5 pb-2 text-muted text-center text-small">
                    <p class="mb-2">© 2020 FrenarWeb</p>

                </footer>
            </div>          
        </form>
        <c:import url="bootstrap.jsp"></c:import>  

            <script type="text/javascript">
                class Repuesto {
                    constructor(id_repuesto) {
                        this.id = id_repuesto;
                    }
                }
                //FUNCIÓN PARA VALIDAR QUE NO SE CARGUE 2 VECES EL MISMO ITEM EN EL CARRITO
                function ValidarCarrito(idRepuesto) {
                    var AlguienLogeado = false;
            <c:choose>
                <c:when test="${(cliente != null && cliente != false) || (administrador != null && administrador != false) || (operador != null && operador != false) }">
                    AlguienLogeado = true;
                </c:when>
            </c:choose>

                    var idCliente = 0;
                    var VentaParaCliente = true;

            <c:choose>
                <c:when test="${cliente == false}">
                    <c:choose>
                        <c:when test="${idCt > 0}">
                    idCliente = ${idCt}
                        </c:when>
                        <c:otherwise>
                    idCliente = document.getElementById('cboCliente').value;
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    idCliente = ${idCt}
                </c:otherwise>
            </c:choose>
                    console.log(idCliente);

                    if (idCliente > 0) {
                        VentaParaCliente = true;
                    } else {
                        VentaParaCliente = false;
                    }
                    var carro = new Array();
            <c:forEach items="${carrito.lstRepuesto}" var="c">
                    var r = new Repuesto(${c.r.id_repuesto});
                    carro.push(r);
            </c:forEach>

                    if (AlguienLogeado) {
                        if (VentaParaCliente) {
                            if (!carro.length > 0) {
                                location.href = "DetalleArticuloServlet?idRepuesto=" + idRepuesto + "&idCliente=" + idCliente;
                            } else {
                                var id = 0;
                                for (repuesto of carro) {
                                    if (idRepuesto == repuesto.id) {
                                        id = idRepuesto;
                                        break;
                                    }
                                }
                                if (id != 0) {
                                    location.href = "DetalleArticuloServlet?idCliente=" + idCliente + "&modificar=" + id;
                                } else {
                                    location.href = "DetalleArticuloServlet?idCliente=" + idCliente + "&idRepuesto=" + idRepuesto;
                                }
                            }
                        } else {
                            alert('Debe seleccionar al menos un cliente');
                        }
                    } else {
                        alert('Primero debe estar logeado. Si no tiene una cuenta se la puede crear');
                        location.href = "./LoginServlet";
                    }
                }
        </script>
    </body>
</html>