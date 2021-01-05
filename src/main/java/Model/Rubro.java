
package Model;

public class Rubro {
    private int id_rubro;
    private String rubro;

    //GET&SET
    public int getId() {
        return id_rubro;
    }

    public void setId(int id) {
        this.id_rubro = id;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    //CONSTRUCTORES
    //TRAER RUBROS
    public Rubro(int id, String rubro) {
        this.id_rubro = id;
        this.rubro = rubro;
    }
    //CREAR UN REPUESTO
    public Rubro(int id) {
        this.id_rubro = id;
    }
    //TRAER REPUESTOS
    public Rubro(String r){
        this.rubro = r;
    }

    @Override
    public String toString() {
        return rubro;
    }
    
}
