class Auto {
    constructor(id_auto, marca, modelo, patente, id_cliente) {
        this.id_auto = id_auto;
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
        this.id_cliente = id_cliente;
    }
    toString() {
        return this.marca+' '+modelo+', patente: '+ patente;
    }
}

