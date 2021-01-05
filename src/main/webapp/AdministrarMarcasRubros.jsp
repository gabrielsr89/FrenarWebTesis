<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administración de marcas y rubros de repuestos</title>
    </head>
    <body>
        <div class="container-fluid" style="color: ivory;">
            <div class="row justify-content-center">
                <div class="col-9">
                    <div class="container-fluid bg-secondary" >
                        <div class="row" style="height: 100%;">
                            <nav class="col-md-2 d-none d-md-block bg-light sidebar pt-5">
                                <c:import url="menu.jsp" >
                                </c:import>
                            </nav>
                            <div class="col-md-10"  style="overflow: hidden; margin-top: 100px;">

                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="margin-top: 60px!important;">
                                    <h1 class="titulares">Administración de marcas y rubros de repuestos</h1>
                                </div>
                                <span class="mt-2" style="font-size: 18px;">Alta o modificación y eliminación de rubros y marcas.</span>


                                <center>
                                    <%-- form --%>
                                    <div class="row" style="color: ivory; margin-top: 60px">                
                                        <div class="col">
                                            <form method="POST" acction="./AdministrarMarcasRubrosServlet" name="frmMarcaRubro">
                                                <div id="divRadio" style="font-size: 20px;">
                                                    <h1>Seleccione una opción</h1>
                                                    <input type="radio" onclick="funcionRbt(true)" id="rbtMarca" name="rbt">
                                                    <label for="rbtMarca">Ingresar nueva marca</label><br/>
                                                    <input type="radio" onclick="funcionRbt(true)" id="rbtRubro" name="rbt">
                                                    <label for="rbtRubro">Ingresar nuevo rubro</label><br/>
                                                    <div class="mt-3">
                                                        <a href="./AdministrarRepuestosServlet" style="font-size: 25px; color: ivory;">Volver a administrar repuestos</a>
                                                    </div>
                                                </div>
                                                <div id="divFormulario" style="display: none;">
                                                    <input type="hidden" id="hiddenId" name="hiddenId">
                                                    <input type="hidden" id="hiddenAccion" name="hiddenAccion">
                                                    <span id="spanAccion" style="font-size: 40px;"></span><br/>
                                                    <label class="mt-2" for="txtNuevoRegistro" style="text-align: left; font-size: 18px;">Ingrese el nuevo registro    </label>
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
                                                <table class="table table-dark">
                                                    <thead>
                                                        <tr>
                                                            <th scope="col">Marca</th>
                                                            <th scope="col">Eliminar</th>
                                                            <th scope="col">Modificar</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${lstMarcas}" var="m" >
                                                            <tr>                                        
                                                                <td>${m.marcaRepuesto}</td>
                                                                <td><a href="#" onclick="EliminarMarca(${m.id_marcaRepuesto})" id="AnclaEliminar" >Eliminar</a></td>                                       
                                                                <td><a href="#" onclick="ModificarMarca(${m.id_marcaRepuesto}, '${m.marcaRepuesto}')">Modificar</a></td>
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
                                                    url: "AdministrarMarcasRubrosServlet",

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


                                        <%-- Rubros --%>
                                        <div class="col" style="border-left: 2px solid ivory;">
                                            <h1>Rubros</h1>
                                            <c:if test="${fn:length('lstRubros') <= 0}">
                                                <h4>No hay rubros cargados en el sistema</h4>
                                            </c:if>
                                            <c:if test="${fn:length('lstRubros') > 0}">
                                                <table class="table table-dark">
                                                    <thead>
                                                        <tr>
                                                            <th scope="col">Rubro</th>
                                                            <th scope="col">Eliminar</th>
                                                            <th scope="col">Modificar</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${lstRubros}" var="r" >
                                                            <tr>                                        
                                                                <td>${r.rubro}</td>
                                                                <td><a href="#" onclick="EliminarRubro(${r.id})">Eliminar</a></td>                                       
                                                                <td><a href="#" onclick="ModificarRubro(${r.id}, '${r.rubro}')">Modificar</a></td>
                                                            </tr>      
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </c:if>
                                        </div>
                                        <script type="text/javascript">
                                            function EliminarRubro(id) {
                                                $.ajax({
                                                    type: "POST",
                                                    url: "AdministrarMarcasRubrosServlet",

                                                    data: {eliminarRubro: id},

                                                    success: function (data, textStatus, jqXHR) {
                                                        if (data != 'true') {
                                                            alert('No se puede eliminar este rubro debido a que de eliminarlo comprometería la integridad del sistema. Para más información contactese con el técnico.')
                                                        } else {
                                                            alert('Rubro eliminado de forma exitosa');
                                                            location.reload();
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
            var rbtRubro = document.getElementById('rbtRubro');
            var spanAccion = document.getElementById('spanAccion');
            var txtNuevoRegistro = document.getElementById('txtNuevoRegistro');
            var hiddenId = document.getElementById('hiddenId');
            var hiddenAccion = document.getElementById('hiddenAccion');

            function ModificarMarca(id, nombre) {
                spanAccion.innerHTML = "Modificar nombre de la marca";
                txtNuevoRegistro.value = nombre + '';
                hiddenId.value = id;
                hiddenAccion.value = 'modificarMarca';
                SwichFormulario(true);
            }
            function ModificarRubro(id, nombre) {
                spanAccion.innerHTML = "Modificar nombre del rubro";
                txtNuevoRegistro.value = nombre + '';
                hiddenId.value = id;
                hiddenAccion.value = 'modificarRubro';
                SwichFormulario(true);
            }

            function funcionRbt() {
                if (rbtMarca.checked) {
                    spanAccion.innerHTML = "Alta de marca";
                    hiddenAccion.value = 'altaMarca';
                }
                if (rbtRubro.checked) {
                    spanAccion.innerHTML = "Alta de rubro";
                    hiddenAccion.value = 'altaRubro';
                }
                hiddenId.value = 0;
                SwichFormulario(true);
            }

            function Cancel() {
                txtNuevoRegistro.value = "";
                hiddenAccion.value = "";
                hiddenId.value = 0;
                SwichFormulario(false);
            }

            function Enviar() {
                if (txtNuevoRegistro.value == '') {
                    alert('No puede haber texto vacío');
                    txtNuevoRegistro.focus();
                } else {
                    frmMarcaRubro.submit();
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
                    rbtRubro.checked = false;
                }
            }

        </script>
    </body>
</html>
