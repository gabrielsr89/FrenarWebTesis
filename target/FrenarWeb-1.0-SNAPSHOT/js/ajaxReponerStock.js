class DetalleCarrito {
    constructor( id_repuesto, nombre_repuesto, cantidad, precio_unitario) {        
        this.id_repuesto = id_repuesto;
        this.nombre_repuesto = nombre_repuesto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
    }
    toString() {
      
        this.id_repuesto +' '+
        this.nombre_repuesto +' '+
        this.cantidad+' '+
        this.precio_unitario;
    }
}
/*
$(document).ready(function () {

    $('#btnEnviar').click(function (event) {

        var id_proveedor = $('#cboProveedor').val();       
        var id_repuesto = $('#cboRepuesto').val();       
        var cantidad = $('#spnCantidad').val();       

        $.ajax({
            type: "POST",
            url: "ReponerStockServlet",

            data: {id_prov: id_proveedor, id_r:id_repuesto, cantidad: cantidad},

            success: function (data, textStatus, jqXHR) { 
                $('#divResultado').html(data);
            },
            error: function (jqXHR) {
                console.log("error")
            }
        });
        //return false;
    });
});*/



