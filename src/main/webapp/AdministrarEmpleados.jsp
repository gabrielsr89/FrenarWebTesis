
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrar empleados</title>
    </head>
    <body>
        <div class="container-fluid" style="">
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
                                    <h1 class="titulares">Administrar empleados</h1>
                                </div>
                                <div style="margin-top: 0px; margin-bottom: 30px;line-height: 2px; color: ivory;    ">
                                    <span>
                                        Puede seleccionar a una persona del listado para asignarle un nuevo rol en la empresa.
                                    </span>
                                </div>
                                <form method="POST" action="./AdministrarEmpleadoServlet">
                                    <input type="hidden" value="${id}" name="identificalo">
                                    <input type="hidden" value="${nombreApellido}" name="nombre">
                                    <div class="row">
                                        <div class="col"> 
                                            <label class="listadoHeader" for="cboTipoUsuario">Tipo de empleado</label>
                                            <select class="form-control" id="cboTipoUsuario" name="idTipoUsuario">                        
                                                <option value = "0">Despliegue para seleccionar</option>
                                                <c:forEach items="${lstTu}" var="tu">  
                                                    <option value="${tu.id_tipousuario}" ${usuario eq tu.id_tipousuario?'selected':''}>${tu}
                                                    </option></c:forEach></select>
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
                                                <input type="submit" class="btn btn-dark" value="Aplicar cambio" id="aceptar">
                                                <input type="button" class="btn btn-dark " value="nuevo empleado" onclick="location.href = './AltaPersonaServlet?tipoUsuario=operador'">
                                                <input type="button" class="btn btn-dark " value="Cancelar" onclick="history.go(-1);">
                                            </div>
                                        </div>
                                    </div>

                                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                                        <h1 class="secundarios">Listado de empleados</h1>
                                    </div>
                                    <div style="height: 100%; overflow: auto;">
                                        <c:choose>
                                            <c:when test = "${lstOperadores == null}">
                                                <tr>
                                                    <td>
                                                        <h2>No hay operadores, cargue un nuevo operador</h2>     
                                                    </td>   
                                                </tr>
                                            </c:when>
                                            <c:otherwise>
                                                <table class="table-striped table-sm" style="overflow: auto;">
                                                    <thead class="listadoHeader">
                                                        <tr>
                                                            <th>Identificador</th>
                                                            <th>Nombre y apellido</th>
                                                            <th>Cargo</th>
                                                            <th>Fecha ingreso</th>
                                                            <th>Cambiar cargo</th>
                                                            <th>Eliminar</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody class="listadoResto">
                                                        <c:forEach items="${lstOperadores}" var="lo" >
                                                            <tr style="text-align: center">
                                                                <td>${lo.p.id}</td>
                                                                <td>${lo.p.nombre} ${lo.p.apellido}</td>
                                                                <td>${lo.tu.usuario}</td>
                                                                <td>${lo.FechaIngreso()}</td>
                                                                <td><a class="nav-link text-white text-center" href="./AdministrarEmpleadoServlet?id=${lo.p.id}">Cambiar cargo</a></td>
                                                                <td><a class="nav-link text-white text-center" href="#">Eliminar</a> </td>
                                                            </tr>
                                                        </c:forEach>                                                        
                                                    </tbody>
                                                </table>
                                            </c:otherwise>
                                        </c:choose>
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
