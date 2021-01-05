
package Model;

public class DetallePedidoRepuesto {
    private int id_detalleRepuesto;
    private int id_factura;
    private int cantidad;
    private int id_repuesto;
    private double precio;
    private Repuesto r;

    //GET & SET
    
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public int getId_repuesto() {
        return id_repuesto;
    }

    public void setId_repuesto(int id_repuesto) {
        this.id_repuesto = id_repuesto;
    }
    public Repuesto getR() {
        return r;
    }

    public void setR(Repuesto r) {
        this.r = r;
    }
    
    public int getId_detalleRepuesto() {
        return id_detalleRepuesto;
    }

    public void setId_detalleRepuesto(int id_detalleRepuesto) {
        this.id_detalleRepuesto = id_detalleRepuesto;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    //CONSTRUCTORES
    //alta
    
    public DetallePedidoRepuesto(int id_detalle, int id_factura, int id_repuesto, int cantidad, double precio) {
        this.id_detalleRepuesto = id_detalle;
        this.id_factura = id_factura;
        this.id_repuesto = id_repuesto;
        this.cantidad = cantidad;
        this.precio = precio;
        
    }
    public DetallePedidoRepuesto(int id_detalle, int id_factura, int id_repuesto, int cantidad, double precio, Repuesto r) {
        this.id_detalleRepuesto = id_detalle;
        this.id_factura = id_factura;
        this.id_repuesto = id_repuesto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.r=r;
        
    }
    public DetallePedidoRepuesto(int cantidad, Repuesto r) {
        this.cantidad = cantidad;
        this.r = r;
    }
    //TOSTRING

    @Override
    public String toString() {
        return  "Cantidad: "+cantidad + ", Repuesto:" + r.getRepuesto();
    }
    
    
    
    
}
