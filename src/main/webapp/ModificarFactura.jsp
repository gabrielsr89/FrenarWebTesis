<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modificación de facturas</title>
    </head>
    <body>
        <div class="container-fluid" style="color: ivory;">
            <div class="row justify-content-center" style="height: 100%;">
                <div class="col-9">
                    <div class="container-fluid bg-dark" >
                        <div class="row" style="height: 100%;">
                            <nav class="col-md-2 d-none d-md-block bg-light sidebar pt-5">
                                <c:import url="menu.jsp" >
                                </c:import>
                            </nav>
                            <div class="col-md-10"  style="overflow: hidden; margin-top: 100px;">

                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="margin-top: 60px!important;">
                                    <h1 class="titulares">Modificar ${tipoFactura}</h1>
                                </div>

                                <center>
                                    <small style="color: ivory; font-size: 18px;">Modificar los detalles de un ${tipoFactura}</small><br/>                    
                                </center>


                                <div class="row mt-5">
                                    <div class="col" id="divDatosFactura">                                        
                                        <h1><span class="">Datos de la factura número: </span>${carrito.id_factura}</h1>
                                        <div class="row">
                                            <div class="col">
                                                <ul style="font-size: 25px;">
                                                    <li > Fecha: ${carrito.Fecha()}</li>
                                                    <li> Importe: ${carrito.calcularTotal()}</li>                                            
                                                    <li> Cliente: ${carrito.ObtenerNombreCliente()}</li>      
                                                    <li> Auto: ${carrito.marcaModeloDelAuto()}</li>
                                                </ul>                                                
                                            </div>                                            
                                        </div>
                                    </div>                                    
                                </div>

                                <div class="row mt-5" style="height: 100%;">
                                    <div class="col bg-secondary text-center" style="height: 100%;">
                                        <div id="divSinRepuestos" style="display: block; align-items: center;">
                                            <button type="button" class="btn btn-light" onclick="AgregarRepuestos()" id="btnEnviar" style="margin-top: 100px;">Agregar repuestos</button>
                                        </div>

                                        <%-- DIV DE REPUESTOS--%>
                                        <div id="divConRepuestos">
                                            <div class="row">
                                                <div class="col text-left">
                                                    <h1 class="mt-5" id="titRepuestos">Agregar un repuesto</h1>
                                                    <label class="mt-4" for="cboRepuesto">Repuesto</label>
                                                    <select class="form-control " id="cboRepuesto" name="idRepuesto" style="width: 100%;">    
                                                        <option value="0"> Seleccione una opción</option>>
                                                        <c:forEach items="${lstRepuestos}" var="rep">  
                                                            <option value="${rep.id_repuesto}" ${repuesto eq rep.id_repuesto?'selected':''}>${rep}</option>                            
                                                        </c:forEach>  
                                                    </select>

                                                </div>    
                                            </div>
                                            <div class="row">
                                                <div class="col text-left">
                                                    <label class="mt-3" for="spnCantidad">Cantidad </label><br/>
                                                    <input class="text-right" type="number" value="1" id="spnCantidad" name="cantidad" min="1" max="50">
                                                </div>
                                                <div class="col mt-4">
                                                    <a class="nav-link text-white" href="./AdministrarRepuestosServlet" >Administrar repuestos</a>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col mt-5">
                                                    <button type="button" class="btn btn-dark" onclick="EnviarRepuestos()" id="btnEnviar" >Aceptar</button>
                                                    <button type="button" class="btn btn-dark" onclick="CancelarRepuestos()" id="btnEnviar">Cancelar</button>
                                                </div>
                                            </div>
                                            <%-- listado de detalles de  REPUESTOS--%>
                                            <div class="row mt-5">
                                                <div class="col">
                                                    <table class="table table-dark">
                                                        <thead>
                                                            <tr>
                                                                <th scope="col">Repuesto</th>
                                                                <th scope="col">Cantidad</th>
                                                                <th scope="col">Modificar</th>
                                                                <th scope="col">Eliminar</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach items="${carrito.lstRepuesto}" var="dr" >
                                                                <tr>                                        
                                                                    <td>
                                                                        ${dr.r.repuesto}
                                                                    </td>
                                                                    <td>
                                                                        ${dr.cantidad}
                                                                    </td>
                                                                    <td>
                                                                        <a href="#" onclick="ModificarRepuesto(${dr.id_repuesto}, ${dr.cantidad}, ${dr.id_detalleRepuesto})"> Modificar </a>
                                                                    </td>
                                                                    <td>
                                                                        <a href="#" onclick="ABMrepuesto('eliminar',${dr.id_detalleRepuesto}, ${dr.cantidad}, ${dr.id_repuesto}, ${dr.id_factura}), ''"> Eliminar </a>
                                                                    </td>
                                                                </tr>      
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>                                                
                                            </div>
                                        </div>
                                        <script type="text/javascript">
                                            var divSinRepuestos = document.getElementById('divSinRepuestos');
                                            var divConRepuestos = document.getElementById('divConRepuestos');
                                            var divSinServicios = document.getElementById('divSinServicios');
                                            var divConServicios = document.getElementById('divConServicios');
                                            var titRepuestos = document.getElementById('titRepuestos');
                                            var cboRepuesto = document.getElementById('cboRepuesto');
                                            var spnCantidad = document.getElementById('spnCantidad');
                                            var cantRepuestos = ${carrito.lstRepuesto.size()};
                                            var cantServicios = ${carrito.lstServicio.size()};
                                            var tipoFactura = '${tipoFactura}';
                                            var id_factura = '${carrito.id_factura}'
                                            var action = 'agregar';
                                            var id_d = 0;

                                            function ModificarRepuesto(id_repuesto, cantidad, id_detalle) {
                                                console.log(id_repuesto, cantidad, id_detalle);
                                                cboRepuesto.value = id_repuesto;
                                                titRepuestos.innerHTML = 'Modificar repuesto';
                                                spnCantidad.value = cantidad;
                                                id_d = id_detalle;
                                                action = 'modificar';
                                            }

                                            function EnviarRepuestos() {
                                                var id_repuesto = cboRepuesto.value;
                                                var cantidad = spnCantidad.value;
                                                ABMrepuesto(action, id_d, cantidad, id_repuesto, id_factura, tipoFactura);
                                            }

                                            function ABMrepuesto(action, id_detalle, cantidad, id_repuesto, id_factura) {

                                                var conf = true;
                                                var ver = true;

                                                console.log(action);
                                                console.log(tipoFactura);
                                                if (action === 'eliminar') {
                                                    conf = confirm('Esta seguro de eliminar este detalle?');
                                                } else {
                                                    if (cboRepuesto.value == 0) {
                                                        alert('Debe seleccionar al menos un repuesto');
                                                        cboRepuesto.focus();
                                                        ver = false;
                                                    }
                                            <c:forEach items="${carrito.lstRepuesto}" var="d">
                                                    if (id_repuesto == '${d.id_repuesto}') {
                                                        var confirmar = confirm('El repuesto ya se encuentra incluído, quiere modificar la cantidad?');
                                                        if (confirmar) {
                                                            id_detalle = '${d.id_detalleRepuesto}';
                                                            if (action === 'agregar') {
                                                                action = 'modificar';
                                                            }
                                                        } else {
                                                            ver = false;
                                                        }
                                                    }
                                            </c:forEach>
                                                }
                                                if (conf && ver) {
                                                    $.ajax({
                                                        type: "POST",
                                                        url: "ModificarFacturaServlet",
                                                        data: {id_detalle: id_detalle, accion: action, id_repuesto: id_repuesto, cantidad: cantidad, id_factura: id_factura, tipoFactura: tipoFactura},
                                                        success: function (data, textStatus, jqXHR) {

                                                            if (data != 'true') {
                                                                if (action === 'modificar') {
                                                                    alert('Error en el sistema, No se pudo realizar la modificación');
                                                                }
                                                                if (action === 'eliminar') {
                                                                    alert('Error en el sistema, no se pudo eliminar el registro')
                                                                }
                                                                if (action === 'agregar') {
                                                                    alert('Error en el sistema, no se pudo agregar el registro')
                                                                }
                                                            } else {
                                                                alert('Se realizo correctamente la solicitud');
                                                                location.reload();
                                                            }
                                                        }
                                                        ,
                                                        error: function (jqXHR) {
                                                            console.log("error")
                                                        }
                                                    }
                                                    );
                                                }

                                            }

                                            function CancelarRepuestos() {
                                                cboRepuesto.value = 0;
                                                titRepuestos.innerHTML = "Agregar un repuesto";
                                                spnCantidad.value = 1;
                                                action = 'agregar';
                                                if (cantRepuestos === 0) {
                                                    divConRepuestos.style.display = "none";
                                                    divSinRepuestos.style.display = "inline";
                                                }
                                            }
                                        </script>

                                    </div>
                                    <div class="col bg-light text-center" style="height: 100%; color: black;">
                                        <div id="divSinServicios">
                                            <button type="button" class="btn btn-dark" id="cancelarButton" onClick="AgregarServicios()" style="margin-top: 100px;">Agregar servicios</button>
                                        </div>
                                        <div id="divConServicios">
                                            <div class="row">
                                                <div class="col text-left">
                                                    <h1 class="mt-5" id="titServicio">Agregar un servicio</h1>
                                                    <label class="mt-4" for="cboServicio">Servicio</label>
                                                    <select class="form-control " id="cboServicio" style="width: 100%;">    
                                                        <option value="0"> Seleccione una opción</option>>
                                                        <c:forEach items="${lstServicios}" var="s">  
                                                            <option value="${s.id_servicio}" ${servicio eq s.id_servicio?'selected':''}>${s}</option>                            
                                                        </c:forEach>  
                                                    </select>
                                                </div>    
                                            </div>
                                            <div class="row">                                                
                                                <div class="col mt-4">
                                                    <a class="nav-link" href="./AdministrarServicios">Administrar servicios</a>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col mt-5">
                                                    <button type="button" class="btn btn-dark" onclick="EnviarServicio()">Aceptar</button>
                                                    <button type="button" class="btn btn-dark" onclick="CancelarServicio()">Cancelar</button>
                                                </div>
                                            </div>
                                            <%-- listado de detalles de  REPUESTOS--%>
                                            <div class="row mt-5">
                                                <div class="col">
                                                    <table class="table table-dark">
                                                        <thead>
                                                            <tr>
                                                                <th scope="col">Servicio</th>
                                                                <th scope="col">Duración/precio(estimados)</th>
                                                                <th scope="col">Modificar</th>
                                                                <th scope="col">Cancelar servicio</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach items="${carrito.lstServicio}" var="ds" >
                                                                <tr>                                        
                                                                    <td>
                                                                        ${ds.s.servicio}
                                                                    </td>
                                                                    <td>
                                                                        Duración: ${ds.s.tiempo}, precio: ${ds.precioServicio}
                                                                    </td>
                                                                    <td>
                                                                        <a href="#" onclick="ModificarServicio(${ds.id_servicio}, ${ds.id_detalleServicio})"> Modificar </a>
                                                                    </td>
                                                                    <td> 
                                                                        <a href="#" onclick="ABMservicio('eliminar',${ds.id_detalleServicio}, ${ds.id_servicio}, ${ds.id_factura}), ''"> Eliminar </a>
                                                                    </td>
                                                                </tr>      
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>                                                
                                            </div>

                                        </div>                                        
                                    </div>
                                </div>
                                <script type="text/javascript">
                                    var divSinServicios = document.getElementById('divSinServicios');
                                    var divConServicios = document.getElementById('divConServicios');                                    
                                    var titServicio = document.getElementById('titServicio');
                                    var cboServicio = document.getElementById('cboServicio');
                                    var cantRepuestos = ${carrito.lstRepuesto.size()};
                                    var cantServicios = ${carrito.lstServicio.size()};
                                    var tipoFactura = '${tipoFactura}';
                                    var id_factura = '${carrito.id_factura}'
                                    var actionar = 'agregar';
                                    var id_d = 0;

                                    function ModificarServicio(id_servicio, id_detalle) {
                                        cboServicio.value = id_servicio;
                                        titRepuestos.innerHTML = 'Modificar servicio';
                                        id_d = id_detalle;
                                        actionar = 'modificar';
                                    }

                                    function EnviarServicio() {
                                        var id_repuesto = cboServicio.value;
                                        ABMservicio(actionar, id_d, id_repuesto, id_factura, tipoFactura);
                                    }
                                    
                                    function CancelarServicio() {
                                        cboRepuesto.value = 0;
                                        titRepuestos.innerHTML = "Agregar un servicio";
                                        actionar = 'agregar';
                                        if (cantServicio === 0) {
                                            divConRepuestos.style.display = "none";
                                            divSinRepuestos.style.display = "inline";
                                        }
                                    }

                                    function ABMservicio(action, id_detalle, id_servicio, id_factura) {
                                        var conf = true;
                                        var ver = true;
                                        if (action === 'eliminar') {
                                            conf = confirm('Esta seguro de eliminar este servicio?');
                                            if(!conf){
                                                ver = false;
                                            }
                                        } else {
                                            if (cboServicio.value == 0) {
                                                alert('Debe seleccionar al menos un servicio');
                                                cboServicio.focus();
                                                ver = false;
                                            }
                                    <c:forEach items="${carrito.lstServicio}" var="ds">
                                            if (id_servicio == '${ds.id_servicio}') {
                                                alert('El servicio ya se encuentra incluído');
                                                ver = false;
                                            }
                                    </c:forEach>
                                        }
                                        if (conf && ver) {                                            
                                            $.ajax({
                                                type: "POST",
                                                url: "ModificarFacturaServlet",
                                                data: {id_detalle_s: id_detalle, accion_s: action, id_servicio: id_servicio, id_factura_s: id_factura, tipoFactura_s: tipoFactura},
                                                success: function (data, textStatus, jqXHR) {

                                                    if (data != 'true') {
                                                        if (action === 'modificar') {
                                                            alert('Error en el sistema, No se pudo realizar la modificación');
                                                        }
                                                        if (action === 'eliminar') {
                                                            alert('Error en el sistema, no se pudo eliminar el registro')
                                                        }
                                                        if (action === 'agregar') {
                                                            alert('Error en el sistema, no se pudo agregar el registro')
                                                        }
                                                    } else {
                                                        alert('Se realizo correctamente la solicitud');
                                                        location.reload();
                                                    }
                                                }
                                                ,
                                                error: function (jqXHR) {
                                                    console.log("error")
                                                }
                                            }
                                            );
                                        }

                                    }

                                    

                                </script>    

                                <script type="text/javascript">
                                    var divSinRepuestos = document.getElementById('divSinRepuestos');
                                    var divConRepuestos = document.getElementById('divConRepuestos');
                                    var divSinServicios = document.getElementById('divSinServicios');
                                    var divConServicios = document.getElementById('divConServicios');
                                    var titRepuestos = document.getElementById('titRepuestos');

                                    window.onload = function acomodarDivs()
                                    {
                                        if (cantRepuestos == 0 && cantServicios == 0) {
                                            Swich(false, false);
                                        }
                                        if (cantRepuestos > 0 && cantServicios == 0) {
                                            Swich(true, false);
                                        }
                                        if (cantRepuestos == 0 && cantServicios > 0) {
                                            Swich(false, true);
                                        }
                                        if (cantRepuestos > 0 && cantServicios > 0) {
                                            Swich(true, true);
                                        }
                                    }

                                    function Swich(x, y) {
                                        if (x) {
                                            divConRepuestos.style.display = "inline";
                                            divSinRepuestos.style.display = "none";
                                        } else {
                                            divConRepuestos.style.display = "none";
                                            divSinRepuestos.style.display = "inline";
                                        }
                                        if (y) {
                                            divConServicios.style.display = "inline";
                                            divSinServicios.style.display = "none";
                                        } else {
                                            divConServicios.style.display = "none";
                                            divSinServicios.style.display = "inline";
                                        }
                                    }

                                    function AgregarRepuestos() {
                                        divConRepuestos.style.display = "inline";
                                        divSinRepuestos.style.display = "none";
                                        titRepuestos.innerHTML = "Agregar un repuesto"
                                    }

                                    function AgregarServicios() {
                                        divConServicios.style.display = "inline";
                                        divSinServicios.style.display = "none";
                                    }

                                </script>

                                <%--
                  <div class="row mt-5">
                                                                    <div class="col" style="color: ivory;">
                                                                        <c:choose>
                                                                            <c:when test = "${r == null}">
                                                                                <h2>Error! no existe la compra</h2>  
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <div class="row text-center">
                                                                                    <div class="col">
                                                                                        <h1>
                                                                                            <span style="color: antiquewhite; font-size: 30px;">PROVEEDOR:</span> ${r.nombreProveedor}
                                                                                        </h1>
                                                                                        <h4>
                                                                                            <span style="color: antiquewhite; font-size: 18px;">ID COMPRA:</span> "${r.id_reposicion}"
                                                                                        </h4>
                                                                                        <h4>
                                                                                            <span style="color: antiquewhite; font-size: 18px;">IMPORTE</span> <span id="spanImporte">${r.importe}</span>
                                                                                        </h4>

                                                    </div>
                                                </div>


                                                <form method="POST" action="./ReponerStockServlet" name="frmStock">
                                                    <input type="hidden" id="hiddenIdDetalle" name="idDetalle">
                                                    <div class="row mt-4 mb-5">
                                                        <div class="col">
                                                            <div class="row ">
                                                                <div class="col">
                                                                    <div class="row">
                                                                        <div class="col d-flex" style="align-items: center; text-align: center;">
                                                                            <h1 id="tit">Agregar un detalle</h1><br/>
                                                                        </div>                                                                            
                                                                    </div>
                                                                    <div class="row">
                                                                        <div class="col d-flex" style="align-items: flex-end; text-align: center;">
                                                                            <a href="./ReponerStockServlet" style="font-size: 20px; color: ivory; ">Volver a la administración de compras</a>   
                                                                        </div>                                                                            
                                                                    </div>
                                                                </div>
                                                                <div class="col" style="border-left: 1px solid white;">
                                                                    <label for="cboRepuesto">Repuesto</label>
                                                                    <select class="form-control" id="cboRepuesto" name="idRepuesto" style="width: 80%;">    
                                                                        <option value="0"> Seleccione una opción</option>>
                                                                        <c:forEach items="${lstRepuestos}" var="rep">  
                                                                            <option value="${rep.id_repuesto}" ${repuesto eq rep.id_repuesto?'selected':''}>${rep}</option>                            
                                                                        </c:forEach>  
                                                                    </select><br/>
                                                                    <label for="spnCantidad">Cantidad </label><br/>
                                                                    <input type="number" value="1" id="spnCantidad" name="cantidad" min="1" max="50">
                                                                </div>
                                                            </div>
                                                            <div class="row mt-5">
                                                                <div class="col"></div>
                                                                <div class="col">
                                                                    <button type="button" class="btn btn-dark" onclick="Enviar()" id="btnEnviar" style="width: 50%">Aceptar</button>
                                                                    <button type="button" class="btn btn-dark" id="cancelarButton" onClick="Cancelar()" style="width: 30%">Cancelar</button>
                                                                </div>
                                                            </div>

                                                        </div>
                                                    </div>
                                                </form>

                                                <div class="row mt-5">
                                                    <div class="col" style="border-bottom: 2px solid white; width: 90%;">
                                                        <h2>
                                                            Listado de detalles
                                                        </h2>
                                                    </div>
                                                </div>
                                                <div class="mb-5">
                                                    <small>
                                                        Listado de detalles de la compra, puede cambiar el repuesto y la cantidad dichos cambios impactaran en el stock.                                                              
                                                    </small></div>

                                                <table class="table table-dark">
                                                    <thead>
                                                        <tr>
                                                            <th scope="col">Repuesto</th>
                                                            <th scope="col">Cantidad</th>
                                                            <th scope="col">Modificar</th>
                                                            <th scope="col">Eliminar</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${r.detalles_reposicion}" var="repo" >
                                                            <tr>                                        
                                                                <td>
                                                                    ${repo.repuesto}
                                                                </td>
                                                                <td>
                                                                    ${repo.cantidad}
                                                                </td>
                                                                <td>
                                                                    <a href="#" onclick="Modificar(${repo.id_detalle}, ${repo.cantidad}, ${repo.id_repuesto})"> Modificar </a>
                                                                </td>
                                                                <td>
                                                                    <a href="#" onclick="Eliminar(${repo.id_detalle}, ${repo.cantidad}, ${repo.id_repuesto}, ${r.id_reposicion})"> Eliminar </a>
                                                                </td>
                                                            </tr>      
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                                <script>

                                                    var spnCantidad = document.getElementById('spnCantidad');
                                                    var cboRepuesto = document.getElementById('cboRepuesto');
                                                    var hiddenIdDetalle = document.getElementById('hiddenIdDetalle');
                                                    var tit = document.getElementById('tit');
                                                    var bandera = true;
                                                    function Swich(x) {
                                                    if (x) {
                                                    tit.innerHTML = "Agregar un detalle";
                                                    spnCantidad.value = 1;
                                                    bandera = true;
                                                    cboRepuesto.value = 0;
                                                    } else {
                                                    tit.innerHTML = "Modificar un detalle";
                                                    bandera = false;
                                                    }
                                                    }

                                                    function Modificar(id_detalle, cantidad, id_repuesto) {
                                                    console.log(id_repuesto);
                                                    cboRepuesto.value = id_repuesto;
                                                    spnCantidad.value = cantidad;
                                                    hiddenIdDetalle.value = id_detalle;
                                                    Swich(false);
                                                    }

                                                    function Enviar() {

                                                    if (cboRepuesto.value != 0) {
                                                    var id_reposicion = ${r.id_reposicion};
                                                    var id_detalle = 0;
                                                    var cant = spnCantidad.value;
                                                    var id_rep = cboRepuesto.value;
                                                    var accion = '';
                                                    var validarModificacionEnAlta = true;
                                                    if (bandera) {
                                                    accion = 'alta';
                                                    var repuestos = new Array();
                                                    <c:forEach items="${r.detalles_reposicion}" var="repo" >
                                                    repuestos.push('${repo.id_repuesto}');
                                                    </c:forEach>
                                                    for (id_repuesto of repuestos) {
                                                    if (id_repuesto === id_rep) {
                                                    var conf = confirm('Este repuesto ya se encuentra cargado entre los detalles. Desea cambiar la cantidad?');
                                                    if (confirm) {
                                                    accion = 'modificar';
                                                    id_detalle = id_repuesto;
                                                    validarModificacionEnAlta = true;
                                                    } else {
                                                    validarModificacionEnAlta = false;
                                                    }
                                                    }
                                                    }


                                                    } else {
                                                    accion = 'modificar';
                                                    id_detalle = hiddenIdDetalle.value;
                                                    }
                                                    console.log(id_reposicion, id_detalle, cant, id_rep, accion);
                                                    if (validarModificacionEnAlta) {
                                                    $.ajax({
                                                    type: "POST",
                                                            url: "ReponerStockServlet",
                                                            data: {idDetalle: id_detalle, cant: cant, id_rep: id_rep, accion: accion, id_reposicion: id_reposicion},
                                                            success: function (data, textStatus, jqXHR) {
                                                            if (data != 'true') {
                                                            alert('No se pudo realizar la solicitud')
                                                            } else if (accion === 'modificar') {
                                                            alert('Se ha modifico exitosamente el registro, el stock se ha actualizado');
                                                            location.reload();
                                                            } else {
                                                            alert('Se concreto el alta del detalle');
                                                            location.reload();
                                                            }
                                                            }
                                                    ,
                                                            error: function (jqXHR) {
                                                            console.log("error")
                                                            }
                                                    }
                                                    );
                                                    }
                                                    Swich(true);
                                                    } else {
                                                    alert('Debe seleccionar al menos un repuesto');
                                                    cboRepuesto.focus();
                                                    }

                                                    }

                                                    function Eliminar(id_detalle, cantidad, id_repuesto, id_reposicion) {

                                                    $.ajax({
                                                    type: "POST",
                                                            url: "ReponerStockServlet",
                                                            data: {idDetalle: id_detalle, accion: 'eliminar', id_rep: id_repuesto, cant: cantidad, id_reposicion: id_reposicion},
                                                            success: function (data, textStatus, jqXHR) {
                                                            if (data != 'true') {
                                                            alert('No se puede eliminar el único detalle de la transacción. Si desea borrar la transacción dirijase al sector de administración de compras')
                                                            } else {
                                                            alert('Se eliminó correctamente el registro, se restableció el stock');
                                                            location.reload();
                                                            }
                                                            }
                                                    ,
                                                            error: function (jqXHR) {
                                                            console.log("error")
                                                            }
                                                    }
                                                    );
                                                    }
                                                    function Cancelar() {
                                                    spnCantidad.value = 0;
                                                    document.getElementById("cboRepuesto").value = 0;
                                                    bandera = true;
                                                    Swich(false);
                                                    }

                                            </c:otherwise>
                                        </c:choose>
                                                </script>--%>
                            </div>                                    
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
