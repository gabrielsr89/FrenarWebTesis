<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<header>           
    <nav class="navbar navbar-expand-lg navbar-white bg-dark fixed-top">
        <a class="navbar-brand " href="./headerServlet"><h1 id="tituloHeader">Frenar</h1></a>  

        <ul class="navbar-nav text-white">
            <li>
                <div class="nav-item dropdown " >
                    <a class="btn btn-dark dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span class="headerGeneral">Contacto</span>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item ubicacion" href="https://wa.link/jhg18j"><img src="./img/whatsapp.png"><span> Envíamos un whatsapp para aclarar tus dudas!</span></a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item mt-0 ubicacion" href="https://goo.gl/maps/rVumtS7rPgRhPcxK9"><img src="./img/localizacion.png"><span> Monseñor pablo cabrera 2105. Click para ver el mapa!</span></a>
                        <div class="dropdown-divider"></div> 
                        <span class="dropdown-item ubicacion"><img src="./img/telefono.png"> <span>Teléfono fijo: 0351-4872830</span></span>
                    </div>
                </div>
            </li>



            <li>
                <c:choose>
                    <c:when test = "${banderaIniciar == true || banderaIniciar == null}">
                        <a class="btn btn-dark" href="./LoginServlet"><span class="headerGeneral">Iniciar sesión</span></a>                        
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test = "${administrador != null || operador != null || cliente != null}">
                                <div class="nav-item dropdown">
                                    <a class="btn btn-dark dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <span class="headerGeneral">${p.nombre} ${p.apellido}</span>
                                    </a>
                                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                        <a class="dropdown-item" href="./inicioPersonaServlet">Acceder al sistema</a>
                                        <div class="dropdown-divider"></div>                                        
                                        <a class="dropdown-item" href="./LogoutServlet?url=${url}">Cerrar sesion</a>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>
                    </c:otherwise>
                </c:choose> 
            </li>
        </ul>        
    </nav>  
</header>