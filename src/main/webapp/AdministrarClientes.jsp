
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrar clientes</title>
    </head>
    <body>

        <div class="container-fluid">
            <div class="row justify-content-center">
                <div class="col-9">
                    <div class="container-fluid bg-secondary " >
                        <div class="row d-flex align-items-stretch" style="height: 100%;">
                            <nav class="col-md-2 d-none d-md-block bg-light sidebar pt-5">
                                <c:import url="menu.jsp" >
                                </c:import>
                            </nav>
                            <div class="col-md-10 bg-secondary"  style="overflow: hidden;">


                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="margin-top: 60px!important">
                                    <h1 class="titulares">Administrar clientes</h1>
                                </div>
                                <div style="margin-top: 0px; margin-bottom: 30px;line-height: 2px; color: ivory;    ">
                                    <span>
                                        Puede seleccionar a un cliente del listado para cambiar la categoría del mismo, o de no tener categoría asignarle una.
                                    </span>
                                </div>
                                <form method="POST" action="./AdministrarClientesServlet" name="frmTipoCt">
                                    <input type="hidden" value="${id}" name="identificalo">
                                    <input type="hidden" value="${nombreApellido}" name="nombre">
                                    <div class="row">
                                        <div class="col"> 
                                            <label class="listadoHeader" for="cboTipoCliente">Categorías de cliente</label>
                                            <select class="form-control" id="cboTipoCliente" name="idTipoCliente">                        
                                                <option value = "0">Despliegue para seleccionar</option>
                                                <c:forEach items="${lstTipoCliente}" var="tc">  
                                                    <option value="${tc.idtipo}" ${tipo eq tc.idtipo?'selected':''}>${tc}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col">
                                            <label  class="listadoHeader" for="mostrarNombre">Nombre y apellido </label>
                                            <input type="text" class="form-control " id="mostrarNombre" disabled = "true"value="${nombreApellido}">

                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col" id="resultado">                       

                                        </div>
                                        <div class="col">
                                            <div class ="col" style="margin-top: 30px; text-align: right">
                                                <input type="button" onclick ="Enviar()" class="btn btn-dark" value="Aplicar cambio" id="aceptar">
                                                <input type="button" class="btn btn-dark " value="Nuevo cliente" onclick="location.href = './AltaPersonaServlet?tipoUsuario=cliente'">
                                                <input type="button" class="btn btn-dark " value="Cancelar" onclick="history.go(-1);">
                                            </div>
                                        </div>
                                    </div>

                                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                                        <h1 class="secundarios">Listado de clientes</h1>
                                    </div>
                                    <div style="height: 100%">
                                        <table class="table-striped table-sm">
                                            <c:choose>
                                                <c:when test = "${lstCliente == null}">
                                                    <tr>
                                                        <td>
                                                            <h2>No existen clientes</h2>     
                                                        </td>   
                                                    </tr>
                                                </c:when>
                                                <c:otherwise>

                                                    <thead class="listadoHeader">
                                                        <tr>
                                                            <th>Número de cliente</th>
                                                            <th>Nombre y apellido</th>
                                                            <th>Categoría</th>
                                                            <th>Fecha de inscripción</th>
                                                            <th>Contacto</th>
                                                            <th>Cambiar categoría</th>
                                                            <th>Eliminar</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody class="listadoResto">
                                                        <c:forEach items="${lstCliente}" var="lc" >
                                                            <tr style="text-align: center">
                                                                <td>${lc.id}</td>
                                                                <td>${lc.p.nombre} ${lc.p.apellido}</td>
                                                                <td>${lc.tc.tipo}</td>
                                                                <td>${lc.FechaIngreso()}</td>
                                                                <td style="display: grid">
                                                                    <span style="line-height: 15px; margin-top: 10px; "><small>Teléfono: ${lc.p.telefono}</small></span>
                                                                    <span style="line-height: 15px;  margin-top: 10px;"><small>Teléfono alternativo: ${lc.p.telefono}</small></span>
                                                                    <span style="line-height: 15px;  margin-top: 10px; margin-bottom: 10px;"><small>Correo: ${lc.p.email}</small></span>   
                                                                </td>
                                                                <td><a class="nav-link text-white text-center" href="./AdministrarClientesServlet?id=${lc.p.id}">Cambiar categoría</a></td>
                                                                <td><a class="nav-link text-white text-center"  href="#" onclick="EliminarCt(${lc.id})">Eliminar</a> </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </c:otherwise>
                                            </c:choose>
                                        </table>
                                        <script type="text/javascript">
                                            var cboTipoCliente = document.getElementById('cboTipoCliente');
                                             function Enviar(){
                                                 if(cboTipoCliente.value == 0){
                                                     alert('Debe seleccionar al menos una opción');
                                                     cboTipoCliente.focus();
                                                 }
                                                 else{
                                                     frmTipoCt.submit();
                                                 }                                                
                                            }                                            
                                            function EliminarCt(id) {
                                                $.ajax({
                                                    type: "POST",
                                                    url: "AdministrarClientesServlet",

                                                    data: {id_cliente: id, eliminar: 'eliminar'},

                                                    success: function (data, textStatus, jqXHR) {
                                                        if (data != 'true') {                                                           
                                                            alert('No se puede eliminar este cliente debido a que de eliminarlo comprometería la integridad del sistema. Para más información contactese con el técnico.')
                                                        } else{
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
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
