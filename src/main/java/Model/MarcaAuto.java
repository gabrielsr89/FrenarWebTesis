
package Model;


public class MarcaAuto {
    private int id_marcaAuto;
    private String marcaAuto;

    public int getId_marcaAuto() {
        return id_marcaAuto;
    }

    public void setId_marcaAuto(int id_marcaAuto) {
        this.id_marcaAuto = id_marcaAuto;
    }

    public String getMarcaAuto() {
        return marcaAuto;
    }

    public void setMarcaAuto(String marcaAuto) {
        this.marcaAuto = marcaAuto;
    }

    public MarcaAuto(int id_marcaAuto, String marcaAuto) {
        this.id_marcaAuto = id_marcaAuto;
        this.marcaAuto = marcaAuto;
    }

    public MarcaAuto() {
    }

    @Override
    public String toString() {
        return  marcaAuto ;
    }

    public String toStringRevoluciones() {
        return  "{"+id_marcaAuto + ", " + marcaAuto +"}";
    }
    
}
