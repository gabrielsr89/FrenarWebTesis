
package Model;

import Controller.GestorAutos;
import Controller.GestorServicios;

public class DetallePedidoServicio {
    private int id_detalleServicio;
    private int factura;   
    private int id_servicio;
    private double precioServicio;
    private int id_factura;
    
    OrdenTrabajo o;    
    Servicio s;
    
    // get & set
    public OrdenTrabajo getO() {
        return o;
    }

    public void setO(OrdenTrabajo o) {
        this.o = o;
    }
    public Servicio getS() {
        return s;
    }

    public void setS(Servicio s) {
        this.s = s;
    }
    
    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public int getId_detalleServicio() {
        return id_detalleServicio;
    }

    public void setId_detalleServicio(int id_detalleServicio) {
        this.id_detalleServicio = id_detalleServicio;
    }

    public int getFactura() {
        return factura;
    }

    public void setFactura(int factura) {
        this.factura = factura;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public double getPrecioServicio() {
        return precioServicio;
    }

    public void setPrecioServicio(double precioServicio) {
        this.precioServicio = precioServicio;
    }    
    //constructor
    public DetallePedidoServicio(int id_detalle, int id_factura, int id_servicio, double precio, Servicio s, OrdenTrabajo o) {
        this.id_detalleServicio = id_detalle;
        this.id_factura = id_factura;
        this.id_servicio = id_servicio;
        this.precioServicio = precio;
        this.s = s;
        this.o = o;        
    }
   
    public DetallePedidoServicio(int id_detalle, int id_factura, int id_servicio, double precio) {
        this.id_detalleServicio = id_detalle;
        this.id_factura = id_factura;
        this.id_servicio = id_servicio;
        this.precioServicio = precio;
    }
    public DetallePedidoServicio(int id_detalle, int id_factura, int id_servicio, double precio, Servicio s) {
        this.id_detalleServicio = id_detalle;
        this.id_factura = id_factura;
        this.id_servicio = id_servicio;
        this.precioServicio = precio;
        this.s = s;
    }
    public DetallePedidoServicio( int id_servicio) {   
        this.id_servicio = id_servicio;        
    }

    public DetallePedidoServicio(int factura, int id_servicio, double precioServicio, Servicio s) {
        this.factura = factura;
        this.id_servicio = id_servicio;
        this.precioServicio = precioServicio;
        this.s = s;
    }
    public String MostrarAuto(){
        GestorAutos ga = new GestorAutos();
        int id_auto = ga.ObtenerIdAutoPorIdFactura(id_factura);
        String resultado = ga.obtenerMarcaModeloPorIdAuto(id_auto);
        return resultado;
    }

    @Override
    public String toString() {
        Servicio z = new GestorServicios().obtenerServicio(id_servicio);
        return z.getServicio()+", Duración: "+z.getTiempo()+" min aprox.";
    }
 
}
