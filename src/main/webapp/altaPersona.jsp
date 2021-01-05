
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./css/FrenarFont.css">
        <link rel="stylesheet" href="./css/BlackieFont.css">

        <title>Nuevo usuario</title>
    </head> 
    <body id="fondomain">

        <form method="POST" accion="./AltaPersonaServlet" class="form-altapersona bg-secondary" id="miFormulario" name="frmPersona">  

            <div> <input type="hidden" name="tipoUsuario" value="${tipoUsuario}"></div>
            <div> <input type="hidden" name="action" id="hiddenAction"></div>            


            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="titulares">${accion} de usuario</h1>
            </div>

            <div class="container" style="height: 100%;">

                <%--NOMBRE APELLIDO--%>
                <div class="row mt-5">
                    <div class="col">
                        <label for="nombre">Nombre</label>
                        <input type="text" class="form-control" id="nombre" placeholder="Ingresar nombre" name="nombre">
                    </div>

                    <div class="col">
                        <label for="apellido">Apellido</label>
                        <input type="text" class="form-control" id="apellido" placeholder="Ingresar apellido" name="apellido">
                    </div>
                </div>

                <div class="w-100" id="breakrowTop"></div>
                <div class="dropdown-divider"></div>                
                <div class="w-100" id="breakrowBottom"></div>

                <%--CORREO CONTRASEÑA--%>

                <div class="row">
                    <div class="col">
                        <label for="email">Correo electrónico</label>
                        <div style="display: flex; align-items: center; text-align: center;">
                            <input type="mail" class="form-control" name="mail" id="mail" placeholder="Ingresar correo electrónico" style="width: 90%;">
                            <span id="result" style="width: 10%;"></span>
                        </div>

                    </div>
                    <div class="col" style="text-align: center;"> 
                        <input type="button" class="btn btn-dark" id="verificarMail" onclick="ActivarCambio()" value="Cambiar correo" style="position:absolute; bottom: 0px; left: 20px;">                          
                        <input type="button" class="btn btn-dark" id="CancelarCambioMail" onclick = "cancelarMail()" value="Cancelar" style="position:absolute; bottom: 0px; left: 20px;">                         

                    </div>
                </div>
                <script type="text/javascript">

                </script>


                <div class="w-100" id="brakrowMiddle"></div>

                <div class="row">
                    <div class="col">
                        <label for="clave1">Contraseña</label>
                        <input type="password" name="pw" class="form-control" id="clave1" <%--onblur="HabilitarVerificacion()"--%> placeholder="Ingresar contraseña">
                    </div>
                    <div class="col" style="text-align: center;">
                        <c:choose>
                            <c:when test = "${accion == 'Alta'}">                                 
                                <input type="button" class="btn btn-dark" onclick="ValidarPass()" id="validarPass" value="Validar contraseña" style="position:absolute; bottom: 0px; left: 20px;">
                            </c:when>
                        </c:choose>
                        <c:choose>
                            <c:when test = "${accion == 'Modificación'}">
                                <input type="button" class="btn btn-dark" onclick="ValidarPass()" id="validarPass" value="Validar contraseña" style="position:absolute; bottom: 0px; left: 20px;">
                                <input type="button" class="btn btn-dark" onclick="CambiarPass()" id="cambiarPass" value="Cambiar contraseña" style="position:absolute; bottom: 0px; left: 20px;">
                                <input type="button" class="btn btn-dark" onclick="CancelarPass()" id="cancelarPass" value="Cancelar" style="position:absolute; bottom: 0px; left: 160px;">
                            </c:when>
                        </c:choose>
                    </div>

                </div>
                <div class="w-100" id="brakrowMiddle"></div>

                <div class="row">   
                    <div class="col ">
                        <label for="clave2">Confirmar contraseña</label>
                        <input type="password" name="clave2" class="form-control" id="clave2" placeholder="Confirmar contraseña" onblur="ValidarPass()">                        
                    </div>        
                    <div class="col" style="display: flex; align-items: center;">
                        <span id="confirmarPass" ></span> 
                    </div>
                </div>

                <div class="w-100" id="breakrowTop"></div>
                <div class="dropdown-divider"></div>                
                <div class="w-100" id="breakrowBottom"></div>

                <%--TELEFONOS--%>
                <div class="row">
                    <div class="col">
                        <label for="telPersonal">Teléfono personal</label>
                        <input type="text" class="form-control" name="telefono" onblur="ValidarTelefono()" id="telPersonal" placeholder="Teléfono" name="tel">
                    </div>
                    <div class="col">
                        <label for="telAlt">Teléfono alternativo</label>
                        <input type="text" class="form-control" name="tel_alternativo" id="telAlt" onblur="ValidarTelefono()" placeholder="Teléfono alternativo" name="telAlter">
                    </div>
                </div>

                <div class="w-100" id="breakrowTop"></div>
                <div class="dropdown-divider"></div>                
                <div class="w-100" id="breakrowBottom"></div>

                <%--UBICACIÓN--%>
                <%--PROVINCIA--%>
                <div class="row">
                    <div class="col-sm">
                        <label for="cboProvincia">Provincia</label>
                        <select class="form-control" id="cboProvincia" name="idProvincia">                        
                            <option value = "0">Despliegue para seleccionar</option>
                            <c:forEach items="${ptc}" var="p">  
                                <option value="${p.id_provincia}" ${provincia eq p.id_provincia?'selected':''}>${p}</option>                            
                            </c:forEach>                            
                        </select>
                    </div>
                    <%--LOCALIDAD--%>
                    <div class="col-sm">               
                        <label for="cboLocalidad">Localidad</label>
                        <select class="form-control" id="cboLocalidad" name="idLocalidad">                        
                            <option value = "0">Despliegue para seleccionar</option> 
                            <c:forEach items="${lstLocalidades}" var="l">  
                                <option value="${l.id_localidad}" ${localidad eq l.id_localidad?'selected':''}>${l}</option>                            
                            </c:forEach>   
                        </select>
                    </div>
                    <%--BARRIO--%>
                    <div class="col-sm">
                        <label for="cboBarrio">Barrio</label>
                        <select class="form-control" id="cboBarrio" name="idBarrio">                        
                            <option value = "0">Despliegue para seleccionar</option>   
                            <c:forEach items="${lstBarrios}" var="b">  
                                <option value="${b.id_barrio}" ${barrio eq b.id_barrio?'selected':''}>${b}</option>                            
                            </c:forEach>   
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div class="col" id="nuevoBarrioContainer" style="display: none; margin-top: 20px;">
                        <input type="text" class="form-control" name="nuevoBarrio" id="nuevoBarrio" placeholder="Ingrese el nombre del barrio" style="width: 60%; margin-left: 50%; transform: translateX(-50%);">
                    </div>                        
                </div>
                <div class="w-100" id="brakrowMiddle"></div>
                <%--DOMICILIO--%>
                <div class="row mb-5">
                    <div class="col-sm-8"> 
                        <label for="dir">Ingrese su dirección</label>
                        <input type="text" class="form-control col-8" id="dir" placeholder="Ingresar dirección" name="direccion">
                    </div>
                    <%--CP--%>
                    <div class="col-4-sm-4">
                        <label for="cp">Código postal</label>
                        <input type="text" class="form-control" id="cp" placeholder="CP" name="cp">
                    </div>
                </div>
            </div>
            <%--BOTONES--%>
            <div class="row mb-5">
                <div class="col" id="aceptarDiv">
                    <button type="button" class="btn btn-dark" onclick="enviar()"style="width: 50%">Aceptar</button>
                    <button type="button" class="btn btn-dark" id="cancelarButton" onClick="history.go(-1);" style="width: 30%">Cancelar</button>
                </div>
            </div>
        </form>

        <c:import url="bootstrap.jsp"></c:import>

            <script src="js/CombosUbicacion.js" type="text/javascript"></script>
            <script src="js/VerificarCorreo.js" type="text/javascript"></script>

            <script type="text/javascript">// para que se carguen los combos 

                        var cboLocalidad = document.getElementById('cboLocalidad');
                        var cboProvincia = document.getElementById('cboProvincia');
                        var cboBarrio = document.getElementById('cboBarrio');
                        var habLocalidad = new Boolean(false);

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
                        if (habLocalidad) {
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
                        }


                        //validaciones generales y para preparar la modificación
                        //ELEMENTOS
                        var accion = '${accion}';

                        var nombre = document.getElementById('nombre');
                        var apellido = document.getElementById('apellido');
                        var direccion = document.getElementById('dir');
                        var cp = document.getElementById('cp');
                        var telefono = document.getElementById('telPersonal');
                        var telAlter = document.getElementById('telAlt');
                        var email = document.getElementById('mail');
                        var result = document.getElementById('result');
                        var verificarMail = document.getElementById('verificarMail');
                        var CancelarCambioMail = document.getElementById('CancelarCambioMail');
                        var clave1 = document.getElementById('clave1');
                        var clave2 = document.getElementById('clave2');
                        var confirmarPass = document.getElementById('confirmarPass');
                        var cambiarPass = document.getElementById('cambiarPass');
                        var cancelarPass = document.getElementById('cancelarPass');
                        var validarPass = document.getElementById('validarPass');


                        //CONFIGURAR ARRANQUE PARA INTERACTUAR CON LOS ELEMENTOS CORRESPONDIENTES
                        if (accion == "Modificación") {
                            CancelarCambioMail.style.display = "none";
                            cancelarPass.style.display = "none";
                        } else {
                            verificarMail.style.display = "none";
                            CancelarCambioMail.style.display = "none";
                        }

                        //clave2.disabled = true;
                        //PARA VALIDACIÓN DE CONFIRMACIÓN DE PASSWORD
                        var passValidada = false;
                        //PARA VALIDACIÓN GENERAL
                        var validado = true;

                        if (accion == "Modificación") {
                            //MostrarAncla();
                            nombre.value = '${p.nombre}';
                            apellido.value = '${p.apellido}';
                            direccion.value = '${p.domicilio}';
                            cp.value = '${p.cp}';
                            telefono.value = '${p.telefono}';
                            telAlter.value = '${p.tel_alternativo}';

                            email.value = '${p.email}';

                            clave1.disabled = true;
                            clave1.value = '${p.pass}';
                            clave2.value = '${p.pass}';

                            //PARA SETEAR CON VALOR POR DEFECTO EL COMBO CON JQUERY
                            $("#cboLocalidad > option[value=" + ${p.localidad.id_localidad} + "]").attr("selected", true);
                            $("#cboProvincia > option[value=" + ${p.provincia.id_provincia} + "]").attr("selected", true);
                            $("#cboBarrio > option[value=" + ${p.barrio.id_barrio} + "]").attr("selected", true);
                        } else {

                        }
                        //METODOS PARA EL CAMBIO DE MAIL Y SU VALIDACIÓN
                        function ActivarCambio() {
                            result.style.display = "inline";
                            email.focus();
                            verificarMail.style.display = "none";
                            CancelarCambioMail.style.display = "inline";

                        }
                        function cancelarMail() {
                            email.value = '${p.email}';
                            result.style.display = "none";
                            verificarMail.style.display = "inline";
                            CancelarCambioMail.style.display = "none";

                            validado = true;
                        }


                        //TODO ESTO ES PARA LA VERIFICACIÓN DE PASSWORD
                        function CambiarPass() {
                            contenidoClave1 = clave1.value;
                            contenidoClave2 = clave2.value;
                            clave1.disabled = false;
                            clave1.focus();

                            cambiarPass.style.display = "none";
                            cancelarPass.style.display = "inline";
                            validarPass.style.display = "inline";

                        }
                        function CancelarPass() {
                            clave1.value = '{p.pass}';
                            clave2.value = '{p.pass}';
                            clave1.disabled = true;
                            clave2.disabled = true;
                            confirmarPass.style.display = "none";
                            cambiarPass.style.display = "inline";
                            cancelarPass.style.display = "none";
                            validarPass.style.display = "none";
                        }
                        function ValidarPass() {
                            if (clave1.value === clave2.value) {
                                confirmarPass.innerHTML = "<div class=\"row\" style=\"margin-top:20px;\"><div style=\"width: 50px;\"><img src=\"./img/ok.png\"></div><div class\"col\"><h3 style=\"color:ivory; font-size: 16px; margin-bottom:0px;  line-height: 50px;\">Confirme el formulario!</h3></div></div>";

                                if (accion === 'Modificación') {
                                    confirmarPass.style.display = "inline";
                                    cambiarPass.style.display = "inline";
                                    cancelarPass.style.display = "none";
                                    validarPass.style.display = "none";
                                }
                                validado = true;

                            } else {
                                confirmarPass.innerHTML = "<div class=\"row\" style=\"margin-top:20px;\">\n\
                                                    <div style=\"width: 50px;\">\n\
                                                        <img src=\"./img/Advertencia.png\">\n\
                                                    <\/div>\n\
                                                    <div class=\"col\">\n\
                                                        <h3 style=\"color: #FF5733; font-size: 25px; position: absolute; bottom: 8px; margin-bottom: 0px;\">    \n\
                                                            No hay coincidencia\n\
                                                        </h3>\n\
                                                    </div>\n\
                                                </div>";
                                confirmarPass.style.display = "inline";
                                alert('La contraseña no coincide');
                                clave1.focus();
                                validado = false;
                            }
                        }
                        function HabilitarVerificacion() {
                            clave2.disabled = false;
                            clave2.focus();
                        }
                        //validaciones generales
                        function ValidacionesGenerales() {
                            if (nombre.value === "") {
                                nombre.focus();
                                alert('El nombre es obligatorio');
                                return false;
                            }
                            if (apellido.value === "") {
                                apellido.focus();
                                alert('El apellido es obligatorio');
                                return false;
                            }
                            if (telefono.value === "") {
                                telefono.focus();
                                alert('El telefono es obligatorio');
                                return false;
                            }
                            if (cboLocalidad.value === 0) {
                                cboLocalidad.focus();
                                alert('Debe seleccionar al menos una localidad');
                                return false;
                            }
                            if (cboProvincia.value === 0) {
                                cboProvincia.focus();
                                alert('Debe seleccionar al menos una provincia');
                                validado = false;
                            }
                            if (cboBarrio.value === 0) {
                                cboBarrio.focus();
                                alert('Debe seleccionar al menos un barrio');
                                return false;
                            }
                            if (clave1.value === "") {
                                clave1.focus();
                                alert('Debe ingresar una password y confirmarla');
                                return false;
                            }
                            if (direccion.value === "") {
                                direccion.focus();
                                alert('Debe ingresar un domicilio');
                                return false;
                            }
                            if (cp.value === "") {
                                cp.focus();
                                alert('Debe ingresar un código postal');
                                return false;
                            }

                            return true;
                        }
                        function ValidarTelefono() {
                            if (!/^\d{10,10}?$/.test(telefono.value) && telefono.value.length) {
                                alert('Debe ingresar solo números y seguir un formato de 10 digitos. Ejemplo: 3513445494');
                                validado = false;
                            } else
                                validado = true;
                        }
                        //CODIGO JQuery para mostrar el valor de texto de un item del combo seleccionado
                        $(function () {
                            $('#cboBarrio').blur(function () {
                                var txtNuevoBarrio = document.getElementById('nuevoBarrioContainer');
                                var texto = $('select[name="idBarrio"] option:selected').text();
                                if (texto === 'OTRO BARRIO') {
                                    txtNuevoBarrio.style.display = "inline";
                                } else {
                                    txtNuevoBarrio.style.display = "none";
                                }
                            });
                        });

                        function enviar() {
                            if (ValidacionesGenerales()) {
                                email.disabled = false;
                                clave1.disabled = false;
                                document.getElementById('hiddenAction').value = accion;


                                ValidarPass();
                                if (validado) {
                                    if (email.value != '' && clave1.value != '') {
                                        console.log(email.value + ' ' + clave1.value)
                                        frmPersona.submit();
                                    } else if (accion === 'Modificación') {


                                        clave1.value = '${p.pass}';
                                        console.log(email.value + ' pw: ' + clave1.value)
                                        frmPersona.submit();
                                    } else {
                                        alert('Debe llenar los campos')
                                    }
                                } else {
                                    alert('revise el formulario');
                                }

                            }
                        }
        </script>
    </body>
</html>
