
package Model;


public class ModeloAuto {
    private int id_modeloAuto;
    private String modeloAuto;
    private int id_marcaAuto;
    private String marca;

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    //GET & SET
    public int getId_marcaAuto() {
        return id_marcaAuto;
    }

    public void setId_marcaAuto(int id_marcaAuto) {
        this.id_marcaAuto = id_marcaAuto;
    }

    public int getId_modeloAuto() {
        return id_modeloAuto;
    }

    public void setId_modeloAuto(int id_modeloAuto) {
        this.id_modeloAuto = id_modeloAuto;
    }

    public String getModeloAuto() {
        return modeloAuto;
    }

    public void setModeloAuto(String modeloAuto) {
        this.modeloAuto = modeloAuto;
    }

    //CONSTRUCTORES
    public ModeloAuto( String modeloAuto, int id_marca){
        this.id_marcaAuto = id_marca;
        this.modeloAuto = modeloAuto;
    }
    
    public ModeloAuto(int id_modeloAuto, String modeloAuto) {
        this.id_modeloAuto = id_modeloAuto;
        this.modeloAuto = modeloAuto;
    }
    
    public ModeloAuto(int id_modeloAuto, String modeloAuto,int id_marca) {
        this.id_modeloAuto = id_modeloAuto;
        this.modeloAuto = modeloAuto;
        this.id_marcaAuto = id_marca;
    }
    
    public ModeloAuto(int id_marca, String marca, int id_modeloAuto, String modeloAuto) {
        this.id_modeloAuto = id_modeloAuto;
        this.modeloAuto = modeloAuto;
        this.id_marcaAuto = id_marca;
        this.marca = marca;
    }

    @Override
    public String toString(){
        return modeloAuto;
    } 
    
    public String toStringRevoluciones() {        
        return "{"+ id_marcaAuto + ", " + marca + ", " + id_modeloAuto  + ", " +  modeloAuto+ '}';
    }
    
   
    
}
