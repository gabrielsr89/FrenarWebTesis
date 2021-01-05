<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administración de ubicaciones</title>
    </head>
    <body>
        <div class="container-fluid">
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
                                    <h1 class="titulares">Alta, baja y modificación de provincias, localidades y barrios</h1>
                                </div>

                                <div class="row">
                                    <div class="col text-center mt-5" id="divRbt">
                                        <input type="radio" name="accion" onclick="EleccionUno(true)" id="rbtAgregar" style="margin-right: 20px;"><label for="rbtAgregar" style="font-size: 25px;"> Agregar</label><br/>
                                        <input type="radio" name="accion" onclick="EleccionUno(true)" id="rbtModificar" style="margin-right: 20px;"><label for="rbtModificar" style="font-size: 25px;"> Modificar</label><br/>
                                        <input type="radio" name="accion" onclick="EleccionUno(true)" id="rbtEliminar" style="margin-right: 20px;"><label for="rbtEliminar" style="font-size: 25px;"> Eliminar </label><br/>
                                        <div class="mt-4">
                                            <a class="nav-link" href="./AdministrarBarriosClientes" style="color: ivory; font-size: 16px;">Administrar barrios ingresados por clientes (${cantidad})</a>
                                        </div>
                                    </div>
                                    <div class="col  text-center mt-5" id="divUbicaciones" style="display: none;">
                                        <input type="radio" name="ubicacion" onclick="EleccionDos()" id="rbtProvincia" style="margin-right: 20px;"><label for="rbtProvincia" style="font-size: 25px;"> Provincias</label><br/>
                                        <input type="radio" name="ubicacion" onclick="EleccionDos()" id="rbtLocalidad" style="margin-right: 20px;"><label for="rbtLocalidad" style="font-size: 25px;"> Localidades </label><br/>
                                        <input type="radio" name="ubicacion" onclick="EleccionDos()" id="rbtBarrio" style="margin-right: 20px;"><label for="rbtBarrio" style="font-size: 25px;"> Barrios </label><br/>

                                        <button class="btn btn-dark mt-5" onclick="EleccionUno(false)">Cancelar</button>
                                    </div>
                                    <div class="col">
                                        <div class="row">
                                            <div class="col mt-5" >
                                                <center>
                                                    <h3 id="tit">
                                                        Seleccione la acción (si es alta, baja o modificación)
                                                    </h3>  
                                                </center>                                                            
                                            </div>
                                        </div>

                                        <div class="row">
                                            <%--PROVINCIAS --%>
                                            <%--P_ALTA--%>
                                            <div  class="col text-center mt-5" id="divAgregarProvincia" style="display: none;">
                                                <label for="txtAgregarProvincia">Ingrese el nombre de la nueva provincia</label>
                                                <input class="input-group" type="text" id="txtAgregarProvincia">
                                            </div>
                                            <%--P_MOD--%>
                                            <div  class="col text-center mt-5" id="divModificarProvincia" style="display: none;">
                                                <label for="cboModificarProvincia">Provincia</label>                                                
                                                <select class="form-control" id="cboModificarProvincia" onchange="ModificarProvincia()">                        
                                                    <option value = "0">Despliegue para seleccionar</option>
                                                    <c:forEach items="${ptc}" var="p">  
                                                        <option value="${p.id_provincia}" ${provincia eq p.id_provincia?'selected':''}>${p}</option>                            
                                                    </c:forEach>                            
                                                </select>
                                                <label for="txtModificarProvincia">Modifique el nombre de la provincia seleccionada</label>
                                                <input class="input-group" type="text" id="txtModificarProvincia">

                                                <script type="text/javascript">
                                                    function ModificarProvincia() {
                                                        document.getElementById("txtModificarProvincia").value = $("#cboModificarProvincia option:selected").text();
                                                    }
                                                </script>
                                            </div>                                            
                                            <%--P_ELIMINAR--%>
                                            <div  class="col text-center mt-5" id="divEliminarProvincia" style="display: none;">
                                                <label for="cboEliminarProvincia">Provincia</label>                                                
                                                <select class="form-control" id="cboEliminarProvincia">                        
                                                    <option value = "0">Despliegue para seleccionar</option>
                                                    <c:forEach items="${ptc}" var="p">  
                                                        <option value="${p.id_provincia}" ${provincia eq p.id_provincia?'selected':''}>${p}</option>                            
                                                    </c:forEach>                            
                                                </select>
                                            </div>

                                            <%--LOCALIDADES --%>
                                            <%--L_ALTA --%>
                                            <div  class="col text-center mt-5" id="divAgregarLocalidad" style="display: none;">
                                                <label for="cboProvinciaAgregarLocalidad">Provincia</label>                                                
                                                <select class="form-control" id="cboProvinciaAgregarLocalidad" name="cboProvincia">                        
                                                    <option value = "0">Despliegue para seleccionar</option>
                                                    <c:forEach items="${ptc}" var="p">  
                                                        <option value="${p.id_provincia}" ${provincia eq p.id_provincia?'selected':''}>${p}</option>                            
                                                    </c:forEach>                            
                                                </select>
                                                <label for="txtAgregarLocalidad">Ingrese el nombre de la nueva localidad</label>
                                                <input class="input-group" type="text"  id="txtAgregarLocalidad">
                                            </div>
                                            <%--L_MOD --%>
                                            <div  class="col text-center mt-5" id="divModificarLocalidad" style="display: none;">

                                                <label for="cboProvinciaModificarLocalidad">Provincia</label>                                                
                                                <select class="form-control" id="cboProvinciaModificarLocalidad" name="cboProvincia">                        
                                                    <option value = "0">Despliegue para seleccionar</option>
                                                    <c:forEach items="${ptc}" var="p">  
                                                        <option value="${p.id_provincia}" ${provincia eq p.id_provincia?'selected':''}>${p}</option>                            
                                                    </c:forEach>                            
                                                </select>

                                                <label for="cboModificarLocalidad">Localidad</label>
                                                <select class="form-control" id="cboModificarLocalidad" onchange="ModificarLocalidad()">                        
                                                    <option value = "0">Seleccione una provincia</option>
                                                </select>
                                                <label for="txtModificarLocalidad">Modifique el nombre de la localidad seleccionada</label>
                                                <input class="input-group" type="text" id="txtModificarLocalidad">

                                                <script type="text/javascript">
                                                    function ModificarLocalidad() {
                                                        document.getElementById("txtModificarLocalidad").value = $("#cboModificarLocalidad option:selected").text();
                                                    }
                                                </script>
                                            </div>
                                            <%--L_ELIMINAR --%>
                                            <div  class="col text-center mt-5" id="divEliminarLocalidad" style="display: none;">
                                                <label for="cboProvinciaEliminarLocalidad">Provincia</label>                                                
                                                <select class="form-control" id="cboProvinciaEliminarLocalidad">                        
                                                    <option value = "0">Despliegue para seleccionar</option>
                                                    <c:forEach items="${ptc}" var="p">  
                                                        <option value="${p.id_provincia}" ${provincia eq p.id_provincia?'selected':''}>${p}</option>                            
                                                    </c:forEach>                            
                                                </select>

                                                <label for="cboEliminarLocalidad">Localidad</label>
                                                <select class="form-control" id="cboEliminarLocalidad">                        
                                                    <option value = "0">Seleccione una provincia</option>
                                                </select>
                                            </div>

                                            <%--BARRIOS --%>
                                            <%--B_ALTA --%>
                                            <div class="col text-center mt-5" id="divAgregarBarrio" style="display: none;">
                                                <label for="cboProvinciaAgregarBarrio">Provincia</label>                                                
                                                <select class="form-control" id="cboProvinciaAgregarBarrio">                        
                                                    <option value = "0">Despliegue para seleccionar</option>
                                                    <c:forEach items="${ptc}" var="p">  
                                                        <option value="${p.id_provincia}" ${provincia eq p.id_provincia?'selected':''}>${p}</option>                            
                                                    </c:forEach>                            
                                                </select>
                                                <label for="cboLocalidadAgregarBarrio">Localidad</label>
                                                <select class="form-control" id="cboLocalidadAgregarBarrio">                        
                                                    <option value = "0">Seleccione una provincia</option>
                                                </select>
                                                <label for="txtAgregarBarrio">Ingrese el nombre del nuevo barrio</label>
                                                <input class="input-group" type="text" name="provincia" id="txtAgregarBarrio">
                                            </div>
                                            <%--B_MOD --%>
                                            <div  class="col text-center mt-5" id="divModificarBarrio" style="display: none;">

                                                <label for="cboProvinciaModificarBarrio">Provincia</label>                                                
                                                <select class="form-control" id="cboProvinciaModificarBarrio">                        
                                                    <option value = "0">Despliegue para seleccionar</option>
                                                    <c:forEach items="${ptc}" var="p">  
                                                        <option value="${p.id_provincia}" ${provincia eq p.id_provincia?'selected':''}>${p}</option>                            
                                                    </c:forEach>                            
                                                </select>
                                                <label for="cboLocalidadModificarBarrio">Localidad</label>
                                                <select class="form-control" id="cboLocalidadModificarBarrio" >                        
                                                    <option value = "0">Seleccione una provincia</option>
                                                </select>
                                                <label for="cboModificarBarrio">Barrio</label>
                                                <select class="form-control" id="cboModificarBarrio" onchange="ModificarBarrio()">                        
                                                    <option value = "0">Seleccione una localidad</option>
                                                </select>
                                                <label for="txtModificarBarrio">Modifique el nombre del barrio seleccionado</label>
                                                <input class="input-group" type="text" name="provincia" id="txtModificarBarrio">


                                                <script type="text/javascript">
                                                    function ModificarBarrio() {
                                                        document.getElementById("txtModificarBarrio").value = $("#cboModificarBarrio option:selected").text();
                                                    }
                                                </script>

                                            </div>
                                            <%--B_ELIMINAR --%>
                                            <div  class="col text-center mt-5" id="divEliminarBarrio" style="display: none;">

                                                <label for="cboProvinciaEliminarBarrio">Provincia</label>                                                
                                                <select class="form-control" id="cboProvinciaEliminarBarrio">                        
                                                    <option value = "0">Despliegue para seleccionar</option>
                                                    <c:forEach items="${ptc}" var="p">  
                                                        <option value="${p.id_provincia}" ${provincia eq p.id_provincia?'selected':''}>${p}</option>                            
                                                    </c:forEach>                            
                                                </select>
                                                <label for="cboLocalidadEliminarBarrio">Localidad</label>
                                                <select class="form-control" id="cboLocalidadEliminarBarrio">                        
                                                    <option value = "0">Seleccione una provincia</option>
                                                </select>
                                                <label for="cboEliminarBarrio">Localidad</label>
                                                <select class="form-control" id="cboEliminarBarrio">                        
                                                    <option value = "0">Seleccione una localidad</option>
                                                </select>

                                            </div>
                                        </div>
                                        <div class="row" id="divBotones" style="display: none;">
                                            <div class="col text-center">
                                                <button class="btn btn-dark mt-5" onclick="Cancelar()">Cancelar</button>
                                                <button class="btn btn-dark mt-5" onclick="Enviar()">Aceptar</button>
                                            </div>
                                        </div>
                                    </div>  
                                </div>
                            </div>

                            <script src="js/CombosUbicacion.js" type="text/javascript"></script>
                            <script type="text/javascript">
                                                    //COMBOS
                                                    var cboModificarProvincia = document.getElementById("cboModificarProvincia");
                                                    var cboProvinciaAgregarLocalidad = document.getElementById("cboProvinciaAgregarLocalidad");
                                                    var cboProvinciaModificarLocalidad = document.getElementById("cboProvinciaModificarLocalidad");
                                                    var cboModificarLocalidad = document.getElementById("cboModificarLocalidad");
                                                    var cboProvinciaEliminarLocalidad = document.getElementById("cboProvinciaEliminarLocalidad");
                                                    var cboEliminarLocalidad = document.getElementById("cboEliminarLocalidad");
                                                    var cboProvinciaAgregarBarrio = document.getElementById("cboProvinciaAgregarBarrio");
                                                    var cboLocalidadAgregarBarrio = document.getElementById("cboLocalidadAgregarBarrio");
                                                    var cboProvinciaModificarBarrio = document.getElementById("cboProvinciaModificarBarrio");
                                                    var cboLocalidadModificarBarrio = document.getElementById("cboLocalidadModificarBarrio");
                                                    var cboModificarBarrio = document.getElementById("cboModificarBarrio");
                                                    var cboProvinciaEliminarBarrio = document.getElementById("cboProvinciaEliminarBarrio");
                                                    var cboLocalidadEliminarBarrio = document.getElementById("cboLocalidadEliminarBarrio");
                                                    var cboEliminarBarrio = document.getElementById("cboEliminarBarrio");
                                                    //OTRAS VARIABLES
                                                    var txtAgregarProvincia = document.getElementById("txtAgregarProvincia");
                                                    var txtModificarProvincia = document.getElementById("txtModificarProvincia");
                                                    var txtAgregarLocalidad = document.getElementById("txtAgregarLocalidad");
                                                    var txtModificarLocalidad = document.getElementById("txtModificarLocalidad");
                                                    var txtAgregarBarrio = document.getElementById("txtAgregarBarrio");
                                                    var txtModificarBarrio = document.getElementById("txtModificarBarrio");
                                                    //rbts
                                                    var rbtProvincia = document.getElementById('rbtProvincia');
                                                    var rbtLocalidad = document.getElementById('rbtLocalidad');
                                                    var rbtBarrio = document.getElementById('rbtBarrio');
                                                    var rbtAgregar = document.getElementById('rbtAgregar');
                                                    var rbtModificar = document.getElementById('rbtModificar');
                                                    var rbtEliminar = document.getElementById('rbtEliminar');
                                                    //divs
                                                    //provincia
                                                    var divAgregarProvincia = document.getElementById('divAgregarProvincia');
                                                    var divModificarProvincia = document.getElementById('divModificarProvincia');
                                                    var divEliminarProvincia = document.getElementById('divEliminarProvincia');
                                                    //localidades
                                                    var divAgregarLocalidad = document.getElementById('divAgregarLocalidad');
                                                    var divModificarLocalidad = document.getElementById('divModificarLocalidad');
                                                    var divEliminarLocalidad = document.getElementById('divEliminarLocalidad');
                                                    //barrios
                                                    var divAgregarBarrio = document.getElementById('divAgregarBarrio');
                                                    var divModificarBarrio = document.getElementById('divModificarBarrio');
                                                    var divEliminarBarrio = document.getElementById('divEliminarBarrio');
                                                    //otros divs
                                                    var divRbt = document.getElementById('divRbt');
                                                    var divBotones = document.getElementById('divBotones');
                                                    var divUbicaciones = document.getElementById('divUbicaciones');

                                                    var tit = document.getElementById('tit');
                                                    var accion = '';
                                                    var ubicacion = '';
                                                    
                                                    //-METODOS/EVENTOS- PARA CARGAR COMBOS
                                                    //agregar
                                                    cboProvinciaAgregarBarrio.addEventListener('change', function () {
                                                        cambiarLocalidadesPorProvincia(cboProvinciaAgregarBarrio, cboLocalidadAgregarBarrio);
                                                    });
                                                    //modificar
                                                    //m_localidad
                                                    cboProvinciaModificarLocalidad.addEventListener('change', function () {
                                                        cambiarLocalidadesPorProvincia(cboProvinciaModificarLocalidad, cboModificarLocalidad);
                                                    });
                                                    //m_barrio
                                                    cboProvinciaModificarBarrio.addEventListener('change', function () {
                                                        cambiarLocalidadesPorProvincia(cboProvinciaModificarBarrio, cboLocalidadModificarBarrio);
                                                    });
                                                    cboLocalidadModificarBarrio.addEventListener('change', function () {
                                                        cambiarBarriosPorLocalidad(cboLocalidadModificarBarrio, cboModificarBarrio);
                                                    });
                                                    //eliminar
                                                    //e_localidad
                                                    cboProvinciaEliminarLocalidad.addEventListener('change', function () {
                                                        cambiarLocalidadesPorProvincia(cboProvinciaEliminarLocalidad, cboEliminarLocalidad);
                                                    });
                                                    //e_barrios
                                                    cboProvinciaEliminarBarrio.addEventListener('change', function () {
                                                        cambiarLocalidadesPorProvincia(cboProvinciaEliminarBarrio, cboLocalidadEliminarBarrio);
                                                    });
                                                    cboLocalidadEliminarBarrio.addEventListener('change', function () {
                                                        cambiarBarriosPorLocalidad(cboLocalidadEliminarBarrio, cboEliminarBarrio);
                                                    });
                                                    function cambiarLocalidadesPorProvincia(comboProvincia, comboLocalidad) {
                                                        var localidades = new Array();

                                <c:forEach items="${lpjs}" var="l">
                                                        var localidad = new Ubicacion(${l.id_localidad}, '${l.localidad}', ${l.id_provincia});
                                                        localidades.push(localidad);
                                </c:forEach>
                                                        var localidadesFiltradas = new Array();
                                                        for (l of localidades) {
                                                            if (l.id_padre == comboProvincia.value) {
                                                                localidadesFiltradas.push(l);
                                                            }
                                                        }
                                                        localidadesFiltradas.sort(localidadesFiltradas.descripcion);
                                                        addOptions(comboLocalidad, localidadesFiltradas);
                                                    }

                                                    function cambiarBarriosPorLocalidad(comboLocalidad, comboBarrio) {
                                                        var barrios = new Array();
                                <c:forEach items="${bpjs}" var="b">
                                                        var barrio = new Ubicacion(${b.id_barrio}, '${b.barrio}', ${b.id_localidad});
                                                        barrios.push(barrio);
                                </c:forEach>
                                                        var barriosFiltrados = new Array();
                                                        for (b of barrios) {
                                                            if (b.id_padre == comboLocalidad.value) {
                                                                barriosFiltrados.push(b);
                                                            }
                                                        }
                                                        addOptions(comboBarrio, barriosFiltrados);
                                                    }

                                                    function resetearCombo(domElementCombo, x) {
                                                        var texto = '';
                                                        if (x) {
                                                            texto = 'Seleccione una provincia';
                                                        } else {
                                                            texto = 'Seleccione una localidad';
                                                        }
                                                        if (domElementCombo.options.length != 0) {
                                                            //PRIMERO LIMPIO EL COMBO DE LOS ELEMENTOS QUE TENGA
                                                            for (let i = domElementCombo.options.length; i >= 0; i--) {
                                                                domElementCombo.remove(i);
                                                            }
                                                            //CREO UN OPTION CON PRIMERA POSICIÓN
                                                            var opcion = document.createElement('option');
                                                            opcion.value = 0;
                                                            opcion.textContent = texto;
                                                            domElementCombo.add(opcion);
                                                        }
                                                    }

                                                    function Cancelar() {
                                                        EleccionUno(true);
                                                        divBotones.style.display = "none";
                                                        ubicacion = '';
                                                        divAgregarProvincia.style.display = "none";
                                                        divModificarProvincia.style.display = "none";
                                                        divEliminarProvincia.style.display = "none";
                                                        divAgregarLocalidad.style.display = "none";
                                                        divModificarLocalidad.style.display = "none";
                                                        divEliminarLocalidad.style.display = "none";
                                                        divAgregarBarrio.style.display = "none";
                                                        divModificarBarrio.style.display = "none";
                                                        divEliminarBarrio.style.display = "none";
                                                        //CANCELAR FORM
                                                        txtAgregarProvincia.value = '';
                                                        txtModificarProvincia.value = '';
                                                        txtAgregarLocalidad.value = '';
                                                        txtModificarLocalidad.value = '';
                                                        txtAgregarBarrio.value = '';
                                                        txtModificarBarrio.value = '';
                                                        //resetCBO
                                                        cboModificarProvincia.value = 0;
                                                        cboEliminarProvincia.value = 0;
                                                        cboProvinciaAgregarLocalidad.value = 0;
                                                        cboProvinciaModificarLocalidad.value = 0;
                                                        cboProvinciaEliminarLocalidad.value = 0;
                                                        cboProvinciaAgregarBarrio.value = 0;
                                                        cboProvinciaModificarBarrio.value = 0;
                                                        cboProvinciaEliminarBarrio.value = 0;
                                                        resetearCombo(cboModificarLocalidad, true)
                                                        resetearCombo(cboEliminarLocalidad, true)
                                                        resetearCombo(cboLocalidadAgregarBarrio, true)
                                                        resetearCombo(cboLocalidadModificarBarrio, true)
                                                        resetearCombo(cboLocalidadEliminarBarrio, true)
                                                        resetearCombo(cboModificarBarrio, false)
                                                        resetearCombo(cboEliminarBarrio, false)
                                                        //FIN CANCELAR FORM
                                                    }
                                                    
                                                    function Enviar() {
                                                        var id = 0;
                                                        var texto = '';
                                                        var id_padre = 0;                                                        

                                                        if (accion === 'agregar') {
                                                            if (ubicacion === 'provincia') {
                                                                accion = 'agregarProvincia';
                                                                texto = txtAgregarProvincia.value;
                                                                id_padre = 0;
                                                                id = 0;
                                                            }
                                                            if (ubicacion === 'localidad') {
                                                                accion = 'agregarLocalidad';
                                                                texto = txtAgregarLocalidad.value;
                                                                id_padre = cboProvinciaAgregarLocalidad.value;
                                                                id = 0;
                                                            }
                                                            if (ubicacion === 'barrio') {
                                                                accion = 'agregarBarrio';
                                                                texto = txtAgregarBarrio.value;
                                                                id_padre = cboLocalidadAgregarBarrio.value;
                                                                id = 0;
                                                            }
                                                        }
                                                        if (accion === 'modificar') {
                                                            if (ubicacion === 'provincia') {
                                                                accion = 'modificarProvincia';
                                                                texto = txtModificarProvincia.value;
                                                                id_padre = 0;
                                                                id = cboModificarProvincia.value;
                                                            }
                                                            if (ubicacion === 'localidad') {
                                                                accion = 'modificarLocalidad';
                                                                texto = txtModificarLocalidad.value;
                                                                id_padre = cboProvinciaModificarLocalidad.value;
                                                                id = cboModificarLocalidad.value;
                                                            }
                                                            if (ubicacion === 'barrio') {
                                                                accion = 'modificarBarrio';
                                                                texto = txtModificarBarrio.value;
                                                                id_padre = cboLocalidadModificarBarrio.value;
                                                                id = cboModificarBarrio.value;
                                                            }
                                                        }
                                                        if (accion === 'eliminar') {
                                                            if (ubicacion === 'provincia') {
                                                                accion = 'eliminarProvincia';
                                                                texto = '';
                                                                id_padre = 0;
                                                                id = cboEliminarProvincia.value;
                                                            }
                                                            if (ubicacion === 'localidad') {
                                                                accion = 'eliminarLocalidad';
                                                                texto = '';
                                                                id_padre = 0;
                                                                id = cboEliminarLocalidad.value;
                                                            }
                                                            if (ubicacion === 'barrio') {
                                                                accion = 'eliminarBarrio';
                                                                texto = '';
                                                                id_padre = 0;
                                                                id = cboEliminarBarrio.value;
                                                            }
                                                        }
                                                        $.ajax({
                                                            type: "POST",
                                                            url: "AdministrarUbicacionesServlet",
                                                            data: {id: id, texto: texto, id_padre: id_padre, accion: accion},
                                                            success: function (data, textStatus, jqXHR) {
                                                                if (data != 'true') {
                                                                    alert('No se permite realizar esta acción ya que compomete la integridad del sistema. Para más información contactese con el técnico')
                                                                } else {
                                                                    alert('Solicitud procesada exitosamente');
                                                                    location.reload();
                                                                }
                                                            },
                                                            error: function (jqXHR) {
                                                                console.log("error")
                                                            }
                                                        });                                                        
                                                        Cancelar();
                                                        EleccionUno(false);
                                                    }

                                                    function EleccionUno(x) {
                                                        if (x) {
                                                            if (rbtAgregar.checked) {
                                                                accion = 'agregar';
                                                                tit.innerHTML = 'Elija entre provincia, localidad o barrio para realizar el alta';
                                                            }
                                                            if (rbtModificar.checked) {
                                                                accion = 'modificar';
                                                                tit.innerHTML = 'Elija entre provincia, localidad o barrio para realizar la modificación';
                                                            }
                                                            if (rbtEliminar.checked) {
                                                                accion = 'eliminar';
                                                                tit.innerHTML = 'Elija entre provincia, localidad o barrio para eliminar el registro';
                                                            }
                                                            divRbt.style.display = "none";
                                                            divUbicaciones.style.display = "inline";
                                                            rbtProvincia.checked = false;
                                                            rbtLocalidad.checked = false;
                                                            rbtBarrio.checked = false;

                                                        } else {
                                                            Cancelar();
                                                            rbtAgregar.checked = false;
                                                            rbtModificar.checked = false;
                                                            rbtEliminar.checked = false;
                                                            tit.innerHTML = 'Seleccione la acción (si es alta, baja o modificación)';
                                                            divRbt.style.display = "inline";
                                                            divUbicaciones.style.display = "none";
                                                            accion = '';
                                                        }
                                                    }

                                                    function EleccionDos() {
                                                        divBotones.style.display = "inline";
                                                        if (rbtProvincia.checked) {
                                                            ubicacion = 'provincia';
                                                        }
                                                        if (rbtLocalidad.checked) {
                                                            ubicacion = 'localidad';
                                                        }
                                                        if (rbtBarrio.checked) {
                                                            ubicacion = 'barrio';
                                                        }
                                                        //provincias
                                                        if (rbtProvincia.checked && accion === 'agregar') {

                                                            divAgregarProvincia.style.display = "inline";
                                                            tit.innerHTML = "Agregar una provincia";

                                                            divModificarProvincia.style.display = "none";
                                                            divEliminarProvincia.style.display = "none";
                                                            divAgregarLocalidad.style.display = "none";
                                                            divModificarLocalidad.style.display = "none";
                                                            divEliminarLocalidad.style.display = "none";
                                                            divAgregarBarrio.style.display = "none";
                                                            divModificarBarrio.style.display = "none";
                                                            divEliminarBarrio.style.display = "none";

                                                        }
                                                        if (rbtProvincia.checked && accion === 'modificar') {
                                                            divModificarProvincia.style.display = "inline";
                                                            tit.innerHTML = "Modificar una provincia";

                                                            divAgregarProvincia.style.display = "none";
                                                            divEliminarProvincia.style.display = "none";
                                                            divAgregarLocalidad.style.display = "none";
                                                            divModificarLocalidad.style.display = "none";
                                                            divEliminarLocalidad.style.display = "none";
                                                            divAgregarBarrio.style.display = "none";
                                                            divModificarBarrio.style.display = "none";
                                                            divEliminarBarrio.style.display = "none";

                                                        }
                                                        if (rbtProvincia.checked && accion === 'eliminar') {
                                                            divEliminarProvincia.style.display = "inline";
                                                            tit.innerHTML = "Eliminar una provincia";

                                                            divAgregarProvincia.style.display = "none";
                                                            divModificarProvincia.style.display = "none";
                                                            divAgregarLocalidad.style.display = "none";
                                                            divModificarLocalidad.style.display = "none";
                                                            divEliminarLocalidad.style.display = "none";
                                                            divAgregarBarrio.style.display = "none";
                                                            divModificarBarrio.style.display = "none";
                                                            divEliminarBarrio.style.display = "none";
                                                        }
                                                        //localidades
                                                        if (rbtLocalidad.checked && accion === 'agregar') {
                                                            divAgregarLocalidad.style.display = "inline";
                                                            tit.innerHTML = "Agregar una localidad";

                                                            divAgregarProvincia.style.display = "none";
                                                            divModificarProvincia.style.display = "none";
                                                            divEliminarProvincia.style.display = "none";
                                                            divModificarLocalidad.style.display = "none";
                                                            divEliminarLocalidad.style.display = "none";
                                                            divAgregarBarrio.style.display = "none";
                                                            divModificarBarrio.style.display = "none";
                                                            divEliminarBarrio.style.display = "none";
                                                        }
                                                        if (rbtLocalidad.checked && accion === 'modificar') {
                                                            divModificarLocalidad.style.display = "inline";
                                                            tit.innerHTML = "Modificar una localidad";

                                                            divAgregarProvincia.style.display = "none";
                                                            divModificarProvincia.style.display = "none";
                                                            divEliminarProvincia.style.display = "none";
                                                            divAgregarLocalidad.style.display = "none";
                                                            divEliminarLocalidad.style.display = "none";
                                                            divAgregarBarrio.style.display = "none";
                                                            divModificarBarrio.style.display = "none";
                                                            divEliminarBarrio.style.display = "none";
                                                        }
                                                        if (rbtLocalidad.checked && accion === 'eliminar') {
                                                            divEliminarLocalidad.style.display = "inline";
                                                            tit.innerHTML = "Eliminar una localidad";

                                                            divAgregarProvincia.style.display = "none";
                                                            divModificarProvincia.style.display = "none";
                                                            divEliminarProvincia.style.display = "none";
                                                            divAgregarLocalidad.style.display = "none";
                                                            divModificarLocalidad.style.display = "none";
                                                            divAgregarBarrio.style.display = "none";
                                                            divModificarBarrio.style.display = "none";
                                                            divEliminarBarrio.style.display = "none";
                                                        }
                                                        //barrios
                                                        if (rbtBarrio.checked && accion === 'agregar') {
                                                            divAgregarBarrio.style.display = "inline";
                                                            tit.innerHTML = "Agregar un barrio";

                                                            divAgregarProvincia.style.display = "none";
                                                            divModificarProvincia.style.display = "none";
                                                            divEliminarProvincia.style.display = "none";
                                                            divAgregarLocalidad.style.display = "none";
                                                            divModificarLocalidad.style.display = "none";
                                                            divEliminarLocalidad.style.display = "none";
                                                            divModificarBarrio.style.display = "none";
                                                            divEliminarBarrio.style.display = "none";
                                                        }
                                                        if (rbtBarrio.checked && accion === 'modificar') {
                                                            divModificarBarrio.style.display = "inline";
                                                            tit.innerHTML = "Modificar un barrio";

                                                            divAgregarProvincia.style.display = "none";
                                                            divModificarProvincia.style.display = "none";
                                                            divEliminarProvincia.style.display = "none";
                                                            divAgregarLocalidad.style.display = "none";
                                                            divModificarLocalidad.style.display = "none";
                                                            divEliminarLocalidad.style.display = "none";
                                                            divAgregarBarrio.style.display = "none";
                                                            divEliminarBarrio.style.display = "none";

                                                        }
                                                        if (rbtBarrio.checked && accion === 'eliminar') {
                                                            divEliminarBarrio.style.display = "inline";
                                                            tit.innerHTML = "Eliminar un barrio";

                                                            divAgregarProvincia.style.display = "none";
                                                            divModificarProvincia.style.display = "none";
                                                            divEliminarProvincia.style.display = "none";
                                                            divAgregarLocalidad.style.display = "none";
                                                            divModificarLocalidad.style.display = "none";
                                                            divEliminarLocalidad.style.display = "none";
                                                            divAgregarBarrio.style.display = "none";
                                                            divModificarBarrio.style.display = "none";
                                                        }
                                                    }

                            </script>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
