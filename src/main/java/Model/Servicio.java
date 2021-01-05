
package Model;

public class Servicio {
    private int id_servicio;
    private Categorias categoria;
    private String servicio;
    private String descripcion;
    private int tiempo;
    private double precio;
    private int id_categoria;

    //GET & SET

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }
    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    //CONSTRUCTOR 
    //TRAER LISTADO
    public Servicio(int id_servicio, Categorias categoria, String servicio, String descripcion, int tiempo, double precio) {
        this.id_servicio = id_servicio;
        this.categoria = categoria;
        this.servicio = servicio;
        this.descripcion = descripcion;
        this.tiempo = tiempo;
        this.precio = precio;        
    }
    //NUEVO SERVICIO
    public Servicio(String servicio, int tiempo, int id_categoria, String descripcion){
        this.servicio = servicio;
        this.tiempo = tiempo;
        this.id_categoria = id_categoria;
        this.descripcion = descripcion;
    }    
    //OBTENER UN SERVICIO
    public Servicio(int id_servicio, String servicio, int tiempo, double precio, int categoria, String descripcion) {
        this.id_servicio = id_servicio;
        this.servicio = servicio;
        this.tiempo = tiempo;
        this.precio = precio;
        this.id_categoria = categoria;
        this.descripcion = descripcion;
    }

    //TO STRING
    @Override
    public String toString() {
        return servicio+", demora (aproximada): "+tiempo+", precio (estimado): "+precio;
    }
    
}
