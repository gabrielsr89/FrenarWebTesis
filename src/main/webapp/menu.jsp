<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Menu administrador</title>
        <style>
            .Botones{
                padding-left: 0px !important;
                border: none;
                background: transparent;
                color: #007bff;
                text-align:  left;
            }
            .Botones:hover{
                color: #0f60b7;    
                border-bottom: 1px solid grey;
            }
            .Botones:focus{
                outline: none;

            }            
        </style>


    </head>
    <body id="fondomain">
        <div class="row mb-5">
            <div class="col">                
                <c:import url="header.jsp">
                </c:import>
            </div>
        </div>

        <nav class="col-md-2 d-none d-md-block bg-light sidebar pt-5" >
            <div class="sidebar-sticky">
                <ul class="nav flex-column">

                    <ul style="padding-left: 10px !important; list-style-type:disc">
                        <%--DATOS GENERALES--%>

                        <button class="nav-link Botones" onclick="location.href = './inicioPersonaServlet'">
                            <h5>Mis datos</h5>
                        </button>


                        <%--MENU ADMINISTRADOR--%>
                        <c:choose>
                            <c:when test='${administrador == true}'>

                                <button class="nav-link Botones" onclick="ActivarAtencionCliente()">
                                    <h5 style="padding-left: 0px !important; ">Atención al cliente</h5>
                                </button>                                

                                <div id="divAtencionCliente" style="display: none">
                                    <li> <a class="nav-link" href="./VerificarEstadoServicioServlet?id_c=48">Ver estado del servicio en curso</a>   </li>
                                    <li ><a class="nav-link" href="./HistorialFacturasServlet">Ver historial de facturas</a></li>
                                    <li ><a class="nav-link" href="./RegistrarAutoClienteServlet">Registrar nuevo auto</a></li>
                                    <li ><a class="nav-link" href="./ClientesPorServiciosServlet">Listado de clientes por servicio</a></li>
                                    <li ><a class="nav-link" href="./NuevoPagoServlet">Gestionar cobranza</a></li>                                   
                                    <li ><a class="nav-link" href="./CatRepuestosServlet">Vender repuesto</a></li>
                                    <li ><a class="nav-link" href="./NuevoPresupuestoServlet">Nuevo presupuesto</a></li>
                                    <li ><a class="nav-link" href="./FacturadoYPagadoListados">Servicios y repuestos listos para entrega</a></li>
                                    <li ><a class="nav-link" href="./ConfirmarPresupuestoServlet">Confirmar presupuestos</a></li>
                                    <li ><a class="nav-link" href="./ConfirmarReservaServlet">Listados de reservas</a></li>
                                    <li ><a class="nav-link" href="./ListarTrabajos">Listar servicios pendientes</a></li>                                    
                                </div>

                                <button  class="nav-link Botones"  onclick="ActivarGestionAdministrativa()">
                                    <h5>Gestiones administrativas</h5>
                                </button>
                                <div id="divGestionAdministrativa" style="display: none">
                                    <li ><a class="nav-link" href="./AdministrarMarcaModeloAuto">Administrar marcas y modelos de vehículos</a></li>
                                    <li ><a class="nav-link" href="./AdministrarServicios">Administrar servicios</a></li>
                                    <li ><a class="nav-link" href="./ReponerStockServlet">Administrar compras (Reposición de stock)</a></li>
                                    <li ><a class="nav-link" href="./AdministrarProveedoresServlet">Administrar proveedores</a></li>
                                    <li ><a class="nav-link" href="./AdministrarRepuestosServlet">Administrar repuestos</a></li>
                                    <li ><a class="nav-link" href="./AdministrarClientesServlet">Administrar clientes</a></li>
                                    <li ><a class="nav-link" href="./AdministrarEmpleadoServlet">Administrar empleados</a></li>
                                    <li ><a class="nav-link" href="./AdministrarUbicacionesServlet">Administrar ubicaciones</a></li>                                    
                                </div>

                                <button  class="nav-link Botones"  onclick="ActivarReportes()">
                                    <h5>Reportes</h5>
                                </button>

                                <div id="divReportes" style="display: none">
                                    <li ><a class="nav-link" href="./ReportesStockServlet">Reportes de stock <span style="color: red;">(${AlertaStock})</span></a></li>
                                    <li ><a class="nav-link" href="./ReporteGastosServlet">Reportes de gastos</a></li>                                    
                                    <li ><a class="nav-link" href="./Reportes">Reportes generales</a></li>                                    
                                </div>
                            </ul>


                        </c:when>    
                    </c:choose>

                    <%--MENU OPERADOR--%>
                    <c:choose>
                        <c:when test='${operador == true}'>
                            <button  class="nav-link Botones"  onclick="ActivarAtencionCliente()">
                                <h5>Atención al cliente</h5>
                            </button>                            
                            <div id="divAtencionCliente" style="display: none">
                                <li><a class="nav-link" href="./VerificarEstadoServicioServlet?id_c=48">Ver estado del servicio en curso</a> </li>  
                                <li ><a class="nav-link" href="./RegistrarAutoClienteServlet">Registrar nuevo auto</a></li>
                                <li ><a class="nav-link" href="./ClientesPorServiciosServlet">Listado de clientes por servicio</a></li>
                                <li ><a class="nav-link" href="./CatRepuestosServlet">Vender repuesto</a></li>
                                <li ><a class="nav-link" href="./NuevoPresupuestoServlet">Nuevo presupuesto</a></li>
                                <li ><a class="nav-link" href="./ListarTrabajos">Listar servicios pendientes</a></li>                                   

                            </div>
                            <button  class="nav-link Botones"  onclick="ActivarGestionAdministrativa()">
                                <h5>Gestiones de facturación</h5>
                            </button>
                            <div id="divGestionAdministrativa" style="display: none">
                                <li ><a class="nav-link" href="./FacturadoYPagadoListados">Servicios y repuestos listos para entrega</a></li>
                                <li ><a class="nav-link" href="./ConfirmarReservaServlet">Listados de reservas</a></li>
                                <li ><a class="nav-link" href="./HistorialFacturasServlet">Ver historial de facturas</a></li>
                                <li ><a class="nav-link" href="./NuevoPagoServlet">Gestionar cobranza</a></li>                                   
                                <li ><a class="nav-link" href="./ConfirmarPresupuestoServlet">Confirmar presupuestos</a></li>                                  
                            </div>

                        </c:when>
                    </c:choose>

                    <%--MENU CLIENTE--%>
                    <c:choose>
                        <c:when test='${cliente == true}'>
                            <h5 class="Botones">Servicios</h5>
                            <a class="nav-link" href="./VerificarEstadoServicioServlet">Ver estado del servicio en curso</a>                                 
                            <a class="nav-link" href="./ConfirmarPresupuestoServlet">Confirmar presupuestos</a>
                            <a class="nav-link" href="./RegistrarAutoClienteServlet">Registrar auto nuevo</a>
                            <h5 class="Botones">Facturación</h5>
                            <a class="nav-link" href="./HistorialFacturasServlet">Historial de servicios recibidos y compras realizadas</a>
                            <a class="nav-link" href="./ConfirmarReservaServlet">Mis reservas</a>
                            <button  class="nav-link Botones" onclick="location.href = './CatRepuestosServlet'">
                                <h5 >Catálogo de repuestos</h5>
                            </button>
                        </c:when>
                    </c:choose>                            
                </ul>
                <script type="text/javascript">
                    var divAtencionCliente = document.getElementById('divAtencionCliente');
                    var divGestionAdministrativa = document.getElementById('divGestionAdministrativa');
                    var divReportes = document.getElementById('divReportes');
                    var titAtencion = document.getElementById('titAtencion');
                    var uno = false;
                    var dos = false;
                    var tres = false;
                    function ActivarAtencionCliente() {
                        if (!uno || dos || tres) {
                            $("#divAtencionCliente").fadeIn(1000);
                            divGestionAdministrativa.style.display = "none";
                            divReportes.style.display = "none";
                            uno = true;
                            dos = false;
                            tres = false;

                        } else {
                            $("#divAtencionCliente").fadeOut(500);
                            uno = false;

                        }
                    }
                    function ActivarGestionAdministrativa() {

                        if (!dos || uno || tres) {
                            $("#divGestionAdministrativa").fadeIn(1000);
                            divAtencionCliente.style.display = "none";
                            divReportes.style.display = "none";
                            uno = false;
                            dos = true;
                            tres = false;
                        } else {
                            $("#divGestionAdministrativa").fadeOut(500);
                            divGestionAdministrativa.style.display = "none";
                            dos = false;
                        }
                    }
                    function ActivarReportes() {
                        if (!tres || uno || dos) {
                            $("#divReportes").fadeIn(1000);
                            divAtencionCliente.style.display = "none";
                            divGestionAdministrativa.style.display = "none";
                            uno = false;
                            dos = false;
                            tres = true;
                        } else {
                            $("#divReportes").fadeOut(500);
                            tres = false;
                        }
                    }
                </script>

            </div>
        </nav>
        <c:import url="bootstrap.jsp"></c:import>  
        </body>
    </html>


<%-- IMAGENES SVG
<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file"><path d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"></path><polyline points="13 2 13 9 20 9"></polyline></svg>



--%>