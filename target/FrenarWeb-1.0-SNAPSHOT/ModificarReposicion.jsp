<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reposición de stock</title>
    </head>
    <body>
        <div class="container-fluid" style="color: ivory;">
            <div class="row justify-content-center" style="height: 100%;">
                <div class="col-9">
                    <div class="container-fluid bg-secondary" >
                        <div class="row" style="height: 100%;">
                            <nav class="col-md-2 d-none d-md-block bg-light sidebar pt-5">
                                <c:import url="menu.jsp" >
                                </c:import>
                            </nav>
                            <div class="col-md-10 bg-secondary"  style="overflow: hidden; margin-top: 100px;">



                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="margin-top: 60px!important;">
                                    <h1 class="titulares">Modificar reposición</h1>
                                </div>
                                <center>
                                    <small style="color: ivory; font-size: 18px;">Modificar una compra</small><br/>                    
                                </center>
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

                                                </script>
                                            </c:otherwise>
                                        </c:choose>
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
