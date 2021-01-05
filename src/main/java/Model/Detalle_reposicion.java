
package Model;

import java.text.Format;
import java.text.SimpleDateFormat;

public class Detalle_reposicion {
    //Detalles_reposicion(id_detalle, id_repuesto, precio_unitario ,cantidad)*/
    private int id_detalle;
    private int id_repuesto;
    private String repuesto;
    private double precio_unitario;
    private int cantidad;
    private int id_reposicion;

    public int getId_reposicion() {
        return id_reposicion;
    }

    public void setId_reposicion(int id_reposicion) {
        this.id_reposicion = id_reposicion;
    }
    
    public String getRepuesto() {
        return repuesto;
    }

    public void setRepuesto(String repuesto) {
        this.repuesto = repuesto;
    }

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public int getId_repuesto() {
        return id_repuesto;
    }

    public void setId_repuesto(int id_repuesto) {
        this.id_repuesto = id_repuesto;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    //cargar
    public Detalle_reposicion(int id_detalle, int id_repuesto, double precio_unitario, int cantidad, String repuesto) {
        this.id_detalle = id_detalle;
        this.id_repuesto = id_repuesto;
        this.repuesto = repuesto;
        this.precio_unitario = precio_unitario;
        this.cantidad = cantidad;
    }
    //alta
    public Detalle_reposicion(int id_repuesto, double precio_unitario, int cantidad) {     
        this.id_repuesto = id_repuesto;
        this.precio_unitario = precio_unitario;
        this.cantidad = cantidad;
    }
    

    @Override
    public String toString() {
        return "Detalle_reposicion{" + "id_detalle=" + id_detalle + ", id_repuesto=" + id_repuesto + ", precio_unitario=" + precio_unitario + ", cantidad=" + cantidad + '}';
    }
    
    
}
