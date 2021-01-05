<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporte de compras</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row justify-content-center"  style=" min-height: 100%;">
                <div class="col-9">
                    <div class="container-fluid bg-secondary">
                        <div class="row"   style=" min-height: 100%;">
                            <nav class="col-md-2 d-none d-md-block bg-light sidebar pt-5">
                                <c:import url="menu.jsp" >
                                </c:import>
                            </nav>
                            <div class="col-md-10"  style="overflow: hidden; margin-top: 100px; color: ivory;">

                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="margin-top: 60px!important;">
                                    <h1 class="titulares">Reporte de compras</h1>
                                </div>

                                <small style="color: ivory; font-size: 18px;">Reportes de compras realizadas a los distintos proveedores.</small><br/>                    



                                <div class="row" style="padding-top: 10px; color:ivory; font-size: 22px;">

                                    <%-- Columna 2 --%>
                                    <div class="col-md-4 order-md-2 mb-4" >
                                        <div class="border-bottom pt-0" style="margin-top: 60px!important;">
                                            <h4>Compras de repuestos por mes</h4>
                                        </div>
                                        <small style="color: ivory; font-size: 18px;">Parametrize su búsqueda para ver los gastos en compras de repuestos</small><br/>                    
                                        <div class="row mt-3"  >
                                            <div class="col">

                                                <label for="cboRepuestosPorMes" id="spanProveedor">Meses</label>                                
                                                <select class="form-control mb-2 w-100" id="cboRepuestosPorMes">                        
                                                    <option value = "0">Todos los meses</option>     
                                                    <option value = "1">Enero</option>     
                                                    <option value = "2">Febrero</option>     
                                                    <option value = "3">Marzo</option>     
                                                    <option value = "4">Abril</option>     
                                                    <option value = "5">Mayo</option>     
                                                    <option value = "6">Junio</option>     
                                                    <option value = "7">Julio</option>     
                                                    <option value = "8">Agosto</option>     
                                                    <option value = "9">Septiembre</option>     
                                                    <option value = "10">Octubre</option>     
                                                    <option value = "11">Noviembre</option>     
                                                    <option value = "12">Diciembre</option>                                    
                                                </select>

                                                <label for="cboYear" id="spanProveedor">Año</label>                                
                                                <select class="form-control mb-2 w-100" id="cboYear"> 
                                                    <option value="0">Todos los años</option>                                
                                                    <c:forEach items="${lstYear}" var="y">
                                                        <option value="${y}">${y}</option>
                                                    </c:forEach>                                                             
                                                </select>
                                                <div style="margin-top: 50px;">
                                                    <center >
                                                        <button type="button" class="btn btn-dark mt5" onclick="Enviar()" id="btnEnviar" style="width: 80%; ">Buscar</button>
                                                    </center>

                                                </div>

                                                <script type="text/javascript">
                                                    var cboMes = document.getElementById('cboRepuestosPorMes')
                                                    var cboYear = document.getElementById('cboYear')
                                                    function Enviar() {
                                                        location.href = './ReporteGastosServlet?mes=' + cboMes.value + '&year=' + cboYear.value;
                                                    }
                                                </script>
                                            </div>
                                        </div>
                                    </div>

                                    <%-- Columna 1 --%>
                                    <div class="col-md-8 order-md-1">
                                        <div class="border-bottom pt-0" style="margin-top: 60px!important; margin-bottom: 50px;">
                                            <h4>Reporte</h4>
                                        </div>
                                        <center>
                                            <div class="row">
                                                <div class="col-8 mx-auto">
                                                    <c:choose>
                                                        <c:when test="${lstParametrizada != null}">
                                                            <h3>
                                                                Año: ${year} - Mes: ${mes}
                                                            </h3>
                                                            <c:choose>
                                                                <c:when test="${lstParametrizada.size() > 0}">
                                                                    <table class="table table-striped table-dark">
                                                                        <thead>
                                                                            <tr>                                                   
                                                                                <th scope="col">Repuestos</th>                                           
                                                                                <th scope="col">Cantidad</th>                                           
                                                                                <th scope="col">Importe total</th>                                           
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <c:forEach items="${lstParametrizada}" var="gm">
                                                                                <tr>                                                        
                                                                                    <td>
                                                                                        ${gm.repuesto}
                                                                                    </td>                                                    
                                                                                    <td>
                                                                                        ${gm.cantidad}
                                                                                    </td>                                                    
                                                                                    <td>
                                                                                        ${gm.importe}
                                                                                    </td>                                                    
                                                                                </tr>
                                                                            </c:forEach>
                                                                        </tbody>
                                                                    </table>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    No existen resultados para la busqueda
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <h1 style="font-size: 90px; padding-top: 50px;">
                                                                Bienvenido
                                                            </h1>
                                                            <small>
                                                                Interactue con el menu de la derecha para comenzar
                                                            </small>

                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>  
                                        </center>                    
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
