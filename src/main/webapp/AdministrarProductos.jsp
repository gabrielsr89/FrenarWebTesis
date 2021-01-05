
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
    <head>
        <meta http-equiv="Expires" content="0">
        <meta http-equiv="Last-Modified" content="0">
        <meta http-equiv="Cache-Control" content="no-cache, mustrevalidate">
        <meta http-equiv="Pragma" content="no-cache">
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
                                    <h1 class="titulares">Administrar repuestos</h1>
                                </div>

                                <h1 class="secundarios">${accion}</h1>
                                <form method="POST" action="./AdministrarRepuestosServlet" enctype="multipart/form-data" name="frmRepuesto">
                                    <c:choose>
                                        <c:when test="${r.id_repuesto != null}">
                                            <input type="hidden" value="${r.id_repuesto}" name ="idRepuesto">                                            
                                        </c:when>                                        
                                    </c:choose>
                                    <div  style="width: 80% !important; align-items: center; margin-left: 10%; padding-bottom: 60px;">

                                        <%--NOMBRE desc --%>
                                        <div class="row">
                                            <div class="col">
                                                <div class="row pb-3">
                                                    <div class="col">
                                                        <label class="listadoHeader" for="repuesto">Nombre del repuesto</label>
                                                        <input class="form-control" type="text" placeholder="Hasta 100 caracteres." id="nombreRepuesto" name="repuesto" >                        
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col">
                                                        <label class="listadoHeader" for="txtDescripcion">Descripción del repuesto</label>
                                                        <input type="text" class="form-control" name="descripcion" id="txtDescripcion" placeholder="La descripción puede ser de hasta 500 carácteres">

                                                    </div>
                                                </div>
                                            </div>
                                            
                                            <%--rubros --%>
                                            <div class ="col">
                                                <div class="row">
                                                    <div class="col">
                                                        <label class="listadoHeader" for="cboRubros">Rubros</label>
                                                        <select class="form-control" id="cboRubros" name="idR">                        
                                                            <option value ="0">Despliegue para seleccionar</option>
                                                            <c:forEach items="${lstRubro}" var="r">  
                                                                <option value="${r.id}" ${rubro eq r.id?'selected':''}>${r}</option>                            
                                                            </c:forEach>                         
                                                        </select>
                                                    </div>
                                                </div>                                    

                                                <%--marcas--%>
                                                <div class="row pt-3">
                                                    <div class="col">
                                                        <label class="listadoHeader" for="cboMarcas">Marcas de repuestos</label>
                                                        <select class="form-control" id="cboMarcas" name="idM">                        
                                                            <option value = "0">Despliegue para seleccionar</option>
                                                            <c:forEach items="${lstMarcaRepuesto}" var="mr">  
                                                                <option value="${mr.id_marcaRepuesto}" ${marcaRepuesto eq mr.id_marcaRepuesto?'selected':''}>${mr}</option>                            
                                                            </c:forEach>                     
                                                        </select>  
                                                    </div>
                                                </div>
                                                <div class="row">   
                                                    <div class="col">
                                                        <div class="col text-center mt-2">
                                                            <a class="nav-link text-white" href="./AdministrarMarcasRubrosServlet" style="font-size: 18px;">Administrar marcas y rubros</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <%--text area--%>

                                        </div>

                                        <%--FOTO PRECIO --%>
                                        <div class="row mt-4">
                                            <div class="col" style="width: 10px !important; text-align: center; background-image: url('./img/fondox.jpg') ; background-size: cover; " >
                                                <div class="row">
                                                    <div class="col mt-3" >
                                                        <label for="textoInput">
                                                            <img id="imgInput" src="${foto}" style="width: 210px; height: 210px;">
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col mt-3">                           

                                                        <label id="textoInput" for="inputado" style="width: 100% !important;"><span class="form-control-file">Subir nueva foto</span></label>

                                                        <span class="form-control-file"> 
                                                            <input type="file" class="form-control-file" id="inputado" name="foto" value="${foto}" multiple="false" > 
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col mt-5" st>
                                                <div class="row">  
                                                    <div class="col" style="margin-left: 50%; transform: translateX(-50%);">
                                                        <label  class="listadoHeader " for="precio">Precio</label>
                                                        <input type="text" class="form-control " id="precio" placeholder="$" name="precio" style="width: 300px;align-self: center;"><br/>
                                                        <label  class="listadoHeader " for="precio">Stock mínimo</label>
                                                        <input type="number" class="form-control text-right" value="5" id="spnStockMinimo" placeholder="stock mínimo" name="stock_min" style="width: 300px;align-self: center;" min="1">
                                                    </div>
                                                </div>

                                                <div class ="col" style="position: absolute; bottom: 0px; text-align: right;">
                                                    <input type="button" class="btn btn-dark mb-2" onclick="Enviar()" value="Aceptar">
                                                    <input type="button" class="btn btn-dark mb-2" value="Cancelar" onclick="location.href = './AdministrarRepuestosServlet'">
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </form>

                                <%-- LISTADOS --%>
                                <div class="row d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center  mb-3 border-bottom">
                                    <div class="col">
                                        <h1 class="secundarios">Listado de repuestos</h1>

                                    </div>  
                                </div>
                                <div class="row">
                                    <div class="col text-right" style="color: ivory;">
                                        <h3>
                                            ${tipoListado}
                                        </h3>
                                    </div>
                                    <div class="col">
                                        <div class="row mb-5">
                                            <div class="col">
                                                <div style="display: inline-flex">                            
                                                    <select class="form-control mb-2" id="cboListado" >      
                                                        <option value="0"> Ordenar listado por precio</option>
                                                        <option value="1"> Ordenar listado por rubros</option>
                                                        <option value="2"> Ordenar listado por marcas</option>
                                                    </select>
                                                    <button type="button" class="btn btn-dark mt5" onclick="Envio()" id="btnEnviar" style="height: 87% ">Buscar</button>
                                                </div>
                                                <script type="text/javascript">
                                                    var cboListado = document.getElementById('cboListado')
                                                    function Envio() {
                                                        location.href = './AdministrarRepuestosServlet?numListado=' + cboListado.value;
                                                    }
                                                </script>
                                            </div>
                                        </div>        
                                    </div>
                                </div>


                                <div class="table-responsive">

                                    <c:choose>
                                        <c:when test = "${lstRepuesto == null}">
                                            <tr>
                                                <td>
                                                    <h2>No hay repuestos disponibles</h2>     
                                                </td>   
                                            </tr>
                                        </c:when>
                                        <c:otherwise>
                                            <table class="table table-striped table-sm">
                                                <thead class="listadoHeader">
                                                    <tr>
                                                        <th>#</th>
                                                        <th>Repuesto</th>
                                                        <th>Rubro</th>
                                                        <th>Marca</th>
                                                        <th>Descripción</th>
                                                        <th>Imagen</th>
                                                        <th>Precio</th>
                                                        <th>Modificar</th>
                                                        <th>Eliminar</th>
                                                    </tr>
                                                </thead>
                                                <c:forEach items="${lstRepuesto}" var="rep" >
                                                    <tbody class="listadoResto">
                                                        <tr >
                                                            <td>${rep.id_repuesto}</td>
                                                            <td>${rep.repuesto}</td>
                                                            <td>${rep.r.rubro}</td>
                                                            <td>${rep.mr.marcaRepuesto}</td>
                                                            <td style="overflow: auto; max-height: ">${rep.descripcion}</td>
                                                            <td><img class="imgTable" src="${rep.foto}"></td>
                                                            <td>$ ${rep.precio}</td>
                                                            <td><a class="nav-link text-white text-center" href="./AdministrarRepuestosServlet?idRepuesto=${rep.id_repuesto}">Modificar</a></td>
                                                            <td><a class="nav-link text-white text-center" onclick="EliminarRepuesto(${rep.id_repuesto})" href="#">Eliminar</a> </td>
                                                        </tr>
                                                    </tbody>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </table>
                                </div>
                                <script type="application/javascript">    
                                    var nombreRepuesto = document.getElementById('nombreRepuesto');
                                    var precio = document.getElementById('precio');
                                    var txtDescripcion = document.getElementById('txtDescripcion');
                                    var cboRubros = document.getElementById('cboRubros');
                                    var cboMarcas = document.getElementById('cboMarcas');

                                    function Enviar(){                                        
                                    var x = true;
                                    if(nombreRepuesto.value ===''){
                                    x= false;
                                    alert('Tiene que ingresar nombre de repuesto');                                
                                    nombreRepuesto.focus();                                            
                                    }
                                    else if(cboRubros.value == 0){
                                    x= false;
                                    alert('Tiene que seleccionar un rubro');                                
                                    cboRubros.focus();                                            
                                    }
                                    else if(cboMarcas.value == 0){
                                    x= false;
                                    alert('Tiene que seleccionar una marca');                                
                                    cboMarcas.focus();                                            
                                    }
                                    else if(txtDescripcion.value.length == 0 || txtDescripcion.value.length > 500 ){
                                    x = false;
                                    alert('Debe ingresar la descripción del repuesto, la cual no debe superar los 500 caracteres');                                
                                    txtDescripcion.focus();                                            
                                    } else if(typeof precio.value !== 'undefined'){
                                    if(!/^(\d{1,9})(\.\d{0,2})?$/.test(precio.value) && precio.value.length){
                                    x = false;        
                                    alert('Debe ingresar un campo numérico de hasta 9 digitos.\nDespués del "." debe ingresar hasta 2 dígitos.\nEjemplo 987654321.99');  
                                    precio.focus();
                                    }
                                    }                                            
                                    if(x){
                                    frmRepuesto.submit();
                                    }                                        
                                    }

                                    function EliminarRepuesto(id){
                                    $.ajax({
                                    type: "POST",
                                    url: "AdministrarRepuestosServlet",

                                    data: {id_repuesto: id, eliminar: 'eliminar'},

                                    success: function (data, textStatus, jqXHR) {
                                    if (data != 'true') {                                                           
                                    alert('No se puede eliminar este repuesto debido a que de eliminarlo comprometería la integridad del sistema. Para más información contactese con el técnico.')
                                    } else{
                                    alert('Repuesto eliminado de forma exitosa');
                                    location.reload();
                                    }
                                    },
                                    error: function (jqXHR) {
                                    console.log("error")
                                    }
                                    });
                                    }

                                    //foto
                                    jQuery('input[type=file]').change(function(e){
                                    var filename = jQuery(this).val().split('\\').pop();
                                    var idname = jQuery(this).attr('id');        
                                    console.log(idname);        
                                    document.getElementById('textoInput').innerText = filename; 
                                    document.getElementById('imgInput').src = URL.createObjectURL(e.target.files[0]);        
                                    });


                                    if('${accion}' === 'Modificar repuesto'){
                                    //SI ES MODIFICACIÓN QUE MUESTRE EL CONTENIDO, SI ES ALTA EL PLACEHOLDER
                                    nombreRepuesto.value = '${r.repuesto}';
                                    var precio = '${precio}';
                                    if (precio != ''){
                                    document.getElementById('precio').value =  precio;                                   
                                    }   
                                    txtDescripcion.value = '${r.descripcion}';
                                    //{r.descripcion}
                                    //PARA SETEAR CON VALOR POR DEFECTO EL COMBO CON JQUERY
                                    document.getElementById('textoInput').innerText = '${foto}';
                                    $("#cboRubros > option[value="+ ${idRubro} +"]").attr("selected",true);
                                    $("#cboMarcas > option[value="+ ${idMarca} +"]").attr("selected",true); 

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
