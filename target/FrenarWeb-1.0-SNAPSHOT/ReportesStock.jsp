<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reportes de stock</title>
    </head>
    <body>
        <div class="container-fluid" >
            <div class="row justify-content-center" style="height: 100%;">
                <div class="col-9">
                    <div class="container-fluid bg-secondary" >
                        <div class="row" style="height: 100%;">
                            <nav class="col-md-2 d-none d-md-block bg-light sidebar pt-5">
                                <c:import url="menu.jsp" >
                                </c:import>
                            </nav>
                            <div class="col-md-10 bg-secondary"  style="overflow: hidden; margin-top: 100px; color: ivory;">
                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="margin-top: 60px!important;">
                                    <h1 class="titulares">Reportes de stock</h1>
                                </div>                                
                                <small style="color: ivory; font-size: 18px;">Listado de repuestos ordenados por stock.  Aquellos repuestos cuyo stock registrado sea de 5 unidades o se verá reflejado en el listado de alerta.</small><br/>                    
                                <center>
                                    <div class="row mt-5 mb-5">
                                        <div class="col">
                                            <c:choose>
                                                <c:when test="${lstAlerta.size()>0}">
                                                    <h1 class="mb-4">
                                                        Alerta de stock en los siguientes repuestos.
                                                    </h1>
                                                    <table class="table table-striped table-dark mb-3" style="width: 60%;">
                                                        <thead>
                                                            <tr>
                                                                <th scope="col">Repuesto</th>
                                                                <th scope="col">Stock</th>

                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach items="${lstAlerta}" var="r" >
                                                                <tr>
                                                                    <th scope="row">${r.repuesto}</th>
                                                                    <td style="color: ivory">${r.stock}</td>                                                    
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                    <a href="ReponerStockServlet" style="font-size: 25px; color: ivory;">Reposición de stock</a>

                                                </c:when>
                                                <c:otherwise>
                                                    <h1>
                                                        No hay alerta de stock de momento
                                                    </h1>
                                                </c:otherwise>
                                            </c:choose>                                            
                                        </div>                                        
                                    </div>
                                    <div class="row mt-5 mb-5">
                                        <div class="col">
                                            <c:choose>
                                                <c:when test="${lstNoAlerta != null}">
                                                    <h1 class="mb-4">
                                                        Listado de stock
                                                    </h1>
                                                    <table class="table table-striped table-dark mb-3" style="width: 60%;">
                                                        <thead>
                                                            <tr>
                                                                <th scope="col">Repuesto</th>
                                                                <th scope="col">Stock</th>

                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach items="${lstNoAlerta}" var="r" >
                                                                <tr>
                                                                    <th scope="row">${r.repuesto}</th>
                                                                    <td style="color: ivory">${r.stock}</td>                                                    
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                    <a href="#" style="font-size: 25px; color: ivory;">Volver arriba</a>

                                                </c:when>
                                                <c:otherwise>
                                                    <h1>
                                                        No se encuentran repuestos con stock registrado mayor a 5
                                                    </h1>
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
    </body>
</html>