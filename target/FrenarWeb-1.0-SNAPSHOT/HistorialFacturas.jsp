
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Historial</title>
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
                            <div class="col-md-10 bg-secondary"  style="overflow: hidden; margin-top: 100px; color:ivory;">

                                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" style="padding-top: 100px!important">
                                    <c:choose>
                                        <c:when test="${cliente == false}">
                                            <h1 class="titulares" >Historial de servicios realizados y repuestos vendidos</h1>
                                        </c:when>
                                        <c:otherwise>
                                            <h1 class="titulares">Historial de servicios realizados y repuestos comprados</h1>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="row mt-5" id="divFormulario">
                                    <div class="col">
                                        <div class="row " id="divEmpleados">
                                            <div class="col text-center">

                                                <label class="listadoHeader" for="cboCliente">Seleccione el cliente</label>
                                                <select class="form-control" id="cboCliente" name="idCliente" style="width: 50%; transform: translateX(-50%); margin-left: 50%;">                        
                                                    <option value = "0">Todos los clientes</option>
                                                    <c:forEach items="${lstCliente}" var="ct">  
                                                        <option value="${ct.id}" ${ct.p.nombre eq ct.id?'selected':''}>${ct.p.toString()}</option>
                                                    </c:forEach>
                                                </select>


                                            </div>
                                        </div>                                    
                                        <div class="row mt-5">
                                            <div class="col">
                                                <%-- combo de auto y 3 rbt si only servicios/repuestos o ambas cosas--%>
                                                <label class="input-group" for="cboAutos">Seleccione el auto</label>
                                                <select class="custom-select" id="cboAutos" name="idAuto">
                                                    <option value="0">Todos los vehículos</option>                                       
                                                </select>
                                            </div>
                                            <div class="col">
                                                <input type="radio" name="rbt" id="rbtServicios"><label for="rbtServicios" style="font-size: 18px; margin-left: 10px;"> Filtrar facturas que tengan servicios</label><br/>
                                                <input type="radio" name="rbt" id="rbtRepuestos"><label for="rbtRepuestos" style="font-size: 18px; margin-left: 10px;"> Filtrar facturas que tengan repuestos</label><br/>
                                                <input type="radio" name="rbt" id="rbtTodo"><label for="rbtTodo" style="font-size: 18px; margin-left: 10px;"> Facturas con servicios y repuestos</label>     <br/>

                                            </div>
                                        </div>
                                        <div class="row mt-3">
                                            <div class="col text-center">
                                                <input type="button" class="btn btn-dark" onclick="Buscar()" value="Buscar" style="width: 35%;">
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <%-- LISTADO --%>
                                <h1 class="mt-5" style="border-bottom: 1px solid ivory">
                                    Listado de facturas
                                </h1>
                                <div class="text-center mt-5" id="listado">




                                    <table class="table table-dark" id="tablaResultados">  
                                        <tbody id="CuerpoTabla" style="color: ivory; font-size: 18px;">
                                            
                                        </tbody>                                        
                                    </table>    
                                    
                                    <div id="divSinResultados" hidden="true">
                                        <h3>
                                            No hay resultados
                                        </h3>                                        
                                    </div>    
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="js/CombosUbicacion.js" type="text/javascript"></script>
        <script type="text/javascript">

                                                    var rbtServicios = document.getElementById('rbtServicios');
                                                    var tablaResultados = document.getElementById('tablaResultados');
                                                    var divSinResultados = document.getElementById('divSinResultados');
                                                    var rbtRepuestos = document.getElementById('rbtRepuestos');
                                                    var rbtTodo = document.getElementById('rbtTodo');
                                                    var cboCliente = document.getElementById('cboCliente');
                                                    var CuerpoTabla = document.getElementById('CuerpoTabla');
                                                    var cboAutos = document.getElementById('cboAutos');
                                                    var divEmpleados = document.getElementById('divEmpleados');
                                                    var lstAuto = new Array();
            <c:forEach items="${lstAutos}" var="a">
                                                    var auto = new Ubicacion(${a.id_auto}, '${a.marca} ${a.modelo}, patente: ${a.patente}', ${a.id_cliente});
                                                        lstAuto.push(auto);
            </c:forEach>
                                                        var otraEleccion = 0;
                                                        var autosFiltrados = new Array();

                                                        window.onload = function () {
                                                            var id_auto = ${id_auto};
                                                            var id_ct = ${id_cliente};
                                                            var cliente = ${cliente};

                                                            if (!cliente) {
                                                                otraEleccion = 3;
                                                                Buscar()
                                                                divEmpleados.style.display = "inline";
                                                            } else {
                                                                cboCliente.value = id_ct;
                                                                divEmpleados.style.display = "none";
                                                                for (a of lstAuto) {
                                                                    if (a.id_padre == ${id_cliente}) {
                                                                        autosFiltrados.push(a);
                                                                    }
                                                                }
                                                                autosFiltrados.sort(autosFiltrados.marca);
                                                                CargarComboAutos(cboAutos, autosFiltrados);
                                                                autosFiltrados.length = 0;
                                                            }
                                                            if (id_auto > 0) {
                                                                cboAutos.value = id_auto;
                                                            }
                                                            if (id_ct > 0) {
                                                                cboCliente.value = id_ct;
                                                            }
                                                        }

                                                        cboCliente.addEventListener('change', (event) => {
                                                            for (a of lstAuto) {
                                                                if (a.id_padre == cboCliente.value) {
                                                                    autosFiltrados.push(a);
                                                                }
                                                            }
                                                            autosFiltrados.sort(autosFiltrados.marca);
                                                            CargarComboAutos(cboAutos, autosFiltrados);
                                                            autosFiltrados.length = 0;
                                                        });

                                                        function CargarComboAutos(domElementCombo, ubicacionesArray) {
                                                            //INSTANCIO AL COMBO
                                                            var combo = domElementCombo;
                                                            //SI EL COMBO ESTA CARGADO
                                                            if (combo.options.length != 0) {
                                                                //PRIMERO LIMPIO EL COMBO DE LOS ELEMENTOS QUE TENGA
                                                                for (let i = domElementCombo.options.length; i >= 0; i--) {
                                                                    domElementCombo.remove(i);
                                                                }
                                                                array = new Array();
                                                                array = ubicacionesArray;
                                                                array.sort();
                                                                //CREO UN OPTION CON PRIMERA POSICIÓN
                                                                var opcion0 = document.createElement('option');
                                                                opcion0.value = 0;
                                                                opcion0.textContent = 'Todos los vehículos';
                                                                combo.add(opcion0);
                                                                //RECORRO AL ARREGLO PARA IR AGREGANDOLE AL SELECT SU CONTENIDO    
                                                                for (u of array) {
                                                                    var opcion = document.createElement('option');
                                                                    opcion.textContent = u.descripcion;
                                                                    opcion.value = u.id;
                                                                    combo.add(opcion, u.id);
                                                                }
                                                            }
                                                        }

                                                        function Buscar() {

                                                            var eleccion = otraEleccion;
                                                            var id_c = 0;
                                                            var id_a = 0

                                                            if (cboCliente.value != 0) {
                                                                id_c = cboCliente.value;
                                                            }
                                                            if (cboAutos.value != 0) {
                                                                id_a = cboAutos.value;
                                                            }

                                                            if (rbtRepuestos.checked) {
                                                                eleccion = 1;
                                                            }
                                                            if (rbtServicios.checked) {
                                                                eleccion = 2;
                                                            }
                                                            if (rbtTodo.checked) {
                                                                eleccion = 3;
                                                            }

                                                            console.log('el '+ eleccion+' id_c '+id_c+' id_a '+id_a);
                                                            $.ajax({
                                                                type: "POST",
                                                                url: "HistorialFacturasServlet",
                                                                data: {eleccion: eleccion, id_c: id_c, id_a: id_a},
                                                                success: function (data, textStatus, jqXHR) {
                                                                    
                                                                    console.log(data);
                                                                    
                                                                    var primerExpresion = new RegExp('{.*?}', 'g');
                                                                    var arreglo = data.match(primerExpresion);
                                                                    var facturas = new Array();

                                                                    if (arreglo != null) {
                                                                        tablaResultados.hidden = false;
                                                                        divSinResultados.hidden = true;

                                                                        for (a of arreglo) {
                                                                            var expre = /{(\d+?), (.+?), (.+?), (.+?), (.+?), (.+)}/i;
                                                                            var objeto = a.match(expre, "i");
                                                                            var f = new Factura(objeto[1], objeto[2], objeto[3], objeto[4], objeto[5], objeto[6]);
                                                                            facturas.push(f);
                                                                        }
                                                                        CuerpoTabla.innerHTML = '';
                                                                        for (f of facturas) {

                                                                            var fila = document.createElement("tr");
                                                                            var celda1 = document.createElement("td");
                                                                            var celda2 = document.createElement("td");
                                                                            var celda3 = document.createElement("td");

                                                                            celda1.innerHTML = "Número factura: <strong>" + f.id_factura + "</strong> <br/> Fecha: <strong>" + f.fecha + "</strong><br/>Importe: <strong>" + f.importe + "</strong>";
                                                                            celda2.innerHTML = "Cliente: <strong>" + f.nombreCliente + "</strong><br/>Vehículo: <strong>" + f.nombreAuto + "</strong><br/>Empleado responsable: <strong>" + f.nombreResponsable + "</strong>";
                                                                            celda3.innerHTML = "<a class=\"nav-link\" href=\"./DetalleEntregaServlet?id_factura=" + f.id_factura + "\" style=\"color: ivory; font-size: 18px; transform: translateY(50%); margin-bottom: -50%; \">Ver detalles</a>";

                                                                            fila.appendChild(celda1);
                                                                            fila.appendChild(celda2);
                                                                            fila.appendChild(celda3);
                                                                            
                                                                            console.log(CuerpoTabla);

                                                                            CuerpoTabla.appendChild(fila);
                                                                            console.log(CuerpoTabla);
                                                                        }
                                                                    } else {
                                                                        tablaResultados.hidden = true;
                                                                        divSinResultados.hidden = false;
                                                                    }
                                                                },
                                                                error: function (jqXHR) {
                                                                    console.log("error")
                                                                }
                                                            });

                                                        }

        </script>
    </body>
</html>
