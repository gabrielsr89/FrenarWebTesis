package Model;

public class Barrio {

    //VARIABLES
    private int id_barrio;
    private String barrio;
    private int id_localidad;
    private boolean nuevo;
    
    //atributos generados para gestión de ubicacion
    private Localidad localidad;
    private Provincia provincia;

    //GET&SET    
    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }
    public boolean isNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    public int getId_localidad() {
        return id_localidad;
    }

    public void setId_localidad(int id_localidad) {
        this.id_localidad = id_localidad;
    }

    public int getId_barrio() {
        return id_barrio;
    }

    public void setId_barrio(int id_barrio) {
        this.id_barrio = id_barrio;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    //CONSTRUCTORES
    //TRAER BARRIOS COMBO 
    public Barrio(int id_barrio, String barrio) {
        this.id_barrio = id_barrio;
        this.barrio = barrio;
    }

    //PARA JS
    public Barrio(int id_barrio, String barrio, int id_l) {
        this.id_barrio = id_barrio;
        this.barrio = barrio;
        this.id_localidad = id_l;
    }

    //PARA USO EN CLASE PERSONA Y CREAR UNA PERSONA CON UN BARRIO
    public Barrio(int id_barrio) {
        this.id_barrio = id_barrio;
    }

    //PARA CREAR UN BARRIO NUEVO
    public Barrio(String nombreBarrio, int idLocalidad) {
        this.barrio = nombreBarrio;
        this.id_localidad = idLocalidad;
    }

    //PARA LISTAR BARRIOS NUEVOS
    public Barrio(int id, String nombreBarrio, Localidad localidad, Provincia provincia) {
        this.id_barrio = id;
        this.barrio = nombreBarrio;
        this.localidad = localidad;
        this.provincia = provincia;
        this.nuevo = nuevo;
    }

    @Override
    public String toString() {
        return barrio;
    }

}
