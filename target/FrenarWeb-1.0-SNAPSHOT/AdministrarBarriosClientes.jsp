<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administración de ubicaciones</title>
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
                            <div class="col-md-10 bg-secondary"  style="overflow: hidden; margin-top: 100px; color: ivory;">


                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="margin-top: 60px!important">
                                    <h1 class="titulares">Confirmar barrios nuevos</h1>
                                </div>
                                <div style="margin-top: 0px; margin-bottom: 30px;line-height: 15px; color: ivory;  font-size:18px;  ">
                                    <small>
                                        Listado de barrios incorporados al sistema por clientes. Confirme si lo ingresado por el cliente es verídico o si ya estaba cargado previamente en el sistema y a la hora de inscribirse en el sistema el cliente no lo encontró
                                    </small>
                                </div>
                                <form method="POST" accion="./AdministrarUbicacionesServlet" style="color:ivory" name="frmClientesBarrio">
                                    <c:choose>
                                        <c:when test = "${perkins != null}">
                                            <input type="hidden" value="${perkins.id}" name="idPerkins">
                                            <input type="hidden" value="${perkins.barrio.id_barrio}" name="idBarrio">
                                            <input type="hidden" value="${perkins.localidad.id_localidad}" name="idLocalidad">
                                            <input type="hidden" value="${perkins.barrio.barrio}" name="nombreBarrio">
                                        </c:when>   
                                    </c:choose>
                                    <div class="row">
                                        <div class="col">
                                            <c:choose>
                                                <c:when test = "${perkins == null}">
                                                    <span style="font-size: 30px;">
                                                        Seleccione un barrio del listado
                                                    </span>
                                                </c:when>   
                                                <c:otherwise>
                                                    <span style="font-size: 30px;">${perkins.barrio.barrio} <small>→ Barrio ingresado por el cliente</small></span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>                    
                                    </div>
                                    <div id="divForm" style="display: none;">
                                        <div class="row" style="position: relative;">
                                            <div class="col" style="margin-top: 20px;">
                                                <%--BUSCADOR--%>
                                                <label for="buscador"><small>Verifique en el desplegable si el barrio ingresado existe en nuestro sistema</small></label>
                                                <div class="input-group" style="width: 58%;">
                                                    <input type="text" class="form-control" id="buscador" onblur="Buscador()" placeholder="Buscar por nombre de barrio" name="buscador"> 
                                                    <div class="input-group-append">
                                                        <button type="button" class="btn btn-dark" onclick="Buscador()" style="width: 150px;">Buscar</button>
                                                    </div>
                                                </div>
                                            </div>
                                            <%--CBO--%>
                                            <div class="col" style="position:absolute; bottom: 0px; width: 40%; right: 10px;">
                                                <label for="cboBarriosFiltrados">Listado de barrios existentes</label>
                                                <select class="form-control" name="cbo" id="cboBarriosFiltrados">
                                                    <option value="-1">No hay busquedas vigentes</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row mt-3">
                                            <div class="col">
                                                <%--RADIOS--%>
                                                <div class="form-check">
                                                    <input class="form-check-input"  type="radio"  name="rbtConfirmar" id="rbtBarrioCorrecto" value="option1">
                                                    <label class="form-check-label" for="rbtBarrioCorrecto" style="fotn-size: 25px; color:#9FFF33">
                                                        El barrio ingresado por el cliente es correcto <small style="color: ivory;"> No existe en nuestra base de datos y los datos son veridicos, por lo que agregamos al sistema el barrio en cuestión.</small>
                                                    </label>
                                                </div>
                                                <div class="form-check">
                                                    <input class="form-check-input"  type="radio" name="rbtConfirmar" id="rbtBarrioOtro" value="option2">
                                                    <label class="form-check-label" for="rbtBarrioOtro" style="fotn-size: 25px; color: #FF7433">
                                                        El barrio ingresado por el cliente es falso <small style="color: ivory;">Al cliente en cuestión no se le asigna barrio de momento</small>
                                                    </label>
                                                </div>
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="rbtConfirmar" id="rbtBarrioCombo" value="option3">
                                                    <label class="form-check-label" for="rbtBarrioCombo" style="fotn-size: 25px; color: #33FFB5">
                                                        El barrio ingresado existe en nuestra base de datos <small style="color: ivory;">El cliente no encontró entre los barrios disponibles su barrio en el momento de su inscripción, por lo que borramos el barrio ingresado por él y cargamos el barrio que figura en el desplegable, el cual teníamos cargado en nuestra base de datos, para salvaguardar la integridad de los datos.</small>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mt-3">
                                            <div class="col">
                                                <input type="button" onclick="Enviar()" class="btn btn-dark" value="Confirmar opcion" id="aceptar">
                                                <input type="button" class="btn btn-dark" onclick="location.href = './AdministrarUbicacionesServlet'" value="Cancelar operación" id="aceptar">
                                            </div>                                        
                                        </div>
                                    </div>
                                </form>     

                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                                    <h1 class="secundarios">Listado de barrios no confirmados</h1>
                                </div>
                                <div style="height: 100%">
                                    <table class="table-striped table-sm" style="font-size: 25px; color:ivory;">
                                        <c:choose>
                                            <c:when test = "${lstBarriosNuevos == null || lstBarriosNuevos.size() == 0}">
                                                <tr>
                                                    <td>
                                                        <h2 style="color: ivory">Todos los barrios están validados</h2>     
                                                    </td>   
                                                </tr>
                                            </c:when>
                                            <c:otherwise>
                                                <thead>
                                                    <tr>
                                                        <th>Identificador</th>
                                                        <th>Nuevo barrio</th>
                                                        <th>Localidad</th>
                                                        <th>Provincia</th>
                                                        <th>Persona</th>
                                                        <th>Contacto</th>                                    
                                                        <th>Seleccionar</th>                                    
                                                    </tr>
                                                </thead>
                                                <tbody style="font-size: 18px;">
                                                    <c:forEach items="${lstBarriosNuevos}" var="lbn" >
                                                        <tr style="text-align: center">
                                                            <td>${lbn.barrio.id_barrio}</td>
                                                            <td>${lbn.barrio.barrio}</td>
                                                            <td>${lbn.localidad.localidad}</td>
                                                            <td>${lbn.provincia.provincia}</td>
                                                            <td>${lbn.nombre} ${lbn.apellido}</td>
                                                            <td style="display: grid">
                                                                <span style="line-height: 15px; margin-top: 10px; "><small>Teléfono: ${lbn.telefono}</small></span>
                                                                <span style="line-height: 15px;  margin-top: 10px;"><small>Teléfono alternativo: ${lbn.telefono}</small></span>
                                                                <span style="line-height: 15px;  margin-top: 10px; margin-bottom: 10px;"><small>Correo: ${lbn.email}</small></span>   
                                                            </td>
                                                            <td><a class="nav-link text-white text-center" href="./AdministrarBarriosClientes?id=${lbn.id}">Elegir</a> </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </c:otherwise>
                                        </c:choose>
                                    </table>
                                </div>
                                <script src="js/CombosUbicacion.js" type="text/javascript"></script>
                                <script type="text/javascript">
                                    var rbtBarrioCombo = document.getElementById('rbtBarrioCombo');
                                    var cboBarriosFiltrados = document.getElementById('cboBarriosFiltrados');
                                    function Enviar(){
                                        var x = true;
                                        if(rbtBarrioCombo.checked && !(cboBarriosFiltrados.value > 0)){
                                            alert('Debe seleccionar un item del listado de barrios existentes. Puede filtrar la busqueda escribiendo en el buscador')
                                            cboBarriosFiltrados.focus();
                                            x = false;
                                        }
                                        if(x){
                                            frmClientesBarrio.submit();
                                        }
                                    }
                                    
                                    var divForm = document.getElementById('divForm');
                                    var x = '${perkins.nombre}'
                                    var z = '${lstBarriosNuevos.size()}'
                                    
                                    window.onload = function(){
                                        if (x!== '' && z!== '0'){
                                            divForm.style.display = "inline";
                                        } else{
                                            divForm.style.display = "none";
                                            
                                        }
                                    }

                                                    function Buscador() {
                                                        var buscador = document.getElementById('buscador');
                                                        var cboBarriosFiltrados = document.getElementById('cboBarriosFiltrados');
                                                        var barrios = new Array();
                                    <c:forEach items="${lstBarrios}" var="b">
                                                        var barrio = new Ubicacion(${b.id_barrio}, '${b.barrio}', ${b.localidad.id_localidad});
                                                        barrios.push(barrio);
                                    </c:forEach>
                                                        var barrioSeleccionado = null;
                                    <c:choose>
                                        <c:when test="${perkins != null}">
                                                        barrioSeleccionado = new Ubicacion(${perkins.barrio.id_barrio}, '${perkins.barrio.barrio}', ${perkins.localidad.id_localidad});
                                        </c:when>
                                    </c:choose>
                                                        var barriosFiltrados = new Array();
                                                        for (b of barrios) {
                                                            if (b.id_padre === barrioSeleccionado.id_padre) {
                                                                if (b.descripcion.toLowerCase().includes(buscador.value.toLowerCase())) {
                                                                    barriosFiltrados.push(b);
                                                                }
                                                            }
                                                        }
                                                        if (barriosFiltrados.length != 0) {
                                                            addOptions(cboBarriosFiltrados, barriosFiltrados);

                                                        } else {
                                                            for (let i = cboBarriosFiltrados.options.length; i >= 0; i--) {
                                                                cboBarriosFiltrados.remove(i);
                                                            }
                                                            var despliegue = document.createElement('option');
                                                            despliegue.textContent = 'Despliegue para seleccionar';
                                                            cboBarriosFiltrados.add(despliegue);
                                                        }
                                                        var otraOpcion = document.createElement('option');
                                                        otraOpcion.value = 0;
                                                        otraOpcion.textContent = "No hay coincidencia";
                                                        cboBarriosFiltrados.add(otraOpcion);

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
