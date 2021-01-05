<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nuevo presupuesto</title>
    </head>
    <body onload="Buscar()"> 
        <div class="container-fluid">
            <div class="row justify-content-center" style="min-height: 100%;">
                <div class="col-9">
                    <div class="container-fluid bg-secondary">
                        <div class="row" style="height: 100%;">
                            <nav class="col-md-2 d-none d-md-block bg-light sidebar pt-5">
                                <c:import url="menu.jsp" >
                                </c:import>
                            </nav>
                            <div class="col-md-10 bg-secondary"  style="height: 100%;margin-top: 100px;">

                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="margin-top: 60px!important;">
                                    <h1 class="titulares">Nuevo presupuesto</h1>
                                </div>
                                <div class="row" style="margin-top: 30px; margin-bottom: 50px; color: ivory; text-align: center;">
                                    <div class="col" style="display: inline-grid; width: 90%;">
                                        <span class="mt-2">Puede crear un nuevo presupuesto cargando primero al cliente y su vehículo.</span>
                                        <span class="mt-2"> Si el cliente es usuario final seleccione un vehículo, si no, elija entre los clientes registrados y sus respectivos vehículos</span> 
                                        <span class="mt-2">Luego seleccione los servicios del listado, para lo cual se puede ayudar para buscarlos por categoría o por nombre.</span>
                                        <span class="mt-2">En caso de que se requiera agregar un servicio extra a los servicios ya estandarizados haga click en "Agregar otro tipo de servicio"</span>
                                    </div>                    
                                </div>

                                <div class="row" id="divRbt" style="margin-top: 30px; margin-bottom: 50px;  color: ivory; text-align: center; font-size: 20px;">

                                    <div class="col">                       
                                        <div class="d-flex align-items-center" style="right: 10px; position: absolute;">
                                            <label class="form-check-label" for="rbtClienteFinal" style="padding-right: 10px;">Cliente usuario final </label>         
                                            <input type="radio" name="ElegirCliente"  id="rbtClienteFinal" value="option1" onclick="MostrarTipoCliente()" >
                                        </div>
                                    </div>

                                    <div class="col">
                                        <div class="d-flex align-items-center" style="left: 10px; position: absolute;">
                                            <input type="radio" name="ElegirCliente" id="rbtClienteRegistrado" value="option2" onclick="MostrarTipoCliente()">
                                            <label class="form-check-label" for="rbtClienteRegistrado" style="padding-left: 10px;"> Cliente registrado</label>
                                        </div>
                                    </div>   

                                </div>


                                <%-- ################ SI EL CLIENTE EXISTE #############################--%>
                                <div class="row" id="divClienteRegistrado" style="display: none;">
                                    <div class="col">
                                        <div class="row">
                                            <div class="col text-center">                            
                                                <label class="listadoHeader" for="cboCliente">Seleccione el cliente</label>
                                                <select class="form-control" id="cboCliente" name="idCliente"  style="width: 60%; margin-left: 50%; transform: translateX(-50%);">                        
                                                    <option value = "0">Despliegue para seleccionar</option>
                                                    <c:forEach items="${lstCliente}" var="ct">  
                                                        <option value="${ct.id}" ${ct.p.nombre eq ct.id?'selected':''}>${ct.p.toString()}
                                                        </option></c:forEach>
                                                    </select> 
                                                </div>
                                            </div>
                                            <div class="row" style="">
                                                <div class="col" style="margin-top: 20px; text-align: center; display: inline-grid">
                                                    <label class="listadoHeader" for="cboAutos" style="margin-bottom: 0px;">Seleccione uno de los vehículos del cliente</label>
                                                    <small style="color: ivory; margin-top: 0px; margin-bottom: 10px">Si el vehículo del cliente es nuevo haga click en registrar vehículo nuevo</small>
                                                    <div class="input-group mb-3" style="width: 60%;margin-left: 50%; transform: translateX(-50%);">
                                                        <select class="custom-select" id="cboAutos" name="idAuto">
                                                            <option value="0">Despliegue para seleccionar el vehículo</option>                                       
                                                        </select>
                                                        <div class="input-group-append">
                                                            <input type="button" class="btn btn-dark" for="inputGroupSelect02" onclick="RegistrarNuevoAutoParaCliente()" value="Registrar vehículo nuevo">
                                                        </div>
                                                    </div>                                 
                                                </div>
                                            </div>

                                        </div>
                                    </div>

                                    <div class="row" id="divResultadoAutoRegistrado" style="display: none;">
                                        <div class="col" style="display: inline-grid; color: ivory; font-size: 30px;">
                                            <h4 style="border-bottom: ivory 1px solid;">Datos del vehículo a reparar</h4>
                                            <strong id="lblCliente"></strong>
                                            <strong id="lblAuto"></strong>
                                        </div>
                                    </div>
                                <%-- ################ SI EL CLIENTE NO EXISTE #############################--%>
                                <div class="row" id="divClienteFinal" style="display: none;">
                                    <div class="col">
                                        <div class="row">
                                            <div class="col" style="width: 80%">
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
                                                <select  class="form-control" id="cboModelos" name="id_modelo" >                        
                                                    <option value = "0">Despliegue para seleccionar</option>                       
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
                                    </div>
                                    <div class="row text-center mt-5">
                                        <div class="col text-center">
                                            <label  class="listadoHeader" for="txtPatente">Ingrese la patente</label>
                                            <input type="text" class="form-control " name="patente" id="txtPatente" style="width:30%; margin-left: 50%; transform: translateX(-50%);">                        
                                        </div>
                                    </div>
                                </div>

                                <div class="row" id="divBotones" style="display: none">
                                    <div class="col text-center mt-4">
                                        <input type="button" id="btnRegistrarAuto" class="btn btn-dark" onclick="RegistrarAuto()" value="Registrar el vehículo para el presupuesto">
                                        <input type="button" id="btnCancelarAuto" class="btn btn-dark" onclick="CancelarRegistro()" value="Cambiar vehículo o cliente">
                                    </div>
                                </div>

                                <div class="row mt-5 bg-secondary" id="divCargaDeServicios" style="display: none;">                

                                    <div class="col-md-4 order-md-2 mb-4">
                                        <div class="border-bottom">
                                            <h4 style="color: ivory">Servicios cargados</h4>
                                        </div>

                                        <form method="POST" accion="./NuevoPresupuestoServlet" name="frmServicios">
                                            <div id="divCarritoServicios">
                                                <%--CARGA LOS SERVICIOS DESDE JS--%>


                                            </div>
                                            <div id="divCarritoCierre">
                                                <%--CARGA CLIENTE/AUTO CON LOS BOTONES DE SUBMIT Y CANCELAR DESDE JS--%>

                                            </div>
                                        </form>
                                    </div>

                                    <%-- ------------ LISTADO DE SERVICIOS ----------------------%>
                                    <div class="col-md-8 order-md-1" >
                                        <div class="border-bottom">
                                            <h4 style="color: ivory">Servicios</h4>
                                        </div>
                                        <div class="row mt-4 text-center mb-4">
                                            <div class="col">

                                                <label style="color: ivory" for="txtServicio">Buscar por nombre de servicio</label>
                                                <input class="form-control" type="text" placeholder="Hasta 100 caracteres." id="txtServicio" name="servicio" >  

                                            </div>
                                            <div class="col">

                                                <label style="color: ivory" for="cboCategoria">Elegir categoría de servicio</label>                               
                                                <select class="form-control" id="cboCategoria" name="id_categoria" onchange="Buscar()">                        
                                                    <option value = "0">Despliegue para seleccionar</option>
                                                    <c:forEach items="${lstCategorias}" var="c">  
                                                        <option value="${c.id_categoria}" ${categoria eq c.id_categoria?'selected':''}>${c}</option>                            
                                                    </c:forEach>                     
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row mt-4">
                                            <div class="col text-center">
                                                <input type="button" class="btn btn-dark mb-2 " onclick="Buscar()"  value="Buscar por parámetro">
                                                <input type="button" class="btn btn-dark mb-2 " onclick="CancelarBusqueda()"value="Cancelar busqueda">
                                                <input type="button" class="btn btn-dark mb-2 " id="btnAgregarServicio" value="Agregar otro tipo de servicio" onclick="ActivarCategoriaCinco()">
                                            </div>
                                        </div>

                                        <%-- ACÁ ARRANCA OTRO TIPO DE SERVICIO --%>
                                        <div class="row text-white text-center" id="divOtroTipo" style="display: none;">
                                            <div class="col">
                                                <div class="row">
                                                    <div class="col" >
                                                        <div class="row">
                                                            <div class="col">
                                                                <label for="txtNuevoServicio">Nombre del servicio</label>
                                                                <input class="form-control" type="text" placeholder="Hasta 100 caracteres." id="txtNuevoServicio" name="servicio" >                        
                                                            </div>
                                                        </div>

                                                        <div class="row">
                                                            <div class="col mt-3">
                                                                <label  for="spnMinutos">Duración en minutos del servicio</label>
                                                                <input class="form-control" type="number"  id="spnMinutos" name="tiempo" min="1">                        
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col">
                                                        <label for="txtDescripcion">Descripción del servicio</label>
                                                        <textarea class="form-control" name="descripcion" id="txtDescripcion" placeholder="La descripción puede ser de hasta 500 carácteres" style="resize: none; height: 123px;"></textarea>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col text-center mt-3">
                                                        <input type="button" class="btn btn-dark mb-2 " onclick="Enviar()" value="Aceptar">
                                                        <input type="button" class="btn btn-dark mb-2 " value="Cancelar" onclick="CancelarCategoriaCinco()">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <%-- FIN TIPO DE SERVICIO --%>

                                        <div class="mt-4 mb-5">
                                            <c:choose>
                                                <c:when test = "${lstServicios == null}">
                                                    <h4 style="color: ivory; text-align: center;">No hay servicios disponibles</h4>                                        
                                                </c:when>
                                                <c:otherwise>
                                                    <c:forEach items="${lstServicios}" var="s" >
                                                        <ul class="list-group" style="color: ivory;"  id="divListado">

                                                        </ul>
                                                    </c:forEach>                                                    
                                                        <div class="text-center">
                                                         <a class="nav-link" href="#" style="color: ivory; font-size: 25px;">Subir</a>                                                            
                                                        </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                                       


                                <script src="js/CombosUbicacion.js" type="text/javascript"></script>
                                <script src="js/NuevoRepuestoJs.js" type="text/javascript"></script>

                                <script type="text/javascript">
                                                            var divClienteRegistrado = document.getElementById('divClienteRegistrado');
                                                            var divClienteFinal = document.getElementById('divClienteFinal');
                                                            var rbtClienteRegistrado = document.getElementById('rbtClienteRegistrado');
                                                            var rbtClienteFinal = document.getElementById('rbtClienteFinal');
                                                            var cboAutos = document.getElementById('cboAutos');
                                                            var cboCliente = document.getElementById('cboCliente');
                                                            var divBotones = document.getElementById('divBotones');
                                                            var divResultadoAutoRegistrado = document.getElementById('divResultadoAutoRegistrado');
                                                            var lblCliente = document.getElementById('lblCliente');
                                                            var lblAuto = document.getElementById('lblAuto');
                                                            var btnRegistrarAuto = document.getElementById('btnRegistrarAuto');
                                                            var btnCancelarAuto = document.getElementById('btnCancelarAuto');
                                                            var divRbt = document.getElementById('divRbt');
                                                            var divCargaDeServicios = document.getElementById('divCargaDeServicios');
                                                            var bandera = false;
                                                            var banderaTipoCliente = false; //SI ESTA EN FALSA ES CT FINAL, SI NO CT REGISTRADO                        
                                                            var cboModelos = document.getElementById('cboModelos');
                                                            var cboMarcas = document.getElementById('cboMarcas');
                                                            var divMarca = document.getElementById('divMarca');
                                                            var divModelo = document.getElementById('divModelo');
                                                            var txtModelo = document.getElementById('txtModelo');
                                                            var txtMarca = document.getElementById('txtMarca');
                                                            var txtPatente = document.getElementById('txtPatente');
                                                            var banderaModelo = false;
                                                            var banderaMarca = false;
                                                            var divListado = document.getElementById("divListado");
                                                            var txtServicio = document.getElementById("txtServicio");
                                                            var cboCategoria = document.getElementById("cboCategoria");
                                                            var divOtroTipo = document.getElementById('divOtroTipo');
                                                            var txtNuevoServicio = document.getElementById('txtNuevoServicio');
                                                            var txtDescripcion = document.getElementById('txtDescripcion');
                                                            var spnMinutos = document.getElementById('spnMinutos');
                                                            var btnAgregarServicio = document.getElementById('btnAgregarServicio');
                                                            var ArreglosIdServicios = new Array();
                                                            var CarritoServicios = new Array();
                                                            var nuevoServicio = null;
                                                            var cancelacionNuevoServicio = false;
                                                            var lstServicios = new Array();
                                                            var casosClientes = 0;//ver detalles en método: CargarClienteAlServlet()
                                    <c:forEach items="${lstServicios}" var="s">
                                                            var s = new Servicio(${s.id_servicio}, ${s.categoria.id_categoria}, '${s.servicio}', '${s.descripcion}', ${s.tiempo}, ${s.precio});
                                                            lstServicios.push(s);
                                    </c:forEach>
                                                            var lstAuto = new Array();
                                    <c:forEach items="${lstAutos}" var="a">
                                                            var auto = new Ubicacion(${a.id_auto}, '${a.marca} ${a.modelo}, patente: ${a.patente}', ${a.id_cliente});
                                                                lstAuto.push(auto);
                                    </c:forEach>



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

                                                                function  RegistrarNuevoAutoParaCliente() {
                                                                    if (cboCliente.value > 0) {
                                                                        location.href = "./RegistrarAutoClienteServlet?idCliente=" + cboCliente.value;
                                                                    } else {
                                                                        alert('Para realizar esta acción, tiene que al menos seleccionar un cliente del desplegable');
                                                                        cboCliente.focus();
                                                                    }
                                                                }

                                                                function CancelarRegistro() {
                                                                    rbtClienteFinal.checked = false;
                                                                    rbtClienteRegistrado.checked = false;
                                                                    rbtClienteFinal.disabled = false;
                                                                    rbtClienteRegistrado.disabled = false;
                                                                    divClienteRegistrado.style.display = "none";
                                                                    divClienteFinal.style.display = "none";
                                                                    divResultadoAutoRegistrado.style.display = "none";
                                                                    bandera = false;
                                                                    btnRegistrarAuto.style.display = "none";
                                                                    btnCancelarAuto.style.display = "none";
                                                                    cboCliente.value = 0;
                                                                    cboAutos.value = -1;
                                                                    divCargaDeServicios.style.display = "none";
                                                                    CancelarServicios();
                                                                }

                                                                function RegistrarAuto() {
                                                                    var idCliente = 0;
                                    <c:choose>
                                        <c:when test="${idCliente != null}">
                                                                    idCliente = ${idCliente};
                                        </c:when>

                                    </c:choose>
                                                                    if (idCliente != 0) {
                                                                        if (cboAutos.value < 1) {
                                                                            alert('Debe seleccionar un vehículo');
                                                                        } else {
                                                                            bandera = true;
                                                                            divResultadoAutoRegistrado.style.display = "inline";
                                                                            rbtClienteFinal.disabled = true;
                                                                            rbtClienteRegistrado.disabled = true;
                                                                            btnRegistrarAuto.style.display = "none";
                                                                            divClienteRegistrado.style.display = "none";
                                                                            divClienteFinal.style.display = "none";
                                                                            divCargaDeServicios.style.display = "flex";
                                                                            CargarClienteAlServlet();
                                                                            var cliente = cboCliente.options[cboCliente.selectedIndex].text;
                                                                            var auto = cboAutos.options[cboAutos.selectedIndex].text;
                                                                            lblCliente.textContent = 'Cliente: ' + cliente;
                                                                            lblAuto.textContent = 'Vehículo: ' + auto;
                                                                            casosClientes = 4;

                                                                        }

                                                                    } else {
                                                                        var x = false; // SI ESTA EN TRUE PASÓ LAS VALIDACIONES
                                                                        if (!bandera) {
                                                                            if (banderaTipoCliente) {
                                                                                if (cboCliente.value != 0) {
                                                                                    if (cboAutos.value != -1) {
                                                                                        var cliente = cboCliente.options[cboCliente.selectedIndex].text;
                                                                                        var auto = cboAutos.options[cboAutos.selectedIndex].text;
                                                                                        lblCliente.textContent = 'Cliente: ' + cliente;
                                                                                        lblAuto.textContent = 'Vehículo: ' + auto;

                                                                                        casosClientes = 4;
                                                                                        x = true;

                                                                                    } else {
                                                                                        alert('Debe seleccionar al menos un vehículo')
                                                                                        cboAutos.focus();
                                                                                        divResultadoAutoRegistrado.style.display = "none";
                                                                                    }
                                                                                } else {
                                                                                    alert('Debe seleccionar al menos un cliente')
                                                                                    cboCliente.focus();
                                                                                    divResultadoAutoRegistrado.style.display = "none";
                                                                                }
                                                                            } else {
                                                                                if (cboMarcas.value == -1) {
                                                                                    alert("Tiene que elegir marca del vehículo a registrar");
                                                                                    cboMarcas.focus();

                                                                                } else if (cboModelos.value == -1) {
                                                                                    alert("Tiene que elegir modelo del vehículo a registrar");
                                                                                    cboMarcas.focus();
                                                                                } else if (txtPatente.value == '') {
                                                                                    alert("Tiene que ingresar la patente del vehículo a registrar");
                                                                                    txtPatente.focus();
                                                                                } else {
                                                                                    var marca = "";
                                                                                    var modelo = "";
                                                                                    if (cboMarcas.value == 159) {
                                                                                        marca = txtMarca.value;
                                                                                        modelo = txtModelo.value;
                                                                                        casosClientes = 3;

                                                                                    } else {
                                                                                        marca = cboMarcas.options[cboMarcas.selectedIndex].text;
                                                                                        if (cboModelos.value == 1010) {
                                                                                            modelo = txtModelo.value;
                                                                                            casosClientes = 2;
                                                                                        } else {
                                                                                            modelo = cboMarcas.options[cboMarcas.selectedIndex].text;
                                                                                            casosClientes = 1;
                                                                                        }
                                                                                    }
                                                                                    var auto = cboAutos.options[cboAutos.selectedIndex].text;
                                                                                    lblCliente.textContent = "Cliente usuario final";
                                                                                    lblAuto.textContent = 'Vehículo: ' + marca + ' ' + modelo + ', patente: ' + txtPatente.value;
                                                                                    x = true;
                                                                                }
                                                                            }
                                                                            if (x) {
                                                                                bandera = true;
                                                                                divResultadoAutoRegistrado.style.display = "inline";
                                                                                rbtClienteFinal.disabled = true;
                                                                                rbtClienteRegistrado.disabled = true;
                                                                                btnRegistrarAuto.style.display = "none";
                                                                                btnCancelarAuto.style.display = "inline";
                                                                                divClienteRegistrado.style.display = "none";
                                                                                divClienteFinal.style.display = "none";
                                                                                divCargaDeServicios.style.display = "flex";
                                                                                CargarClienteAlServlet();
                                                                            }
                                                                        } else {
                                                                            alert("Ya hay un vehículo registrado, cancele el registro para registrar otro")
                                                                        }
                                                                    }
                                                                }

                                                                function CargarClienteAlServlet() {
                                                                    var cadena = "";
                                                                    if (casosClientes == 1) {
                                                                        //caso1: ct tiene marca modelo registrado
                                                                        modelo = cboMarcas.value;
                                                                        marca = cboMarcas.value;
                                                                        patente = txtPatente.value;
                                                                        cadena = "<input type=\"hidden\" value=\"caso1\" name=\"casoCliente\">"
                                                                                + "<input type=\"hidden\" value=\"" + modelo + "\" name=\"idModelo\">"
                                                                                + "<input type=\"hidden\" value=\"" + marca + "\" name=\"idMarca\">"
                                                                                + "<input type=\"hidden\" value=\"" + patente + "\" name=\"patente\">";

                                                                    }
                                                                    if (casosClientes == 2) {
                                                                        //caso2: ct tiene marca registrada pero no el modelo
                                                                        modelo = txtModelo.value;
                                                                        marca = cboMarcas.value;
                                                                        patente = txtPatente.value;
                                                                        cadena = "<input type=\"hidden\" value=\"caso2\" name=\"casoCliente\">"
                                                                                + "<input type=\"hidden\" value=\"" + modelo + "\" name=\"nombreModelo\">"
                                                                                + "<input type=\"hidden\" value=\"" + marca + "\" name=\"idMarca\">"
                                                                                + "<input type=\"hidden\" value=\"" + patente + "\" name=\"patente\">";
                                                                    }
                                                                    if (casosClientes == 3) {
                                                                        //caso3: no tiene ni marca ni modelo registrado
                                                                        modelo = txtModelo.value;
                                                                        marca = txtMarca.value;
                                                                        patente = txtPatente.value;
                                                                        cadena = "<input type=\"hidden\" value=\"caso3\" name=\"casoCliente\">"
                                                                                + "<input type=\"hidden\" value=\"" + modelo + "\" name=\"nombreModelo\">"
                                                                                + "<input type=\"hidden\" value=\"" + marca + "\" name=\"nombreMarca\">"
                                                                                + "<input type=\"hidden\" value=\"" + patente + "\" name=\"patente\">";
                                                                    }
                                                                    if (casosClientes == 4) {
                                                                        //caso4: es un ct registrado con repectiva marca y modelo*/
                                                                        ct = cboCliente.value;
                                                                        aut = cboAutos.value;
                                                                        cadena = "<input type=\"hidden\" value=\"caso4\" name=\"casoCliente\">"
                                                                                + "<input type=\"hidden\" value=\"" + ct + "\" name=\"ct\">"
                                                                                + "<input type=\"hidden\" value=\"" + aut + "\" name=\"aut\">";
                                                                    }
                                                                    return cadena;
                                                                }

                                                                cboCliente.addEventListener('change', (event) => {
                                                                    var autosFiltrados = new Array();
                                                                    for (a of lstAuto) {
                                                                        if (a.id_padre == cboCliente.value) {
                                                                            autosFiltrados.push(a);
                                                                        }
                                                                    }

                                                                    autosFiltrados.sort(autosFiltrados.marca);

                                                                    addOptions(cboAutos, autosFiltrados);
                                                                });

                                                                function MostrarTipoCliente() {
                                                                    if (!bandera) {
                                                                        if (rbtClienteFinal.checked) {
                                                                            divClienteFinal.style.display = "inline";
                                                                            divBotones.style.display = "inline";
                                                                            btnRegistrarAuto.style.display = "inline";
                                                                            btnCancelarAuto.style.display = "none";
                                                                            banderaTipoCliente = false;
                                                                        } else {
                                                                            divClienteFinal.style.display = "none";
                                                                        }

                                                                        if (rbtClienteRegistrado.checked) {
                                                                            divClienteRegistrado.style.display = "inline";
                                                                            divBotones.style.display = "inline";
                                                                            btnRegistrarAuto.style.display = "inline";
                                                                            btnCancelarAuto.style.display = "none";
                                                                            banderaTipoCliente = true;
                                                                        } else {
                                                                            divClienteRegistrado.style.display = "none";
                                                                        }
                                                                    } else {
                                                                        alert('Ya hay un vehículo registrado, presione cambiar vehículo o cliente si quiere cambiar el registro');
                                                                        rbtClienteRegistrado.checked = false;
                                                                        rbtClienteFinal.checked = false;
                                                                        btnCancelarAuto.style.display = "inline";

                                                                    }
                                                                }

                                                                function ActivarCategoriaCinco() {
                                                                    divOtroTipo.style.display = "inline";
                                                                    btnAgregarServicio.style.display = "none";
                                                                    txtNuevoServicio.value = 'Otro servicio';
                                                                }

                                                                function CancelarCategoriaCinco() {
                                                                    txtDescripcion.value = '';
                                                                    txtNuevoServicio.value = '';
                                                                    spnMinutos.value = 0;
                                                                    btnAgregarServicio.style.display = "inline";
                                                                    divOtroTipo.style.display = "none";
                                                                }

                                                                function Buscar() {
                                                                    SiSeConoceAlCliente();
                                                                    var lstPrimerFiltro = new Array();
                                                                    var lstSegundoFiltro = new Array();
                                                                    divListado.innerHTML = "";
                                                                    if (cboCategoria.value != 0) {
                                                                        for (s of lstServicios) {
                                                                            if (s.id_categoria == cboCategoria.value) {
                                                                                lstPrimerFiltro.push(s);
                                                                            }
                                                                        }
                                                                    } else {
                                                                        lstPrimerFiltro = lstServicios;
                                                                    }

                                                                    if (txtServicio != '') {
                                                                        for (s of lstPrimerFiltro) {
                                                                            if (s.servicio.toLowerCase().includes(txtServicio.value.toLowerCase())) {
                                                                                lstSegundoFiltro.push(s);
                                                                            }
                                                                        }
                                                                    } else {
                                                                        lstSegundoFiltro = lstPrimerFiltro;
                                                                    }
                                                                    for (s of lstSegundoFiltro) {
                                                                        divListado.innerHTML += "<li class=\"list-group-item bg-dark  mt-3\"><div class=\"row\"><div class=\"col\" style=\"align-items: center; display: flex; margin-left: 20px;\"><h4>"
                                                                                + s.servicio + "</h4></div><div class=\"col\" style=\"align-items: center; display: flex; text-align: center;  width: 25%; height: 150px; overflow: auto;\"><span>"
                                                                                + s.descripcion + "</span></div><div class=\"col\" style=\"align-items: center; display: inline-grid; text-align: center;\"><span style=\"border: none !important\">$"
                                                                                + s.tiempo + " minutos</span><span  style=\"border: none !important\">$"
                                                                                + s.precio + "</span></div><div class=\"col\" style=\"align-items: center; display: flex; text-align: center;\"><input type=\"button\" class=\"btn btn-light text-white bg-dark\" value=\"Agregar servicio\" onclick=\"ValidarServicio("
                                                                                + s.id_servicio + ")\"></div></div>\n\</li>";
                                                                    }
                                                                }
                                                                function SiSeConoceAlCliente() {
                                    <c:choose>
                                        <c:when test="${idCliente != null}">
                                                                    var idCliente = ${idCliente};
                                                                    rbtClienteRegistrado.checked = true;
                                                                    rbtClienteFinal.disabled = true;
                                                                    btnCancelarAuto.style.display = "none";
                                                                    btnRegistrarAuto.style.display = "inline";
                                                                    cboCliente.value = idCliente;
                                                                    cboCliente.disabled = true;
                                                                    divClienteRegistrado.style.display = "inline";
                                                                    divBotones.style.display = "inline";


                                                                    var autosFiltrados = new Array();
                                                                    for (a of lstAuto) {
                                                                        if (a.id_padre == idCliente) {
                                                                            autosFiltrados.push(a);
                                                                        }
                                                                    }

                                                                    autosFiltrados.sort(autosFiltrados.marca);

                                                                    addOptions(cboAutos, autosFiltrados);

                                        </c:when>
                                    </c:choose>

                                                                }

                                                                function CancelarBusqueda() {
                                                                    cboCategoria.value = 0;
                                                                    txtServicio.value = '';
                                                                    Buscar();
                                                                }

                                                                function validarNuevoServicio() {
                                                                    if (txtNuevoServicio.value == '') {
                                                                        alert('Debe ingresar nombre del servicio nuevo');
                                                                        txtNuevoServicio.focus();
                                                                        return false;
                                                                    }
                                                                    if (txtDescripcion.value == '') {
                                                                        alert('Debe ingresar la descripción');
                                                                        txtDescripcion.focus();
                                                                        return false;
                                                                    }
                                                                    if (spnMinutos.value == 0 || spnMinutos.value < 0) {
                                                                        alert('Debe ingresar tiempo aproximado en minutos mayor a 0');
                                                                        spnMinutos.focus();
                                                                        return false;
                                                                    }
                                                                    return true;
                                                                }

                                                                function Enviar() {
                                                                    if (validarNuevoServicio()) {
                                                                        var precio = spnMinutos.value * 15;
                                                                        nuevoServicio = new Servicio(0, 5, txtNuevoServicio.value, txtDescripcion.value, Number(spnMinutos.value), precio);
                                                                        cancelacionNuevoServicio = true;
                                                                        ValidarServicio(0);
                                                                    }
                                                                }

                                                                function ServicioNuevo() {
                                                                    var cadena = "";
                                                                    if (cancelacionNuevoServicio) {
                                                                        cadena = "<input type=\"hidden\" value=\"" + nuevoServicio.servicio + "\" name=\"nombreServicioNuevo\">"
                                                                                + "<input type=\"hidden\" value=\"" + nuevoServicio.descripcion + "\" name=\"descripcionServicioNuevo\">"
                                                                                + "<input type=\"hidden\" value=\"" + nuevoServicio.tiempo + "\" name=\"tiempoServicioNuevo\">";
                                                                    }
                                                                    return cadena;
                                                                }

                                                                function ValidarServicio(id) {
                                                                    //LIMPIA EL CARRITO PARA QUE NO SE SOBREESCRIBAN LOS DATOS
                                                                    CarritoServicios.length = 0;
                                                                    //VALIDA QUE NO SE CARGUEN MÁS DE UNA VEZ LOS MISMOS SERVICIOS
                                                                    if (ArreglosIdServicios.length != 0) {

                                                                        if (ArreglosIdServicios.includes(id)) {
                                                                            alert('Ya se ha ingresado el servicio numero ' + id);
                                                                        } else {
                                                                            ArreglosIdServicios.push(id);
                                                                        }
                                                                    } else {
                                                                        ArreglosIdServicios.push(id);
                                                                    }
                                                                    //CARGA EL ARRAY DEL CARRITO CON SERVICIOS
                                                                    if (nuevoServicio !== null) {
                                                                        if (cancelacionNuevoServicio) {
                                                                            CarritoServicios.push(nuevoServicio);
                                                                        }
                                                                    }
                                                                    for (s of lstServicios) {
                                                                        for (id of ArreglosIdServicios) {
                                                                            if (s.id_servicio === id) {
                                                                                CarritoServicios.push(s);
                                                                                break;
                                                                            }
                                                                        }
                                                                    }

                                                                    ActualizarCarrito();

                                                                }

                                                                function ActualizarCarrito() {
                                                                    if (CarritoServicios.length != 0) {
                                                                        document.getElementById('divCarritoServicios').innerHTML = "";
                                                                        for (s of CarritoServicios) {
                                                                            document.getElementById('divCarritoServicios').innerHTML += "<ul class=\"list-group mb-3 mt-2\"><li class=\"list-group-item\"><div class=\"row mt-2 mb-2\"><div class=\"col\"><h6 class=\"my-0\">"
                                                                                    + s.servicio + "</h6></div><div class=\"col\"><span>"
                                                                                    + ObtenerNombreCategoria(s.id_categoria) + "</span></div></div>\n\<div class=\"row text-center\"><div class=\"col mt-3 mb-2\"><small class=\"text-muted\">Tiempo en minutos:"
                                                                                    + s.tiempo + "</small></div><div class=\"col\" style=\"text-align: center; align-items: center; display: flex; width: 50px;\"><span>Subtotal: $"
                                                                                    + s.precio + "</span></div></div>  <div class=\"row\"><div class=\"col\" style=\"width: 80%; text-align: center\"><input type=\"button\" class=\"btn btn-secondary\" value=\"Cancelar servicio\" onclick=\"RemoverServicio("
                                                                                    + s.id_servicio + ")\"> </div></div></li>"
                                                                                    + "<input type=\"hidden\" value=\"" + s.id_servicio + "\" name=\"idServicios\">";
                                                                        }

                                                                        var demora = 0;
                                                                        var precioFinal = 0;

                                                                        for (s of CarritoServicios) {
                                                                            demora += s.tiempo;
                                                                            precioFinal += s.precio;
                                                                        }
                                                                        document.getElementById('divCarritoCierre').innerHTML = "<li class=\"list-group-item\" style=\"text-align: center; margin-top: 10px;\"><div class=\"row\" style=\"display: table;\"><div class=\"col\"><span>Demora aproximada:</span></div><div class=\"col\" style=\"display: table-cell; vertical-align: middle;\"><strong>"
                                                                                + demora + " minutos </strong></div></div><div class=\"row\" style=\"display: table;\"><div class=\"col\"><span>Precio aproximado:</span></div><div class=\"col\" style=\"display: table-cell; vertical-align: middle;\"><strong> $"
                                                                                + precioFinal + "</strong></div></div><div class=\"row\" style=\"margin-top: 10px;\"><div class=\"col\"><input type=\"button\" class=\"btn btn-secondary\" style=\"width: 80%\" onclick=\"EnviarFormulario()\" value=\"Confirmar servicios\"></div></div><div class=\"row\" style=\"margin-top: 10px;\"><div class=\"col\"><button type=\"button\" class=\"btn btn-secondary\" style=\"width: 80%\" onclick=\"CancelarServicios()\">Cancelar servicios</button></div></div></li></ul>"
                                                                                + CargarClienteAlServlet() + " " + ServicioNuevo();

                                                                    } else {
                                                                        document.getElementById('divCarritoServicios').innerHTML = "";
                                                                        document.getElementById('divCarritoCierre').innerHTML = "";
                                                                    }

                                                                }

                                                                function RemoverServicio(id_servicio) {
                                                                    if (id_servicio == 0) {
                                                                        cancelacionNuevoServicio = false;
                                                                    }
                                                                    var posicionServicioId = ArreglosIdServicios.indexOf(id_servicio)
                                                                    ArreglosIdServicios.splice(posicionServicioId, 1);

                                                                    var posicionCarrito = 0;
                                                                    for (var i = 0; i < CarritoServicios.length; i++) {
                                                                        if (CarritoServicios[i].id_servicio === id_servicio) {
                                                                            posicionCarrito = i;
                                                                        }
                                                                    }
                                                                    CarritoServicios.splice(posicionCarrito, 1);
                                                                    ActualizarCarrito();
                                                                }

                                                                function ObtenerNombreCategoria(id_categoria) {
                                                                    if (id_categoria === 1) {
                                                                        return 'Servicios de frenos';
                                                                    }
                                                                    if (id_categoria === 2) {
                                                                        return 'Gomería';
                                                                    }
                                                                    if (id_categoria === 3) {
                                                                        return 'Tren delantero alineado y balanceo';
                                                                    }
                                                                    if (id_categoria === 4) {
                                                                        return 'Centrado de llanta';
                                                                    }
                                                                    if (id_categoria === 5) {
                                                                        return 'Otro servicio';
                                                                    } else
                                                                        return 'Otro';
                                                                }

                                                                function CancelarServicios() {
                                                                    CarritoServicios.length = 0;
                                                                    ArreglosIdServicios.length = 0;
                                                                    ActualizarCarrito();
                                                                }

                                                                function EnviarFormulario() {

                                                                    document.frmServicios.submit();
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
