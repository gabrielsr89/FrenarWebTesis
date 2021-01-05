<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrar nuevo auto</title>
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
                            <div class="col-md-10 bg-secondary"  style="overflow: hidden; margin-top: 100px; color: ivory;">


                                <form method="POST" accion="RegistrarAutoClienteServlet" name="frmRegistrarAuto">
                                    <input type="hidden" id="hiddenAccion" name="accion"/>
                                    <input type="hidden" id="hiddenIdAuto"  name="id_auto"/>
                                    <c:choose>
                                        <c:when test = "${idCliente != null}">
                                            <input type="hidden" value="${idCliente}" name="idCliente">
                                            <h1 style="font-size: 40px; color: ivory; border-bottom:  ivory solid 1px; margin-top:  100px;"> Registrar vehículo nuevo para ${nombreApellido}</h1>
                                        </c:when>
                                        <c:otherwise>
                                            <h3  style="font-size: 40px; color: ivory; border-bottom:  ivory solid 1px; margin-top:  100px;">Registrar vehículo nuevo</h3>
                                            <c:choose>
                                                <c:when test = '${cliente != true}'>
                                                    <div class="row">
                                                        <div class="col text-center">

                                                            <label class="listadoHeader mt-5" for="cboCliente">Debe seleccionar un cliente del desplegable para comenzar</label><select class="form-control" id="cboCliente" name="idCliente"  style="width: 60%; margin-left: 50%; transform: translateX(-50%);"><option value = "0">Despliegue para seleccionar</option><c:forEach items="${lstCliente}" var="ct"><option value="${ct.id}" ${ct.p.nombre eq ct.id?'selected':''}>${ct.p.nombre}</option></c:forEach></select> 
                                                            </div>
                                                        </div>
                                                </c:when>
                                            </c:choose>

                                        </c:otherwise>

                                    </c:choose>

                                    <div class="row">
                                        <div class="col" style="margin-top: 20px; margin-bottom: 30px; color: ivory; text-align: center; display: inline-grid; margin-left: 50%; transform: translateX(-50%)">
                                            <span>Seleccione marca para seleccionar el modelo y registrar.</span>
                                            <span>En caso de que no encuentre su marca o modelo puede seleccionar opción OTRA MARCA / OTRO MODELO para ingresarlos manualmente, de esta manera un moderador posteriormente verificará la veracidad de los datos ingresados.</span>
                                        </div>
                                    </div>

                                    <c:choose>
                                        <c:when test = "${idCliente != null}">
                                            <input type="hidden" value="${idCliente}" name="idCliente">
                                        </c:when>
                                    </c:choose>
                                    <div class="row">                                        <div class="col" style="width: 80%">
                                            <%--CBO MARCA--%>
                                            <label class="listadoHeader" for="cboMarcas">Seleccione la marca</label>
                                            <select class="form-control" id="cboMarcas" name="id_marca" >                        
                                                <option value = "-1">Despliegue para seleccionar</option>
                                                <c:forEach items="${lstMarcas}" var="m">  
                                                    <option value="${m.id_marcaAuto}" ${m.marcaAuto eq m.id_marcaAuto?'selected':''}>${m}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col" style="width: 80%">
                                            <%--CBO MODELO--%>
                                            <label class="listadoHeader" for="cboModelos">Seleccione modelo</label>
                                            <select  class="form-control" id="cboModelos" name="id_modelo">                        
                                                <option value = "0">Despliegue para seleccionar</option>   
                                                <c:forEach items="${lstModelos}" var="m">  
                                                    <option value="${m.id_modeloAuto}" ${m.modeloAuto eq m.id_modeloAuto?'selected':''}>${m}</option>
                                                </c:forEach>
                                            </select>
                                        </div>                    
                                    </div>
                                    <div class="row">
                                        <div class="col"  id="divMarca" style="display: none; width: 80%">
                                            <%--TEXTO MARCA--%>    
                                            <label  class="listadoHeader" for="txtMarca">Nombre de la marca no registrada</label>
                                            <input type="text" class="form-control " name="marcaNueva" id="txtMarca">

                                        </div>
                                        <div class="col" id="divModelo" style="display: none; width: 80%">
                                            <%--TEXTO MODELO--%>   
                                            <label  class="listadoHeader" for="txtModelo">Nombre del modelo no registrado </label>
                                            <input type="text" class="form-control " name="modeloNuevo" id="txtModelo">
                                        </div>                    
                                    </div>
                                    <div class="row text-center mt-5">
                                        <div class="col text-center">
                                            <label  class="listadoHeader" for="txtPatente">Ingrese la patente</label>
                                            <input type="text" class="form-control " name="patente" id="txtPatente" style="width:30%; margin-left: 50%; transform: translateX(-50%);">
                                            <input type="button" class="btn btn-dark mt-4" onclick="Enviar()"  id="aceptar" style="width:40%;" >
                                            <input type="button" class="btn btn-dark mt-4" onclick="location.href = './RegistrarAutoClienteServlet'" value="Cancelar gestion" id="aceptar" style="width:20%;" >
                                        </div>
                                    </div>
                                    <c:choose>
                                        <c:when test = "${administrador == true}">
                                            <div class="row mt-3">
                                                <div class="col text-center">                                                    
                                                    <a class="nav-link" href="./AdministrarMarcaModeloAuto" style="color:ivory; font-size: 25px;">Administración de marcas y modelos de autos</a>
                                                </div>
                                            </div>
                                        </c:when>
                                    </c:choose>

                                </form>

                                <div class="mb-5" id="divListado">
                                    <h1 class="titulares mt-5" style="border-bottom: ivory solid 1px; ">Vehículos registrados</h1>
                                    <ul class="list-group mt-4" style="display: list-item" id="ulListado">
                                        <c:choose>
                                            <c:when test = "${lstAutos.size() == 0}">
                                                <h2 style="color: ivory; text-align: center;">No hay vehículos registrados</h2>     
                                            </c:when>
                                            <c:otherwise>
                                                <smal style="color: ivory; width: 40%;">No podrá eliminar o modificar el vehículo si tiene al menos algún servicio realizado para salvaguardar la integridad de los datos</smal>

                                                <c:forEach items="${lstAutos}" var="a" >

                                                    <li class="list-group-item bg-dark text-white" style="">
                                                        <div class="row">
                                                            <div class="col ">
                                                                <h4>${a.patente}</h4>
                                                            </div>
                                                            <div class="col ">

                                                                <h4>${a.marca}</h4>
                                                            </div>
                                                            <div class="col ">
                                                                <h4>${a.modelo}</h4>

                                                            </div>
                                                            <c:choose>
                                                                <c:when test="${cliente== false}">
                                                                    <div class="col ">
                                                                        <a class="text-white" href="#" onclick="EliminarAuto(${a.id_auto})">Eliminar</a>
                                                                    </div>
                                                                    <div class="col ">
                                                                        <a class="text-white" href="./RegistrarAutoClienteServlet?idAuto=${a.id_auto}">Modificar</a></td>
                                                                    </div>
                                                                </c:when>
                                                            </c:choose>
                                                            <div class="col ">
                                                                <a class="text-white" href="./HistorialFacturasServlet?idAuto=${a.id_auto}&idCliente=${a.id_cliente}">Ver historial</a>
                                                            </div>
                                                        </div>
                                                    </li>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </ul>
                                </div>
                                <script src="js/RegistrarAuto.js" type="text/javascript"></script>
                                <script src="js/CombosUbicacion.js" type="text/javascript"></script>
                                <script type="text/javascript">
                                                                            var cboModelos = document.getElementById('cboModelos');
                                                                            var cboCliente = document.getElementById('cboCliente');
                                                                            var cboMarcas = document.getElementById('cboMarcas');
                                                                            var divMarca = document.getElementById('divMarca');
                                                                            var divModelo = document.getElementById('divModelo');
                                                                            var txtModelo = document.getElementById('txtModelo');
                                                                            var txtMarca = document.getElementById('txtMarca');
                                                                            var txtPatente = document.getElementById('txtPatente');
                                                                            var divListado = document.getElementById('divListado');
                                                                            var ulListado = document.getElementById('ulListado');
                                                                            var hiddenAccion = document.getElementById('hiddenAccion');
                                                                            var hiddenIdAuto = document.getElementById('hiddenIdAuto');
                                                                            var aceptar = document.getElementById('aceptar');
                                                                            var banderaModelo = false;
                                                                            var banderaMarca = false;
                                                                            var bandera = false;

                                                                            var modAuto = '${auto.id_auto}';
                                                                            var banderaModificacion = false;

                                                                            function EliminarAuto(id) {
                                                                                $.ajax({
                                                                                    type: "POST",
                                                                                    url: "RegistrarAutoClienteServlet",
                                                                                    data: {eliminarAuto: id},
                                                                                    success: function (data, textStatus, jqXHR) {
                                                                                        if (data != 'true') {
                                                                                            alert('No se puede eliminar este vehículo debido a que de eliminarlo comprometería la integridad del sistema. Para más información contactese con el técnico.')
                                                                                        } else {
                                                                                            alert('Vehículo eliminado de forma exitosa');
                                                                                            location.reload();
                                                                                        }
                                                                                    },
                                                                                    error: function (jqXHR) {
                                                                                        console.log("error")
                                                                                    }
                                                                                });
                                                                            }

                                                                            window.onload = function () {
                                                                                if (modAuto != '') {
                                                                                    cboCliente.value = '${auto.id_cliente}';
                                                                                    cboMarcas.value = '${auto.id_marca}';
                                                                                    cboModelos.value = '${auto.id_modelo}';
                                                                                    txtPatente.value = '${auto.patente}';
                                                                                    hiddenIdAuto.value = '${id_auto}'
                                                                                    banderaModificacion = true;
                                                                                    aceptar.value = 'Modificar';
                                                                                } else {
                                                                                    aceptar.value = 'Registrar auto'
                                                                                }
                                                                            }

                                    <c:choose>
                                        <c:when test = "${idCliente != null}">
                                                                            bandera = true;
                                        </c:when>
                                    </c:choose>
                                                                            if (!bandera) {
                                                                                var Autos = new Array();
                                    <c:forEach items="${lstAutosJs}" var="a">
                                                                                var auto = new Auto(${a.id_auto}, '${a.marca}', '${a.modelo}', '${a.patente}', ${a.id_cliente});
                                                                                Autos.push(auto);
                                    </c:forEach>
                                                                                //cargar combo
                                                                                cboCliente.addEventListener('change', (event) => {
                                                                                    ulListado.innerHTML = "";
                                                                                    var AutosFiltrados = new Array();
                                                                                    for (a of Autos) {
                                                                                        if (cboCliente.value == a.id_cliente) {
                                                                                            AutosFiltrados.push(a);
                                                                                        }
                                                                                    }
                                                                                    console.log(AutosFiltrados.length);
                                                                                    if (AutosFiltrados.length > 0) {
                                                                                        for (a of AutosFiltrados) {
                                                                                            document.getElementById('ulListado').innerHTML += "<li class=\"list-group-item bg-dark text-white\"><div class=\"row\"><div class=\"col\"><h4>"
                                                                                                    + a.patente + "</h4></div><div class=\"col\"><h4>"
                                                                                                    + a.marca + "</h4></div><div class=\"col\"><h4>"
                                                                                                    + a.modelo + "</h4></div><div class=\"col\"><a class=\"text-white\" href=\"#\" onclick=\"EliminarAuto("+ a.id_auto+")\"> Eliminar </a></div><div class=\"col\"><a class=\"text-white\""
                                                                                                    + "href=\"./RegistrarAutoClienteServlet?idAuto=" + a.id_auto + "\">Modificar</a></td></div><div class=\"col\"><a class=\"text-white\" "
                                                                                                    + "href=\"./HistorialFacturasServlet?idAuto="+a.id_auto+"&idCliente="+a.id_cliente+"\">Ver historial</a></div></div></li>";
                                                                                        }

                                                                                    } else {
                                                                                        ulListado.innerHTML += "<br/><h3 style=\"color: ivory;\">El cliente no tiene vehículo registrado</h3>";
                                                                                    }
                                                                                });
                                                                                //fin
                                                                            }

                                                                            cboModelos.addEventListener('change', (event) => {
                                                                                if (cboModelos.value == 1010) {
                                                                                    divModelo.style.display = "inline";
                                                                                    banderaModelo = true;
                                                                                } else {
                                                                                    divModelo.style.display = "none";
                                                                                    banderaModelo = false;
                                                                                }
                                                                            });

                                                                            cboMarcas.addEventListener('change', (event) => {
                                                                                var modelos = new Array();
                                    <c:forEach items="${lstModelos}" var="m">
                                                                                var modelo = new Ubicacion(${m.id_modeloAuto}, '${m.modeloAuto}', ${m.id_marcaAuto});
                                                                                modelos.push(modelo);
                                    </c:forEach>
                                                                                var modelosFiltrados = new Array();
                                                                                for (m of modelos) {
                                                                                    if (m.id_padre == cboMarcas.value) {
                                                                                        modelosFiltrados.push(m);
                                                                                    }
                                                                                }
                                                                                //CARGAR EL CBO MODELOS
                                                                                modelosFiltrados.sort(modelosFiltrados.descripcion);
                                                                                addOptions(cboModelos, modelosFiltrados);
                                                                                //AGREGARLE LA OPCIÓN OTRO MODELO
                                                                                var opcionOtroModelo = document.createElement('option');
                                                                                opcionOtroModelo.value = 1010;
                                                                                opcionOtroModelo.textContent = 'Otro modelo';
                                                                                cboModelos.add(opcionOtroModelo);
                                                                                //ORDERNARLO ALFABETICAMENTE
                                                                                var selectToSort = jQuery('#cboModelos');
                                                                                var optionActual = selectToSort.val();
                                                                                selectToSort.html(selectToSort.children('option').sort(function (a, b) {
                                                                                    return a.text === b.text ? 0 : a.text < b.text ? -1 : 1;
                                                                                })).val(optionActual);
                                                                                if (cboMarcas.value == 159) {
                                                                                    divMarca.style.display = "inline";
                                                                                    divModelo.style.display = "inline";
                                                                                    cboModelos.value = 1010;
                                                                                    banderaMarca = true;
                                                                                } else {
                                                                                    divMarca.style.display = "none";
                                                                                    divModelo.style.display = "none";
                                                                                    banderaMarca = false;
                                                                                }
                                                                            });
                                                                            function Enviar() {

                                                                                if (cboMarcas.value == -1 || cboModelos.value == -1) {
                                                                                    alert('Debe elegir marca y modelo');
                                                                                } else if ((cboMarcas.value == 159 &&
                                                                                        (txtMarca.value == '' || txtModelo.value == ''))
                                                                                        || (cboModelos.value == 1010 && txtModelo.value == '')) {
                                                                                    alert('Tiene que llenar los campos')

                                                                                } else {
                                                                                    if (txtPatente.value == '') {

                                                                                        alert('Debe ingresar la patente');
                                                                                    } else {
                                                                                        if (!bandera) {
                                                                                            if (banderaModificacion) {
                                                                                                hiddenAccion.value = "modificar";
                                                                                                document.frmRegistrarAuto.submit();
                                                                                            } else {
                                                                                                hiddenAccion.value = "alta";
                                                                                                if (cboCliente.value > 0) {
                                                                                                    document.frmRegistrarAuto.submit();
                                                                                                } else {
                                                                                                    alert('Debe seleccionar al menos un cliente');
                                                                                                    cboCliente.focus();
                                                                                                }
                                                                                            }
                                                                                        } else {
                                                                                            document.frmRegistrarAuto.submit();
                                                                                        }
                                                                                    }
                                                                                }
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
