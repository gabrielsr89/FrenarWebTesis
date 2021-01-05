
package Model;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Reposicion {
    private int id_reposicion;
    private Date fecha_reposicion;
    private int id_proveedor;
    private String nombreProveedor;
    private int id_usuario;
    private double importe;
    ArrayList<Detalle_reposicion> detalles_reposicion = new ArrayList<>();
    

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public ArrayList<Detalle_reposicion> getDetalles_reposicion() {
        return detalles_reposicion;
    }

    public void setDetalles_reposicion(ArrayList<Detalle_reposicion> detalles_reposicion) {
        this.detalles_reposicion = detalles_reposicion;
    }

    public int getId_reposicion() {
        return id_reposicion;
    }

    public void setId_reposicion(int id_reposicion) {
        this.id_reposicion = id_reposicion;
    }

    public Date getFecha_reposicion() {
        return fecha_reposicion;
    }

    public void setFecha_reposicion(Date fecha_reposicion) {
        this.fecha_reposicion = fecha_reposicion;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public double getImporte() {
        return importe;
    }
    
    public void setImporte(double importe) {
        this.importe = importe;
    }

    //Traer reposiciones
    public Reposicion(int id_reposicion, Date fecha_reposicion, int id_proveedor, String nombreProveedor, int id_usuario, double importe, ArrayList<Detalle_reposicion> detalles_reposicion) {
        this.id_reposicion = id_reposicion;
        this.fecha_reposicion = fecha_reposicion;
        this.id_proveedor = id_proveedor;
        this.nombreProveedor = nombreProveedor;
        this.id_usuario = id_usuario;
        this.importe = importe;
        this.detalles_reposicion = detalles_reposicion;
    }    
    //crear reposicion
    public Reposicion(int id_proveedor, int id_usuario, double importe, ArrayList<Detalle_reposicion> detalles_reposicion) {
        this.id_proveedor = id_proveedor;
        this.id_usuario = id_usuario;
        this.importe = importe;
        this.detalles_reposicion = detalles_reposicion;
    }    
    //traer repo
    
    public String Detalles(){
        String cadena = "";
        for (Detalle_reposicion d : detalles_reposicion) {
            cadena += "Repuesto: "+d.getRepuesto()+", cantidad: "+d.getCantidad()+", precio unitario: "+d.getPrecio_unitario()+".  //  ";
        }   
        return cadena;
    }
    
    public String FechaReposicion() {
        String fecha ="";
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");        	
        fecha = formatter.format(this.fecha_reposicion);     
        return fecha;
    }

    @Override
    public String toString() {
        String cadena = "";
        for (Detalle_reposicion d : detalles_reposicion) {
            cadena += d.toString()+"\n ";
        }
        return "Id_prov: "+ id_proveedor +"Id_usuario: "+ id_usuario +"importe: "+ importe +"Detalles: "+ cadena;
    }    
    
}
