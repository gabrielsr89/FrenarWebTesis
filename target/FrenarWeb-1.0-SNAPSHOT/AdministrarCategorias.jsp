<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administración de categorías de servicio</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row justify-content-center">
                <div class="col-9">
                    <div class="container-fluid bg-secondary" >
                        <div class="row" style="height: 100%;">
                            <nav class="col-md-2 d-none d-md-block bg-light sidebar pt-5">
                                <c:import url="menu.jsp" >
                                </c:import>
                            </nav>
                            <div class="col-md-10 bg-secondary"  style="overflow: hidden; margin-top: 100px; color:ivory;">

                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="margin-top: 60px!important;">
                                    <h1 class="titulares">Administración de categorías de servicio</h1>
                                </div>
                                <span class="mt-2" style="font-size: 18px;">Alta, modificación o eliminación de categorías de servicio</span>

                                <center>
                                    <%-- form --%>
                                    <div class="row" style="color: ivory; margin-top: 60px">                
                                        <div class="col">
                                            <form method="POST" acction="./AdministrarCategoriasServlet" name="frmCategorias">

                                                <div id="divFormulario">
                                                    <input type="hidden" id="hiddenId" name="hiddenId">
                                                    <input type="hidden" id="hiddenAccion" name="hiddenAccion">
                                                    <span id="spanAccion" style="font-size: 40px;">Alta de categoría</span><br/>
                                                    <label class="mt-2" for="txtNuevoRegistro" style="text-align: left; font-size: 18px;">Ingrese el nuevo registro    </label>
                                                    <input type="text" class="form-control mb-3" id="categoria" placeholder="Ingrese nuevo registro" name="categoria" style="width: 50%;">
                                                    <button type="button" class="btn btn-dark" onclick="Enviar()"style="width: 30%">Aceptar</button>
                                                    <button type="button" class="btn btn-dark" id="cancelarButton" onClick="Cancel()" style="width: 20%">Cancelar</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="row mt-5">
                                        <%-- listado --%>
                                        <div class="col">
                                            <h1 class="mb-5">Listado de categorías</h1>
                                            <c:if test="${fn:length('lstCategorias') <= 0}">
                                                <h4>No hay categorías cargadas en el sistema</h4>
                                            </c:if>
                                            <c:if test="${fn:length('lstCategorias') > 0}">
                                                <table class="table table-dark">
                                                    <thead>
                                                        <tr>
                                                            <th scope="col">Categoría</th>
                                                            <th scope="col">Eliminar</th>
                                                            <th scope="col">Modificar</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${lstCategorias}" var="c" >
                                                            <tr>                                        
                                                                <td>${c.categoria}</td>
                                                                <td><a href="#" onclick="Eliminar(${c.id_categoria})">Eliminar</a></td>                                       
                                                                <td><a href="#" onclick="Modificar(${c.id_categoria}, '${c.categoria}')">Modificar</a></td>
                                                            </tr>      
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </c:if>
                                        </div>
                                        <script type="text/javascript">
                                            var spanAccion = document.getElementById('spanAccion');
                                            var categoria = document.getElementById('categoria');
                                            var hiddenId = document.getElementById('hiddenId');
                                            var hiddenAccion = document.getElementById('hiddenAccion');
                                            hiddenId.value = 0;

                                            function Modificar(id, nombre) {
                                                spanAccion.innerHTML = "Modificar categoría";
                                                categoria.value = nombre + '';
                                                hiddenId.value = id;
                                                hiddenAccion.value = 'modificar';
                                            }
                                            function Cancel() {
                                                spanAccion.innerHTML = "Alta de categoría";
                                                categoria.value = "";
                                                hiddenAccion.value = "";
                                                hiddenId.value = 0;
                                            }
                                            function Enviar() {
                                                if (categoria.value == '') {
                                                    alert('No puede haber texto vacío');
                                                    categoria.focus();
                                                } else {
                                                    frmCategorias.submit();
                                                }
                                            }
                                            
                                            function Eliminar(id) {
                                                $.ajax({
                                                    type: "POST",
                                                    url: "AdministrarCategoriasServlet",

                                                    data: {eliminar: id},

                                                    success: function (data, textStatus, jqXHR) {
                                                        if (data != 'true') {
                                                            alert('No se puede eliminar esta categoría debido a que de eliminarla comprometería la integridad del sistema. Para más información contactese con el técnico.')
                                                        } else {
                                                            alert('Categoría eliminada de forma exitosa');
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
    </body>
</html>
