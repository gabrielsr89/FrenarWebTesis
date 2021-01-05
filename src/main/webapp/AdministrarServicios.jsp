
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<html>
    <head>
        <meta http-equiv=?Content-Type? content=?text/html; charset=UTF-8?/>        
        <title>Administrar servicios</title>
        <style type="text/css"> html,body{ margin:0px; height:100%; } </style>
    </head>
    <body id="fondomain">


        <div class="container-fluid">
            <div class="row justify-content-center">
                <div class="col-9">
                    <div class="container-fluid bg-secondary" >
                        <div class="row" style="height: 100%;">
                            <nav class="col-md-2 d-none d-md-block bg-light sidebar pt-5">
                                <c:import url="menu.jsp" >
                                </c:import>
                            </nav>
                            <div class="col-md-10"  style="overflow: hidden;">



                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="padding-top: 100px!important">
                                    <h1 class="titulares">Administrar servicios</h1>
                                </div>
                                <div class="row">
                                    <div class="col" style="margin-top: 20px; margin-bottom: 30px; color: ivory; text-align: center; display: inline-grid; margin-left: 50%; transform: translateX(-50%)">
                                        <span>Puede crear un nuevo servicio o seleccionar a un servicio del listado para realizar ajustes sobre el mismo.</span>
                                        <span>En el caso que se requiera crear un servicio para un caso particular debe seleccionar en categorías "Otros arreglos".</span>
                                    </div>
                                </div>

                                <%--#################--FORMULARIO--##################--%>
                                <form method="POST" action="./AdministrarServicios" name="frmServicios">
                                    <c:choose>
                                        <c:when test="${s != null}">
                                            <input type="hidden" name="id_servicio" value="${s.id_servicio}">                        
                                        </c:when>
                                    </c:choose>
                                    <div class="row">
                                        <div class="col" >
                                            <div class="row">
                                                <div class="col">
                                                    <label class="listadoHeader" for="txtServicio">Nombre del servicio</label>
                                                    <input class="form-control" type="text" placeholder="Hasta 100 caracteres." id="txtServicio" name="servicio" style="width: 80%;">                        
                                                </div>
                                            </div>
                                            <div class="row mt-3">
                                                <div class="col ">

                                                    <label class="listadoHeader" for="cboCategoria">Elegir categoría del servicio</label>                               
                                                    <select class="form-control" id="cboCategoria" name="id_categoria" style="width: 80%; margin-right: 10px;">                        
                                                        <option value = "0">Despliegue para seleccionar</option>
                                                        <c:forEach items="${lstCategorias}" var="c">  
                                                            <option value="${c.id_categoria}" ${categoria eq c.id_categoria?'selected':''}>${c}</option>                            
                                                        </c:forEach>                     
                                                    </select>
                                                    <a href="./AdministrarCategoriasServlet" style="color: ivory;">Administrar categorías de servicios</a>

                                                </div>
                                            </div>
                                            <div class="row mt-3">
                                                <div class="col">
                                                    <label class="listadoHeader" for="spnMinutos">Duración en minutos del servicio</label>
                                                    <input class="form-control text-right" type="number" value="1" id="spnMinutos" name="tiempo" min="1" style="width: 80%;">                        
                                                </div>
                                            </div>

                                        </div>
                                        <div class="col">
                                            <div class="row">
                                                <div class="col">
                                                    <label class="listadoHeader" for="descripcion">Descripción del servicio</label>
                                                    <textarea class="form-control" name="descripcion" id="descripcion" placeholder="La descripción puede ser de hasta 500 carácteres" style="resize: none; height: 211px; width: 80%;"></textarea>
                                                </div>
                                            </div>

                                            <div class="row mt-3">
                                                <div class="col text-center">
                                                    <input type="button" onclick="Enviar()" class="btn btn-dark mb-2 " value="Aceptar">
                                                    <input type="button" class="btn btn-dark mb-2 " value="Cancelar" onclick="location.href = './AdministrarServicios'">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>

                                <script type="text/javascript">
                                    var txtServicio = document.getElementById('txtServicio');
                                    var cboCategoria = document.getElementById('cboCategoria');
                                    var spnMinutos = document.getElementById('spnMinutos');
                                    var descripcion = document.getElementById('descripcion');
                                    function Enviar(){
                                    var x = true;
                                    if (txtServicio.value == ''){
                                    txtServicio.focus();
                                    x = false;
                                    }
                                    if (cboCategoria.value == 0){
                                    cboCategoria.focus();
                                    x = false;
                                    }
                                    if (descripcion.value.length == 0 || descripcion.value.length > 500){
                                    descripcion.focus();
                                    x = false;
                                    }

                                    if (x){
                                    frmServicios.submit();
                                    } else{
                                    alert('Verifique los campos')
                                    }
                                    }


                                    /*------------PARA QUE CARGUEN LOS INPUT AL MODIFICAR------------*/
                                    var accion = false;
                                    <c:choose>
                                        <c:when test="${s != null}">
                                    accion = true;
                                        </c:when>
                                    </c:choose>
                                    if (accion) {
                                    document.getElementById('txtServicio').value = '${s.servicio}';
                                    document.getElementById('cboCategoria').value = 0${s.id_categoria};
                                    document.getElementById('spnMinutos').value = ${s.tiempo};
                                    document.getElementById('descripcion').innerHTML = '${s.descripcion}';
                                    }

                                </script>
                                <%--#################--LISTADO--##################--%>
                                <div class="row">
                                    <div class="col">
                                        <c:choose>
                                            <c:when test = "${lstServicio.size() == 0}">

                                                <h2 style="color: ivory; text-align: center;">No tenemos descripción para este servicio en particular</h2>     

                                            </c:when>
                                            <c:otherwise>
                                                <table class="table-striped table-sm mt-5 bg-dark" style="position: static !important; margin-left: 50%; transform: translateX(-50%); width: 90%; border-radius: 30px;">
                                                    <thead class="listadoHeader">
                                                        <tr>
                                                            <th>Categoría</th>
                                                            <th>Servicio</th>
                                                            <th>Descripción</th>
                                                            <th>Demora aproximada</th>
                                                            <th>Precio de mano de obra</th>
                                                            <th>Modificar servicio</th>
                                                            <th>Eliminar servicio</th>                                        
                                                        </tr>
                                                    </thead>
                                                    <tbody class="listadoResto">
                                                        <c:forEach items="${lstServicios}" var="lst" >
                                                            <tr style="text-align: center">
                                                                <td>${lst.categoria.categoria}</td>
                                                                <td>${lst.servicio}</td>
                                                                <td>${lst.descripcion}</td>
                                                                <td>${lst.tiempo} minutos</td>
                                                                <td>$${lst.precio}</td>
                                                                <td><a href="./AdministrarServicios?id_servicio=${lst.id_servicio}">Modificar servicio</a></td>
                                                                <td><a href="./EliminarServiciosServlet?id_servicio=${lst.id_servicio}">Eliminar servicio</a></td>                                       

                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </c:otherwise>
                                            </c:choose>
                                        </table>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col text-center mt-3">
                                        <span class="text-white" style="position:static; padding-bottom:  30px; left: 50%; transform: translateX(-50%);">© 2020</span>
                                    </div>
                                </div>
                            </div> 
                        </div>
                    </div>
                    <c:import url="bootstrap.jsp"></c:import>
                </div>                
            </div>            
        </div>

    </body>
</html>
