
package Model;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class OrdenTrabajo {
    private int id_orden;
    private int id_detalle;
    private int id_estado;
    private String estado;
    private int id_usuario;
    Timestamp inicio;
    Timestamp fin;

    //GET & SET
    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Timestamp getInicio() {
        return inicio;
    }

    public void setInicio(Timestamp inicio) {
        this.inicio = inicio;
    }

    public Timestamp getFin() {
        return fin;
    }

    public void setFin(Timestamp fin) {
        this.fin = fin;
    }
    
    public OrdenTrabajo(int id_orden, int id_estado, String estado){
        this.id_orden = id_orden;
        this.id_estado = id_estado;
        this.estado = estado;        
    }
    
    public String FechaInicio(){
        String cadena = "";
        DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");         
        cadena = "Fecha: "+fecha.format(inicio);   
        return cadena;    
    }
    public String HoraInicio(){
        DateFormat hora = new SimpleDateFormat("HH:mm:ss"); 
        return "Hora: "+hora.format(inicio);
    }
    
    public String FechaFinEstimado(){
        DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");        
        return "Fecha: "+formato.format(fin);    
    }
   
    public String HoraFinEstimado(){
        DateFormat formato = new SimpleDateFormat("HH:mm:ss");        
        return "Hora: "+formato.format(fin);    
    }
    
    
    
    
}
