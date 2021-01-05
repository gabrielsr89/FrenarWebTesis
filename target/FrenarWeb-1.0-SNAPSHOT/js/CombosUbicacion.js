class Ubicacion {
    constructor(id, descripcion, id_padre) {
        this.id = id;
        this.descripcion = descripcion;
        this.id_padre = id_padre;
    }
    toString() {
        return this.descripcion;
    }
}
function addOptions(domElementCombo, ubicacionesArray) {
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
        opcion0.value = -1;
        opcion0.textContent = 'Despliegue para seleccionar';
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

class Modelo {
    constructor(id_marca, marca, id_modelo, modelo) {
        this.id_marca = id_marca;
        this.marca = marca;
        this.id_modelo = id_modelo;
        this.modelo = modelo;
    }
    toString() {
        return this.modelo;
    }
}

class Marca {
    constructor(id_marca, marca) {
        this.id_marca = id_marca;
        this.marca = marca;
    }
    toString() {
        return this.marca;
    }
}

class Factura {
    constructor(id_factura, fecha, importe, nombreCliente, nombreAuto, nombreResponsable ) {
        this.id_factura = id_factura;
        this.fecha = fecha;
        this.importe = importe;
        this.nombreCliente = nombreCliente;
        this.nombreAuto = nombreAuto;
        this.nombreResponsable = nombreResponsable;
    }
    toString() {
        return 'número de factura: '+this.id_factura;
    }
}

