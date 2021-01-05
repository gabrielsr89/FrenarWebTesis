<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <title>FrenarWeb</title>        
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./css/FrenarFont.css">
        <link rel="stylesheet" href="./css/BlackieFont.css">
        <link rel="stylesheet" href="./css/IndexServicios.css">
        <link rel="stylesheet" href="./css/Header.css">        
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:700">


    </head>
    <body id="fondomain">

        <c:import url="header.jsp"></c:import>  
       

        <div class="bg-secondary" style="position: relative;left:50%; transform: translateX(-50%); width: 60%; height: 100%;">
            <div class="ubicacionGeneral">
                <div class="container mx-auto position-relative mt-5">
                    <%--<div class="row-cols-1">
                        <div class="col mx-auto "><p id="ServiFrenos">Servi frenos<p></p></div>
                    </div>--%>
                    <div class="row row-cols-1 pb-5">
                        <div class="col mx-auto my-2 mt-0 text-center"><h2 id="ServiFrenos">Servi frenos</h2><h1 id="titulo">Frenar</h1></div>
                    </div>
                </div>

                <div class="container bg-secondary" style="width: 100%;">
                    <div class="row-cols-1 ">
                        <div class="col mx-auto my-0 mt-0"><h1 class="texto-borde subtitulos text-center">Nuestros servicios</h1></div>
                    </div>
                    <%-- DESDE ACÁ LO NUEVO --%>
                    <div class="contPrincipal mt-4">
                        <button onclick="Servicio()" id="aPrincipal" >
                            <img id="imgUno" class="imgPrincipal">
                            <small class="smPrincipal" id="smUno" style=""></small>
                        </button>
                    </div>
                    <div class="contSecundario">
                        <div class="contenedoresMenores">
                            <button onclick="CambiarDos()" id="aDos">
                                <img src="#" id="imgDos" class="imgPrincipal">
                                <small class="smalles" id="smDos" style=""></small>
                            </button>

                        </div>
                        <div class="contenedoresMenores">

                            <button onclick="CambiarTres()" id="aTres">
                                <img src="#" id="imgTres" class="imgPrincipal">
                                <small class="smalles" id="smTres" style=""></small>
                            </button>
                        </div>
                        <div class="contenedoresMenores">
                            <button href="#" onclick="CambiarCuatro()" id="aCuatro">
                                <img src="#" id="imgCuatro" class="imgPrincipal">
                                <small class="smalles" id="smCuatro" style=""></small>
                            </button>

                        </div>
                    </div>
                </div>
            </div>     
        </div>
        <c:import url="bootstrap.jsp"></c:import>  

                    <script type="text/javascript">
                        var imgUno = document.getElementById("imgUno");
                        var imgDos = document.getElementById("imgDos");
                        var imgTres = document.getElementById("imgTres");
                        var imgCuatro = document.getElementById("imgCuatro");
                        
                        var smUno = document.getElementById("smUno");
                        var smDos = document.getElementById("smDos");
                        var smTres = document.getElementById("smTres");
                        var smCuatro = document.getElementById("smCuatro");
                        
                        var urlGomeria = "./img/gomeria.png";
                        var urlCentrado = "./img/centradoLlantas.png";
                        var urlFrenos  = "./img/frenos.jpg";
                        var urlTren =  "./img/trenDelantero.png";
                     
                        var aUno = document.getElementById("aUno");
                        var aDos = document.getElementById("aDos");
                        var aTres = document.getElementById("aTres");
                        var aCuatro = document.getElementById("aCuatro");
                        
                        window.onload = function a(){
                            imgUno.src = urlGomeria;
                            smUno.textContent = "Gomería";
                            
                            imgDos.src = urlCentrado;
                            smDos.textContent = "Centrado de llanta";
                            
                            imgTres.src = urlFrenos;
                            smTres.textContent = "Servicio de frenos";
                            
                            imgCuatro.src = urlTren; 
                            smCuatro.textContent = "Tren delantero";
                        }
                        function Servicio(){
                            if(smUno.textContent==='Servicio de frenos'){
                                location.href= "./CatServiciosServlet?id_categoria=1";                                
                            }
                            if(smUno.textContent==='Gomería'){
                                location.href= "./CatServiciosServlet?id_categoria=2";                                
                            }
                            if(smUno.textContent==='Tren delantero'){
                                location.href= "./CatServiciosServlet?id_categoria=3";                                
                            }
                            if(smUno.textContent==='Centrado de llanta'){
                                location.href= "./CatServiciosServlet?id_categoria=4";                                
                            }
                        }
                        function CambiarDos(){
                            $("#smUno").fadeOut(0);
                            var IMGvieja = imgUno.src;
                            var IMGnueva = imgDos.src;
                            imgUno.src = IMGnueva;
                            imgDos.src = IMGvieja;
                            
                            var TXTviejo = smUno.textContent;                            
                            var TXTnuevo = smDos.textContent;
                            smDos.textContent = TXTviejo;
                            smUno.textContent = TXTnuevo;
                           
                           $("#smUno").fadeIn(3000);
                        }
                        function CambiarTres(){
                            $("#smUno").fadeOut(0);
                            var IMGvieja = imgUno.src;
                            var IMGnueva = imgTres.src;
                            imgUno.src = IMGnueva;
                            imgTres.src = IMGvieja;
                            
                            var TXTviejo = smUno.textContent;                            
                            var TXTnuevo = smTres.textContent;
                            smTres.textContent = TXTviejo;
                            smUno.textContent = TXTnuevo;
                           
                           $("#smUno").fadeIn(3000);
                        }
                        function CambiarCuatro(){
                            $("#smUno").fadeOut(0);
                            var vieja = imgUno.src;
                            var nueva = imgCuatro.src;
                            imgUno.src = nueva;
                            imgCuatro.src = vieja;
                            var TXTviejo = smUno.textContent;                            
                            var TXTnuevo = smCuatro.textContent;
                            smCuatro.textContent = TXTviejo;
                            smUno.textContent = TXTnuevo;                           
                           $("#smUno").fadeIn(3000);
                        }
                       
                        
                    </script>
    </body>
</html>
