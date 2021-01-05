<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reposición de stock</title>
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
                            <div class="col-md-10 bg-secondary"  style="overflow: hidden; margin-top: 100px; color:ivory;">



                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="margin-top: 60px!important;">
                                    <h1 class="titulares">Reposición de stock</h1>
                                </div>
                                <center>
                                    <small style="color: ivory; font-size: 18px;">Elija un proveedor. Luego si desea puede filtrar por rubros la busqueda, para seleccionar el repuesto y finalmente elegir la cantidad.</small><br/>                    
                                    <small style="color: ivory; font-size: 18px;">En caso de que no se encuentre el proveedor o repuesto deseado, puede acceder al sector de administración para incorporar al listado lo necesario</small>
                                </center>


                                <div class="row" style="padding-top: 10px; color:ivory; font-size: 22px;">

                                    <%-- Columna 2 --%>
                                    <div class="col-md-4 order-md-2 mb-4" >
                                        <div class="border-bottom pt-0" style="margin-top: 60px!important; margin-bottom: 50px;">
                                            <h4>Repuestos seleccionados</h4>
                                        </div>
                                        <form method="POST" action="./ReponerStockServlet" name="frmStock">
                                            <div class="row" >
                                                <div class="col">
                                                    <div class="row " id="divContenidoCarrito" >
                                                        <span style="color: ivory; font-size: 30">
                                                            No existen elementos cargados
                                                        </span>
                                                    </div>
                                                    <div class="bg-dark" id="divDetalles">

                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>

                                    <%-- Columna 1 --%>
                                    <div class="col-md-8 order-md-1">
                                        <div class="border-bottom pt-0" style="margin-top: 60px!important; margin-bottom: 50px;">
                                            <h4>Cargar repuestos por proveedor</h4>
                                        </div>
                                        <center>
                                            <div class="row">
                                                <div class="col-8 mx-auto">
                                                    <%-- proveedor --%>
                                                    <div id="divProveedor">

                                                        <label for="cboProveedor" id="spanProveedor">Proveedores</label>                                
                                                        <select class="form-control mb-2 w-100" id="cboProveedor" name="idProveedor">                        
                                                            <option value = "0">Despliegue para seleccionar</option>     
                                                            <c:forEach items="${lstProveedor}" var="p">  
                                                                <option value="${p.id}" ${proveedor eq p.id?'selected':''}>${p}</option>                            
                                                            </c:forEach> 
                                                        </select>
                                                    </div>

                                                    <%-- rubro --%>
                                                    <label for="cboRubro" id="spnRubros">Rubros</label>
                                                    <select class="form-control mb-2" id="cboRubro" name="idRubro">                        
                                                        <option value = "0">Todos los rubros</option> 
                                                        <c:forEach items="${lstRubros}" var="r">  
                                                            <option value="${r.id}" ${rubro eq r.id?'selected':''}>${r}</option>                            
                                                        </c:forEach>  
                                                    </select>

                                                    <%-- producto --%>
                                                    <label for="cboRepuesto" id="spanRepuestos">Repuesto</label>
                                                    <select class="form-control" id="cboRepuesto" name="idRepuesto" >                        
                                                        <option value = "0">Despliegue para seleccionar repuesto</option>  
                                                    </select>
                                                </div>
                                            </div>      
                                            <%-- cantidad --%>
                                            <div class="row mt-4">
                                                <div class="col-8 mx-auto">                              
                                                    <label for="spnCantidad">Cantidad</label>
                                                    <input type="number" onblur="ValidarSpn()" class="form-control text-right" id="spnCantidad" placeholder="0" name="cantidad" min="1" max="50" value="1" style="width: 30% !important;">
                                                </div> 
                                            </div>                    

                                            <%-- botones --%>
                                            <div class="row mt-5" style="width: 70%;">
                                                <div class="col text-center">
                                                    <button type="button" class="btn btn-dark" onclick="Enviar()" id="btnEnviar" style="width: 50%">Registrar reposición</button>
                                                    <button type="button" class="btn btn-dark" id="cancelarButton" onClick="location.reload()" style="width: 30%">Cancelar</button>
                                                </div>
                                            </div> 
                                        </center>                    
                                    </div>     
                                </div>  

                                <%-- LISTADO --%>
                                <div>
                                    <div class="mt-5 mb-2" style="border-bottom: 1px solid ivory">
                                        <h1 style="color: ivory;">
                                            Listado de reposiciones realizadas
                                        </h1>
                                    </div>
                                    <small style="color: ivory; font-size: 18px;">
                                        Tenga en cuenta que al eliminar un registro el stock se actualizara acorde a la cantidad y a los productos del registro eliminado.
                                    </small>
                                    <div style="margin-top: 50px;">
                                        <c:choose>
                                            <c:when test = "${lstReposiciones.size() == 0}">
                                                <h2 style="color: ivory; text-align: center;">No se han generado reposiciones aún</h2>    

                                            </c:when>
                                            <c:otherwise>
                                                <table class="table table-dark">
                                                    <thead>
                                                        <tr>
                                                            <th scope="col">Número de reposición</th>
                                                            <th scope="col">Nombre del proveedor</th>
                                                            <th scope="col">Fecha de la reposicion</th>
                                                            <th scope="col">Detalles</th>                                    
                                                            <th scope="col">Eliminar</th>
                                                            <th scope="col">Modificar</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${lstReposiciones}" var="repo" >
                                                            <tr>                                        
                                                                <td>${repo.id_reposicion}</td>
                                                                <td>${repo.nombreProveedor}</td>
                                                                <td>${repo.FechaReposicion()}</td>
                                                                <td>${repo.Detalles()}</td>
                                                                <td><a href="#" onclick="Eliminar(${repo.id_reposicion})">Eliminar</a>
                                                                <td><a href="#" onclick="location.href= './ReponerStockServlet?id_repo='+${repo.id_reposicion}">Modificar</a>
                                                            </tr>      
                                                        </c:forEach>
                                                    </tbody>
                                                </table>

                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <script src="js/CombosUbicacion.js" type="text/javascript"></script>
                                <script src="js/ajaxReponerStock.js" type="text/javascript"></script>
                                <script src="js/verificarEliminarProveedor.js" type="text/javascript"></script>
                                <script type="text/javascript">
                                                                    spnCantidad = document.getElementById('spnCantidad');
                                                                    var cboProveedor = document.getElementById('cboProveedor');
                                                                    var cboRubro = document.getElementById('cboRubro');
                                                                    var cboRepuesto = document.getElementById('cboRepuesto');
                                                                    var divContenidoCarrito = document.getElementById('divContenidoCarrito');
                                                                    var divDetalles = document.getElementById('divDetalles');
                                                                    var spanProveedor = document.getElementById('spanProveedor');
                                                                    var spanRubros = document.getElementById('spnRubros');
                                                                    var spanRepuestos = document.getElementById('spanRepuestos');
                                                                    var detallesCarrito = new Array();
                                                                    var SeCargoElMismoDetalle = false;
                                                                    var nombreProveedor = null;
                                                                    cboProveedor.options[cboProveedor.selectedIndex].text;
                                                                    var idProveedor = 0;
                                                                    function validaciones() {
                                                                        if (cboProveedor.value == 0) {
                                                                            alert('Debe seleccionar un proveedor');
                                                                            cboProveedor.focus();
                                                                            return false;
                                                                        } else if (cboRepuesto.value == 0) {
                                                                            alert('Debe seleccionar un repuesto');
                                                                            cboRepuesto.focus();
                                                                            return false;
                                                                        } 
                                                                        return true;
                                                                    }


                                                                    function Enviar() {
                                                                        if (validaciones()) {
                                                                            if (idProveedor == 0) {
                                                                                //Arranca
                                                                                nombreProveedor = cboProveedor.options[cboProveedor.selectedIndex].text;
                                                                                idProveedor = cboProveedor.value;
                                                                                SwichProveedor(false);
                                                                            }
                                                                            ActualizarCarrito();
                                                                        }
                                                                    }

                                                                    function SwichProveedor(estado) {
                                                                        if (estado) {
                                                                            //Si esta prendido deja elegir
                                                                            cboProveedor.style.display = "inline";
                                                                            spanProveedor.innerHTML = "Proveedores";
                                                                        } else {
                                                                            cboProveedor.style.display = "none";
                                                                            spanProveedor.innerHTML = "Proveedor: " + nombreProveedor;
                                                                        }

                                                                    }

                                                                    function CancelarCompra() {
                                                                        var conf = confirm('Esta seguro de cancelar la compra?');
                                                                        if (conf) {
                                                                            SwichProveedor(true);
                                                                            cboProveedor.selectedIndex = 0;
                                                                            cboRepuesto.selectedIndex = 0;
                                                                            cboRubro.selectedIndex = 0;
                                                                            detallesCarrito = [];
                                                                            nombreProveedor = '';
                                                                            idProveedor = 0;
                                                                            divContenidoCarrito.innerHTML = "<span style=\"color: ivory; font-size: 30\">No existen elementos cargados</span>";
                                                                            divDetalles.innerHTML = "";
                                                                        }
                                                                    }

                                                                    function ActualizarCarrito() {

                                                                        for (d of detallesCarrito) {
                                                                            if (d.id_repuesto == cboRepuesto.value) {
                                                                                SeCargoElMismoDetalle = true;
                                                                            }
                                                                        }

                                                                        if (SeCargoElMismoDetalle) {
                                                                            //modificar
                                                                            var conf = confirm('Ya se encuentra solicitado el repuesto en la orden. Quiere cambiar a la cantidad solicitada?');
                                                                            if (conf) {

                                                                                for (d of detallesCarrito) {
                                                                                    if (d.id_repuesto == cboRepuesto.value) {
                                                                                        d.cantidad = spnCantidad.value;
                                                                                    }
                                                                                }
                                                                                SeCargoElMismoDetalle = false;
                                                                            }

                                                                        } else {
                                                                            //Cargar el carrito
                                                                            var dc = new DetalleCarrito(cboRepuesto.value, cboRepuesto.options[cboRepuesto.selectedIndex].text, spnCantidad.value, 0);
                                                                            detallesCarrito.push(dc);
                                                                        }

                                                                        //Actualizar el precio
                                                                        divDetalles.innerHTML = "";
                                                                        var filtrarPrecio = new Array();
                                    <c:forEach items="${lstRepuestos}" var="r">
                                                                        var de = new DetalleCarrito(${r.id_repuesto}, '${r.repuesto}', 0,${r.precio});
                                                                        filtrarPrecio.push(de);
                                    </c:forEach>

                                                                        for (var i = 0; i < detallesCarrito.length; i++) {
                                                                            for (var j = 0; j < filtrarPrecio.length; j++) {
                                                                                if (detallesCarrito[i].id_repuesto == filtrarPrecio[j].id_repuesto) {
                                                                                    detallesCarrito[i].precio_unitario = filtrarPrecio[j].precio_unitario;
                                                                                    detallesCarrito[i].nombre_repuesto = filtrarPrecio[j].nombre_repuesto;
                                                                                }
                                                                            }
                                                                        }
                                                                        //Sacar el importe parcial
                                                                        var importeParcial = 0;
                                                                        for (d of detallesCarrito) {
                                                                            importeParcial += d.precio_unitario;
                                                                        }

                                                                        //Llenar los divs
                                                                        divContenidoCarrito.innerHTML = "<input type=\"hidden\" value=\"" + idProveedor + "\" name=\"idProveedor\">\n\
                                                                                            <input type=\"hidden\" value=\"" + importeParcial + "\" name=\"importeParcial\">\n\
                                                                                            <div class=\"col bg-dark\" ><span class=\"text-muted\"> Proveedor: </span>\n\
                                                                                            <h4>" + nombreProveedor + "</h4><strong>\n\
                                                                                            <span class=\"text-muted\">Importe parcial: </span>" + importeParcial + "</strong><br/>\n\
                                                                                            <a href=\"#\" onclick=\"CancelarCompra()\">Cancelar compra</a><br/>\n\
                                                                                            <a href=\"#\" onclick=\"frmStock.submit()\">Confirmar</a></div>";
                                                                        for (d of detallesCarrito) {
                                                                            divDetalles.innerHTML += "  <input type=\"hidden\" value=\"" + d.cantidad + "\" name=\"cantidad\">\n\
                                                                                            <input type=\"hidden\" value=\"" + d.id_repuesto + "\" name=\"id_repuesto\">\n\
                                                                                            <input type=\"hidden\" value=\"" + d.precio_unitario + "\" name=\"precio_unitario\">\n\
                                                                                             <div class=\"row bg-dark\"><div class=\"col\" style=\"border: 2px solid gray;\">\n\
                                                                                                <div class=\"row\">\n\
                                                                                                    <div class=\"col\">\n\
                                                                                                        " + d.nombre_repuesto + "\n\
                                                                                                    </div>\n\
                                                                                                </div>\n\
                                                                                             <div class=\"row\" style=\"font-size: 16px;\">\n\
                                                                                                    <div class=\"col\">\n\
                                                                                                        <strong><span class=\"text-muted\" >Precio: </span>" + d.precio_unitario + "</strong><br/>\n\
                                                                                                        <strong><span class=\"text-muted\">Cantidad: </span>" + d.cantidad + "</strong>\n\
                                                                                                    </div>\n\</div>\n\
                                                                                            </div></div>";
                                                                        }

                                                                        cboRepuesto.selectedIndex = 0;
                                                                        cboRubro.selectedIndex = 0;
                                                                    }

                                                                    function ValidarSpn() {
                                                                        if (spnCantidad.value < 1 || spnCantidad.value > 50) {
                                                                            alert('Los valores tienen que ser entre 1 y 50');
                                                                            spnCantidad.focus();
                                                                        }
                                                                    }

                                                                    cboRubro.addEventListener('change', (event) => {
                                                                        var repuestos = new Array();
                                                                        var repuestosFiltrados = new Array();
                                    <c:forEach items="${lstRepuestos}" var="r">
                                                                        var repuesto = new Ubicacion(${r.id_repuesto}, '${r.repuesto}, precio: ${r.precio}, stock actual: ${r.stock}', ${r.r.id});
                                                                                repuestos.push(repuesto);
                                    </c:forEach>
                                                                                if (cboRubro.value == 0) {
                                                                                    repuestosFiltrados = repuestos;
                                                                                } else {
                                                                                    for (r of repuestos) {
                                                                                        if (r.id_padre == cboRubro.value) {
                                                                                            repuestosFiltrados.push(r);
                                                                                        }
                                                                                    }

                                                                                }
                                                                                repuestosFiltrados.sort(repuestosFiltrados.descripcion);
                                                                                addOptions(cboRepuesto, repuestosFiltrados);
                                                                            }
                                                                            );
                                                                            function Eliminar(id) {
                                                                                var conf = confirm('De eliminar esta compra, se restableceran las cantidades de stock pertinentes a los detalles ¿Está seguro de querer eliminar este registro?');
                                                                                if (conf) {
                                                                                    $.ajax({
                                                                                        type: "POST",
                                                                                        url: "ReponerStockServlet",
                                                                                        data: {eliminar: id},
                                                                                        success: function (data, textStatus, jqXHR) {
                                                                                            if (data != 'true') {
                                                                                                alert('No se puede eliminar esta marca debido a que de eliminarlo comprometería la integridad del sistema. Para más información contactese con el técnico.')
                                                                                            } else {
                                                                                                alert('Marca eliminada de forma exitosa');
                                                                                                location.reload();
                                                                                            }
                                                                                        },
                                                                                        error: function (jqXHR) {
                                                                                            console.log("error")
                                                                                        }
                                                                                    });
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
