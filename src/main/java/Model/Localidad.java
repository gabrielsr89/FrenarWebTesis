package Model;

public class Localidad {

    private int id_localidad;
    private String localidad;
    private int id_provincia;

    //GET&SET
    public int getId_provincia() {
        return id_provincia;
    }

    public void setId_provincia(int id_provincia) {
        this.id_provincia = id_provincia;
    }

    public int getId_localidad() {
        return id_localidad;
    }

    public void setId_localidad(int id_localidad) {
        this.id_localidad = id_localidad;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    //CONSTRUCTORES
    //TRAER LOCALIDADES CON PROVINCIAS JS
    public Localidad(int id_localidad, String localidad, int id_provincia) {
        this.id_localidad = id_localidad;
        this.localidad = localidad;
        this.id_provincia = id_provincia;
    }
    //TRAER LOCALIDADES COMBO
     public Localidad(int id_localidad, String localidad) {
        this.id_localidad = id_localidad;
        this.localidad = localidad;        
    }
    //MOD PERSONA
    public Localidad(int id_localidad) {
        this.id_localidad = id_localidad;
    }
     

    //TOSTRING
    @Override
    public String toString() {
        return localidad;
    }
}
