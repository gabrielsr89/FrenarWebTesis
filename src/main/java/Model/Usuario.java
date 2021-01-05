package Model;

import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;

public class Usuario {

    //VARIABLES
    private int id_usuario;
    private TipoUsuario tu;
    public Persona p;
    private Date fechaIngreso;

    //GET AND SET
    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Persona getP() {
        return p;
    }

    public void setP(Persona p) {
        this.p = p;
    }

    public TipoUsuario getTu() {
        return tu;
    }

    public void setTu(TipoUsuario tu) {
        this.tu = tu;
    }

    //CONSTRUCTORES
    //ALTA
    public Usuario(int id_tipousuario, Persona p) {
        this.tu = new TipoUsuario(id_tipousuario);
        this.p = p;
    }

    //TRAER UN USUARIO
    public Usuario(int id, TipoUsuario tu, Persona p, Date fechaIngreso) {
        this.id_usuario = id;
        this.tu = tu;
        this.p = p;
        this.fechaIngreso = fechaIngreso;
    }
    
    //MÉTODOS

    @Override
    public String toString() {
        return tu.getUsuario() + ", " + p.toString() + " " + fechaIngreso;
    }

    public String FechaIngreso() {
        String fecha ="";
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");        	
        fecha = formatter.format(fechaIngreso);     
        return fecha;
    }
    

}
