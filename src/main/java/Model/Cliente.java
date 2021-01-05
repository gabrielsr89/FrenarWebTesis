
package Model;

import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;

public class Cliente {
    //ATRIBUTOS
    private int id;
    public Persona p;
    public TipoCliente tc;
    private Date fechaIngreso;

    //GET AND SET

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    public TipoCliente getTc() {
        return tc;
    }

    public void setTc(TipoCliente tc) {
        this.tc = tc;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Persona getP() {
        return p;
    }
    public void setP(Persona p) {
        this.p = p;
    }
    //CONSTRUCTORES
    //TRAER
    public Cliente(int id, Persona p, TipoCliente tc, Date fechaIngreso) {
        this.id = id;
        this.p = p;
        this.tc = tc;
        this.fechaIngreso = fechaIngreso;
    }
    public Cliente(Persona p, TipoCliente tc){
        this.p = p;
        this.tc = tc;
    }
    //CREAR
    public Cliente(Persona p){
        this.p = p;
    }
    @Override
    public String toString() {
        return p.toString();
    }
     public String FechaIngreso() {
        String fecha ="";
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");        	
        fecha = formatter.format(fechaIngreso);     
        return fecha;
    }
    
    
    
}
