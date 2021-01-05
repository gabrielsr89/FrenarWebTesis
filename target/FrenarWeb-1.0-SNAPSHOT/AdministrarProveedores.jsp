<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administración de proveedores</title>
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
                            <div class="col-md-10"  style="overflow: hidden; margin-top: 100px; color: ivory;">

                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="margin-top: 60px!important;">
                                    <h1 class="titulares">Administración de proveedores</h1>
                                </div>
                                <div class="row" style="margin-top: 30px; margin-bottom: 50px; color: ivory; text-align: center;">
                                    <div class="col" style="display: inline-grid; width: 90%;">
                                        <span class="mt-2">Alta o modificación de proveedores.</span>
                                    </div>                
                                </div>

                                <form method="POST" action="./AdministrarProveedoresServlet" name="frmProveedor">
                                    <input type="hidden" value="${id_prov}" name="id_prov">
                                    <div class="row" style="color: ivory;">                
                                        <%-- DATOS GENERALES Y DE CONTACTO --%>
                                        <div class="col">
                                            <div class="row">
                                                <div class="col mb-3">
                                                    <label for="proveedor">Nombre de proveedor</label>
                                                    <input type="text" class="form-control" id="proveedor" placeholder="Ingresar nombre" name="proveedor" value="${prov.proveedor}">
                                                </div>                        
                                            </div>                    
                                            <div class="row">
                                                <div class="col">
                                                    <label class="mt-2" for="email">Email</label>
                                                    <input type="text" class="form-control" id="email" placeholder="Ingresar email" name="email" value="${prov.p.email}">
                                                    <label class="mt-2" for="telefono" >Teléfono de contacto</label>
                                                    <input type="text" class="form-control" id="telefono" placeholder="Ingresar teléfono de contacto" name="telefono" value="${prov.p.telefono}">
                                                    <label class="mt-2" for="pagina">Página</label>
                                                    <input type="text" class="form-control" id="pagina" placeholder="Ingresar página" name="pagina" value="${prov.pagina}">
                                                </div>                        
                                            </div> 
                                        </div>

                                        <%-- DOMICILIO --%>
                                        <div class="col">
                                            <div class="row">
                                                <div class="col">
                                                    <label for="cboProvincia">Provincia</label>
                                                    <select class="form-control mb-2" id="cboProvincia" name="idProvincia" value="${prov.p.provincia.id_provincia}">                        
                                                        <option value = "0">Despliegue para seleccionar</option>
                                                        <c:forEach items="${ptc}" var="p">  
                                                            <option value="${p.id_provincia}" ${provincia eq p.id_provincia?'selected':''}>${p}</option>                            
                                                        </c:forEach>                            
                                                    </select>
                                                    <label for="cboLocalidad">Localidad</label>
                                                    <select class="form-control mb-2" id="cboLocalidad" name="idLocalidad" value="${prov.p.localidad.id_localidad}">                        
                                                        <option value = "0">Despliegue para seleccionar</option> 
                                                        <c:forEach items="${lstLocalidades}" var="l">  
                                                            <option value="${l.id_localidad}" ${localidad eq l.id_localidad?'selected':''}>${l}</option>                            
                                                        </c:forEach>   
                                                    </select>
                                                    <label for="cboBarrio">Barrio</label>
                                                    <select class="form-control" id="cboBarrio" name="idBarrio" value="${prov.p.barrio.id_barrio}">                        
                                                        <option value = "0">Despliegue para seleccionar</option>   
                                                        <c:forEach items="${lstBarrios}" var="b">  
                                                            <option value="${b.id_barrio}" ${barrio eq b.id_barrio?'selected':''}>${b}</option>                            
                                                        </c:forEach>   
                                                    </select>
                                                </div>                        
                                            </div>                    
                                            <div class="row mt-4">
                                                <div class="col-8">
                                                    <label for="direccion">Dirección</label>
                                                    <input type="text" class="form-control" id="direccion" placeholder="Ingresar dirección" name="direccion" value="${prov.p.domicilio}">
                                                </div>                        
                                                <div class="col-4">
                                                    <label for="cp">CP</label>
                                                    <input type="text" class="form-control" id="cp" placeholder="Ingresar CP" name="cp" value="${prov.p.cp}">                                
                                                </div>                    
                                            </div>                    
                                        </div>                
                                    </div>
                                    <div class="row mt-5">
                                        <div class="col text-center">
                                            <button type="button" class="btn btn-dark" onclick="enviar()"style="width: 50%">Aceptar</button>
                                            <button type="button" class="btn btn-dark" id="cancelarButton" onClick="location.href = './AdministrarProveedoresServlet'" style="width: 30%">Cancelar</button>
                                        </div>
                                    </div>                    
                                </form>
                                <%-- LISTADO --%>
                                <div style="color: ivory" >
                                    <div class="mt-5 mb-2" style="border-bottom: 1px solid ivory">
                                        <h1 style="color: ivory;">
                                            Listado de proveedores
                                        </h1>
                                    </div>
                                    <div style="margin-bottom: 40px;">
                                        <small style="font-size: 18px;">
                                            Puede paramétrizar el listado y elegir si quiere un listado con todos los proveedores,
                                            donde también puede eliminar o modificar al proveedor seleccionado, 
                                            o bien puede elegir ver un listado de los proveedores ordenados por marcas o rubros, 
                                            teniendo en cuenta que cualquiera de los casos los proveedores 
                                            van a ser mostrados de los más a los menos frecuentados.
                                        </small>                    
                                    </div>

                                    <div class="row mb-5">
                                        <div class="col">
                                            <div style="display: inline-flex">                            
                                                <select class="form-control mb-2" id="cboListado" >      
                                                    <option value="0"> Todos los proveedores</option>
                                                    <option value="1"> Proveedores por rubro</option>
                                                    <option value="2"> Proveedores por marca</option>
                                                </select>
                                                <button type="button" class="btn btn-dark mt5" onclick="Envio()" id="btnEnviar" style="height: 87% ">Buscar</button>
                                            </div>
                                            <script type="text/javascript">
                                                var cboListado = document.getElementById('cboListado')
                                                function Envio() {
                                                    location.href = './AdministrarProveedoresServlet?numListado=' + cboListado.value;
                                                }
                                            </script>
                                        </div>
                                    </div>            
                                    <div class="row">
                                        <div class="col">
                                            <c:choose>
                                                <c:when test="${lstProv == null}">
                                                    <h2>
                                                        No hay proveedores registrados.
                                                    </h2>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test = "${opcion == 'todos'}">
                                                            <h2>
                                                                Listado de todos los proveedores
                                                            </h2>

                                                            <table class="table table-dark">
                                                                <thead>
                                                                    <tr>
                                                                        <th scope="col">Número de proveedor</th>
                                                                        <th scope="col">Nombre del proveedor</th>
                                                                        <th scope="col">Contacto</th>
                                                                        <th scope="col">Ubicación</th>
                                                                        <th scope="col">Eliminar</th>
                                                                        <th scope="col">Modificar</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <c:forEach items="${lstProv}" var="p" >
                                                                        <tr>                                        
                                                                            <td>${p.id}</td>
                                                                            <td>${p.proveedor}</td>
                                                                            <td>Teléfono: ${p.p.telefono}<br/> Email: ${p.p.email}<br/><c:choose><c:when test="${p.pagina != 'No dispone'}"><a href="${p.pagina}">${p.pagina}</a></c:when><c:otherwise>Pagina: ${p.pagina}</c:otherwise></c:choose></td>
                                                                            <td>${p.p.domicilio}<br/>Barrio: ${p.p.barrio.toString()}</td>
                                                                            <td><a href="#" onclick="Eliminar(${p.id})">Eliminar</a></td>
                                                                            <td><a href="./AdministrarProveedoresServlet?idProveedor=${p.id}">Modificar</a></td>
                                                                        </tr>      
                                                                    </c:forEach>
                                                                </tbody>
                                                            </table>
                                                        </c:when>
                                                    </c:choose>

                                                    <c:choose>
                                                        <c:when test = "${opcion == 'rubros'}">
                                                            <h2>
                                                                Listado de proveedores por rubros
                                                            </h2>
                                                            <table class="table table-dark">
                                                                <thead>
                                                                    <tr>
                                                                        <th scope="col">Rubro</th>
                                                                        <th scope="col">Número de proveedor</th>
                                                                        <th scope="col">Nombre del proveedor</th>
                                                                        <th scope="col">Contacto</th>
                                                                        <th scope="col">Ubicación</th>                                          
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <c:forEach items="${lstProv}" var="p" >
                                                                        <tr>       
                                                                            <td>${p.descripcion_aux}</td>
                                                                            <td>${p.id}</td>
                                                                            <td>${p.proveedor}</td>
                                                                            <td>Teléfono: ${p.p.telefono}<br/> Email: ${p.p.email}<br/><c:choose><c:when test="${p.pagina != 'No dispone'}"><a href="${p.pagina}">${p.pagina}</a></c:when><c:otherwise>Pagina: ${p.pagina}</c:otherwise></c:choose></td>
                                                                            <td>${p.p.domicilio}<br/>Barrio: ${p.p.barrio.toString()}</td>
                                                                        </tr>      
                                                                    </c:forEach>
                                                                </tbody>
                                                            </table>
                                                        </c:when>
                                                    </c:choose>

                                                    <c:choose>
                                                        <c:when test="${opcion == 'marcas'}">
                                                            <h2>
                                                                Listado de proveedores por marcas
                                                            </h2>
                                                            <table class="table table-dark">
                                                                <thead>
                                                                    <tr>
                                                                        <th scope="col">Marca</th>
                                                                        <th scope="col">Número de proveedor</th>
                                                                        <th scope="col">Nombre del proveedor</th>
                                                                        <th scope="col">Contacto</th>
                                                                        <th scope="col">Ubicación</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <c:forEach items="${lstProv}" var="p" >
                                                                        <tr>                                        
                                                                            <td>${p.descripcion_aux}</td>
                                                                            <td>${p.id}</td>
                                                                            <td>${p.proveedor}</td>
                                                                            <td>Teléfono: ${p.p.telefono}<br/> Email: ${p.p.email}<br/><c:choose><c:when test="${p.pagina != 'No dispone'}"><a href="${p.pagina}">${p.pagina}</a></c:when><c:otherwise>Pagina: ${p.pagina}</c:otherwise></c:choose></td>
                                                                            <td>${p.p.domicilio}<br/>Barrio: ${p.p.barrio.toString()}</td>
                                                                        </tr>      
                                                                    </c:forEach>
                                                                </tbody>
                                                            </table>
                                                        </c:when>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>

                                        </div>                    
                                    </div>
                                </div>
                            </div>
                            <script src="js/CombosUbicacion.js" type="text/javascript"></script>
                            <script type="text/javascript">
                                                                                function Eliminar(x) {
                                                                                    $.ajax({
                                                                                        type: "POST",
                                                                                        url: "AdministrarProveedoresServlet",

                                                                                        data: {eliminarProveedor: x},

                                                                                        success: function (data, textStatus, jqXHR) {
                                                                                            if (data != 'true') {
                                                                                                alert('No se puede eliminar este proveedor debido a que de eliminarlo comprometería la integridad del sistema. Para más información contactese con el técnico')
                                                                                            } else {
                                                                                                alert('Proveedor eliminado de forma exitosa');
                                                                                                location.reload();
                                                                                            }

                                                                                        },
                                                                                        error: function (jqXHR) {
                                                                                            console.log("error")
                                                                                        }
                                                                                    });

                                                                                }



                                                                                var cboProvincia = document.getElementById('cboProvincia');
                                                                                var cboLocalidad = document.getElementById('cboLocalidad');
                                                                                var cboBarrio = document.getElementById('cboBarrio');
                                                                                var proveedor = document.getElementById('proveedor');
                                                                                var email = document.getElementById('email');
                                                                                var telefono = document.getElementById('telefono');
                                                                                var pagina = document.getElementById('pagina');
                                                                                var direccion = document.getElementById('direccion');
                                                                                var cp = document.getElementById('cp');

                                                                                function validaciones() {
                                                                                    if (proveedor.value === '') {
                                                                                        alert('Debe ingresar el nombre del proveedor');
                                                                                        proveedor.focus();
                                                                                        return false;
                                                                                    }
                                                                                    if (telefono.value === '') {
                                                                                        alert('Debe ingresar el telefono');
                                                                                        telefono.focus();
                                                                                        return false;
                                                                                    } else if (!/^\d{7,10}?$/.test(telefono.value) && telefono.value.length) {
                                                                                        alert('El formato del teléfono debe ser numérico de entre 7 a 10 digitos.\nEjemplo: 4999999 o 3515999999');
                                                                                        telefono.focus();
                                                                                        return false;
                                                                                    }

                                                                                    if (cboProvincia.value == 0) {
                                                                                        alert('Debe seleccionar una provincia');
                                                                                        cboProvincia.focus();
                                                                                        return false;
                                                                                    }
                                                                                    if (cboLocalidad.value == 0) {
                                                                                        alert('Debe seleccionar una localidad');
                                                                                        cboLocalidad.focus();
                                                                                        return false;
                                                                                    }
                                                                                    if (cboBarrio.value == 0) {
                                                                                        alert('Debe seleccionar un barrio');
                                                                                        cboBarrio.focus();
                                                                                        return false;
                                                                                    }

                                                                                    if (email.value === '') {
                                                                                        email.value = 'No dispone';
                                                                                    }

                                                                                    if (pagina.value === '') {
                                                                                        pagina.value = 'No dispone';
                                                                                    }

                                                                                    if (direccion.value === '') {
                                                                                        alert('Debe ingresar la direccion');
                                                                                        direccion.focus();
                                                                                        return false;
                                                                                    }
                                                                                    if (cp.value === '') {
                                                                                        alert('Debe ingresar el código postal');
                                                                                        cp.focus();
                                                                                        return false;
                                                                                    }
                                                                                    return true;
                                                                                }

                                                                                function enviar() {
                                                                                    if (validaciones()) {
                                                                                        if (cboLocalidad.value === 0) {
                                                                                            alert('Debe seleccionar una localidad');
                                                                                            cboLocalidad.focus();                                                                                            

                                                                                        } else {
                                                                                            frmProveedor.submit();
                                                                                        }
                                                                                    }
                                                                                }

                                                                                //CARGAR COMBO DE LOCALIDADES SEGÚN PROVINCIA
                                                                                cboProvincia.addEventListener('change', (event) => {

                                                                                    var localidades = new Array();

                                <c:forEach items="${lpjs}" var="l">
                                                                                    var localidad = new Ubicacion(${l.id_localidad}, '${l.localidad}', ${l.id_provincia});
                                                                                    localidades.push(localidad);
                                </c:forEach>
                                                                                    var localidadesFiltradas = new Array();
                                                                                    for (l of localidades) {
                                                                                        if (l.id_padre == cboProvincia.value) {
                                                                                            localidadesFiltradas.push(l);
                                                                                        }
                                                                                    }

                                                                                    localidadesFiltradas.sort(localidadesFiltradas.descripcion);
                                                                                    addOptions(cboLocalidad, localidadesFiltradas);
                                                                                });

                                                                                //CARGAR COMBO DE BARRIOS SEGÚN LOCALIDAD

                                                                                cboLocalidad.addEventListener('change', (event) => {

                                                                                    var barrios = new Array();
                                <c:forEach items="${bpjs}" var="b">
                                                                                    var barrio = new Ubicacion(${b.id_barrio}, '${b.barrio}', ${b.id_localidad});
                                                                                    barrios.push(barrio);
                                </c:forEach>
                                                                                    barrios.sort();

                                                                                    var barriosFiltrados = new Array();

                                                                                    for (b of barrios) {
                                                                                        if (b.id_padre == cboLocalidad.value) {
                                                                                            barriosFiltrados.push(b);
                                                                                        }
                                                                                    }
                                                                                    addOptions(cboBarrio, barriosFiltrados);

                                                                                    //PARA ORDENAR LOS BARRIOS ALFABETICAMENTE
                                                                                    var selectToSort = jQuery('#cboBarrio');
                                                                                    var optionActual = selectToSort.val();
                                                                                    selectToSort.html(selectToSort.children('option').sort(function (a, b) {
                                                                                        return a.text === b.text ? 0 : a.text < b.text ? -1 : 1;
                                                                                    })).val(optionActual);
                                                                                }
                                                                                );

                            </script> 
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
