class Servicio {
    constructor(id_servicio, id_categoria, servicio, descripcion, tiempo, precio) {
        this.id_servicio = id_servicio;
        this.id_categoria = id_categoria;
        this.servicio = servicio;
        this.descripcion = descripcion;
        this.tiempo = tiempo;
        this.precio = precio;        
    }
    toString() {
        return this.descripcion;
    }
}
class Auto {
    constructor(id_auto, marca, modelo, patente, id_cliente) {
        this.id_auto = id_auto;
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
        this.id_cliente = id_cliente;
           
    }
    toString() {
        return this.descripcion;
    }
}
