
package Model;

public class Proveedor {
    private int id;
    private String proveedor;
    private Persona p;
    private String pagina;
    
    //Para cargar las listas
    private int id_aux;
    private String descripcion_aux;

    public int getId_aux() {
        return id_aux;
    }

    public void setId_aux(int id_aux) {
        this.id_aux = id_aux;
    }

    public String getDescripcion_aux() {
        return descripcion_aux;
    }

    public void setDescripcion_aux(String descripcion_aux) {
        this.descripcion_aux = descripcion_aux;
    }
    
    
    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Persona getP() {
        return p;
    }

    public void setP(Persona p) {
        this.p = p;
    }

    //cargar listado
    public Proveedor(int id, String proveedor, Persona p, String pagina) {
        this.id = id;
        this.proveedor = proveedor;
        this.p = p;
        this.pagina = pagina;
    }
    //listados parametrizados
    public Proveedor(int id, String proveedor, Persona p, String pagina, int id_aux, String descripcion_aux) {
        this.id = id;
        this.proveedor = proveedor;
        this.p = p;
        this.pagina = pagina;
        this.id_aux = id_aux;
        this.descripcion_aux = descripcion_aux;
    }
    
    //alta prov
    public Proveedor(String proveedor, Persona p, String pagina) {
        this.proveedor = proveedor;
        this.p = p;
        this.pagina = pagina;
    }

    @Override
    public String toString() {
        return proveedor;
    }
    
    
    
    
}
