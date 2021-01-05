
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrar productos</title>
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
                            <div class="col-md-10"  style="overflow: hidden; margin-top: 100px;">


                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                                    <h1 class="titulares">Bienvenido ${p.nombre} ${p.apellido}</h1></div>
                                <div style="text-align: center;">
                                    <div>
                                        <h3 class="secundarios bg-dark">Su tipo de usuario es ${tipo}</h3>
                                    </div>
                                    <div >
                                        <h3 class="secundarios" style="margin-top:30px; margin-bottom: 15px;">Datos personales</h3>
                                        <div style="margin-left:10px;">
                                            <ul class="list-group" style="color: ivory; font-size: 25px;">
                                                <li class="list-group">
                                                    Correo: ${p.email}
                                                </li>
                                                <li class="list-group">
                                                    Teléfono: ${p.telefono}
                                                </li>
                                                <li class="list-group">
                                                    Teléfono alternativo: ${p.tel_alternativo}
                                                </li>
                                                <li class="list-group">                            
                                                    <ul class="list-group"> 
                                                        <li class="list-group">Domicilio: ${p.domicilio}, barrio ${p.barrio.barrio}</li>
                                                        <li class="list-group">Provincia: ${p.provincia.provincia}, localidad: ${p.localidad.localidad}</li>
                                                        <li class="list-group">CP: ${p.cp}</li>
                                                    </ul>
                                                </li>                        
                                            </ul>
                                        </div>
                                        <div style="margin-top: 35px; margin-bottom: 100%;">
                                            <c:choose>
                                                <c:when test="${cliente == true}">
                                                    <a href="./AltaPersonaServlet?idPersona=${p.id}" class="text-white" ><span style="color:ivory; font-size: 20px;">*Para modificar sus datos haga click aqui*</span></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="./AltaPersonaServlet?idPersona=${p.id}&modificarEmpleado=true" class="text-white" ><span style="color:ivory; font-size: 20px;">*Para modificar sus datos haga click aqui*</span></a>

                                                </c:otherwise>
                                            </c:choose>
                                        </div>
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

