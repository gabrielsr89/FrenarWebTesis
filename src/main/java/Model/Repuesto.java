
package Model;

public class Repuesto {
    private int id_repuesto;
    private MarcaRepuesto mr;
    private Rubro r;
    private String repuesto;
    private String descripcion;
    private int stock;
    private double precio;
    private String foto;
    private int stock_min;
   
    //Get && Set
    public int getStock_min() {
        return stock_min;
    }

    public void setStock_min(int stock_min) {
        this.stock_min = stock_min;
    }
    
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public int getId_repuesto() {
        return id_repuesto;
    }
    public void setId_repuesto(int id_repuesto) {
        this.id_repuesto = id_repuesto;
    }
    public MarcaRepuesto getMr() {
        return mr;
    }
    public void setMr(MarcaRepuesto mr) {
        this.mr = mr;
    }
    public Rubro getR() {
        return r;
    }
    public void setR(Rubro r) {
        this.r = r;
    }
    public String getRepuesto() {
        return repuesto;
    }
    public void setRepuesto(String repuesto) {
        this.repuesto = repuesto;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }
    //listarstock
    public Repuesto(String repuesto, int stock){
        this.repuesto = repuesto;
        this.stock = stock;    
    }
    
    //CONSTRUCTOR
    //LISTAR
    public Repuesto(int id_repuesto, MarcaRepuesto mr, Rubro r, String repuesto, String descripcion, int stock, double precio, String foto) {
        this.id_repuesto = id_repuesto;
        this.mr = mr;
        this.r = r;
        this.repuesto = repuesto;
        this.descripcion = descripcion;
        this.stock = stock;
        this.precio = precio;
        this.foto = foto;
    }
    //NUEVO
    public Repuesto( String repuesto, MarcaRepuesto mr, Rubro r,  String descripcion, double precio, String foto, int stock_min) {
        this.mr = mr;
        this.r = r;
        this.repuesto = repuesto;
        this.descripcion = descripcion;        
        this.stock_min = stock_min;
        this.precio = precio;
        this.foto = foto;
    }
    //MODIFICAR
    public Repuesto( int id,String repuesto, MarcaRepuesto mr, Rubro r,  String descripcion, double precio, String foto, int stock_min) {
        this.id_repuesto = id;
        this.mr = mr;
        this.r = r;
        this.repuesto = repuesto;
        this.descripcion = descripcion;
        this.stock_min = stock_min;
        this.precio = precio;
        this.foto = foto;
    }

    //TO STRING
    @Override
    public String toString() {
        return repuesto ;
    }
    
    
    
    
}
