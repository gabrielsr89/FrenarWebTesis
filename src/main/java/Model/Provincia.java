package Model;

public class Provincia {

    private int id_provincia;
    private String provincia;
    //GET&SET
    public int getId_provincia() {
        return id_provincia;
    }
    public void setId_provincia(int id_provincia) {
        this.id_provincia = id_provincia;
    }
    public String getProvincia() {
        return provincia;
    }
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    //CONSTRUCTOR
    public Provincia(int id_provincia, String provincia) {
        this.id_provincia = id_provincia;
        this.provincia = provincia;
    }
    //MOD PERSONA
    public Provincia(int id_provincia) {
        this.id_provincia = id_provincia;
    }
    
    //TOSTRING
    @Override
    public String toString() {
        return provincia;
    }
}
