
package Model;

public class MarcaRepuesto {
    private int id_marcaRepuesto;
    private String marcaRepuesto;

    public int getId_marcaRepuesto() {
        return id_marcaRepuesto;
    }

    public void setId_marcaRepuesto(int id_marcaRepuesto) {
        this.id_marcaRepuesto = id_marcaRepuesto;
    }

    public String getMarcaRepuesto() {
        return marcaRepuesto;
    }

    public void setMarcaRepuesto(String marcaRepuesto) {
        this.marcaRepuesto = marcaRepuesto;
    }

    //CONSTRUCTOR
    //TRAER
    public MarcaRepuesto(int id_marcaRepuesto, String marcaRepuesto) {
        this.id_marcaRepuesto = id_marcaRepuesto;
        this.marcaRepuesto = marcaRepuesto;
    }
    
    //LISTAR REPUESTOS
    public MarcaRepuesto(String marcaRepuesto) {
        this.marcaRepuesto = marcaRepuesto;
    }
    //CREAR REPUESTO
    public MarcaRepuesto(int id_marcaRepuesto) {
        this.id_marcaRepuesto = id_marcaRepuesto;
    }
    //LISTAR REPUESTOS
    
    //TO STRING
    @Override
    public String toString() {
        return marcaRepuesto ;
    }
    
}
