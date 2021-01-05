<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administración de marcas y modelos de vehículos</title>
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
                                    <h1 class="titulares">Administración de marcas y modelos de vehículos</h1>
                                </div>
                                <span class="mt-2" style="font-size: 18px;">Alta o modificación y eliminación de marcas y modelos de vehículos.</span>


                                <center>
                                    <%-- form --%>
                                    <div class="row" style="color: ivory; margin-top: 60px">                
                                        <div class="col">
                                            <form method="POST" acction="./AdministrarMarcaModeloAuto" name="frmMarcaModelo">
                                                <div id="divRadio" style="font-size: 20px;">
                                                    <h1>Seleccione una opción</h1>
                                                    <input type="radio" onclick="funcionRbt(true)" id="rbtMarca" name="rbt">
                                                    <label for="rbtMarca">Ingresar nueva marca</label><br/>
                                                    <input type="radio" onclick="funcionRbt(true)" id="rbtModelo" name="rbt">
                                                    <label for="rbtModelo">Ingresar un nuevo modelo</label><br/>
                                                </div>
                                                <div id="divFormulario" style="display: none;">
                                                    <input type="hidden" id="hiddenId" name="hiddenId">
                                                    <input type="hidden" id="hiddenAccion" name="hiddenAccion">
                                                    <span id="spanAccion" style="font-size: 40px;"></span><br/>
                                                    <div id="divSelectMarcas">
                                                        <label id="lblCbo" class="mt-3" for="cboMarcas" style="text-align: left; font-size: 18px;">Seleccione la marca</label>
                                                        <select class="form-control" id="cboMarcas" name="id_marca" style="width: 50%;">                        
                                                            <option value="0">Despliegue para seleccionar</option>
                                                            <c:forEach items="${lstMarcas}" var="m">  
                                                                <option value="${m.id_marcaAuto}" ${m.marcaAuto eq m.id_marcaAuto?'selected':''}>${m}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <label class="mt-2" for="txtNuevoRegistro" style="text-align: left; font-size: 18px;">Ingrese el nuevo registro</label>
                                                    <input type="text" class="form-control mb-3" id="txtNuevoRegistro" placeholder="Ingrese nuevo registro" name="txtNuevoRegistro" style="width: 50%;">
                                                    <button type="button" class="btn btn-dark" onclick="Enviar()"style="width: 30%">Aceptar</button>
                                                    <button type="button" class="btn btn-dark" id="cancelarButton" onClick="Cancel()" style="width: 20%">Cancelar</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="row mt-5" style="border-top: 2px solid ivory;">
                                        <%-- Marcas --%>
                                        <div class="col" style="border-right: 2px solid ivory;">
                                            <h1>Marcas</h1>
                                            <c:if test="${fn:length('lstMarcas') <= 0}">
                                                <h4>No hay marcas cargadas en el sistema</h4>
                                            </c:if>
                                            <c:if test="${fn:length('lstMarcas') > 0}">
                                                <div class="row">
                                                    <div class="col mb-5">
                                                        <label for="txtParametroModelo">Parametrizar listado</label>
                                                        <input type="text" onchange="BuscarMarca()" id="txtParametroMarca" class="input-group" placeholder="Buscar marca" style="width: 70%"/>
                                                    </div>                                                    
                                                </div>
                                                <table class="table table-dark">
                                                    <thead>
                                                        <tr>
                                                            <th scope="col">Marca</th>
                                                            <th scope="col">Eliminar</th>
                                                            <th scope="col">Modificar</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="CuerpoTablaMarca">
                                                        <c:forEach items="${lstMarcas}" var="m" >
                                                            <tr>                                        
                                                                <td>${m.marcaAuto}</td>
                                                                <td><a href="#" onclick="EliminarMarca(${m.id_marcaAuto})" id="AnclaEliminar" >Eliminar</a></td>                                       
                                                                <td><a href="#" onclick="ModificarMarca(${m.id_marcaAuto}, '${m.marcaAuto}')">Modificar</a></td>
                                                            </tr>      
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </c:if>
                                        </div>
                                        <script type="text/javascript">
                                            function EliminarMarca(id) {
                                                $.ajax({
                                                    type: "POST",
                                                    url: "AdministrarMarcaModeloAuto",
                                                    data: {eliminarMarca: id},
                                                    success: function (data, textStatus, jqXHR) {
                                                        if (data != 'true') {
                                                            alert('No se puede eliminar esta marca debido a que de eliminarla comprometería la integridad del sistema. Para más información contactese con el técnico.')
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
                                        </script>


                                        <%-- modelos --%>
                                        <div class="col" style="border-left: 2px solid ivory;">
                                            <h1>Modelos</h1>
                                            <c:if test="${fn:length('lstModelos') <= 0}">
                                                <h4>No hay modelos cargados en el sistema</h4>
                                            </c:if>
                                            <c:if test="${fn:length('lstModelos') > 0}">
                                                <div class="row">
                                                    <div class="col mb-5">
                                                        <label for="txtParametroModelo">Parametrizar listado</label>
                                                        <input onchange="BuscarModelo()" type="text" id="txtParametroModelo" class="input-group" placeholder="Buscar modelo" style="width: 70%"/>
                                                    </div>                                                    
                                                </div>

                                                <table class="table table-dark" id="tablaModelos">
                                                    <thead>
                                                        <tr>
                                                            <th scope="col">Marca</th>
                                                            <th scope="col">Modelo</th>
                                                            <th scope="col">Eliminar</th>
                                                            <th scope="col">Modificar</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="CuerpoTablaModelo">
                                                        <c:forEach items="${lstModelos}" var="m" >
                                                            <tr>                                        
                                                                <td >${m.marca}</td>
                                                                <td>${m.modeloAuto}</td>
                                                                <td><a href="#" onclick="EliminarModelo(${m.id_modeloAuto})">Eliminar</a></td>                                       
                                                                <td><a href="#" onclick="ModificarModelo(${m.id_modeloAuto}, ${m.id_marcaAuto},'${m.modeloAuto}')">Modificar</a></td>
                                                            </tr>      
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </c:if>
                                        </div>
                                        <script type="text/javascript">
                                            function EliminarModelo(id) {
                                                $.ajax({
                                                    type: "POST",
                                                    url: "AdministrarMarcaModeloAuto",
                                                    data: {eliminarModelo: id},
                                                    success: function (data, textStatus, jqXHR) {
                                                        if (data != 'true') {
                                                            alert('No se puede eliminar este modelo debido a que de eliminarlo comprometería la integridad del sistema. Para más información contactese con el técnico.')
                                                        } else {
                                                            alert('Modelo eliminado de forma exitosa');
                                                            location.reload();
                                                        }
                                                    },
                                                    error: function (jqXHR) {
                                                        console.log("error")
                                                    }
                                                });
                                            }
                                        </script>

                                        <script src="js/CombosUbicacion.js" type="text/javascript"></script>
                                        <script type="text/javascript">
                                            var txtParametroModelo = document.getElementById('txtParametroModelo');
                                            var txtParametroMarca = document.getElementById('txtParametroMarca');
                                            var CuerpoTablaModelo = document.getElementById('CuerpoTablaModelo');
                                            var CuerpoTablaMarca = document.getElementById('CuerpoTablaMarca');

                                            function BuscarModelo() {
                                                $.ajax({
                                                    type: "POST",
                                                    url: "AdministrarMarcaModeloAuto",
                                                    data: {txtParametroModelo: txtParametroModelo.value},
                                                    success: function (data, textStatus, jqXHR) {

                                                        var primerExpresion = new RegExp('{.*?}', 'g');
                                                        var arreglo = data.match(primerExpresion);
                                                        var modelos = new Array();

                                                        for (a of arreglo) {
                                                            var expre = /{(\d+?), (.+?), (\d+?), (.+)}/i;
                                                            // var expre = /{(\d+?), (.+)}/i;   
                                                            var objeto = a.match(expre, "i");
                                                            var m = new Modelo(objeto[1], objeto[2], objeto[3], objeto[4]);
                                                            modelos.push(m);
                                                        }
                                                        CuerpoTablaModelo.innerHTML = '';
                                                        for (m of modelos) {

                                                            var fila = document.createElement("tr");
                                                            var celda1 = document.createElement("td");
                                                            var celda2 = document.createElement("td");
                                                            var celda3 = document.createElement("td");
                                                            var celda4 = document.createElement("td");

                                                            celda1.innerHTML = m.marca;
                                                            celda2.innerHTML = m.modelo;
                                                            celda3.innerHTML = "<a href=\"#\" onclick=\"EliminarModelo(" + m.id_modelo + ")\">Eliminar</a>";
                                                            celda4.innerHTML = "<a href=\"#\" onclick=\"ModificarModelo(" + m.id_modelo + ", " + m.id_marca + ", '" + m.modelo + "')\">Modificar</a>";

                                                            fila.appendChild(celda1);
                                                            fila.appendChild(celda2);
                                                            fila.appendChild(celda3);
                                                            fila.appendChild(celda4);

                                                            CuerpoTablaModelo.appendChild(fila);
                                                        }
                                                    },
                                                    error: function (jqXHR) {
                                                        console.log("error")
                                                    }
                                                });
                                            }
                                            function BuscarMarca() {
                                                $.ajax({
                                                    type: "POST",
                                                    url: "AdministrarMarcaModeloAuto",
                                                    data: {txtParametroMarca: txtParametroMarca.value},
                                                    success: function (data, textStatus, jqXHR) {

                                                        var primerExpresion = new RegExp('{.*?}', 'g');
                                                        var arreglo = data.match(primerExpresion);
                                                        var marcas = new Array();

                                                        for (a of arreglo) {
                                                            var expre = /{(\d+?), (.+)}/i;
                                                            var objeto = a.match(expre, "i");
                                                            var m = new Marca(objeto[1], objeto[2]);
                                                            marcas.push(m);
                                                        }

                                                        CuerpoTablaMarca.innerHTML = '';

                                                        for (m of marcas) {

                                                            var fila = document.createElement("tr");
                                                            var celda1 = document.createElement("td");
                                                            var celda2 = document.createElement("td");
                                                            var celda3 = document.createElement("td");

                                                            celda1.innerHTML = m.marca;
                                                            celda2.innerHTML = "<a href=\"#\" onclick=\"EliminarMarca(" + m.id_marca + ")\">Eliminar</a>";
                                                            celda3.innerHTML = "<a href=\"#\" onclick=\"ModificarMarca(" + m.id_marca + ", '" + m.marca + "')\">Modificar</a>";

                                                            fila.appendChild(celda1);
                                                            fila.appendChild(celda2);
                                                            fila.appendChild(celda3);

                                                            CuerpoTablaMarca.appendChild(fila);
                                                        }
                                                    },
                                                    error: function (jqXHR) {
                                                        console.log("error")
                                                    }
                                                });

                                            }
                                        </script>
                                    </div>
                                </center>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            var divRadio = document.getElementById('divRadio');
            var divFormulario = document.getElementById('divFormulario');
            var rbtMarca = document.getElementById('rbtMarca');
            var rbtModelo = document.getElementById('rbtModelo');
            var spanAccion = document.getElementById('spanAccion');
            var txtNuevoRegistro = document.getElementById('txtNuevoRegistro');
            var hiddenId = document.getElementById('hiddenId');
            var hiddenAccion = document.getElementById('hiddenAccion');
            var divSelectMarcas = document.getElementById('divSelectMarcas');
            var cboMarcas = document.getElementById('cboMarcas');
            var lblCbo = document.getElementById('lblCbo');

            function ModificarMarca(id, nombre) {
                spanAccion.innerHTML = "Modificar marca";
                cboMarcas.style.display = "none";
                lblCbo.style.display = "none";
                txtNuevoRegistro.value = nombre + '';
                hiddenId.value = id;
                hiddenAccion.value = 'modificarMarca';
                SwichFormulario(true);
            }

            function ModificarModelo(id, id_marca, nombre) {
                console.log(id+' '+ id_marca+ ' '+nombre);
                
                spanAccion.innerHTML = "Modificar nombre del modelo";
                divSelectMarcas.style.display ="inline";
                cboMarcas.value = id_marca;
                txtNuevoRegistro.value = nombre + '';
                hiddenId.value = id;
                hiddenAccion.value = 'modificarModelo';
                SwichFormulario(true);
            }

            function funcionRbt() {
                if (rbtMarca.checked) {
                    spanAccion.innerHTML = "Alta de marca";
                    hiddenAccion.value = 'altaMarca';
                    divSelectMarcas.style.display = 'none';
                    cboMarcas.style.display = "none";
                    lblCbo.style.display = "none";
                }
                if (rbtModelo.checked) {                    
                    divSelectMarcas.style.display = 'inline';
                    spanAccion.innerHTML = "Alta de modelo";
                    hiddenAccion.value = 'altaModelo';
                }
                hiddenId.value = 0;
                SwichFormulario(true);
            }

            function Cancel() {
                $('#cboMarcas').val('0').change();
                txtNuevoRegistro.value = "";
                hiddenAccion.value = "";
                hiddenId.value = 0;
                SwichFormulario(false);
            }

            function Enviar() {
                var x = true;
                if (txtNuevoRegistro.value == '') {
                    alert('No puede haber texto vacío');
                    txtNuevoRegistro.focus();
                    x = false;
                }
                if (cboMarcas.value == 0 && hiddenAccion.value == 'altaModelo') {
                    alert('Debe seleccionar la marca');
                    cboMarcas.focus();
                    x = false;
                }
                if (x) {
                    frmMarcaModelo.submit();
                }
            }

            function SwichFormulario(x) {
                if (x) {
                    divRadio.style.display = "none";
                    divFormulario.style.display = "inline";
                } else {
                    divRadio.style.display = "inline";
                    divFormulario.style.display = "none";
                    rbtMarca.checked = false;
                    rbtModelo.checked = false;
                }
            }

        </script>
    </body>
</html>
