<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
    <head>
        <meta charset="utf-8">       
        <title>Iniciar sesión</title>
        <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous">

        </script>

        <script src="js/ajaxVerificarLogin.js" type="text/javascript"></script>
        <script src="js/ajaxRestorePw.js" type="text/javascript"></script>
        <link rel="stylesheet" href="./css/FrenarFont.css">
        <link rel="stylesheet" href="./css/BlackieFont.css">
        <style>
            body{                
                display: flex;                
                align-items: center;
                padding-top: 40px;
                padding-bottom: 40px;                
            }
        </style>
    </head>
    <body class="text-center" id="fondomain">

        <form class="form-signin bg-secondary"  id="bordes">


            <span class="mb-4"><h1 class="bg-secondary mb-3" id="logoFont">Frenar</h1></span>
            <h1 class="h1 mb-3 font-weight-normal " id="secundarios"> Iniciar sesión</h1>

            <input type="email" id="mail" class="form-control mb-2 mr-4" placeholder="Ingrese su correo">
            <div class="mt-2" id="result" style="color: ivory; font-size: 20px;"></div>

            <input type="password" id="password" class="form-control mb-2 mr-4" placeholder="Ingrese su contraseña" required="" >

            <div class="checkbox mb-3">
                <label>
                    <input type="checkbox" value="recuerdame"> <span  class="text-white">Recuerdame</span>
                </label>
            </div>    
            <button class="btn btn-lg btn-primary btn-block mb-3" id="verificarMail" >Iniciar sesión</button>
            <%--<a class="btn btn-lg btn-primary btn-block mb-3" href="#" id="verificarMail">Iniciar sesión</a>--%>
            <div class="container">
                <div class="row">
                    <div class="col">
                        <a href="#" id="enviarRecu" class="text-white">¿Olvidó su contraseña?</a>
                    </div>
                    <div class="col">
                        <a href="./AltaPersonaServlet?tipoUsuario=cliente" class="text-white mb-4">Crear cuenta nueva</a>
                    </div>
                </div>
            </div>
            <div class="modal"></div>
           
            <p class="mt-5 mb-3 text-white">© 2020</p>
        </form>

        <c:import url="bootstrap.jsp"></c:import>
    </body>
</html>

